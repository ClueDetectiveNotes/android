package com.jobseeker.cluedetectivenotes.application.useCase;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class LoadCellUseCase {
    public JSONObject execute(UUID id) throws JSONException {
        Cell cell = GameSetter.getCellInstance(id);

        JSONObject cellObj = new JSONObject();
        cellObj.put("marker", cell.getMarker().getNotation());

        return cellObj;
    }
}
