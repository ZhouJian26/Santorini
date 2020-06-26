package it.polimi.ingsw.model;

interface GodInterface {
    void setCurrentPlayer(String name);

    String getCurrentPlayer();

    void setWorker(int[] positionWorker);

    int[] getPositionWorker();

    void activate(boolean status);

    boolean getStatus();

    void setStatusPlayer(StatusPlayer statusPlayer);

    StatusPlayer getPlayerStatus();

    void getEvent(Event[] events, Cell[][] map, Action[][][] actions);

    String getName();

    God getLastGod();

    void setLastGod(God lastGod);

    void addInfo(CurrentPlayer currentPlayer);
}
