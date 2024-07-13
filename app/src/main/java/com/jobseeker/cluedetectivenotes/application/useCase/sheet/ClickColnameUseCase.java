package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.card.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.ColnameNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyColumnNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyRowNameException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClickColnameUseCase<V> extends UseCase<V>{
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws InferenceModeException, CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, JSONException, CardNotFoundException, NotYetSelectAnyRowNameException, ColnameNotFoundException, NotYetSelectAnyColumnNameException {
        List<Cell> selectedColnameCells = new ArrayList<>();
        if(sheet.isSelectedColname((String) param)){
            sheet.unselectColname();
        }else{
            selectedColnameCells.addAll(sheet.selectColname((String) param));
        }
        return (V) createState(selectedColnameCells, sheet.getSelectedCells());
    }

    private JSONObject createState(List<Cell> selectedColnameCells, List<Cell> selectedCells) throws JSONException {
        Boolean isMultiSelectionMode = sheet.isMultiSelectionMode();

        JSONObject sheetState = new JSONObject();

        List<UUID> selectedColnameCellsIdList = new ArrayList<>();
        for(Cell cell:selectedColnameCells){
            selectedColnameCellsIdList.add(cell.getId());
        }

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("selectedColnameCellsIdList",selectedColnameCellsIdList );
        sheetState.put("selectedCellsIdList",selectedCellsIdList );

        return sheetState;
    }
}
