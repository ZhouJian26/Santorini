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
        for (Observer<T> observer : observers) {
            observer.update(message);
        }
    }

    public void notify(ArrayList<String> targetList, T message) {
        signedObserver.forEach((k, v) -> {
            if (targetList.contains(k))
                v.update(message);
        });
    }
}
