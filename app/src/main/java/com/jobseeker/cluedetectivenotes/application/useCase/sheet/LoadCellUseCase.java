package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LoadCellUseCase {
    public JSONArray execute() throws JSONException {
        JSONArray cellsArr = new JSONArray();
        Sheet sheet = GameSetter.getSheetInstance();
        Map<UUID,Cell> cells = sheet.getCells();
        for(UUID id : cells.keySet()){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", id);
            cellObj.put("mainMarker", Objects.requireNonNull(cells.get(id)).getMarker().getNotation());
            cellObj.put("isInit", Objects.requireNonNull(cells.get(id)).isInit());
            cellsArr.put(cellObj);
        }
        return cellsArr;
    }
}
