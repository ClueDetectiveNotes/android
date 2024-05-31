package com.jobseeker.cluedetectivenotes.application.useCase.snapshot;

import com.jobseeker.cluedetectivenotes.domain.model.snapshot.SnapshotManager;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.exceptions.SnapshotStackIsEmptyException;

import org.json.JSONObject;

public class RedoUseCase extends SnapshotUseCase{
    @Override
    public JSONObject execute() throws Exception {
        try{
            setSnapshot(SnapshotManager.getInstance().popOffRedoSnapshot());
            return super.execute();
        }catch(SnapshotStackIsEmptyException se){
            se.printStackTrace();
        }
        return createState();
    }
}
