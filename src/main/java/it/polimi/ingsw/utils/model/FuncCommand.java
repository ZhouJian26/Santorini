package it.polimi.ingsw.utils.model;

import java.util.Arrays;

public enum FuncCommand {
    SET_GOD_LIST("setGodList"), SET_GOD("setGod"), SET_WORKERS("setWorkers"), SET_COLOR("setColor"),
    CHOOSE_WORKER("chooseWorker"), CHOOSE_ACTION("chooseAction"), SET_START_PLAYER("setStartPlayer"),
    QUIT_PLAYER("quitPlayer");

    private final String value;

    private FuncCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FuncCommand getFromValue(String toGet) {
        return Arrays.asList(FuncCommand.values()).stream().filter(e -> e.value.equals(toGet)).findFirst().orElse(null);
    }
}