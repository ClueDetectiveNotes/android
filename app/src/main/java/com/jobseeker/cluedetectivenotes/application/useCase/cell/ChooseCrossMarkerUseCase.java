package com.jobseeker.cluedetectivenotes.application.useCase.cell;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseCrossMarkerUseCase extends ChooseMainMarkerUseCase{
    public ChooseCrossMarkerUseCase() {
        super(Markers.CROSS);
    }
}
