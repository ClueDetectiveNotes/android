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

public class ChangeDefaultModeUseCase<V> extends UseCase<V>{
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws InferenceModeException, CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, JSONException, CardNotFoundException, NotYetSelectAnyRowNameException, RownameNotFoundException, NotYetSelectAnyColumnNameException {
        sheet.unselectRowname();
        sheet.unselectColname();
        return (V) createState(sheet.getSelectedCells());
    }

    private JSONObject createState(List<Cell> selectedCells) throws JSONException {
        Boolean isMultiSelectionMode = sheet.isMultiSelectionMode();
        Boolean isInferenceMode = sheet.isEqualSelectionMode(SelectionMode.INFERENCE);

        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("isInferenceMode", isInferenceMode);
        sheetState.put("selectedCellsIdList",selectedCellsIdList );
        sheetState.put("selectedRownameCellsIdList",new ArrayList<>() );
        sheetState.put("selectedColnameCellsIdList",new ArrayList<>() );

        return sheetState;
    }
}
