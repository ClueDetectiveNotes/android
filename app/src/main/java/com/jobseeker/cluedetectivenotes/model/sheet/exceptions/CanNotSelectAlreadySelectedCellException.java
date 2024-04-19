package com.jobseeker.cluedetectivenotes.model.sheet.exceptions;

public class CanNotSelectAlreadySelectedCellException extends Exception{
    public CanNotSelectAlreadySelectedCellException(){
        super("Can not select already selected cell");
    }
}
