package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.exceptions;

public class HaveAllOneKindOfCardException extends Exception{
    public HaveAllOneKindOfCardException(){
        super("Can not have all one kind of card");
    }
}
