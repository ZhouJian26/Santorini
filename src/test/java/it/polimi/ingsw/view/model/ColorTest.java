package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class ColorTest {
    @Test
    public void initNull() {
        assertThrows(NullPointerException.class, () -> {
            new Color(null);
        });
        assertThrows(NullPointerException.class, () -> {
            new Color(null, "tosend");
        });
    }

    @Test
    public void init() {
        Color c = new Color("Red");
        assertEquals("Red", c.getValue());
        assertEquals("Color: Red", c.getRawData().get(0));
    }

    @Test
    public void initToRes() {
        Color c = new Color("Red", "toRes");
        assertEquals("Red", c.getValue());
        assertEquals("toRes", c.getToSend());
    }
}