package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.AlreadyContainsSubMarkerItemException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.NotSubMarkerException;

public class AddSubMarkerUseCase {
    public void execute(String text) throws AlreadyContainsSubMarkerItemException, NotSubMarkerException {
        Markers.SUB_MARKER.addSubMarkerItem(text);
    }
}
