package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseQuestionMarkerUseCase<V> extends ChooseMainMarkerUseCase<V>{
    public ChooseQuestionMarkerUseCase() {
        super(Markers.QUESTION);
    }
}
