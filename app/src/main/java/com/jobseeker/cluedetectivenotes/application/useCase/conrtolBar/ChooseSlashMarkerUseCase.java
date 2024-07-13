package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

public class ChooseSlashMarkerUseCase<V> extends ChooseMainMarkerUseCase<V>{
    public ChooseSlashMarkerUseCase() {
        super(Markers.SLASH);
    }
}
