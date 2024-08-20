package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.NotSubMarkerException;

import org.json.JSONException;
import org.json.JSONObject;

public class LoadSubMarkerUseCase {
    public JSONObject execute() throws NotSubMarkerException, JSONException {
        JSONObject controlBarState = new JSONObject();

        controlBarState.put("subMarkerItems",Markers.SUB_MARKER.getSubMarkerItems());
        controlBarState.put("addedSubMarkerItems", Markers.SUB_MARKER.getAddedSubMarkerItems());

        return controlBarState;
    }
}
