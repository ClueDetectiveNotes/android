package com.jobseeker.cluedetectivenotes.model.sheet.exceptions;

public class CanNotUnselectNeverChosenCellException extends Exception{
    public CanNotUnselectNeverChosenCellException(){
        super("Can not unselect never chosen cell");
    }
}
