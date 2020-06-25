package it.polimi.ingsw.model;

class GodPower implements GodInterface {
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
        currentPlayerInfo.setLastGod(lastGod);
    }

    public God getLastGod() {
        return currentPlayerInfo.getLastGod();
    }

    @Override
    public void setCurrentPlayer(String name) {
        currentPlayerInfo.setCurrentPlayer(name);
    }

    @Override
    public String getCurrentPlayer() {
        return currentPlayerInfo.getCurrentPlayer();
    }

    @Override
    public void setWorker(int[] positionWorker) {
        currentPlayerInfo.setPositionWorker(positionWorker);
    }

    @Override
    public int[] getPositionWorker() {
        return currentPlayerInfo.getPositionWorker();
    }

    @Override
    public StatusPlayer getPlayerStatus() {
        return currentPlayerInfo.getStatusPlayer();
    }

    @Override
    public void activate(boolean status) {
        trigged = status;

    }

    public boolean getStatus() {
        return Boolean.TRUE.equals(trigged);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {

    }

    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        currentPlayerInfo.setStatusPlayer(statusPlayer);
    }

    @Override
    public String getName() {
        return owner;
    }
}
