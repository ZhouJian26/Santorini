package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class GameModeTest {
    @Test
    public void convert() {
        assertEquals(null, GameMode.strConverter("potter fly"));
        assertEquals(GameMode.TWO, GameMode.strConverter("TWO"));
        assertEquals(GameMode.THREE, GameMode.strConverter("THREE"));
    }
}