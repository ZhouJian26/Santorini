package it.polimi.ingsw.controller;

/**
 * A class to map function to launch on server side to a client type command
 * naming
 */
enum TypeCommand {
    CURRENT_PLAYER("currentPlayer"), GAME_PHASE("gamePhase"), GAME_MODE("gameMode"), PLAYER("player"), GOD("god"),
    GOD_LIST("godList"), ACTION("action"), BOARD("board"), PLAYER_STATUS("playerStatus"), COLOR("color");

    /**
     * Client side value of the tupe command
     */
    private final String value;

    /**
     * TypeCommand Constructor
     * 
     * @param value client side value of the type command
     */
    private TypeCommand(String value) {
        this.value = value;
    }

    /**
     * Get client side value of the type command
     * 
     * @return client side value
     */
    public String getValue() {
        return value;
    }

}