package com.jobseeker.cluedetectivenotes.application.useCase.markerControlBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseQuestionMarkerUseCase extends ChooseMainMarkerUseCase {
    public ChooseQuestionMarkerUseCase() {
        super(Markers.QUESTION);
    }
}
