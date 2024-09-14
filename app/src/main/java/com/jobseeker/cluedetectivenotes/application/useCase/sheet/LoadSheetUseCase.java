package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.SelectionMode;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadSheetUseCase {
    private Sheet sheet = GameSetter.getSheetInstance();

    public JSONObject execute() throws JSONException {
        return createState();
    }

    private JSONObject createState() throws JSONException {
        List<Cell> selectedCells = sheet.getSelectedCells();
        Boolean isMultiSelectionMode = sheet.isMultiSelectionMode();
        Boolean isInferenceMode = sheet.isEqualSelectionMode(SelectionMode.INFERENCE);

        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("isInferenceMode", isInferenceMode);
        sheetState.put("selectedCellsIdList",selectedCellsIdList );

        return sheetState;
    }
}
