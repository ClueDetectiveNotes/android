package com.jobseeker.cluedetectivenotes.domain.model.sheet.cell;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.AlreadyContainsSubMarkerItemException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.NotSubMarkerException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.NotSubMarkerItemMemberException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Colname;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Rowname;

import java.util.List;
import java.util.UUID;

public class Cell {
    private final UUID id;
    private final Rowname rowname;
    private final Colname colname;
    private final Marker mainMarker;
    private final SubMarker subMarker;
    private boolean isInit = false;
    private boolean isLock = false;

    public Cell(UUID id, Rowname rowname, Colname colname) {
        this.id = id;
        this.rowname = rowname;
        this.colname = colname;
        this.mainMarker = new Marker();
        this.subMarker = new SubMarker();
    }

    public Cell(UUID id, Rowname rowname, Colname colname, Markers initMarker) throws MarkerMismatchException {
        this.id = id;
        this.rowname = rowname;
        this.colname = colname;
        this.mainMarker = new Marker();
        this.subMarker = new SubMarker();

        this.mainMarker.setMarker(initMarker);
        isInit = true;
    }

    public Rowname getRowname() {
        return rowname;
    }

    public Colname getColname() {
        return colname;
    }

    public boolean isEmptyMainMarker() {
        return mainMarker.isEmpty();
    }

    public void setMainMarker(Markers marker) throws MarkerMismatchException {
        if(isInit){
            return;
        }
        if(isLock){
            return;
        }
        mainMarker.setMarker(marker);
    }

    public boolean isEmptySubMarkers() {
        return subMarker.isEmpty();
    }

    public void setSubMarkerItem(String subMarkerItem) throws AlreadyContainsSubMarkerItemException, NotSubMarkerItemMemberException, NotSubMarkerException {
        subMarker.push(subMarkerItem);
    }

    public List<String> getSubMarkerItems() {
        return subMarker.getItems();
    }

    public void removeMainMarker() {
        if(isInit){
            return;
        }
        if(isLock){
            return;
        }
        mainMarker.setEmpty();
    }

    public boolean removeSubMarkerItem(String subMarkerItem) {
        return subMarker.remove(subMarkerItem);
    }

    public void clearSubMarkerItems(){
        subMarker.clear();
    }

    public boolean containsSubMarkerItem(String subMarkerItem) {
        return subMarker.contains(subMarkerItem);
    }

    public boolean equalsMainMarker(Markers marker) {
        return mainMarker.equals(marker);
    }

    public UUID getId() {
        return id;
    }

    public Marker getMarker() {
        return mainMarker;
    }

    public boolean isInit() {
        return isInit;
    }

    public boolean isLocked() { return isLock; }

    public void lock(){
        if(mainMarker.equals(Markers.CHECK) || mainMarker.equals(Markers.CROSS)){
            isLock = true;
        }
    }

    public void unlock(){
        isLock = false;
    }
}
