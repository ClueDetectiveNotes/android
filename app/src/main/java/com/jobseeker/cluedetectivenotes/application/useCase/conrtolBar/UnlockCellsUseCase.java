package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.SnapshotManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UnlockCellsUseCase {
    private final Sheet sheet;
    private final SnapshotManager snapshotManager;

    public UnlockCellsUseCase() throws CellNotFindException {
        sheet = GameSetter.getSheetInstance();
        snapshotManager = SnapshotManager.getInstance();
    }

    public JSONObject execute() throws JSONException {
        JSONObject sheetState = new JSONObject();
        JSONArray cellsArr = new JSONArray();

        sheet.unlockCells();
        snapshotManager.unlockSnapshot();

        Map<UUID, Cell> cells = sheet.getCells();
        for(UUID id : cells.keySet()){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", id);
            cellObj.put("isLock", Objects.requireNonNull(cells.get(id)).isLocked());
            cellsArr.put(cellObj);
        }

        sheetState.put("isCellsLocked", sheet.isCellsLocked());
        sheetState.put("lockedCells", cellsArr);
        return sheetState;
    }
}
