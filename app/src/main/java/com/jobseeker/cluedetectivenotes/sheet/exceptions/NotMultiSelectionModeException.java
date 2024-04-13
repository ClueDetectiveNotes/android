package com.jobseeker.cluedetectivenotes.sheet.exceptions;

public class NotMultiSelectionModeException extends Exception{
    public NotMultiSelectionModeException(){
        super("Current selection mode is not multi selection mode");
    }
}
