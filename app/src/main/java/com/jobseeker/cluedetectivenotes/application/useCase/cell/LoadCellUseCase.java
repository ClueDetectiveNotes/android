package com.jobseeker.cluedetectivenotes.application.useCase.cell;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.observer.ICellObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class LoadCellUseCase {
    Cell cell;
    public LoadCellUseCase(UUID id, ICellObserver observer){
        cell = GameSetter.getCellInstance(id);
        cell.registerObserver(observer);
    }
    public JSONObject execute() throws JSONException {

        JSONObject cellObj = new JSONObject();
        cellObj.put("marker", cell.getMarker().getNotation());

        return cellObj;
    }
    public void removeObserver(ICellObserver observer){
        cell.removeObserver(observer);
    }
}
