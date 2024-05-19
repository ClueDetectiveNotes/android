package com.jobseeker.cluedetectivenotes.application.useCase;

public abstract class UseCaseDecorator<V> extends UseCase<V>{
    protected final UseCase<V> wrappee;
    protected UseCaseDecorator(UseCase<V> wrappee){
        this.wrappee = wrappee;
    }
}
