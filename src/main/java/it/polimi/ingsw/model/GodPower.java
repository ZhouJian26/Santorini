package it.polimi.ingsw.model;

import java.util.NoSuchElementException;

public class GodPower implements GodInterface {
    public final God god;
    public final String owner;
    private Boolean trigged;
    private static int[] positionWorker = new int[2];
    private static String currentPlayer;
    private static StatusPlayer statusPlayer;
    private static God lastGod;


    public GodPower(God god, String name) {
        this.god = god;
        owner = name;
        trigged=false;
    }

    public void setLastGod(God lastGod) {
        GodPower.lastGod = lastGod;
    }

    public God getLastGod() {
        return lastGod;
    }

    @Override
    public void setCurrentPlayer(String name) {
        currentPlayer = name;
    }
    @Override
    public String getCurrentPlayer(){
        return currentPlayer;
    }

    @Override
    public void setWorker(int[] positionWorker) {
        GodPower.positionWorker[0] = positionWorker[0];
        GodPower.positionWorker[1] = positionWorker[1];
    }
    @Override
    public int[] getPositionWorker(){
        int[] position=new int[2];
        position[0]=positionWorker[0];
        position[1]=positionWorker[1];
        return position;
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
    public StatusPlayer getPlayerStatus() {
        switch (statusPlayer) {
            case WIN:
                return StatusPlayer.WIN;
            case LOSE:
                return StatusPlayer.LOSE;
            case GAMING:
                return StatusPlayer.GAMING;
            case END:
                return StatusPlayer.END;
            default:
                throw new NoSuchElementException();
        }
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {

    }


    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        GodPower.statusPlayer = statusPlayer;
    }

    @Override
    public String getName() {
        return owner;
    }
}
