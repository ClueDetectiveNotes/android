package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseCheckMarkerUseCase<V> extends ChooseMainMarkerUseCase<V>{
    public ChooseCheckMarkerUseCase() {
        super(Markers.CHECK);
    }
}
