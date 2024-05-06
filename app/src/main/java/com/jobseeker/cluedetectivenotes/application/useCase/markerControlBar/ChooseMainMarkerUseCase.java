package com.jobseeker.cluedetectivenotes.application.useCase.markerControlBar;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public abstract class ChooseMainMarkerUseCase {
    private Markers mainMarker;
    public ChooseMainMarkerUseCase(Markers mainMarker){
        this.mainMarker = mainMarker;
    }

    public void execute() throws JSONException, MarkerMismatchException, CellNotFindException {
        Sheet sheet = GameSetter.getSheetInstance();

        if(sheet.isMultiSelectionMode()){
            if(sheet.isEveryCellMarked() && sheet.isSameMarkerInEveryCell(mainMarker)){
                sheet.getSelectedCells().forEach(cell->{
                        cell.removeMainMarker();
                        cell.notifyObserver();
                    }
                );
            }else{
                sheet.getSelectedCells().forEach(cell->{
                    try {
                        cell.setMainMarker(mainMarker);
                    } catch (MarkerMismatchException e) {
                        throw new RuntimeException(e);
                    }
                    cell.notifyObserver();
                });
            }
        }else{
            Cell cell = sheet.getSelectedCells().get(0);
            if(cell.equalsMainMarker(mainMarker)){
                cell.removeMainMarker();
            }else{
                cell.setMainMarker(mainMarker);
            }
            cell.notifyObserver();
        }
    }
}
