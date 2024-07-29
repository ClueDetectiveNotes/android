package com.jobseeker.cluedetectivenotes.application.useCase.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Colname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Rowname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InitSheetStructUseCase {
    private Sheet sheet = GameSetter.getSheetInstance();

    public JSONObject execute() throws JSONException {
        JSONObject sheet = new JSONObject();

        Map<UUID,Cell> cells = this.sheet.getCells();
        Map<String, Map<String, UUID>> cellsMap = new HashMap<>();

        for(UUID id : cells.keySet()){
            Cell cell = cells.get(id);
            if(!cellsMap.containsKey(cell.getRowname().getCard().name())){

                Map<String,UUID> rowCellMap = new HashMap<>();
                rowCellMap.put(cell.getColname().getName(),id);
                cellsMap.put(cell.getRowname().getCard().name(),rowCellMap);

            }else{
                Map<String,UUID> rowCellMap = cellsMap.get(cell.getRowname().getCard().name());
                rowCellMap.put(cell.getColname().getName(), id);
            }
        }
        List<Map<String,String>> rownameList = new ArrayList<>();
        for (Rowname rowname : this.sheet.getRownames()){
            Map<String,String> rownameMap = new HashMap<>();
            rownameMap.put("name",rowname.getCard().name());
            rownameMap.put("type",rowname.getCard().getType().name());
            rownameList.add(rownameMap);

        }
        List<Map<UUID,String>> colnameList = new ArrayList<>();
        for(Colname colname : this.sheet.getColnames()){
            Map<UUID,String> colnameMap = new HashMap<>();
            colnameMap.put(colname.getId(), colname.getName());
            colnameList.add(colnameMap);

            if(colname.isUser()){
                sheet.put("userId", colname.getId());
            }
        }

        sheet.put("cells",cellsMap);
        sheet.put("rownames", rownameList);
        sheet.put("colnames", colnameList);

        return sheet;
    }
}
