package com.jobseeker.cluedetectivenotes.application.useCase.cell;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseCrossMarkerUseCase<V> extends ChooseMainMarkerUseCase<V>{
    public ChooseCrossMarkerUseCase() {
        super(Markers.CROSS);
    }
}
