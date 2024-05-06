package com.jobseeker.cluedetectivenotes.application.useCase.markerControlBar;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import org.json.JSONException;

public class CancelClickedCellUseCase {
    private final Sheet sheet = GameSetter.getSheetInstance();
    public void execute() throws JSONException {
        if(sheet.isMultiSelectionMode()) sheet.switchSelectionMode();
        sheet.unselectCell();
        sheet.notifyObserver();
    }
}
