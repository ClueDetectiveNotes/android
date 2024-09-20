package com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar;

import com.jobseeker.cluedetectivenotes.domain.model.game.Game;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Rowname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChooseCrossMarkerUseCase<V> extends ChooseMainMarkerUseCase<V>{
    public ChooseCrossMarkerUseCase() {
        super(Markers.CROSS);
    }

    @Override
    public <T> V execute(T param) throws JSONException, MarkerMismatchException, CellNotFindException {
        //마킹
        super.execute(param);

        //마킹 후속 작업
        //마킹의 결과로 모든 특정 로우네임의 플레이어 셀이 [크로스]로 마킹되고, 정답셀이 비어있는 경우 정답 셀을 [체크]로 마킹한다
        Sheet sheet = GameSetter.getSheetInstance();

        List<Cell> selectedCells = sheet.getSelectedCells();
        Map<UUID,Cell> cells = sheet.getCells();
        Game game = GameSetter.getGameInstance();
        List<Player> players = game.getPlayers();
        List<UUID> playerIds = players.stream().map(CardHolder::getId).collect(Collectors.toList());

        //선택한 셀들과 연관된 로우네밍 추출
        Set<Rowname> rownames = new HashSet<>();
        for(Cell cell:selectedCells){
            rownames.add(cell.getRowname());
        }

        for(Rowname rowname:rownames){
            List<Cell> cellsOfRowname = cells.values().stream().filter(cell -> cell.getRowname().equals(rowname)).collect(Collectors.toList());
            Cell cellOfAnswer = cellsOfRowname.stream().filter(cell-> !playerIds.contains(cell.getColname().getId())).collect(Collectors.toList()).get(0);
            cellsOfRowname.remove(cellOfAnswer);

            boolean isAllCrossMarker = true;
            for(Cell cell:cellsOfRowname){
                isAllCrossMarker = isAllCrossMarker && cell.equalsMainMarker(Markers.CROSS);
            }
            //로우네임의 플레이어 셀이 [크로스]로 마킹되고, 정답셀이 비어있는 경우
            if(isAllCrossMarker && cellOfAnswer.isEmptyMainMarker()){
                //정답 셀을 [체크]로 마킹
                cellOfAnswer.setMainMarker(Markers.CHECK);
            }else {
                //로우네임의 플레이어 셀이 [크로스]마킹 해제 되고, 정답셀이 [체크]로 마킹되어 있는 경우
                if(!isAllCrossMarker && cellOfAnswer.equalsMainMarker(Markers.CHECK)){
                    //정답셀 [체크]마킹 해제
                    cellOfAnswer.removeMainMarker();
                }
            }
        }

        return (V) createState(sheet);
    }

    private JSONObject createState(Sheet sheet) throws JSONException, CellNotFindException {
        JSONArray cellsArr = new JSONArray();
        //선택된 셀 뿐만 아니라 자동으로 체크되고 해제되는 정답셀도 상태 반영하기 위해 모든 셀에 대해서 작업 수행
        List<Cell> cellList = new ArrayList<>(sheet.getCells().values());

        for(Cell cell : cellList){
            JSONObject cellObj = new JSONObject();
            cellObj.put("id", cell.getId());
            cellObj.put("mainMarker", cell.getMarker().getNotation());
            cellObj.put("subMarkerItems", cell.getSubMarkerItems());
            cellsArr.put(cellObj);
        }
        JSONObject sheetState = new JSONObject();

        List<Cell> selectedRownameCells = sheet.getSelectedRownameCells();
        List<Cell> selectedColnameCells = sheet.getSelectedColnameCells();

        List<Cell> selectedCells = sheet.getSelectedCells();
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
        sheetState.put("cells", cellsArr);
        return sheetState;
    }
}
