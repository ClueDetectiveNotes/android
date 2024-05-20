package com.jobseeker.cluedetectivenotes.domain.model.snapshot;

import java.util.Stack;

public class SnapshotManager {
    static SnapshotManager snapshotManager;
    Snapshot tempSnapshot = new Snapshot();
    Stack<Snapshot> snapshotStack = new Stack<>();
    public static SnapshotManager getInstance(){
        if(snapshotManager == null){
            snapshotManager = new SnapshotManager();
        }
        return snapshotManager;
    }
    public void takeSnapshot(){
        snapshotStack.push(tempSnapshot);
        tempSnapshot = new Snapshot();
    }
    public Snapshot popOffSnapshot() throws Exception{
        if(snapshotStack.isEmpty()) throw new Exception();
        return snapshotStack.pop();
    }
}