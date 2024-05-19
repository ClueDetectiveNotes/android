package com.jobseeker.cluedetectivenotes.application.useCase.snapshot;

import android.util.Log;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.application.useCase.UseCaseDecorator;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.SnapshotManager;

public class SnapshotDecorator<V> extends UseCaseDecorator<V> {
    private final SnapshotManager snapshotManager = new SnapshotManager();
    public SnapshotDecorator(UseCase<V> useCase){
        super(useCase);
    }

    @Override
    public <T> V execute(T param) throws Exception {
        V result = wrappee.execute(param);
        snapshotManager.takeSnapshot();
        return result;
    }
}
