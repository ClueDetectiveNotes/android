package com.jobseeker.cluedetectivenotes.model.sheet;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    String id;
    Rowname rowname;
    Colname colname;

    public static List<Marker> markers = new ArrayList<Marker>();

    public Cell(String id, Rowname rn, Colname cn) {
        this.id = id;
        this.rowname = rn;
        this.colname = cn;
    }

    public static List<Marker>getAllMarkers(){
        return markers;
    }
}
