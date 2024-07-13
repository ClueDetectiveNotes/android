package com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions;

public class RownameNotFoundException extends Exception{
    public RownameNotFoundException(){
        super("Rowname not found");
    }
}
