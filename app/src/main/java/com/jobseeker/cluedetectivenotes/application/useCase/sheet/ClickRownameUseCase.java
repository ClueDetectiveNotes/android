package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.card.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.SelectionMode;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyColumnNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyRowNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.RownameNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClickRownameUseCase<V> extends UseCase<V>{
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws InferenceModeException, CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, JSONException, CardNotFoundException, NotYetSelectAnyRowNameException, RownameNotFoundException, NotYetSelectAnyColumnNameException {
        List<Cell> selectedRownameCells;
        if(sheet.isSelectedRowname((String) param)){
            selectedRownameCells = sheet.unselectRowname((String) param);
        }else{
            selectedRownameCells = sheet.selectRowname((String) param);
        }
        return (V) createState(selectedRownameCells, sheet.getSelectedCells());
    }

    private JSONObject createState(List<Cell> selectedRownameCells, List<Cell> selectedCells) throws JSONException {
        Boolean isMultiSelectionMode = sheet.isMultiSelectionMode();
        Boolean isInferenceMode = sheet.isEqualSelectionMode(SelectionMode.INFERENCE);

        JSONObject sheetState = new JSONObject();

        List<UUID> selectedRownameCellsIdList = new ArrayList<>();
        for(Cell cell:selectedRownameCells){
            selectedRownameCellsIdList.add(cell.getId());
        }

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("isInferenceMode", isInferenceMode);
        sheetState.put("selectedRownameCellsIdList",selectedRownameCellsIdList );
        sheetState.put("selectedCellsIdList",selectedCellsIdList );

        return sheetState;
    }
}
