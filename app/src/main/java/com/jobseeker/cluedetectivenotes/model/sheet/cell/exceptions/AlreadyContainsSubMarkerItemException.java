package com.jobseeker.cluedetectivenotes.model.sheet.cell.exceptions;

public class AlreadyContainsSubMarkerItemException extends Exception {
    public AlreadyContainsSubMarkerItemException(){
        super("That item is already contains sub marker items");
    }
}
