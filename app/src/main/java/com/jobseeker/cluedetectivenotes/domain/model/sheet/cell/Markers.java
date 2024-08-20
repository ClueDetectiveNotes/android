package com.jobseeker.cluedetectivenotes.domain.model.sheet.cell;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.AlreadyContainsSubMarkerItemException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.NotSubMarkerException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Markers {
    CHECK("O",true),CROSS("X",true),QUESTION("?",true),SLASH("/",false),EXCLAMATION("!",false)
    ,SUB_MARKER("0~9",false, Arrays.asList("1","2","3","4","5","6","7","8","9","0"));

    private final String notation;
    private final boolean isDefault;
    private final List<String> subMarkerItems;
    private static final List<Markers> allMarkers = new ArrayList<Markers>();
    private static final List<Markers> defaultMarkers = new ArrayList<Markers>();
    static{
        allMarkers.addAll(Arrays.asList(values()));

        for(Markers marker:values()){
            if(marker.isDefault){
                defaultMarkers.add(marker);
            }
        }
    }
    Markers(String notation, boolean isDefault){
        this.notation = notation;
        this.isDefault = isDefault;
        this.subMarkerItems = null;
    }

    Markers(String notation, boolean isDefault, List<String> subMarkerItems) {
        this.notation = notation;
        this.isDefault = isDefault;
        this.subMarkerItems = subMarkerItems;
    }

    public String getNotation(){
        return this.notation;
    }

    public List<String> getSubMarkerItems() throws NotSubMarkerException {
        if(subMarkerItems == null) throw new NotSubMarkerException();
        return new ArrayList<String>(subMarkerItems);
    }

    public void addSubMarkerItem(String subMarkerItem) throws NotSubMarkerException, AlreadyContainsSubMarkerItemException {
        if(subMarkerItems == null) throw new NotSubMarkerException();
        if(subMarkerItems.contains(subMarkerItem)) throw new AlreadyContainsSubMarkerItemException();
        subMarkerItems.add(subMarkerItem);
    }

    public static List<Markers> getAllMarkers(){
        return allMarkers;
    }

    public static List<Markers> getDefaultMarkers(){
        return defaultMarkers;
    }

    public static Markers findMainMarker(String notation) throws Exception {
        List<Markers> foundMarker = allMarkers.stream().filter(makers->makers.notation.equals(notation)).collect(Collectors.toList());
        if(foundMarker.isEmpty()) throw new Exception();
        return foundMarker.get(0);
    }
}
