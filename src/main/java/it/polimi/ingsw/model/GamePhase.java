package it.polimi.ingsw.model;

public enum GamePhase {
    SET_GOD_LIST, CHOOSE_GOD, SET_WORKERS, ACTIVE, END;

    public GamePhase next() {
        if (this.ordinal() + 1 < GamePhase.values().length)
            return GamePhase.values()[this.ordinal() + 1];
        return GamePhase.values()[this.ordinal()];
    }

    public static GamePhase start() {
        return GamePhase.SET_GOD_LIST;
    }
}
