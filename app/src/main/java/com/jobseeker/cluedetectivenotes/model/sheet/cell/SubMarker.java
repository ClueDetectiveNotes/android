package com.jobseeker.cluedetectivenotes.model.sheet.cell;

import com.jobseeker.cluedetectivenotes.model.sheet.cell.exceptions.AlreadyContainsSubMarkerItemException;
import com.jobseeker.cluedetectivenotes.model.sheet.cell.exceptions.NotSubMarkerException;
import com.jobseeker.cluedetectivenotes.model.sheet.cell.exceptions.NotSubMarkerItemMemberException;

import java.util.ArrayList;
import java.util.List;

public class SubMarker{
    private final List<String> subMarkerItems;
    private final Markers marker;

    public SubMarker(){
        marker = Markers.SUB_MARKER;
        subMarkerItems = new ArrayList<String>();
    }

    public void push(String subMarkerItem) throws NotSubMarkerItemMemberException, NotSubMarkerException, AlreadyContainsSubMarkerItemException {
        if(!marker.getSubMarkerItems().contains(subMarkerItem)) throw new NotSubMarkerItemMemberException();
        if(subMarkerItems.contains(subMarkerItem)) throw new AlreadyContainsSubMarkerItemException();
        subMarkerItems.add(subMarkerItem);
    }

    public boolean remove(String subMarkerItem){
        return subMarkerItems.remove(subMarkerItem);
    }

    public boolean isEmpty() {
        return subMarkerItems.isEmpty();
    }

    public boolean contains(String subMarkerItem) {
        return subMarkerItems.contains(subMarkerItem);
    }

    public List<String> getItems() {
        return new ArrayList<String>(subMarkerItems);
    }
}
