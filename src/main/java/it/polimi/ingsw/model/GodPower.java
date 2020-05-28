package it.polimi.ingsw.model;

import java.util.NoSuchElementException;

public class GodPower implements GodInterface {
    public final God god;
    public final String owner;
    private Boolean trigged;
    private CurrentPlayer currentPlayerInfo;

    @Override
    public void addInfo(CurrentPlayer currentPlayerInfo) {
        this.currentPlayerInfo = currentPlayerInfo;
    }

    public GodPower(God god, String name) {
        this.god = god;
        owner = name;
        trigged = false;
    }

    public void setLastGod(God lastGod) {
        currentPlayerInfo.lastGod = lastGod;
    }

    public God getLastGod() {
        return currentPlayerInfo.lastGod;
    }

    @Override
    public void setCurrentPlayer(String name) {
        currentPlayerInfo.currentPlayer = name;
    }

    @Override
    public String getCurrentPlayer() {
        return currentPlayerInfo.currentPlayer;
    }

    @Override
    public void setWorker(int[] positionWorker) {
        currentPlayerInfo.positionWorker[0] = positionWorker[0];
        currentPlayerInfo.positionWorker[1] = positionWorker[1];
    }

    @Override
    public int[] getPositionWorker() {
        int[] position = new int[2];
        position[0] = currentPlayerInfo.positionWorker[0];
        position[1] = currentPlayerInfo.positionWorker[1];
        return position;
    }

    @Override
    public StatusPlayer getPlayerStatus() {
        switch (currentPlayerInfo.statusPlayer) {
            case WIN:
                return StatusPlayer.WIN;
            case LOSE:
                return StatusPlayer.LOSE;
            case GAMING:
                return StatusPlayer.GAMING;
            case IDLE:
            default:
                return StatusPlayer.IDLE;
        }
    }

    @Override
    public void activate(boolean status) {
        trigged = status;

    }

    public boolean getStatus() {
        if (trigged) {
            return true;
        }
        return false;
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {

    }

    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        currentPlayerInfo.statusPlayer = statusPlayer;
    }

    @Override
    public String getName() {
        return owner;
    }
}
