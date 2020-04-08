package it.polimi.ingsw.model;

public enum GamePhase {
    SET_GOD_LIST, CHOOSE_GOD, SET_WOKERS, ACTIVE, END;

    public GamePhase next() {
        return GamePhase.values()[this.ordinal() + 1];
    }

    public static GamePhase start() {
        return GamePhase.SET_GOD_LIST;
    }
}
