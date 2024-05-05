package com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions;

public class CanNotSelectAlreadySelectedCellException extends Exception{
    public CanNotSelectAlreadySelectedCellException(){
        super("Can not select already selected cell");
    }
}
