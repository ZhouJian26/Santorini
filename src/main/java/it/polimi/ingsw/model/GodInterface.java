package it.polimi.ingsw.model;

public interface GodInterface {
    void setCurrentPlayer(String name);
    void setWorker(int[] positionWorker);
    void activate(boolean status);
    void run(Action[][][] actions);
    StatusPlayer getPlayerStatus();
    void getEvent(Event[] events,Cell[][] map,Action[][][] actions);
    void setAction(Cell[][] map,Action[][][] actions);
    void setStatusPlayer(StatusPlayer statusPlayer);
    String getName();
    boolean getStatus();
    String getCurrentPlayer();
    int[] getPositionWorker();
    God getLastGod();
    void setLastGod(God lastGod);
}
