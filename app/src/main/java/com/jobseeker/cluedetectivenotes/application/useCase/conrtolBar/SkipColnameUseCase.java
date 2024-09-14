package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SkipColnameUseCase<V> extends UseCase<V> {
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws Exception {
        if((Integer)param > 0){
            sheet.selectNextColname();
        }else if((Integer)param < 0){
            sheet.selectPreviousColname();
        }
        return (V) createState();
    }

    private JSONObject createState() throws CellNotFindException, JSONException {
        JSONObject sheetState = new JSONObject();

        List<Cell> selectedCells = sheet.getSelectedCells();
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

        return sheetState;
    }
}
