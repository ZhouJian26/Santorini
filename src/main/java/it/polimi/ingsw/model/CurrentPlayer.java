package it.polimi.ingsw.model;

class CurrentPlayer {

    /**
     * Position of players
     */
    private int[] positionWorker = new int[2];

    /**
     * Player's username
     */
    private String username;

    /**
     * Status of the current Player
     */
    private StatusPlayer statusPlayer;

    /**
     * Last god changed that changed status
     */
    private God lastGod;

    /**
     * Current Player
     */
    public CurrentPlayer() {

    }

    /**
     * Current Player's information
     * @param positionWorker Current Player's workers'positions
     * @param username Current Player's username
     * @param statusPlayer Status of the player
     * @param god Last god on the board that changed his own status
     */
    public CurrentPlayer(int[] positionWorker, String username, StatusPlayer statusPlayer, God god) {
        this.positionWorker = positionWorker;
        this.username = username;
        this.statusPlayer = statusPlayer;
        this.lastGod = god;
    }

    /**
     * To set workers' position
     * @param positionWorker Current position of the worker
     */
    public void setPositionWorker(int[] positionWorker) {
        this.positionWorker = positionWorker;
    }

    /**
     * Set the current players's username
     * @param username player's username
     */
    public void setCurrentPlayer(String username) {
        this.username = username;
    }

    /**
     * Set the current player's status
     * @param statusPlayer player's status
     */
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        this.statusPlayer = statusPlayer;
    }

    /**
     * Set the last god that changed his own status
     * @param god last god that changed his own status
     */
    public void setLastGod(God god) {
        lastGod = god;
    }

    /**
     * Get workers'positions
     * @return positions of the workers
     */
    public int[] getPositionWorker() {
        return positionWorker;
    }

    /**
     * Get the current player
     * @return the current player's username
     */
    public String getCurrentPlayer() {
        return username;
    }

    /**
     * Get the current player's status
     * @return current player's status
     */
    public StatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    /**
     * Get the last god that changed his own status
     * @return Last god that changed his own status
     */
    public God getLastGod() {
        return lastGod;
    }
}
