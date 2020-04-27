package it.polimi.ingsw.utils;

public interface Observer<T> {

    public void update(T message);

}
