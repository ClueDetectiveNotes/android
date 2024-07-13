package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.CancelClickedCellUseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.SelectionMode;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CancelClickedCellUseCaseTest {

    Sheet sheet = null;
    CancelClickedCellUseCase<JSONObject> cancelClickedCellUseCase = null;

    @Before
    public void create(){
        sheet = GameSetter.getSheetInstance();
        cancelClickedCellUseCase = new CancelClickedCellUseCase<>();
    }

    //싱글모드에서 취소하면 선택된 셀이 취소 된다.
    @Test
    public void whenCancelInDefaultModeThenASelectedCellBecomesUnselected() throws Exception {
        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());
        sheet.selectCell(cellIds.get(0));

        JSONObject sheetState = cancelClickedCellUseCase.execute();
        List<UUID> selectedCellIds = (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(selectedCellIds.isEmpty());
    }

    //멀티모드에서 취소하면 선택된 모든 셀이 취소되며 싱글모드로 바뀐다.
    @Test
    public void whenCancelInMultiModeThenSelectedCellsBecomeUnselectedAndSelectionModeSwitchDefaultMode() throws Exception {
        List<UUID> cellIds = new ArrayList<>(sheet.getCells().keySet());
        sheet.switchSelectionMode();
        sheet.selectCell(cellIds.get(0));
        sheet.selectCell(cellIds.get(1));

        JSONObject sheetState = cancelClickedCellUseCase.execute();
        boolean isMultiSelectionMode = (boolean) sheetState.get("isMultiSelectionMode");
        List<UUID> selectedCellIds = (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(selectedCellIds.isEmpty());
        assertFalse(isMultiSelectionMode);
    }

    //추리모드에서 취소하면 선택된 모든 셀이 취소되고, 하이라트된 로우, 컬럼이 취소되며 싱글모드로 바뀐다
    @Test
    public void whenCancelInInferenceModeThenSelectedCellsBecomeUnselectedAndSelectionModeSwitchDefaultModeAndClearHighlightRowsAndColumns() throws Exception {
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        sheet.selectRowname(sheet.getRownames().get(12));
        sheet.selectColname(sheet.getColnames().get(0));

        List<Cell> selectedCells = sheet.getSelectedCells();
        assertEquals(3, selectedCells.size());
        assertTrue(sheet.isEqualSelectionMode(SelectionMode.INFERENCE));

        JSONObject sheetState = cancelClickedCellUseCase.execute();
        boolean isMultiSelectionMode = (boolean) sheetState.get("isMultiSelectionMode");
        List<UUID> selectedCellIds = (List<UUID>) sheetState.get("selectedCellsIdList");

        assertTrue(selectedCellIds.isEmpty());
        assertFalse(isMultiSelectionMode);
    }
}
