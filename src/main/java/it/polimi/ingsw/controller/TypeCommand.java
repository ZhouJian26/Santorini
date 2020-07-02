package it.polimi.ingsw.controller;

/**
 * A class to map functions to launch on server side to a client type command
 * naming
 */
enum TypeCommand {
    CURRENT_PLAYER("currentPlayer"), GAME_PHASE("gamePhase"), GAME_MODE("gameMode"), PLAYER("player"), GOD("god"),
    GOD_LIST("godList"), ACTION("action"), BOARD("board"), PLAYER_STATUS("playerStatus"), COLOR("color");

    /**
     * Client side's value of the specified type of command
     */
    private final String value;

    /**
     * TypeCommand Constructor
     * 
     * @param value client side's value of the specified type of command
     */
    private TypeCommand(String value) {
        this.value = value;
    }

    /**
     * Get client side's value of the specified type of command
     * 
     * @return client side's value
     */
    public String getValue() {
        return value;
    }

}