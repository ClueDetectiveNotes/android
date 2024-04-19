package com.jobseeker.cluedetectivenotes.model.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.model.player.Player;
import com.jobseeker.cluedetectivenotes.model.sheet.Cell;
import com.jobseeker.cluedetectivenotes.model.sheet.Sheet;

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
}