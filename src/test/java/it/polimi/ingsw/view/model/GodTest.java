package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class GodTest {
    @Test
    public void init() {
        God god = new God("god");
        assertEquals("god", god.getValue());
        assertEquals("God: god", god.getRawData().get(0));
    }

    @Test
    public void initToRes() {
        God god = new God("god", "tores");
        assertEquals("god", god.getValue());
        assertEquals("tores", god.getToSend());
    }

    @Test
    public void nullInit() {
        assertThrows(NullPointerException.class, () -> {
            new God(null);
        });
        assertThrows(NullPointerException.class, () -> {
            new God(null, "tosend");
        });
    }
}