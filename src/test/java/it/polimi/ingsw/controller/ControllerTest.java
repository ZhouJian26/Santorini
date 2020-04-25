package it.polimi.ingsw.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

public class ControllerTest {
    @Test
    public void init() {
        new Controller(new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino"))));
    }

    @Test
    public void initNull() {
        assertThrows(NullPointerException.class, () -> {
            new Controller(null);
        });
    }
}