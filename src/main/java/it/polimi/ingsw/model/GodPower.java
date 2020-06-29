package it.polimi.ingsw.model;

class GodPower implements GodInterface {

    /**
     * God enumeration
     */
    public final God god;

    /**
     * Player that owns the current god power
     */
    public final String owner;

    /**
     * If the god power is activated
     */
    private Boolean trigged;

    /**
     * Current Player's information
     */
    private CurrentPlayer currentPlayerInfo;

    /**
     * Add information to the current player
     * @param currentPlayerInfo Player's information needs to be updated
     */
    @Override
    public void addInfo(CurrentPlayer currentPlayerInfo) {
        this.currentPlayerInfo = currentPlayerInfo;
    }

    /**
     * Set the god's power
     * @param god Current god
     * @param name Player's username
     */
    public GodPower(God god, String name) {
        this.god = god;
        owner = name;
        trigged = false;
    }
    /**
     * Set the last god that changed his own status
     * @param lastGod last god that changed his own status
     */
    public void setLastGod(God lastGod) {
        currentPlayerInfo.setLastGod(lastGod);
    }

    /**
     * Get the last god that changed his own status
     * @return last god that changed his own status
     */
    public God getLastGod() {
        return currentPlayerInfo.getLastGod();
    }

    /**
     * Set the current player
     * @param name player's username
     */
    @Override
    public void setCurrentPlayer(String name) {
        currentPlayerInfo.setCurrentPlayer(name);
    }

    /**
     * Get the current player
     * @return current player
     */
    @Override
    public String getCurrentPlayer() {
        return currentPlayerInfo.getCurrentPlayer();
    }

    /**
     * Set the worker's position
     * @param positionWorker worker's position
     */
    @Override
    public void setWorker(int[] positionWorker) {
        currentPlayerInfo.setPositionWorker(positionWorker);
    }

    /**
     * Get the worker's position
     * @return worker's position
     */
    @Override
    public int[] getPositionWorker() {
        return currentPlayerInfo.getPositionWorker();
    }

    /**
     * Get player's status
     * @return player's status
     */
    @Override
    public StatusPlayer getPlayerStatus() {
        return currentPlayerInfo.getStatusPlayer();
    }

    /**
     * Activate God Power's status
     * @param status God's status that needs to be activated
     */
    @Override
    public void activate(boolean status) {
        trigged = status;

    }

    /**
     * Get god's status
     * @return God's stauts
     */
    public boolean getStatus() {
        return Boolean.TRUE.equals(trigged);
    }

    /**
     * Get event
     * @param events event to be updated
     * @param map board situation at the moment
     * @param actions action of the event
     */
    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {

    }

    /**
     * Set player's status
     * @param statusPlayer player's status to be set
     */
    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        currentPlayerInfo.setStatusPlayer(statusPlayer);
    }

    /**
     * Get player's username
     * @return player's username
     */
    @Override
    public String getName() {
        return owner;
    }
}
