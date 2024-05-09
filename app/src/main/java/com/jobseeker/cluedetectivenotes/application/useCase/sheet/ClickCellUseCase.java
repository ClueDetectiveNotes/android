package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

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

public class ClickCellUseCase {
    private final Sheet sheet = GameSetter.getSheetInstance();

    public JSONObject execute(UUID cellId) throws JSONException, CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException {
        if(sheet.isMultiSelectionMode()){
            if(sheet.isSelectedCell(cellId)){
                return createState(sheet.multiUnselectCell(cellId));
            }else{
                return createState(sheet.selectCell(cellId));
            }
        }else{
            if(sheet.isSelectedCell(cellId)){
                sheet.unselectCell();
                return createState(sheet.getSelectedCells());
            }else{
                sheet.unselectCell();
                return createState(sheet.selectCell(cellId));
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
