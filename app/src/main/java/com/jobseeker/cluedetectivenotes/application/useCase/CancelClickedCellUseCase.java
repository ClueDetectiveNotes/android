package com.jobseeker.cluedetectivenotes.application.useCase;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CancelClickedCellUseCase {
    private Sheet sheet = GameSetter.getSheetInstance();
    public JSONObject execute() throws JSONException {
        if(sheet.isMultiSelectionMode()) sheet.switchSelectionMode();
        sheet.unselectCell();
        return createState(sheet.isMultiSelectionMode(), sheet.getSelectedCells());
    }

    private JSONObject createState(Boolean isMultiSelectionMode, List<Cell> selectedCells) throws JSONException {
        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("selectedCellsIdList",selectedCellsIdList );

        return sheetState;
    }
}
