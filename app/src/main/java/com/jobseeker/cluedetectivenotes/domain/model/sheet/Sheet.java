package com.jobseeker.cluedetectivenotes.domain.model.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotUnselectNeverChosenCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.card.Cards;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyColumnNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyRowNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Sheet {
    private final List<Rowname> rownames;
    private final List<Colname> colnames;
    private boolean multiMode = false;
    private final List<Cell> selectedCells;
    private final Map<UUID, Cell> cells;
    private Rowname selectedRownameSuspect = null;
    private Rowname selectedRownameWeapon = null;
    private Rowname selectedRownameCrimeScene = null;
    private Colname selectedColname = null;
    public Sheet(List<Player> players){
        selectedCells = new ArrayList<Cell>();
        cells = new HashMap<UUID,Cell>();

        colnames = new ArrayList<Colname>();
        for(Player p:players){
            colnames.add(new Colname(p));
        }

        rownames = new ArrayList<Rowname>();
        for(Cards card : Cards.getSuspects()){
            rownames.add(new Rowname(card));
        }
        for(Cards card : Cards.getWeapons()){
            rownames.add(new Rowname(card));
        }
        for(Cards card : Cards.getCrimeScenes()) {
            rownames.add(new Rowname(card));
        }

        for(Rowname rn : rownames){
            for(Colname cn : colnames){
                UUID id = UUID.randomUUID();
                Cell cell = new Cell(id, rn, cn);
                cells.put(id, cell);
            }
        }
    }

    public boolean hasSelectedCell() {
        return !selectedCells.isEmpty();
    }

    public List<Cell> selectCell(Rowname rowname, Colname colname) throws CellNotFindException {
        Cell selectedCell = findCell(rowname,colname);
        selectedCells.add(selectedCell);
        return selectedCells;
    }

    private Cell findCell(Rowname rowname, Colname colname) throws CellNotFindException{
        for(UUID ck : cells.keySet()){
            Cell cell = cells.get(ck);
            assert cell != null;
            if(cell.getRowname().equals(rowname) && cell.getColname().equals(colname)){
                return cell;
            }
        }
        throw new CellNotFindException();
    }

    public List<Rowname> getRownames() {
        return rownames;
    }

    public List<Colname> getColnames() {
        return colnames;
    }

    public void unselectCell() {
        selectedCells.clear();
    }

    public boolean isMultiSelectionMode() {
        return multiMode;
    }

    public void switchSelectionMode() {
        if(isMultiSelectionMode() && hasSelectedCell()) unselectCell();
        multiMode = !multiMode;
    }

    public List<Cell> multiSelectCell(Rowname rowname, Colname colname) throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException {
        if(!isMultiSelectionMode()) throw new NotMultiSelectionModeException();
        if(isSelectedCell(rowname, colname)) throw new CanNotSelectAlreadySelectedCellException();

        selectedCells.add(findCell(rowname,colname));
        return selectedCells;
    }

    public List<Cell> multiUnselectCell(Rowname rowname, Colname colname) throws CellNotFindException, NotMultiSelectionModeException, CanNotUnselectNeverChosenCellException {
        if(!isMultiSelectionMode()) throw new NotMultiSelectionModeException();
        if(!isSelectedCell(rowname, colname)) throw new CanNotUnselectNeverChosenCellException();

        selectedCells.remove(findCell(rowname,colname));
        if(selectedCells.isEmpty()) switchSelectionMode();
        return selectedCells;
    }

    public boolean isSelectedCell(Rowname rowname, Colname colname) throws CellNotFindException {
        Cell cell = findCell(rowname,colname);
        return selectedCells.contains(cell);
    }

    public List<Cell> selectRowname(Rowname rowname) throws CellNotFindException {
        List<Cell> selectedRowCells = new ArrayList<Cell>();

        if(Cards.getSuspects().contains(rowname.getCard())){
            selectedRownameSuspect = rowname;
        }else if(Cards.getWeapons().contains(rowname.getCard())){
            selectedRownameWeapon = rowname;
        }else if(Cards.getCrimeScenes().contains(rowname.getCard())){
            selectedRownameCrimeScene = rowname;
        }

        for(Colname colname: colnames){
            selectedRowCells.add(findCell(rowname, colname));
        }

        return selectedRowCells;
    }

    public List<Cell> selectColname(Colname colname) throws CellNotFindException {
        List<Cell> selectedColCells = new ArrayList<Cell>();

        selectedColname = colname;

        for(Rowname rowname: rownames){
            selectedColCells.add(findCell(rowname,colname));
        }

        return selectedColCells;
    }

    public List<Cell> getCellsIntersectionOfSelection() throws CellNotFindException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        List<Cell> intersectionCells = new ArrayList<Cell>();
        if(selectedColname == null){
            throw new NotYetSelectAnyColumnNameException();
        }else if (selectedRownameSuspect == null && selectedRownameWeapon == null && selectedRownameCrimeScene == null){
            throw new NotYetSelectAnyRowNameException();
        }else{
            if(selectedRownameSuspect != null){
                intersectionCells.add(findCell(selectedRownameSuspect,selectedColname));
            }
            if(selectedRownameWeapon != null){
                intersectionCells.add(findCell(selectedRownameWeapon,selectedColname));
            }
            if(selectedRownameCrimeScene != null){
                intersectionCells.add(findCell(selectedRownameCrimeScene,selectedColname));
            }
        }
        return intersectionCells;
    }

    public boolean isSelectedRowname(Rowname rowname) {
        return rowname.equals(selectedRownameSuspect) || rowname.equals(selectedRownameWeapon) || rowname.equals(selectedRownameCrimeScene);
    }

    public boolean hasSelectedRownameSuspect() {
        return selectedRownameSuspect != null;
    }

    public boolean hasSelectedRownameWeapon() {
        return selectedRownameWeapon != null;
    }

    public boolean hasSelectedRownameCrimeScene() {
        return selectedRownameCrimeScene != null;
    }

    public boolean unselectRowname(Rowname rowname) {
        if(rowname.equals(selectedRownameSuspect)){
            selectedRownameSuspect = null;
            return true;
        }else if(rowname.equals(selectedRownameWeapon)){
            selectedRownameWeapon = null;
            return true;
        }else if(rowname.equals(selectedRownameCrimeScene)){
            selectedRownameCrimeScene = null;
            return true;
        }
        return false;
    }

    public boolean hasSelectedColname() {
        return selectedColname != null;
    }

    public boolean isSelectedColname(Colname colname) {
        return selectedColname == colname;
    }

    public void unselectColname(){
        selectedColname = null;
    }

    public List<Cell> selectCell(UUID cellId) throws CellNotFindException {
        return selectCell(cells.get(cellId).getRowname(),cells.get(cellId).getColname());
    }

    public Map<UUID, Cell> getCells() {
        return cells;
    }

    public boolean isSelectedCell(UUID cellId) throws CellNotFindException {
        return isSelectedCell(cells.get(cellId).getRowname(),cells.get(cellId).getColname());
    }

    public List<Cell> getSelectedCells() {
        return selectedCells;
    }

    public List<Cell> multiUnselectCell(UUID cellId) throws CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException {
        return multiUnselectCell(cells.get(cellId).getRowname(),cells.get(cellId).getColname());
    }
    public boolean isEveryCellMarked(){
        int markedCount = 0;
        for(Cell cell:selectedCells){
            if(!cell.isEmptyMainMarker()){
                markedCount += 1;
            }
        }
        return selectedCells.size() == markedCount;
    }

    public boolean isSameMarkerInEveryCell(Markers marker) {
        List<Cell> markedCells = selectedCells.stream().filter(cell -> !cell.isEmptyMainMarker()).collect(Collectors.toList());

        assert !markedCells.isEmpty();
        Cell firstCell = markedCells.get(0);
        boolean sameMarker = firstCell.equalsMainMarker(marker);
        AtomicInteger markedCount = new AtomicInteger();

        markedCells.forEach(cell->{
            if(firstCell.getMarker().equals(cell.getMarker())) markedCount.incrementAndGet();
        });

        return markedCells.size() == markedCount.get() && sameMarker;
    }
}
