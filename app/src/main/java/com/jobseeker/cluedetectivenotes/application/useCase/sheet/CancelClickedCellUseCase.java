package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CancelClickedCellUseCase<V> extends UseCase<V> {
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws JSONException, InferenceModeException {
        sheet.setDefaultSelectionMode();
        return (V) createState(sheet.isMultiSelectionMode(), sheet.getSelectedCells());
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
