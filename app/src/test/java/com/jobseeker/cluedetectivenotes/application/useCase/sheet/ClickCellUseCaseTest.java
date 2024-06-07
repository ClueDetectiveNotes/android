package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotUnselectNeverChosenCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClickCellUseCaseTest {
    Sheet sheet = null;
    ClickCellUseCase<JSONObject> clickCellUseCase = null;

    @Before
    public void create(){
        sheet = GameSetter.getSheetInstance();
        clickCellUseCase = new ClickCellUseCase<JSONObject>();
    }

    //싱글모드에서 이미 선택된 셀을 클릭하면 해당 셀의 선택이 해제된다
    @Test
    public void clickSelectedCellInSingleModeToUnselect() throws InferenceModeException, CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException, JSONException {
        sheet.setDefaultSelectionMode();
        Cell cell = sheet.getCells().get(new ArrayList<>(sheet.getCells().keySet()).get(0));
        assert cell != null;
        sheet.selectCell(cell.getId());

        assertFalse(sheet.getSelectedCells().isEmpty());

        JSONObject sheetState = clickCellUseCase.execute(cell.getId());
        List<UUID> selectedCellIds =  (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(selectedCellIds.isEmpty());
    }

    //싱글모드에서 기존에 선택되지 않은 셀을 클릭하면 해당 셀이 선택된다
    @Test
    public void clickUnselectedCellInSingleModeToSelect() throws InferenceModeException, CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException, JSONException {
        sheet.setDefaultSelectionMode();
        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());
        sheet.selectCell(cellIds.get(0));

        JSONObject sheetState = clickCellUseCase.execute(cellIds.get(1));
        List<UUID> selectedCellIds =  (List<UUID>) sheetState.get("selectedCellsIdList");

        assertEquals(1, selectedCellIds.size());
        assertNotEquals(cellIds.get(0), selectedCellIds.get(0));
        assertEquals(cellIds.get(1), selectedCellIds.get(0));
    }

    //멀티모드에서 클릭한 셀이 이미 선택되어있고 클릭한 셀 외에 선택한 셀이 있으면 해당 셀이 선택 해제되고, 멀티모드가 유지된다.
    @Test
    public void clickSelectedCellWithOtherCellsSelectedInMultiModeToUnselectAndRetainMultiMode() throws InferenceModeException, CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException, JSONException {
        sheet.setDefaultSelectionMode();
        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());
        sheet.switchSelectionMode();
        sheet.selectCell(cellIds.get(0));
        sheet.selectCell(cellIds.get(1));

        JSONObject sheetState = clickCellUseCase.execute(cellIds.get(1));
        List<UUID> selectedCellIds =  (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(sheet.isMultiSelectionMode());
        assertTrue(selectedCellIds.contains(cellIds.get(0)));
        assertFalse(selectedCellIds.contains(cellIds.get(1)));
    }

    //멀티모드에서 클릭한 셀이 기존에 선택되지 않은 셀이면 해당 셀이 선택된다.
    @Test
    public void clickUnselectedCellInMultiModeToSelect() throws InferenceModeException, CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException, JSONException {
        sheet.setDefaultSelectionMode();
        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());
        sheet.switchSelectionMode();
        sheet.selectCell(cellIds.get(0));
        sheet.selectCell(cellIds.get(1));

        JSONObject sheetState = clickCellUseCase.execute(cellIds.get(2));
        List<UUID> selectedCellIds =  (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(sheet.isMultiSelectionMode());
        assertTrue(selectedCellIds.contains(cellIds.get(2)));
    }
}
