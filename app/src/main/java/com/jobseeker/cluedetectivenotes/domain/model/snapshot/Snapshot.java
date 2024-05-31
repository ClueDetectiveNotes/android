package com.jobseeker.cluedetectivenotes.domain.model.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Snapshot {
    List<UUID> selectedCellIds;
    boolean isMultiMode = false;
    Map<UUID,String> cellsMainMarkers;
    int debugIndex;

    public Snapshot(int debugIndex){
        this.debugIndex = debugIndex;
        selectedCellIds = new ArrayList<>();
        cellsMainMarkers = new HashMap<>();

        Sheet sheet = GameSetter.getSheetInstance();
        for(Cell cell:sheet.getSelectedCells()){
            selectedCellIds.add(cell.getId());
        }
        isMultiMode = sheet.isMultiSelectionMode();

        for(UUID cellId:sheet.getCells().keySet()){
            Cell cell = Objects.requireNonNull(sheet.getCells().get(cellId));
            if(cell.isEmptyMainMarker()){
                cellsMainMarkers.put(cellId, null);
            }else{
                cellsMainMarkers.put(cellId, cell.getMarker().getNotation());
            }
        }
    }

    public boolean getIsMultiMode() {
        return isMultiMode;
    }

    public List<UUID> getSelectedCellIds() {
        return selectedCellIds;
    }

    public Map<UUID, String> getCellsMainMarkers() {
        return cellsMainMarkers;
    }

    public int getDebugIndex() {
        return debugIndex;
    }
}
