package it.polimi.ingsw.model;

public interface Action {
    Event[] execute(Cell[][] map);


    void setGod(God god);

    void setBlocked(boolean blocked);

    void set(boolean status);

    void set(int[] x1, int[] x2, int[] y1, int[] y2, boolean status);

    void set(boolean status, TypeBlock block, int[] position);

    boolean getStatus();

    Action clone();


    God getGod();


}
