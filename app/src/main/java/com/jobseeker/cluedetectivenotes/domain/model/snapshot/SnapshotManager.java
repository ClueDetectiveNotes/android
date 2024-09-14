package com.jobseeker.cluedetectivenotes.domain.model.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.exceptions.SnapshotStackIsEmptyException;

import java.util.Stack;

public class SnapshotManager {
    static SnapshotManager snapshotManager;
    int debugIndex = 0;
    Snapshot tempSnapshot = new Snapshot(debugIndex++);
    Stack<Snapshot> snapshotUndoStack = new Stack<>();
    Stack<Snapshot> snapshotRedoStack = new Stack<>();
    Stack<Snapshot> restoreUndoStack = new Stack<>();

    public SnapshotManager() throws CellNotFindException {
    }

    public static SnapshotManager getInstance() throws CellNotFindException {
        if(snapshotManager == null){
            snapshotManager = new SnapshotManager();
        }
        return snapshotManager;
    }
    public void takeSnapshot() throws CellNotFindException {
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

    public void lockSnapshot() {
        restoreUndoStack.addAll(snapshotUndoStack);
        snapshotUndoStack.clear();
        snapshotRedoStack.clear();
    }

    public void unlockSnapshot(){
        restoreUndoStack.addAll(snapshotUndoStack);
        snapshotUndoStack.clear();
        snapshotUndoStack.addAll(restoreUndoStack);
        restoreUndoStack.clear();
    }
}