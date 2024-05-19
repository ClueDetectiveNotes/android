package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotUnselectNeverChosenCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClickCellUseCase<V> extends UseCase<V> {
    private final Sheet sheet = GameSetter.getSheetInstance();

    @Override
    public <T> V execute(T cellId) throws JSONException, CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException {
        if(sheet.isMultiSelectionMode()){
            if(sheet.isSelectedCell((UUID)cellId)){
                return (V) createState(sheet.multiUnselectCell((UUID)cellId));
            }else{
                return (V) createState(sheet.selectCell((UUID)cellId));
            }
        }else{
            if(sheet.isSelectedCell((UUID)cellId)){
                sheet.unselectCell();
                return (V) createState(sheet.getSelectedCells());
            }else{
                sheet.unselectCell();
                return (V) createState(sheet.selectCell((UUID)cellId));
            }
        }
    }

    private JSONObject createState(List<Cell> selectedCells) throws JSONException {
        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("selectedCellsIdList",selectedCellsIdList );

        return sheetState;
    }
}
