package it.polimi.ingsw.model;

interface GodInterface {

    /**
     * Set the current player
     * @param name player's username
     */
    void setCurrentPlayer(String name);

    /**
     * Get the current player
     * @return current player
     */
    String getCurrentPlayer();

    /**
     * Set the worker's position
     * @param positionWorker worker's position
     */
    void setWorker(int[] positionWorker);

    /**
     * Get the worker's position
     * @return worker's position
     */
    int[] getPositionWorker();

    /**
     * Activate God Power's status
     * @param status God's status that needs to be activated
     */
    void activate(boolean status);

    /**
     * Get god's status
     * @return God's stauts
     */
    boolean getStatus();

    /**
     * Set player's status
     * @param statusPlayer player's status to be set
     */
    void setStatusPlayer(StatusPlayer statusPlayer);

    /**
     * Get player's status
     * @return player's status
     */
    StatusPlayer getPlayerStatus();

    /**
     * Get event
     * @param events event to be updated
     * @param map board situation at the moment
     * @param actions action of the event
     */
    void getEvent(Event[] events, Cell[][] map, Action[][][] actions);

    /**
     * Get player's username
     * @return player's username
     */
    String getName();

    /**
     * Get the last god that changed his own status
     * @return last god that changed his own status
     */
    God getLastGod();

    /**
     * Set the last god that changed his own status
     * @param lastGod last god that changed his own status
     */
    void setLastGod(God lastGod);

    /**
     * Add information to the current player
     * @param currentPlayer Player that information needs to be updated
     */
    void addInfo(CurrentPlayer currentPlayer);
}
