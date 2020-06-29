package it.polimi.ingsw.utils.model;

public enum TypeCommand {
    CURRENT_PLAYER("currentPlayer"), GAME_PHASE("gamePhase"), GAME_MODE("gameMode"), PLAYER("player"), GOD("god"),
    GOD_LIST("godList"), ACTION("action"), BOARD("board"), PLAYER_STATUS("playerStatus"), COLOR("color");

    private final String value;

    private TypeCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}