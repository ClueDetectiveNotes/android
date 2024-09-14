package com.jobseeker.cluedetectivenotes.domain.model.sheet;

import androidx.annotation.NonNull;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.card.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.game.CardHolders;
import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotUnselectNeverChosenCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.card.Cards;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.ColnameNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyColumnNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyRowNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.RownameNotFoundException;

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
    private SelectionMode selectionMode = SelectionMode.DEFAULT;
    private final List<Cell> selectedCells;
    private final Map<UUID, Cell> cells;
    private Rowname selectedRownameSuspect = null;
    private Rowname selectedRownameWeapon = null;
    private Rowname selectedRownameCrimeScene = null;
    private Colname selectedColname = null;
    private boolean isCellsLocked = false;
    public Sheet(CardHolders holders) {
        selectedCells = new ArrayList<Cell>();
        cells = new HashMap<UUID,Cell>();

        List<CardHolder> cardHolderList = new ArrayList<>(holders.getPlayers());
        cardHolderList.add(holders.getAnswer());

        colnames = new ArrayList<Colname>();
        for(CardHolder ch:cardHolderList){
            colnames.add(new Colname(ch));
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

        List<String> hand = holders.getUser().getCardList().stream().map(Card::getName).collect(Collectors.toList());
        List<String> publicCards = holders.getPublicOne().getCardList().stream().map(Card::getName).collect(Collectors.toList());

        for(Rowname rn : rownames){
            for(Colname cn : colnames){
                UUID id = UUID.randomUUID();
                Cell cell;
                try{
                    if(hand.contains(rn.getCard().name())){
                        if(cn.isUser()){
                            //유저의 손패는 체크
                            cell = new Cell(id, rn, cn, Markers.CHECK);
                        }else{
                            //유저가 아닌 경우 크로스
                            cell = new Cell(id, rn, cn, Markers.CROSS);
                        }
                    }else if(publicCards.contains(rn.getCard().name())){
                        //공용 카드는 크로스
                        cell = new Cell(id, rn, cn, Markers.CROSS);
                    }else if(cn.isUser()){
                        //손패가 아닌 셀은 크로스
                        cell = new Cell(id, rn, cn, Markers.CROSS);
                    }else {
                        cell = new Cell(id, rn, cn);
                    }
                }catch (MarkerMismatchException e){
                    cell = new Cell(id, rn, cn);
                }
                cells.put(id, cell);
            }
        }
    }

    public boolean hasSelectedCell() {
        return !selectedCells.isEmpty();
    }

    public List<Cell> selectCell(Rowname rowname, Colname colname) throws CellNotFindException, InferenceModeException {
        if(selectionMode == SelectionMode.PRE_INFERENCE || selectionMode == SelectionMode.INFERENCE){
            switchInferenceMode();
            throw new InferenceModeException();
        }

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

    public void unselectCell() throws CellNotFindException, InferenceModeException {
        if(selectionMode == SelectionMode.PRE_INFERENCE || selectionMode == SelectionMode.INFERENCE){
            switchInferenceMode();
            throw new InferenceModeException();
        }
        selectedCells.clear();
    }

    public void unselectCellDoNotCareInferenceMode(){
        selectedCells.clear();
    }

    public boolean isMultiSelectionMode() {
        return selectionMode == SelectionMode.MULTI;
    }

    public void switchSelectionMode() throws InferenceModeException, CellNotFindException {
        if(selectionMode == SelectionMode.PRE_INFERENCE || selectionMode == SelectionMode.INFERENCE){
            switchInferenceMode();
            throw new InferenceModeException();
        }

        if(isMultiSelectionMode() && hasSelectedCell()) unselectCell();
        selectionMode = selectionMode==SelectionMode.MULTI? SelectionMode.DEFAULT: SelectionMode.MULTI;
    }

    public void setDefaultSelectionMode() {
        unselectCellDoNotCareInferenceMode();
        selectionMode = SelectionMode.DEFAULT;
    }

    public List<Cell> multiSelectCell(Rowname rowname, Colname colname) throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException {
        if(!isMultiSelectionMode()) throw new NotMultiSelectionModeException();
        if(isSelectedCell(rowname, colname)) throw new CanNotSelectAlreadySelectedCellException();

        selectedCells.add(findCell(rowname,colname));
        return selectedCells;
    }

    public List<Cell> multiUnselectCell(Rowname rowname, Colname colname) throws CellNotFindException, NotMultiSelectionModeException, CanNotUnselectNeverChosenCellException, InferenceModeException {
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

    private void switchInferenceMode() throws CellNotFindException{
        if(selectedColname == null & selectedRownameSuspect == null && selectedRownameWeapon == null && selectedRownameCrimeScene == null){
            selectionMode = SelectionMode.DEFAULT;
            selectedCells.clear();
        }else if(selectedColname != null & selectedRownameSuspect != null && selectedRownameWeapon != null && selectedRownameCrimeScene != null){
            try{
                selectedCells.clear();
                selectedCells.addAll(getCellsIntersectionOfSelection());
                selectionMode = SelectionMode.INFERENCE;
            }catch (NotYetSelectAnyRowNameException | NotYetSelectAnyColumnNameException e){
                selectedCells.addAll(new ArrayList<>());
            }
        }else{
            selectionMode = SelectionMode.PRE_INFERENCE;
            selectedCells.clear();
        }
    }

    public boolean isEqualSelectionMode(SelectionMode selectionMode){
        return this.selectionMode == selectionMode;
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
            if(selectedRownameSuspect != null) selectedRowCells.add(findCell(selectedRownameSuspect, colname));
            if(selectedRownameWeapon != null) selectedRowCells.add(findCell(selectedRownameWeapon, colname));
            if(selectedRownameCrimeScene != null) selectedRowCells.add(findCell(selectedRownameCrimeScene, colname));
        }

        switchInferenceMode();

        return selectedRowCells;
    }

    private Rowname findRowname(String cardName) throws CardNotFoundException, RownameNotFoundException {
        Cards card = Cards.findCard(cardName);
        Rowname foundRowname = null;
        for(Rowname rowname:rownames){
            if(card == rowname.getCard()){
                foundRowname = rowname;
                break;
            }
        }
        if(foundRowname == null) throw new RownameNotFoundException();
        return foundRowname;
    }

    public List<Cell> selectRowname(String cardName) throws CardNotFoundException, CellNotFindException, RownameNotFoundException {
        return selectRowname(findRowname(cardName));
    }

    public List<Cell> selectColname(Colname colname) throws CellNotFindException{
        List<Cell> selectedColCells = new ArrayList<Cell>();

        selectedColname = colname;

        for(Rowname rowname: rownames){
            selectedColCells.add(findCell(rowname,colname));
        }

        switchInferenceMode();

        return selectedColCells;
    }

    public Colname findColname(String holderName) throws ColnameNotFoundException {
        Colname foundColname = null;
        for(Colname colname:colnames){
            if(holderName.equals(colname.holder.getName())){
                foundColname = colname;
                break;
            }
        }
        if(foundColname == null) throw new ColnameNotFoundException();
        return foundColname;
    }

    public List<Cell> selectColname(String playerName) throws CellNotFindException, ColnameNotFoundException {
        return selectColname(findColname(playerName));
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

    public boolean isSelectedRowname(String cardName) throws RownameNotFoundException, CardNotFoundException {
        return isSelectedRowname(findRowname(cardName));
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

    public List<Cell> unselectRowname(Rowname rowname) throws CellNotFindException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        List<Cell> selectedRowCells = new ArrayList<>();

        if(rowname.equals(selectedRownameSuspect)){
            selectedRownameSuspect = null;
            switchInferenceMode();
        }else if(rowname.equals(selectedRownameWeapon)){
            selectedRownameWeapon = null;
            switchInferenceMode();
        }else if(rowname.equals(selectedRownameCrimeScene)){
            selectedRownameCrimeScene = null;
            switchInferenceMode();
        }
        for(Colname colname: colnames){
            if(selectedRownameSuspect != null) selectedRowCells.add(findCell(selectedRownameSuspect, colname));
            if(selectedRownameWeapon != null) selectedRowCells.add(findCell(selectedRownameWeapon, colname));
            if(selectedRownameCrimeScene != null) selectedRowCells.add(findCell(selectedRownameCrimeScene, colname));
        }

        return selectedRowCells;
    }

    public List<Cell> unselectRowname(String cardName) throws RownameNotFoundException, CardNotFoundException, CellNotFindException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        return unselectRowname(findRowname(cardName));
    }

    public boolean hasSelectedColname() {
        return selectedColname != null;
    }

    public boolean isSelectedColname(Colname colname) {
        return selectedColname == colname;
    }

    public boolean isSelectedColname(String playerName) throws ColnameNotFoundException {
        return isSelectedColname(findColname(playerName));
    }

    public void unselectColname() throws CellNotFindException {
        selectedColname = null;
        switchInferenceMode();
    }

    public void unselectColnameWithoutSwitch(){
        selectedColname = null;
    }

    public void unselectRowname() throws CellNotFindException {
        selectedRownameWeapon = null;
        selectedRownameSuspect = null;
        selectedRownameCrimeScene = null;
        switchInferenceMode();
    }

    public void unselectRownameWithoutSwitch(){
        selectedRownameWeapon = null;
        selectedRownameSuspect = null;
        selectedRownameCrimeScene = null;
    }

    public List<Cell> selectCell(UUID cellId) throws CellNotFindException, InferenceModeException {
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

    public List<Cell> multiSelectCell(UUID cellId) throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException {
        return multiSelectCell(cells.get(cellId).getRowname(),cells.get(cellId).getColname());
    }

    public List<Cell> multiUnselectCell(UUID cellId) throws CellNotFindException, CanNotUnselectNeverChosenCellException, NotMultiSelectionModeException, InferenceModeException {
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
        List<Cell> markedCells = selectedCells.stream().filter(cell -> !cell.isEmptyMainMarker() && !cell.isInit() && !cell.isLocked()).collect(Collectors.toList());

        assert !markedCells.isEmpty();
        Cell firstCell = markedCells.get(0);
        boolean sameMarker = firstCell.equalsMainMarker(marker);
        AtomicInteger markedCount = new AtomicInteger();

        markedCells.forEach(cell->{
            if(firstCell.getMarker().equals(cell.getMarker())) markedCount.incrementAndGet();
        });

        return markedCells.size() == markedCount.get() && sameMarker;
    }

    public boolean isSameSubMarkerInEveryCell(String subMarkerItem){
        List<Cell> markedCells = selectedCells.stream().filter(cell -> !cell.isEmptySubMarkers()).collect(Collectors.toList());

        //마킹된 셀이 없는 경우
        if(markedCells.isEmpty())
            return false;
        //마킹 된 셀이 선택한 셀과 같지 않은 경우
        if(markedCells.size() != selectedCells.size()) {
            return false;
        }

        Cell firstCell = markedCells.get(0);
        boolean sameMarker = firstCell.containsSubMarkerItem(subMarkerItem);
        AtomicInteger markedCount = new AtomicInteger();

        markedCells.forEach(cell->{
            if(cell.containsSubMarkerItem(subMarkerItem)) markedCount.incrementAndGet();
        });
        return markedCells.size() == markedCount.get() && sameMarker;
    }

    public List<Cell> getSelectedRownameCells() throws CellNotFindException {
        List<Cell> selectedRownameCells = new ArrayList<>();
        if(selectedRownameSuspect != null) selectedRownameCells.addAll(selectRowname(selectedRownameSuspect));
        if(selectedRownameWeapon != null) selectedRownameCells.addAll(selectRowname(selectedRownameWeapon));
        if(selectedRownameCrimeScene != null) selectedRownameCells.addAll(selectRowname(selectedRownameCrimeScene));
        return selectedRownameCells;
    }

    public List<Cell> getSelectedColnameCells() throws CellNotFindException {
        List<Cell> selectedColnameCells = new ArrayList<>();
        if(selectedColname != null ) selectedColnameCells.addAll(selectColname(selectedColname));
        return selectedColnameCells;
    }

    public List<Rowname> getSelectedRownames() {
        List<Rowname> selectedRowname = new ArrayList<>();
        if(selectedRownameSuspect != null) selectedRowname.add(selectedRownameSuspect);
        if(selectedRownameWeapon != null) selectedRowname.add(selectedRownameWeapon);
        if(selectedRownameCrimeScene != null) selectedRowname.add(selectedRownameCrimeScene);
        return selectedRowname;
    }

    public List<Colname> getSelectedColname() {
        List<Colname> selectedColnames = new ArrayList<>();
        if(selectedColname != null) selectedColnames.add(selectedColname);
        return selectedColnames;
    }

    public void selectNextColname() {
        moveColname(1);
    }

    public void selectPreviousColname() {
        moveColname(colnames.size()-2);
    }

    private void moveColname(int gap){
        int currentIndex = colnames.indexOf(selectedColname);
        if(currentIndex == colnames.size()-1){
            selectedColname = colnames.get(0);
        }else{
            selectedColname = colnames.get((currentIndex+gap)%(colnames.size()-1));
        }
    }

    public boolean isCellsLocked(){
        return isCellsLocked;
    }

    public void lockCells(){
        cells.forEach((uuid, cell) -> cell.lock());
        isCellsLocked = true;
    }

    public void unlockCells(){
        cells.forEach((uuid, cell) -> cell.unlock());
        isCellsLocked = false;
    }
}
