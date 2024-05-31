package com.jobseeker.cluedetectivenotes.application.useCase.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.Snapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class SnapshotUseCase {
    private Snapshot snapshot;
    Sheet sheet = GameSetter.getSheetInstance();

    protected void  setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    public JSONObject execute() throws Exception {
        boolean isMultiMode = snapshot.getIsMultiMode();
        List<UUID> selectedCellIds = snapshot.getSelectedCellIds();
        Map<UUID, String> cellsMainMarkers = snapshot.getCellsMainMarkers();

        //sheet 초기화
        sheet.unselectCell();

        if(sheet.isMultiSelectionMode() != isMultiMode){
            sheet.switchSelectionMode();
        }

        if(sheet.isMultiSelectionMode()){
            for(UUID cellId : selectedCellIds){
                sheet.multiSelectCell(cellId);
            }
        }else{
            for(UUID cellId : selectedCellIds){
                sheet.selectCell(cellId);
            }
        }

        for(UUID cellId : sheet.getCells().keySet()){
            Cell cell = sheet.getCells().get(cellId);
            assert cell != null;
            //cell 초기화
            cell.removeMainMarker();
            if(cellsMainMarkers.get(cellId) != null){
                cell.setMainMarker(Markers.findMainMarker(cellsMainMarkers.get(cellId)));
            }
        }

        return createState();
    }

    protected JSONObject createState() throws JSONException {
        Boolean isMultiSelectionMode = sheet.isMultiSelectionMode();
        List<Cell> selectedCells = sheet.getSelectedCells();
        Map<UUID, Cell> cells = sheet.getCells();

        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        JSONArray cellsArr = new JSONArray();

        for(UUID cellId : cells.keySet()){
            Cell cell = cells.get(cellId);
            JSONObject cellObj = new JSONObject();
            assert cell != null;
            cellObj.put("id", cell.getId());
            cellObj.put("mainMarker", cell.getMarker().getNotation());
            cellsArr.put(cellObj);
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("selectedCellsIdList",selectedCellsIdList );
        sheetState.put("cells", cellsArr);

        return sheetState;
    }
}
