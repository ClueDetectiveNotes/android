package com.jobseeker.cluedetectivenotes.domain.model.sheet.observer;

public interface ISheetSubject {
    public void registerObserver(ISheetObserver observer);
    public void removeObserver(ISheetObserver observer);
    public void notifyObserver();
}
