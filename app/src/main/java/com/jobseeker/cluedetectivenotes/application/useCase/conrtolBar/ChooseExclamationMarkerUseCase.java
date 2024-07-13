package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseExclamationMarkerUseCase<V> extends ChooseMainMarkerUseCase<V>{
    public ChooseExclamationMarkerUseCase() {
        super(Markers.EXCLAMATION);
    }
}
