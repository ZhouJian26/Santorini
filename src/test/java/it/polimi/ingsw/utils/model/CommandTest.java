package it.polimi.ingsw.utils.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class CommandTest {
    @Test
    public void initBase() {
        Command c = new Command("type", "info");
        assertEquals("type", c.getType());
        assertEquals("info", c.getInfo());
        c = new Command("type", "funcName", "info", "funcData");
        assertEquals("type", c.getType());
        assertEquals("info", c.getInfo());
        assertEquals("funcName", c.getFuncName());
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
        assertEquals("type", command.getType());
        assertEquals("info", command.getInfo());
        assertEquals("func name", command.getFuncName());
        assertEquals("func data", command.getFuncData());
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