package it.polimi.ingsw.model;

public enum GamePhase {
    SET_GOD_LIST, CHOOSE_GOD, START_PLAYER, SET_COLOR, SET_WORKERS, CHOOSE_WORKER, PENDING, CHOOSE_ACTION, END;

    public GamePhase next() {
        return GamePhase.values()[Math.min(this.ordinal() + 1, GamePhase.values().length - 1)];
    }

    public GamePhase prev() {
        return GamePhase.values()[Math.max(this.ordinal() - 1, 0)];
    }

    public static GamePhase start() {
        return GamePhase.SET_GOD_LIST;
    }
}
