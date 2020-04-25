package it.polimi.ingsw.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandTest {
    @Test
    public void initBase() {
        Command command = new Command("type", "info");
        assertEquals("type", command.type);
        assertEquals("info", command.info);
    }

    public void initBaseNull() {
        assertThrows(NullPointerException.class, () -> {
            new Command(null, "info");
        });
        assertThrows(NullPointerException.class, () -> {
            new Command("type", null);
        });
        assertThrows(NullPointerException.class, () -> {
            new Command(null, null);
        });
    }

    @Test
    public void initComplete() {
        Command command = new Command("type", "func name", "info", "func data");
        assertEquals("type", command.type);
        assertEquals("info", command.info);
        assertEquals("func name", command.funcName);
        assertEquals("func data", command.funcData);
    }

    public void initCompleteNull() {
        assertThrows(NullPointerException.class, () -> {
            new Command(null, "func name", "info", "func data");
        });
        assertThrows(NullPointerException.class, () -> {
            new Command("type", "func name", null, "func data");
        });
        assertThrows(NullPointerException.class, () -> {
            new Command(null, "func name", null, "func data");
        });
    }
}