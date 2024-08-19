package com.jobseeker.cluedetectivenotes.domain.model.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Colname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Rowname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;

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
    Map<UUID, List<String>> cellsSubMarkerItems;
    List<String> selectedRownames;// = sheet.getSelectedRownameCells();
    List<String> selectedColnames;//= sheet.getSelectedColnameCells();
    int debugIndex;

    public Snapshot(int debugIndex) throws CellNotFindException {
        this.debugIndex = debugIndex;
        selectedCellIds = new ArrayList<>();
        cellsMainMarkers = new HashMap<>();
        cellsSubMarkerItems = new HashMap<>();
        selectedRownames = new ArrayList<>();
        selectedColnames = new ArrayList<>();

        Sheet sheet = GameSetter.getSheetInstance();
        for(Cell cell:sheet.getSelectedCells()){
            selectedCellIds.add(cell.getId());
        }
        isMultiMode = sheet.isMultiSelectionMode();
        for(Rowname rowname:sheet.getSelectedRownames()){
            selectedRownames.add(rowname.getCard().name());
        }
        for(Colname colname:sheet.getSelectedColname()){
            selectedColnames.add(colname.getName());
        }

        for(UUID cellId:sheet.getCells().keySet()){
            Cell cell = Objects.requireNonNull(sheet.getCells().get(cellId));
            if(cell.isEmptyMainMarker()){
                cellsMainMarkers.put(cellId, null);
            }else{
                cellsMainMarkers.put(cellId, cell.getMarker().getNotation());
            }
            cellsSubMarkerItems.put(cellId, cell.getSubMarkerItems());
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

    public List<String> getSelectedRownames() { return selectedRownames; }

    public List<String> getSelectedColnames() { return selectedColnames; }

    public Map<UUID, List<String>> getCellsSubMarkerItems(){ return cellsSubMarkerItems; }
}
