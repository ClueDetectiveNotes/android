package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import android.annotation.SuppressLint;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Colname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Rowname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.observer.ISheetObserver;
import com.jobseeker.cluedetectivenotes.ui.viewModel.SheetViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoadSheetUseCase {
    private final Sheet sheet = GameSetter.getSheetInstance();
    public LoadSheetUseCase(ISheetObserver observer){
        sheet.registerObserver(observer);
    }
    @SuppressLint("NewApi")
    public JSONObject execute() throws JSONException {
        JSONObject sheet = new JSONObject();

        Map<UUID,Cell> cells = this.sheet.getCells();
        Map<String, Map<String, UUID>> cellsMap = new HashMap<>();

        for(UUID id : cells.keySet()){
            Cell cell = cells.get(id);
            assert cell != null;
            if(!cellsMap.containsKey(cell.getRowname().getCard().name())){

                Map<String,UUID> rowCellMap = new HashMap<>();
                rowCellMap.put(cell.getColname().getName(),id);
                cellsMap.put(cell.getRowname().getCard().name(),rowCellMap);

            }else{
                Map<String,UUID> rowCellMap = cellsMap.get(cell.getRowname().getCard().name());
                assert rowCellMap != null;
                rowCellMap.put(cell.getColname().getName(), id);
            }
        }

        sheet.put("cells",cellsMap);
        sheet.put("rownames", this.sheet.getRownames().stream().map(rowname -> {
            Map<String,String> rownameMap = new HashMap<>();
            rownameMap.put("name",rowname.getCard().name());
            rownameMap.put("type",rowname.getCard().getType().name());
            return rownameMap;
        }).collect(Collectors.toList()));
        sheet.put("colnames", this.sheet.getColnames().stream().map(colname -> colname.getName()).collect(Collectors.toList()));

        sheet.put("selectedCells", this.sheet.getSelectedCells().stream().map(cell -> cell.getId()).collect(Collectors.toList()));
        sheet.put("isMultiSelectionMode", this.sheet.isMultiSelectionMode());

        return sheet;
    }

    public void removeObserver(ISheetObserver observer) {
        sheet.removeObserver(observer);
    }
}
