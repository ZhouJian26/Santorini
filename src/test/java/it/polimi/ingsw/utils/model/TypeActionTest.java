package it.polimi.ingsw.utils.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TypeActionTest {
    @Test
    public void init() {
        TypeAction ta = new TypeAction("type");
        assertEquals("type", ta.typeAction);
    }
}