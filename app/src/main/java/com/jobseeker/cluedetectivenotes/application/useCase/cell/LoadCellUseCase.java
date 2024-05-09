package com.jobseeker.cluedetectivenotes.application.useCase.cell;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoadCellUseCase {
    public JSONArray execute() throws JSONException {
        JSONArray cellsArr = new JSONArray();
        //Cell cell = GameSetter.getCellInstance(id);
        Sheet sheet = GameSetter.getSheetInstance();
        Map<UUID,Cell> cells = sheet.getCells();
        for(UUID id : cells.keySet()){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", id);
            cellObj.put("mainMarker", cells.get(id).getMarker().getNotation());
            cellsArr.put(cellObj);
        }
        return cellsArr;
    }
}
