package it.polimi.ingsw.controller;

import java.util.Arrays;

/**
 * Class used to map functions launched (to be launched) on server's side and a client function naming
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
     * @param value client side's value
     */
    private FuncCommand(String value) {
        this.value = value;
    }

    /**
     * Get client side's value
     * 
     * @return client side value
     */
    public String getValue() {
        return value;
    }

    /**
     * Convert a client side's into a server side's value, otherwise null
     * 
     * @param toGet client side's value to convert
     * @return client side's value's name
     */
    public static FuncCommand getFromValue(String toGet) {
        return Arrays.asList(FuncCommand.values()).stream().filter(e -> e.value.equals(toGet)).findFirst().orElse(null);
    }
}