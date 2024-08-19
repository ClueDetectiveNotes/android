package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.AlreadyContainsSubMarkerItemException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChooseSubMarkerUseCase <V> extends UseCase<V>  {
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws Exception {
        if(sheet.isMultiSelectionMode()){
            if(sheet.isSameSubMarkerInEveryCell((String)param)){
                for(Cell cell : sheet.getSelectedCells()){
                    cell.removeSubMarkerItem((String)param);
                }
            }else{
                for(Cell cell : sheet.getSelectedCells()){
                    try{
                        cell.setSubMarkerItem((String)param);
                    }catch (AlreadyContainsSubMarkerItemException ignored){
                    }
                }
            }
        }else{
            for(Cell cell : sheet.getSelectedCells()){
                try{
                    cell.setSubMarkerItem((String)param);
                }catch (AlreadyContainsSubMarkerItemException e){
                    cell.removeSubMarkerItem((String)param);
                }
            }
        }
        return (V)createState(sheet.getSelectedCells());
    }
    private JSONObject createState(List<Cell> selectedCells) throws JSONException, CellNotFindException {
        JSONArray cellsArr = new JSONArray();

        for(Cell cell : selectedCells){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", cell.getId());
            cellObj.put("mainMarker", cell.getMarker().getNotation());
            cellObj.put("subMarkerItems", cell.getSubMarkerItems());
            cellsArr.put(cellObj);
        }
        JSONObject sheetState = new JSONObject();

        List<Cell> selectedRownameCells = sheet.getSelectedRownameCells();
        List<Cell> selectedColnameCells = sheet.getSelectedColnameCells();

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

        sheetState.put("selectedCellsIdList",selectedCellsIdList );
        sheetState.put("selectedRownameCellsIdList",selectedRownameCellsIdList );
        sheetState.put("selectedColnameCellsIdList",selectedColnameCellsIdList );
        sheetState.put("cells", cellsArr);
        return sheetState;
    }
}
