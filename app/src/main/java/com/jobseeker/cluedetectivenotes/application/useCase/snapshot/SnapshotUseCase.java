package com.jobseeker.cluedetectivenotes.application.useCase.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.SelectionMode;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
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
        List<String> selectedRownames = snapshot.getSelectedRownames();
        List<String> selectedColnames = snapshot.getSelectedColnames();

        //sheet 초기화
        if(sheet.isEqualSelectionMode(SelectionMode.PRE_INFERENCE) || sheet.isEqualSelectionMode(SelectionMode.INFERENCE)){
            sheet.unselectCellDoNotCareInferenceMode();
            sheet.setDefaultSelectionMode();
        }else{
            sheet.unselectCell();
            if(sheet.isMultiSelectionMode() != isMultiMode){
                sheet.switchSelectionMode();
            }
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

        //rowname 초기화
        sheet.unselectRownameWithoutSwitch();
        for(String rowname:selectedRownames){
            sheet.selectRowname(rowname);
        }
        //colname 초기화
        sheet.unselectColnameWithoutSwitch();
        for(String colname:selectedColnames){
            sheet.selectColname(colname);
        }

        return createState();
    }

    protected JSONObject createState() throws JSONException, CellNotFindException {
        Boolean isMultiSelectionMode = sheet.isMultiSelectionMode();
        List<Cell> selectedCells = sheet.getSelectedCells();
        Map<UUID, Cell> cells = sheet.getCells();
        List<Cell> selectedRownameCells = sheet.getSelectedRownameCells();
        List<Cell> selectedColnameCells = sheet.getSelectedColnameCells();

        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        List<UUID> selectedRownameCellsIdList = new ArrayList<>();
        for(Cell cell:selectedRownameCells){
            selectedRownameCellsIdList.add(cell.getId());
        }

        List<UUID> selectedColnameCellsIdList = new ArrayList<>();
        for(Cell cell:selectedColnameCells){
            selectedColnameCellsIdList.add(cell.getId());
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
        sheetState.put("selectedRownameCellsIdList",selectedRownameCellsIdList );
        sheetState.put("selectedColnameCellsIdList",selectedColnameCellsIdList );
        sheetState.put("cells", cellsArr);

        return sheetState;
    }
}
