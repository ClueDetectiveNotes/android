package com.jobseeker.cluedetectivenotes.application.useCase.snapshot;

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase;
import com.jobseeker.cluedetectivenotes.application.useCase.UseCaseDecorator;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CellNotFindException;
import com.jobseeker.cluedetectivenotes.domain.model.snapshot.SnapshotManager;

public class SnapshotDecorator<V> extends UseCaseDecorator<V> {
    private final SnapshotManager snapshotManager = SnapshotManager.getInstance();
    public SnapshotDecorator(UseCase<V> useCase) throws CellNotFindException {
        super(useCase);
    }
    @Override
    public <T> V execute(T param) throws Exception {
        V result = wrappee.execute(param);
        snapshotManager.takeSnapshot();
        return result;
    }
}
