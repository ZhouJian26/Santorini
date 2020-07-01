package it.polimi.ingsw.controller;

import java.util.Arrays;

/**
 * A class to map function to launch on server side to a client function naming
 */
enum FuncCommand {
    SET_GOD_LIST("setGodList"), SET_GOD("setGod"), SET_WORKERS("setWorkers"), SET_COLOR("setColor"),
    CHOOSE_WORKER("chooseWorker"), CHOOSE_ACTION("chooseAction"), SET_START_PLAYER("setStartPlayer"),
    QUIT_PLAYER("quitPlayer");

    /**
     * value on client side
     */
    private final String value;

    /**
     * Constructor
     * 
     * @param value client side value
     */
    private FuncCommand(String value) {
        this.value = value;
    }

    /**
     * Get client side value
     * 
     * @return client side value
     */
    public String getValue() {
        return value;
    }

    /**
     * Convert a client side into a server side value, otherwise null
     * 
     * @param toGet client naming to convert
     * @return client side naming
     */
    public static FuncCommand getFromValue(String toGet) {
        return Arrays.asList(FuncCommand.values()).stream().filter(e -> e.value.equals(toGet)).findFirst().orElse(null);
    }
}