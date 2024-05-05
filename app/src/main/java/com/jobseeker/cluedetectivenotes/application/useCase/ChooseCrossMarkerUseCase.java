package com.jobseeker.cluedetectivenotes.application.useCase;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class ChooseCrossMarkerUseCase {
    public JSONObject execute(UUID id) throws JSONException, MarkerMismatchException {
        Cell cell = GameSetter.getCellInstance(id);

        if(cell.equalsMainMarker(Markers.CROSS)){
            cell.removeMainMarker();
        }else{
            cell.setMainMarker(Markers.CROSS);
        }
        return createState(cell);
    }
    private JSONObject createState(Cell cell) throws JSONException {
        JSONObject state = new JSONObject();
        state.put("marker", cell.getMarker().getNotation());
        return state;
    }
}
