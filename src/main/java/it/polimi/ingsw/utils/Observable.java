package it.polimi.ingsw.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();
    private HashMap<String, Observer<T>> signedObserver = new HashMap<>();

    public void addObservers(Observer<T> observer) {
        observers.add(observer);
    }

    public void addObservers(String key, Observer<T> observer) {
        addObservers(observer);
        signedObserver.put(key, observer);
    }

    public void notify(T message) {
        updateList(observers, message);
    }

    public void notify(ArrayList<String> targetList, T message) {
        List<Observer<T>> obsList = new ArrayList<>();
        signedObserver.forEach((k, v) -> {
            if (targetList.contains(k))
                obsList.add(v);
        });
        updateList(obsList, message);
    }

    private void updateList(List<Observer<T>> obsList, T message) {
        for (Observer<T> observer : obsList)
            observer.update(message);
    }
}
