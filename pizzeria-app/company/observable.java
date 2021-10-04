package com.company;


public interface observable {


    void addObserver(Observer observer);

    void removeObserver(Observer observer);


    void notifyAllObservers();
}
