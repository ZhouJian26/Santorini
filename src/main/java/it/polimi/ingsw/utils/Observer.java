package it.polimi.ingsw.utils;

/**
 * Observer Class Interface
 * 
 * @param <T> type that the observer observes
 */
public interface Observer<T> {
    /**
     * Observer Update Function
     * 
     * @param message message received
     */
    public void update(T message);

}
