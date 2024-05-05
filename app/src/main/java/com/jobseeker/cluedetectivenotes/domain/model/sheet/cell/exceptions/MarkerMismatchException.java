package com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions;

public class MarkerMismatchException extends Exception {
    public MarkerMismatchException(){
        super("Not set sub marker");
    }
}
