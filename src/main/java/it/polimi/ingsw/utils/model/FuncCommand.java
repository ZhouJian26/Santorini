package it.polimi.ingsw.utils.model;

import java.util.Arrays;

public enum FuncCommand {
    SET_GOD_LIST("setGodList", 2), SET_GOD("setGod", 2), SET_WORKERS("setWorkers", 2), SET_COLOR("setColor", 2),
    CHOOSE_WORKER("chooseWorker", 2), CHOOSE_ACTION("chooseAction", 2), SET_START_PLAYER("setStartPlayer", 2),
    QUIT_PLAYER("quitPlayer", 1);

    public final String value;
    public final int paramClass;

    private FuncCommand(String value, int paramClass) {
        this.value = value;
        this.paramClass = paramClass;
    }

    public static int getParamClass(String toCheck) {
        if (Arrays.asList(FuncCommand.values()).stream().anyMatch(e -> e.value.equals(toCheck)))
            return Arrays.asList(FuncCommand.values()).stream().filter(e -> e.value.equals(toCheck))
                    .map(e -> e.paramClass).findFirst().orElse(-1);
        return -1;
    }
}