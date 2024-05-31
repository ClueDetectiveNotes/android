package com.jobseeker.cluedetectivenotes.domain.model.snapshot.exceptions;

public class SnapshotStackIsEmptyException extends Exception{
    public SnapshotStackIsEmptyException(){
        super("Snapshot does not exist");
    }
}
