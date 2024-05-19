package com.jobseeker.cluedetectivenotes.application.useCase;

public abstract class UseCase<V> {

    public V execute() throws Exception{
        return execute(null);
    };
    public abstract <T> V execute(T param) throws Exception;
}
