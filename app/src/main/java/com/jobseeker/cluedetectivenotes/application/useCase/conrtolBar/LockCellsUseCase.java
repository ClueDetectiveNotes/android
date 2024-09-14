package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.SnapshotManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LockCellsUseCase {
    private final Sheet sheet;
    private final SnapshotManager snapshotManager;

    public LockCellsUseCase() throws CellNotFindException {
        sheet = GameSetter.getSheetInstance();
        snapshotManager = SnapshotManager.getInstance();
    }

    public JSONObject execute() throws JSONException {
        JSONObject sheetState = new JSONObject();

        sheet.lockCells();
        snapshotManager.lockSnapshot();

        sheetState.put("isCellsLocked", sheet.isCellsLocked());
        return sheetState;
    }
}
