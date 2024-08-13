package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
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

public class LongClickCellUseCaseTest {

    Sheet sheet = null;
    LongClickCellUseCase<JSONObject> longClickCellUseCase = null;

    @Before
    public void create() throws MarkerMismatchException {
        sheet = GameSetter.getSheetInstance();
        longClickCellUseCase = new LongClickCellUseCase<>();
    }

    //싱글모드에서 셀을 길게 누르면 셀이 선택되고 멀티모드로 변경된다.
    @Test
    public void longPressCellInSingleModeToSelectAndSwitchToMultiMode() throws InferenceModeException, CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, JSONException {
        sheet.setDefaultSelectionMode();
        assertFalse(sheet.isMultiSelectionMode());

        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());

        JSONObject sheetState = longClickCellUseCase.execute(cellIds.get(0));
        boolean isMultiSelectionMode = (boolean) sheetState.get("isMultiSelectionMode");
        List<UUID> selectedCellIds = (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(isMultiSelectionMode);
        assertTrue(selectedCellIds.contains(cellIds.get(0)));
    }

    //멀티모드에서 기존에 선택되지 않은 셀을 길게 누르면 해당 셀이 선택된다.
    @Test
    public void inMultiModeWhenLongPressOnACellThatIsNotSelectedThenTheCellIsSelected() throws InferenceModeException, CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, JSONException {
        sheet.setDefaultSelectionMode();
        assertFalse(sheet.isMultiSelectionMode());

        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());

        sheet.selectCell(cellIds.get(0));
        sheet.switchSelectionMode();

        JSONObject sheetState = longClickCellUseCase.execute(cellIds.get(1));
        boolean isMultiSelectionMode = (boolean) sheetState.get("isMultiSelectionMode");
        List<UUID> selectedCellIds = (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(isMultiSelectionMode);
        assertTrue(selectedCellIds.contains(cellIds.get(1)));
    }
}
