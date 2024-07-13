package com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions;

public class ColnameNotFoundException extends Exception{
    public ColnameNotFoundException(){
        super("Colname not found");
    }
}
