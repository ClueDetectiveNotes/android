package com.jobseeker.cluedetectivenotes.model.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.model.player.Player;
import com.jobseeker.cluedetectivenotes.model.sheet.cell.Cell;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SheetTest {
    private Sheet sheet;
    List<Player> players;

    @Before
    public void create(){
        players = new ArrayList<Player>();
        players.add(new Player("다산"));
        players.add(new Player("메리"));
        players.add(new Player("코코"));
        sheet = new Sheet(players);
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
    public void switchToMultiSelectionModeFromNoCellSelectedStateOnLongPress(){
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
}