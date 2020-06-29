package it.polimi.ingsw.model;

public enum GamePhase {
    /**
     * Different game phases
     */
    SET_GOD_LIST, CHOOSE_GOD, START_PLAYER, SET_COLOR, SET_WORKERS, CHOOSE_WORKER, PENDING, CHOOSE_ACTION, END;

    /**
     * Gives the next GamePhase
     * @return next GamePhase
     */
    public GamePhase next() {
        return GamePhase.values()[Math.min(this.ordinal() + 1, GamePhase.values().length - 1)];
    }

    /**
     * Gives the previous GamePhase
     * @return previous GamePhase
     */
    public GamePhase prev() {
        return GamePhase.values()[Math.max(this.ordinal() - 1, 0)];
    }

    /**
     * Used only to start the game
     * @return GamePhase as SET_GOD_LIST
     */
    public static GamePhase start() {
        return GamePhase.SET_GOD_LIST;
    }
}
