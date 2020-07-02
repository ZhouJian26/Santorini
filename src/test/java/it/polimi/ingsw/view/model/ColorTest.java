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
    }

    @Test
    public void init() {
        Color c = new Color("Red");
        assertEquals("Red", c.getValue());
        assertEquals("Color: Red", c.getRawData().get(0));
    }
}