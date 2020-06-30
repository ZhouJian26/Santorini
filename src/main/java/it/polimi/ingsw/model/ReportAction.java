package it.polimi.ingsw.model;

class ReportAction {

    /**
     * Player's status
     */
    private final StatusPlayer statusPlayer;

    /**
     * Last god that changed its status
     */
    private final God god;

    /**
     * Report actions
     * @param statusPlayer player's status
     */
    public ReportAction(StatusPlayer statusPlayer) {
        if (statusPlayer == null)
            throw new NullPointerException();
        this.statusPlayer = statusPlayer;
        god = God.STANDARD;
    }

    /**
     * Report actions
     * @param statusPlayer Player's status
     * @param god last god that changed its status
     */
    public ReportAction(StatusPlayer statusPlayer, God god) {
        if (statusPlayer == null || god == null)
            throw new NullPointerException();
        this.god = god;
        this.statusPlayer = statusPlayer;
    }

    /**
     * Get player's status
     * @return player' status
     */
    public StatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    /**
     * Get last god that changed its status
     * @return last god that changed its status
     */
    public God getGod() {
        return god;
    }
}
