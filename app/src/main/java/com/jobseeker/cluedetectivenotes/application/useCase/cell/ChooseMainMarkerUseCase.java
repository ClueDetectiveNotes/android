package com.jobseeker.cluedetectivenotes.application.useCase.cell;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class ChooseMainMarkerUseCase {
    private Markers mainMarker;
    public ChooseMainMarkerUseCase(Markers mainMarker){
        this.mainMarker = mainMarker;
    }
    public JSONArray execute() throws JSONException, MarkerMismatchException {
        Sheet sheet = GameSetter.getSheetInstance();

        if(sheet.isMultiSelectionMode()){
            if(sheet.isEveryCellMarked() && sheet.isSameMarkerInEveryCell(mainMarker)){
                sheet.getSelectedCells().forEach(cell->{
                            cell.removeMainMarker();
                        }
                );
            }else{
                sheet.getSelectedCells().forEach(cell->{
                    try {
                        cell.setMainMarker(mainMarker);
                    } catch (MarkerMismatchException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }else{
            Cell cell = sheet.getSelectedCells().get(0);
            if(cell.equalsMainMarker(mainMarker)){
                cell.removeMainMarker();
            }else{
                cell.setMainMarker(mainMarker);
            }
        }
        return createState(sheet.getSelectedCells());
    }
    private JSONArray createState(List<Cell> selectedCells) throws JSONException {
        JSONArray cellsArr = new JSONArray();

        for(Cell cell : selectedCells){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", cell.getId());
            cellObj.put("mainMarker", cell.getMarker().getNotation());
            cellsArr.put(cellObj);
        }
        return cellsArr;
    }
}
