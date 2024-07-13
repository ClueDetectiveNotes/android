package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Colname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.SelectionMode;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ChooseMainMarkerUseCase<V> extends UseCase<V> {
    private final Markers mainMarker;
    private final Sheet sheet;
    public ChooseMainMarkerUseCase(Markers mainMarker){
        this.mainMarker = mainMarker;
        this.sheet = GameSetter.getSheetInstance();
    }
    @Override
    public <T> V execute(T param) throws JSONException, MarkerMismatchException, CellNotFindException {

        if(sheet.isMultiSelectionMode()){
            if(sheet.isEveryCellMarked() && sheet.isSameMarkerInEveryCell(mainMarker)){
                sheet.getSelectedCells().forEach(Cell::removeMainMarker);
            }else{
                sheet.getSelectedCells().forEach(cell->{
                    try {
                        cell.setMainMarker(mainMarker);
                    } catch (MarkerMismatchException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }else if(sheet.isEqualSelectionMode(SelectionMode.INFERENCE)){
            sheet.getSelectedCells().forEach(cell->{
                try {
                    cell.setMainMarker(mainMarker);
                } catch (MarkerMismatchException e) {
                    throw new RuntimeException(e);
                }
            });
            List<Cell> selectedCells = sheet.getSelectedCells();
            sheet.selectNextColname();
            return (V) createState(selectedCells);
        }else{
            Cell cell = sheet.getSelectedCells().get(0);
            if(cell.equalsMainMarker(mainMarker)){
                cell.removeMainMarker();
            }else{
                cell.setMainMarker(mainMarker);
            }
        }
        return (V) createState(sheet.getSelectedCells());
    }
    private JSONObject createState(List<Cell> selectedCells) throws JSONException, CellNotFindException {
        JSONArray cellsArr = new JSONArray();

        for(Cell cell : selectedCells){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", cell.getId());
            cellObj.put("mainMarker", cell.getMarker().getNotation());
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
