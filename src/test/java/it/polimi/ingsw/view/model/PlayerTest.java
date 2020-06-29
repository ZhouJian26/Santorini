package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Test;

public class PlayerTest {
    @Test
    public void init() {
        Player p = new Player("username", "LOSE", "color", "god", 0);
        List<String> values = p.getValues();
        assertEquals("username", values.get(0));
        assertEquals("LOSE", values.get(1));
        assertEquals("color", values.get(2));
        assertEquals("god", values.get(3));

        values = p.getRawData();
        assertEquals("Username: username", values.get(0));
        assertEquals("God: god", values.get(1));
        assertEquals("Color: color", values.get(2));
        assertEquals("Status: LOSE", values.get(3));

    }

    @Test
    public void initNull() {
        assertThrows(NullPointerException.class, () -> {
            new Player(null, "status", "color", "god", 0);
        });
        assertThrows(NullPointerException.class, () -> {
            new Player("", "status", "color", "god", 0);
        });
    }

}