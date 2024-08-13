package com.jobseeker.cluedetectivenotes.model.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.SelectionMode;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotUnselectNeverChosenCellException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotMultiSelectionModeException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyColumnNameException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.NotYetSelectAnyRowNameException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SheetTest {
    private Sheet sheet;
    List<CardHolder> players;

    @Before
    public void create() throws MarkerMismatchException {
//        players = new ArrayList<CardHolder>();
//        players.add(new CardHolder("다산"));
//        players.add(new CardHolder("메리"));
//        players.add(new CardHolder("코코"));
//        sheet = new Sheet(players);
    }

    //어떤 셀도 선택되지 않은 상태에서 셀을 선택하면 셀에 마커를 선택할 수 있다
    @Test
    public void selectOneCellWithoutAnyCellSelectedCanChooseMarkers() throws Exception {
        assertFalse(sheet.hasSelectedCell());

        List<Cell> selectedCells = sheet.selectCell(sheet.getRownames().get(0), sheet.getColnames().get(0));

        assertTrue(sheet.hasSelectedCell());
        assertEquals(selectedCells.get(0).getClass(), Cell.class);
    }

    //어떤 셀이 선택된 상태에서 어떤 셀이든 선택하면 선택이 취소된다
    @Test
    public void selectAnyCellWhileOneCellSelectedUnselectTheCell() throws Exception {
        sheet.selectCell(sheet.getRownames().get(0), sheet.getColnames().get(0));
        assertTrue(sheet.hasSelectedCell());
        sheet.unselectCell();
        assertFalse(sheet.hasSelectedCell());
    }

    //어떤 셀도 선택되지 않은 상태에서 멀티 선택 모드로 스위치(long press)했을 때 멀티 선택 모드가 된다.
    @Test
    public void switchToMultiSelectionModeFromNoCellSelectedStateOnLongPress() throws InferenceModeException, CellNotFindException {
        assertFalse(sheet.hasSelectedCell());
        assertFalse(sheet.isMultiSelectionMode());
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());
    }

    //멀티 선택 모드일 때 선택되지 않은 셀을 선택하면 셀이 추가된다.
    @Test
    public void selectUnselectedCellInMultiSelectionModeToAddCell() throws Exception {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());

        List<Cell> selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0), sheet.getColnames().get(0));
        int selectedCellsSize = selectedCells.size();

        assertFalse(sheet.isSelectedCell(sheet.getRownames().get(0), sheet.getColnames().get(1)));
        selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0), sheet.getColnames().get(1));

        assertEquals(selectedCellsSize+1 , selectedCells.size());
    }

    //멀티 선택 모드일 때 선택된 셀을 선택하면 해당 셀이 선택 해제된다.
    @Test
    public void selectSelectedCellInMultiSelectionModeToDeselectCell() throws Exception {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());

        List<Cell> selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0), sheet.getColnames().get(0));
        int selectedCellsSize = selectedCells.size();

        assertTrue(sheet.isSelectedCell(sheet.getRownames().get(0), sheet.getColnames().get(0)));
        selectedCells = sheet.multiUnselectCell(sheet.getRownames().get(0), sheet.getColnames().get(0));

        assertEquals(selectedCellsSize-1 , selectedCells.size());
    }

    //멀티 선택 모드일 때 멀티 선택 모드를 해제하면 모든 셀의 선택이 해제되고, 멀티 선택 모드도 해제된다.
    @Test
    public void disableMultiSelectionModeToDeselectAllCellsAndExitMultiSelectionMode() throws Exception{
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());

        sheet.multiSelectCell(sheet.getRownames().get(0), sheet.getColnames().get(0));
        sheet.multiSelectCell(sheet.getRownames().get(1), sheet.getColnames().get(0));

        sheet.switchSelectionMode();
        assertFalse(sheet.isMultiSelectionMode());
        assertFalse(sheet.hasSelectedCell());
    }

    //rowname만 선택되었을 때 row에 해당하는 cell들을 반환한다
    @Test
    public void returnCellsForRownameSelection() throws Exception {
        List<Cell> selectedRowCells = sheet.selectRowname(sheet.getRownames().get(0));

        assertEquals(sheet.getColnames().size(), selectedRowCells.size());

        for(Cell cell:selectedRowCells){
            assertEquals(cell.getClass(), Cell.class);
        }
    }

    //colname만 선택되었을 때 col에 해당하는 cell들을 반환한다
    @Test
    public void returnCellsForColnameSelection() throws Exception {
        List<Cell> selectedColCells = sheet.selectColname(sheet.getColnames().get(0));

        assertEquals(sheet.getRownames().size(), selectedColCells.size());
        for(Cell cell:selectedColCells){
            assertEquals(Cell.class, cell.getClass());
        }
    }

    //rowname과 colname이 선택되었을 때 해당하는 cell들을 반환한다
    @Test
    public void returnCellsForRownameAndColnameSelection() throws Exception {
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectColname(sheet.getColnames().get(0));
        List<Cell> intersectionCells = sheet.getCellsIntersectionOfSelection();

        assertEquals(Cell.class, intersectionCells.get(0).getClass());
    }

    //선택된 rowname을 다시 선택하면 해당 rowname이 선택 해제된다.
    @Test
    public void deselectRownameOnReselection() throws Exception {
        sheet.selectRowname(sheet.getRownames().get(0));
        assertTrue(sheet.hasSelectedRownameSuspect());
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(0)));

        sheet.unselectRowname(sheet.getRownames().get(0));
        assertFalse(sheet.isSelectedRowname(sheet.getRownames().get(0)));
        assertFalse(sheet.hasSelectedRownameSuspect());

        sheet.selectRowname(sheet.getRownames().get(6));
        assertTrue(sheet.hasSelectedRownameWeapon());
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(6)));

        sheet.unselectRowname(sheet.getRownames().get(6));
        assertFalse(sheet.isSelectedRowname(sheet.getRownames().get(6)));
        assertFalse(sheet.hasSelectedRownameWeapon());

        sheet.selectRowname(sheet.getRownames().get(12));
        assertTrue(sheet.hasSelectedRownameCrimeScene());
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(12)));

        sheet.unselectRowname(sheet.getRownames().get(12));
        assertFalse(sheet.isSelectedRowname(sheet.getRownames().get(12)));
        assertFalse(sheet.hasSelectedRownameCrimeScene());
    }

    //선택된 colname을 다시 선택하면 해당 colname이 선택 해제된다.
    @Test
    public void deselectColnameOnReselection() throws Exception{
        sheet.selectColname(sheet.getColnames().get(0));
        assertTrue(sheet.hasSelectedColname());
        assertTrue(sheet.isSelectedColname(sheet.getColnames().get(0)));
        sheet.unselectColname();
        assertFalse(sheet.hasSelectedColname());
    }

    //rowname이 선택된 상태에서 같은 카테고리의 rowname이 선택되었을 때 이전 rowname은 선택 해제되고, 해당 rowname은 선택된다.
    @Test
    public void selectNewRownameAndDeselectPreviousInSameCategory() throws Exception{
        sheet.selectRowname(sheet.getRownames().get(0));
        assertTrue(sheet.hasSelectedRownameSuspect());
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(0)));

        sheet.selectRowname(sheet.getRownames().get(1));
        assertTrue(sheet.hasSelectedRownameSuspect());
        assertFalse(sheet.isSelectedRowname(sheet.getRownames().get(0)));
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(1)));
    }

    //rowname이 선택된 상태에서 다른 카테고리의 rowname이 선택되었을 때 해당 rowname이 추가된다.
    @Test
    public void addRownameSelectionInDifferentCategory() throws Exception{
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        assertTrue(sheet.hasSelectedRownameSuspect());
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(0)));
        assertTrue(sheet.hasSelectedRownameWeapon());
        assertTrue(sheet.isSelectedRowname(sheet.getRownames().get(6)));
    }

    //colname이 선택된 상태에서 colname이 선택되었을 때 이전 colname은 선택 해제되고, 해당 colname은 선택된다.
    @Test
    public void selectNewColnameAndDeselectPrevious() throws Exception {
        sheet.selectColname(sheet.getColnames().get(0));
        assertTrue(sheet.hasSelectedColname());
        assertTrue(sheet.isSelectedColname(sheet.getColnames().get(0)));

        sheet.selectColname(sheet.getColnames().get(1));
        assertTrue(sheet.hasSelectedColname());
        assertFalse(sheet.isSelectedColname(sheet.getColnames().get(0)));
        assertTrue(sheet.isSelectedColname(sheet.getColnames().get(1)));
    }

    //용의자, 무기, 장소에 해당하는 rowname 3개가 player(colname)가 선택되었을 때, 추리세트(셀)를 반환한다
    @Test
    public void returnClueSetForSelectedCategories() throws Exception{
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        sheet.selectRowname(sheet.getRownames().get(12));
        sheet.selectColname(sheet.getColnames().get(0));

        List<Cell> reason = sheet.getCellsIntersectionOfSelection();
        assertEquals(3, reason.size());
    }

    //(추가됨) 멀티모드에서 셀들을 선택하고 마커를 선택했을 때, 현재 설정된 마커와 관계없이 선택된 모든 셀에 마킹이 된다.
    //선택된 셀에 마커가 하나도 없는 경우
    @Test
    public void whenSelectUnmarkedCellsAndChooseMarkerInMultimode_AllSelectedCellsAreMarkedOfTheCurrentlySetMarker() throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, InferenceModeException {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(1));
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(2));

        sheet.getSelectedCells().forEach(cell->{
            try {
                cell.setMainMarker(Markers.CROSS);
            } catch (MarkerMismatchException e) {
                throw new RuntimeException(e);
            }
        });

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().equals(Markers.CROSS));
        }
    }

    //선택된 셀에 일부만 마커가 있는 경우
    @Test
    public void whenSelectMarkedOrUnmarkedCellsAndChooseMarkerInMultimode_AllSelectedCellsAreMarkedOfTheCurrentlySetMarker() throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, MarkerMismatchException, InferenceModeException {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(1));
        List<Cell> selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(2));

        //선택된 셀에 일부만 X마커가 있는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.CROSS);

        for(Cell cell:sheet.getSelectedCells()){
            cell.setMainMarker(Markers.CROSS);
        }

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().equals(Markers.CROSS));
        }

        //선택된 셀에 일부만 ?마커가 있는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.QUESTION);

        for(Cell cell:sheet.getSelectedCells()){
            cell.setMainMarker(Markers.CROSS);
        }

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().equals(Markers.CROSS));
        }

        //선택된 셀에 일부는 X마커가 일부는 ?마커가 있고 나머지는 마커가 없는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.QUESTION);
        selectedCells.get(1).setMainMarker(Markers.CROSS);

        for(Cell cell:sheet.getSelectedCells()){
            cell.setMainMarker(Markers.CROSS);
        }

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().equals(Markers.CROSS));
        }
    }

    //선택된 모든 셀에 마커가 있는 경우
    @Test
    public void whenSelectMarkedCellsAndChooseMarkerInMultimode_AllSelectedCellsAreMarkedOfTheCurrentlySetMarker() throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, MarkerMismatchException, InferenceModeException {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(1));
        List<Cell> selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(2));

        //선택된 셀에 모두 X마커가 있는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.CROSS);
        selectedCells.get(1).setMainMarker(Markers.CROSS);
        selectedCells.get(2).setMainMarker(Markers.CROSS);

        marking(Markers.CROSS);

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().isEmpty());
        }

        //선택된 셀에 모두 ?마커가 있는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.QUESTION);
        selectedCells.get(1).setMainMarker(Markers.QUESTION);
        selectedCells.get(2).setMainMarker(Markers.QUESTION);

        marking(Markers.CROSS);

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().equals(Markers.CROSS));
        }

        //선택된 셀에 일부는 X마커가 있고 나머지는 ?마커가 있는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.QUESTION);
        selectedCells.get(1).setMainMarker(Markers.QUESTION);
        selectedCells.get(2).setMainMarker(Markers.CROSS);

        marking(Markers.CROSS);

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().equals(Markers.CROSS));
        }
    }

    private void marking(Markers mainMarker){
        if(sheet.isEveryCellMarked() && sheet.isSameMarkerInEveryCell(mainMarker)){
            sheet.getSelectedCells().forEach(Cell::removeMainMarker);
        }else{
            sheet.getSelectedCells().forEach(cell->{
                try {
                    cell.setMainMarker(mainMarker);
                } catch (MarkerMismatchException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    //(추가됨) 멀티모드에서 특정 마커가 선택한 모든 셀에 있을 때 해당 마커를 한 번 더 선택하면 모든 마커가 지워진다
    @Test
    public void whenSameMarkerInSelectedCellsOneMoreSelectionOfThatMarkerClearsAllMarker() throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, MarkerMismatchException, InferenceModeException {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(1));
        List<Cell> selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(2));

        //선택된 셀에 모두 X마커가 있는 경우
        for(Cell cell:sheet.getSelectedCells()){
            cell.removeMainMarker();
        }

        selectedCells.get(0).setMainMarker(Markers.CROSS);
        selectedCells.get(1).setMainMarker(Markers.CROSS);
        selectedCells.get(2).setMainMarker(Markers.CROSS);

        marking(Markers.CROSS);

        for(Cell cell:sheet.getSelectedCells()){
            assertTrue(cell.getMarker().isEmpty());
        }
    }

    //(추가됨) 멀티모드에서 모든 셀을 선택해제했을 때 기본 모드가 된다.
    @Test
    public void whenAllCellsAreDeselectedInMultimodeThenGoToDefaultMode () throws CellNotFindException, NotMultiSelectionModeException, CanNotSelectAlreadySelectedCellException, CanNotUnselectNeverChosenCellException, InferenceModeException {
        sheet.switchSelectionMode();
        assertTrue(sheet.isMultiSelectionMode());
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(1));
        List<Cell> selectedCells = sheet.multiSelectCell(sheet.getRownames().get(0),sheet.getColnames().get(2));
        List<Cell> tempSelectedCells = new ArrayList<>(selectedCells);

        for(Cell cell:tempSelectedCells){
            sheet.multiUnselectCell(cell.getId());
        }

        assertTrue(sheet.getSelectedCells().isEmpty());
        assertFalse(sheet.isMultiSelectionMode());
    }

    //(추가됨) 추리모드에서 셀을 클릭하면 모드를 변경하는 예외가 발생한다.(2024-05-26)
    @Test
    public void whenACellIsClickedInInferenceModeThenAnExceptionOccursToChangeMode() throws CellNotFindException, InferenceModeException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        sheet.selectRowname(sheet.getRownames().get(12));
        sheet.selectColname(sheet.getColnames().get(0));

        assertTrue(sheet.isEqualSelectionMode(SelectionMode.INFERENCE));

        Throwable exception = assertThrows(InferenceModeException.class,()-> {
            sheet.selectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        });
        assertEquals(exception.getMessage(),new InferenceModeException().getMessage());
    }

    //(추가됨) 추리전모드에서 셀을 클릭하면 모드를 변경하는 예외가 발생한다.(2024-05-26)
    @Test
    public void whenACellIsClickedInPreinferenceModeThenAnExceptionOccursToChangeMode() throws CellNotFindException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        sheet.selectRowname(sheet.getRownames().get(12));

        assertTrue(sheet.isEqualSelectionMode(SelectionMode.PRE_INFERENCE));

        Throwable exception = assertThrows(InferenceModeException.class,()-> {
            sheet.selectCell(sheet.getRownames().get(0),sheet.getColnames().get(0));
        });
        assertEquals(exception.getMessage(),new InferenceModeException().getMessage());
    }

    //(추가됨) 추리모드에서 셀을 길게 누르면 모드를 변경하는 예외가 발생한다.(2024-05-26)
    @Test
    public void whenACellIsLongPressInInferenceModeThenAnExceptionOccursToChangeMode() throws CellNotFindException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        sheet.selectRowname(sheet.getRownames().get(12));
        sheet.selectColname(sheet.getColnames().get(0));

        assertTrue(sheet.isEqualSelectionMode(SelectionMode.INFERENCE));

        Throwable exception = assertThrows(InferenceModeException.class,()-> {
            sheet.switchSelectionMode();
        });
        assertEquals(exception.getMessage(),new InferenceModeException().getMessage());
    }

    //(추가됨)추리전모드에서 셀을 길게 누르면 모드를 변경하는 예외가 발생한다.(2024-05-26)
    @Test
    public void whenACellIsLongPressInPreinferenceModeThenAnExceptionOccursToChangeMode() throws CellNotFindException, NotYetSelectAnyColumnNameException, NotYetSelectAnyRowNameException {
        sheet.selectRowname(sheet.getRownames().get(0));
        sheet.selectRowname(sheet.getRownames().get(6));
        sheet.selectRowname(sheet.getRownames().get(12));

        assertTrue(sheet.isEqualSelectionMode(SelectionMode.PRE_INFERENCE));

        Throwable exception = assertThrows(InferenceModeException.class,()-> {
            sheet.switchSelectionMode();
        });
        assertEquals(exception.getMessage(),new InferenceModeException().getMessage());
    }

}