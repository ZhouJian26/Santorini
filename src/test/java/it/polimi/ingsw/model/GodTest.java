package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class GodTest {
    @Test
    public void Search() {
        assertEquals(God.APOLLO, God.strConverter("APOLLO"));
    }

    @Test
    public void NotExists() {
        assertNull(God.strConverter("Macarena Tomfy!"));
    }
}