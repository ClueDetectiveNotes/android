package com.jobseeker.cluedetectivenotes.model.sheet;

import static org.junit.Assert.*;

import com.jobseeker.cluedetectivenotes.domain.model.card.Cards;
import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Colname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Rowname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Markers;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class CellTest {

    private Cell cell;

    @Before
    public void create(){
        cell = new Cell(UUID.randomUUID(), new Rowname(Cards.GREEN),new Colname(new CardHolder("코코")));
    }

    //mainMarker를 설정하면 해당 mainMarker가 추가된다.
    @Test
    public void addMainMarker() throws Exception {
        assertTrue(cell.isEmptyMainMarker());
        cell.setMainMarker(Markers.CHECK);
        assertTrue(cell.equalsMainMarker(Markers.CHECK));
    }

    //subMarker를 설정하면 해당 subMarker가 추가된다.
    @Test
    public void addSubMarker() throws Exception {
        assertTrue(cell.isEmptySubMarkers());
        cell.setSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0));
        assertEquals(cell.getSubMarkerItems().get(0), Markers.SUB_MARKER.getSubMarkerItems().get(0));
    }

    //이미 설정된 mainMarker를 다시 설정하면 해당 mainMarker가 삭제된다.
    @Test
    public void removeMainMarkerIfAlreadySet() throws Exception {
        assertTrue(cell.isEmptyMainMarker());
        cell.setMainMarker(Markers.CHECK);
        assertTrue(cell.equalsMainMarker(Markers.CHECK));
        cell.removeMainMarker();
        assertTrue(cell.isEmptyMainMarker());
    }

    //이미 설정된 SubMarker를 다시 설정하면 해당 SubMarker가 삭제된다.
    @Test
    public void removeSubMarkerIfAlreadySet() throws Exception {
        assertTrue(cell.isEmptySubMarkers());
        cell.setSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0));
        assertTrue(cell.containsSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0)));
        assertTrue(cell.removeSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0)));
        assertFalse(cell.containsSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0)));
    }

    //main marker가 있는 상태에서 main marker가 들어오면 새로운 값으로 override된다.
    @Test
    public void overrideMainMarkerIfAlreadyExists() throws Exception {
        assertTrue(cell.isEmptyMainMarker());
        cell.setMainMarker(Markers.CHECK);
        cell.setMainMarker(Markers.CROSS);
        assertFalse(cell.equalsMainMarker(Markers.CHECK));
        assertTrue(cell.equalsMainMarker(Markers.CROSS));
    }

    //sub marker가 있는 상태에서 sub marker가 들어오면 sub markers에 추가된다.
    @Test
    public void addSubMarkerIfAlreadyExists() throws Exception{
        assertTrue(cell.isEmptySubMarkers());
        cell.setSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0));
        cell.setSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(1));
        assertTrue(cell.containsSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(0)));
        assertTrue(cell.containsSubMarkerItem(Markers.SUB_MARKER.getSubMarkerItems().get(1)));
    }
}
