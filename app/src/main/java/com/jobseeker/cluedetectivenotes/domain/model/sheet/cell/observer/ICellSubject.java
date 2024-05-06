package com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.observer;

public interface ICellSubject {
    public void registerObserver(ICellObserver observer);
    public void removeObserver(ICellObserver observer);
    public void notifyObserver();
}
