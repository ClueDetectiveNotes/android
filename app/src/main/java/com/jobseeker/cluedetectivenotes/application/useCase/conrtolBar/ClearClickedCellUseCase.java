package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClearClickedCellUseCase<V> extends UseCase<V> {
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws JSONException, InferenceModeException {
        sheet.getSelectedCells().forEach(Cell::removeMainMarker);
        return (V) createState(sheet.isMultiSelectionMode(), sheet.getSelectedCells());
    }

    private JSONObject createState(Boolean isMultiSelectionMode, List<Cell> selectedCells) throws JSONException {
        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }
        JSONArray cellsArr = new JSONArray();
        for(Cell cell : selectedCells){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", cell.getId());
            cellObj.put("mainMarker", cell.getMarker().getNotation());
            cellsArr.put(cellObj);
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("selectedCellsIdList",selectedCellsIdList );
        sheetState.put("cells", cellsArr);

        return sheetState;
    }
}
