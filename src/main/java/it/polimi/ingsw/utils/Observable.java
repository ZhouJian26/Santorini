package it.polimi.ingsw.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Custom Observable Class
 * 
 * @param <T> type of information that can be observed
 */
public class Observable<T> {
    /**
     * Simple observers'list
     */
    private List<Observer<T>> observers = new ArrayList<>();
    /**
     * Hashmap id-observer, used to send data to a specific observer
     */
    private HashMap<String, Observer<T>> signedObserver = new HashMap<>();

    /**
     * Add an observer
     * 
     * @param observer observer to add
     */
    public void addObservers(Observer<T> observer) {
        observers.add(observer);
    }

    /**
     * Add an observer with a key
     * 
     * @param key      key to bind with the observer
     * @param observer observer to add
     */

    public void addObservers(String key, Observer<T> observer) {
        addObservers(observer);
        signedObserver.put(key, observer);
    }

    /**
     * Send data to all observers
     * 
     * @param message data to send
     */
    public void notify(T message) {
        updateList(observers, message);
    }

    /**
     * Send data to specific observers
     * 
     * @param targetList observer id list
     * @param message    data to send
     */
    public void notify(ArrayList<String> targetList, T message) {
        List<Observer<T>> obsList = new ArrayList<>();
        signedObserver.forEach((k, v) -> {
            if (targetList.contains(k))
                obsList.add(v);
        });
        updateList(obsList, message);
    }

    /**
     * Send data to a observers list
     * 
     * @param obsList observer lists
     * @param message data to send
     */
    private void updateList(List<Observer<T>> obsList, T message) {
        for (Observer<T> observer : obsList)
            observer.update(message);
    }
}
