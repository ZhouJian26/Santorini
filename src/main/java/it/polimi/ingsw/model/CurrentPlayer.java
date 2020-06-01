package it.polimi.ingsw.model;

public class CurrentPlayer {
    private int[] positionWorker = new int[2];
    private String username;
    private StatusPlayer statusPlayer;
    private God lastGod;

    public CurrentPlayer() {

    }

    public CurrentPlayer(int[] positionWorker, String username, StatusPlayer statusPlayer, God god) {
        this.positionWorker = positionWorker;
        this.username = username;
        this.statusPlayer = statusPlayer;
        this.lastGod = god;
    }

    public void setPositionWorker(int[] positionWorker) {
        this.positionWorker = positionWorker;
    }

    public void setCurrentPlayer(String username) {
        this.username = username;
    }

    public void setStatusPlayer(StatusPlayer statusPlayer) {
        this.statusPlayer = statusPlayer;
    }

    public void setLastGod(God god) {
        lastGod = god;
    }

    public int[] getPositionWorker() {
        return positionWorker;
    }

    public String getCurrentPlayer() {
        return username;
    }

    public StatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    public God getLastGod() {
        return lastGod;
    }
}
