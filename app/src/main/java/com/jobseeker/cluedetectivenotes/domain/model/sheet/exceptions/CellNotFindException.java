package com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions;

public class CellNotFindException extends Exception{
    public CellNotFindException(){
        super("Can not find that cell in cells");
    }
}
