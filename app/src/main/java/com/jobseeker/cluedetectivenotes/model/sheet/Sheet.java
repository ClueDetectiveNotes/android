package com.jobseeker.cluedetectivenotes.model.sheet;

import com.jobseeker.cluedetectivenotes.model.card.DefaultCard;
import com.jobseeker.cluedetectivenotes.model.player.Player;
import com.jobseeker.cluedetectivenotes.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
import com.jobseeker.cluedetectivenotes.model.sheet.exceptions.CanNotUnselectNeverChosenCellException;
import com.jobseeker.cluedetectivenotes.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.model.sheet.exceptions.NotMultiSelectionModeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Sheet {
    private List<Rowname> rownames;
    private List<Colname> colnames;
    private boolean multiMode = false;
    private List<Cell> selectedCells;
    private Map<String, Cell> cells;
    public Sheet(List<Player> players){
        selectedCells = new ArrayList<Cell>();
        cells = new HashMap<String,Cell>();

        colnames = new ArrayList<Colname>();
        for(Player p:players){
            colnames.add(new Colname(p));
        }

        rownames = new ArrayList<Rowname>();
        rownames.add(new Rowname(DefaultCard.GREEN));
        rownames.add(new Rowname(DefaultCard.MUSTARD));
        rownames.add(new Rowname(DefaultCard.PEACOCK));
        rownames.add(new Rowname(DefaultCard.PLUM));
        rownames.add(new Rowname(DefaultCard.SCARLET));
        rownames.add(new Rowname(DefaultCard.WHITE));
        rownames.add(new Rowname(DefaultCard.WRENCH));
        rownames.add(new Rowname(DefaultCard.CANDLESTICK));
        rownames.add(new Rowname(DefaultCard.KNIFE));
        rownames.add(new Rowname(DefaultCard.REVOLVER));
        rownames.add(new Rowname(DefaultCard.LEAD_PIPE));
        rownames.add(new Rowname(DefaultCard.ROPE));
        rownames.add(new Rowname(DefaultCard.BATHROOM));
        rownames.add(new Rowname(DefaultCard.STUDY));
        rownames.add(new Rowname(DefaultCard.GAME_ROOM));
        rownames.add(new Rowname(DefaultCard.GARAGE));
        rownames.add(new Rowname(DefaultCard.BEDROOM));
        rownames.add(new Rowname(DefaultCard.LIVING_ROOM));
        rownames.add(new Rowname(DefaultCard.KITCHEN));
        rownames.add(new Rowname(DefaultCard.YARD));
        rownames.add(new Rowname(DefaultCard.DINING_ROOM));

        for(Rowname rn : rownames){
            for(Colname cn : colnames){
                String id = UUID.randomUUID().toString();
                Cell cell = new Cell(id, rn, cn);
                cells.put(id, cell);
            }
        }
    }
    public boolean hasSelectedCell() {
        return !selectedCells.isEmpty();
    }

    public List<Cell> selectCell(Rowname rowname, Colname colname) throws Exception {
        Cell selectedCell = findCell(rowname,colname);
        selectedCells.add(selectedCell);
        return selectedCells;
    }

    private Cell findCell(Rowname rowname, Colname colname) throws CellNotFindException{
        for(String ck : cells.keySet()){
            Cell cell = cells.get(ck);
            if(cell.rowname.equals(rowname) && cell.colname.equals(colname)){
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
        return selectedCells;
    }

    public boolean isSelectedCell(Rowname rowname, Colname colname) throws CellNotFindException {
        Cell cell = findCell(rowname,colname);
        return selectedCells.indexOf(cell) != -1;
    }
}
