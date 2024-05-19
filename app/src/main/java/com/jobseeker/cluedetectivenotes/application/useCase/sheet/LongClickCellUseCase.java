package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LongClickCellUseCase<V> extends UseCase<V>{
    private final Sheet sheet = GameSetter.getSheetInstance();
    @Override
    public <T> V execute(T param) throws Exception {
        if(!sheet.isMultiSelectionMode()) sheet.switchSelectionMode();
        return (V) createState(sheet.isMultiSelectionMode(), sheet.selectCell((UUID) param));
    }

    private JSONObject createState(Boolean isMultiSelectionMode, List<Cell> selectedCells) throws JSONException {
        JSONObject sheetState = new JSONObject();

        List<UUID> selectedCellsIdList = new ArrayList<>();
        for(Cell cell:selectedCells){
            selectedCellsIdList.add(cell.getId());
        }

        sheetState.put("isMultiSelectionMode", isMultiSelectionMode);
        sheetState.put("selectedCellsIdList",selectedCellsIdList );

        return sheetState;
    }
}
