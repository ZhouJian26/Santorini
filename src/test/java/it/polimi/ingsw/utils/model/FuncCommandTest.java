package it.polimi.ingsw.utils.model;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

public class FuncCommandTest {
    @Test
    public void searchNull() {
        assertNull(FuncCommand.getFromValue("harry upihdb wb"));
    }
}