package com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions;

public class CanNotUnselectNeverChosenCellException extends Exception{
    public CanNotUnselectNeverChosenCellException(){
        super("Can not unselect never chosen cell");
    }
}
