package com.jobseeker.cluedetectivenotes.domain.model.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.snapshot.exceptions.SnapshotStackIsEmptyException;

import java.util.Stack;

public class SnapshotManager {
    static SnapshotManager snapshotManager;
    int debugIndex = 0;
    Snapshot tempSnapshot = new Snapshot(debugIndex++);
    Stack<Snapshot> snapshotUndoStack = new Stack<>();
    Stack<Snapshot> snapshotRedoStack = new Stack<>();
    public static SnapshotManager getInstance(){
        if(snapshotManager == null){
            snapshotManager = new SnapshotManager();
        }
        return snapshotManager;
    }
    public void takeSnapshot(){
        snapshotRedoStack.clear();
        snapshotUndoStack.push(tempSnapshot);
        tempSnapshot = new Snapshot(debugIndex++);
    }
    public Snapshot popOffUndoSnapshot() throws SnapshotStackIsEmptyException{
        if(snapshotUndoStack.isEmpty()) throw new SnapshotStackIsEmptyException();
        snapshotRedoStack.push(tempSnapshot);
        tempSnapshot = snapshotUndoStack.pop();
        return tempSnapshot;
    }

    public Snapshot popOffRedoSnapshot() throws SnapshotStackIsEmptyException{
        if(snapshotRedoStack.isEmpty()) throw new SnapshotStackIsEmptyException();
        snapshotUndoStack.push(tempSnapshot);
        tempSnapshot = snapshotRedoStack.pop();
        return tempSnapshot;
    }
}