package com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions;

public class InferenceModeException extends Exception{
    public InferenceModeException(){
        super("Would you quit inference mode?");
    }
}
