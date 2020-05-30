package it.polimi.ingsw.model;

public class CurrentPlayer {
    private int[] positionWorker = new int[2];
    private String currentPlayer;
    private StatusPlayer statusPlayer;
    private God lastGod;
    public CurrentPlayer(){

    }

    public CurrentPlayer(int[] positionWorker, String currentPlayer,StatusPlayer statusPlayer,God god){
        this.positionWorker=positionWorker;
        this.currentPlayer=currentPlayer;
        this.statusPlayer=statusPlayer;
        this.lastGod=god;
    }
    public void setPositionWorker(int[] positionWorker){
        this.positionWorker=positionWorker;
    }

    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer=currentPlayer;
    }

    public void setStatusPlayer(StatusPlayer statusPlayer){
        this.statusPlayer=statusPlayer;
    }
    public void setLastGod(God god){
        lastGod=god;
    }


    public int[] getPositionWorker() {
        return positionWorker;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public StatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    public God getLastGod() {
        return lastGod;
    }
}
