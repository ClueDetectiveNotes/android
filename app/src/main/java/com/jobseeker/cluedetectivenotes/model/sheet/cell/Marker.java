package com.jobseeker.cluedetectivenotes.model.sheet.cell;

import com.jobseeker.cluedetectivenotes.model.sheet.cell.exceptions.MarkerMismatchException;

public class Marker {
    private Markers marker;

    public String getNotation(){
        return marker.getNotation();
    }

    public boolean equals(Markers marker){
        return this.marker == marker;
    }

    public void setMarker(Markers marker) throws MarkerMismatchException {
        if(Markers.SUB_MARKER == marker) throw new MarkerMismatchException();
        this.marker = marker;
    }

    public void setEmpty(){
        this.marker = null;
    }

    public boolean isEmpty() {
        return marker == null;
    }
}
