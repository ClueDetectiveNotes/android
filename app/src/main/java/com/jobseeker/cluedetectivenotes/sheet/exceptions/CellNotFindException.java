package com.jobseeker.cluedetectivenotes.sheet.exceptions;

public class CellNotFindException extends Exception{
    public CellNotFindException(){
        super("Can not find that cell in cells");
    }
}
