package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TypeActionTest {
    @Test
    public void init() {
        TypeAction ta = new TypeAction("type");
        assertEquals("type", ta.getTypeAction());
    }
}