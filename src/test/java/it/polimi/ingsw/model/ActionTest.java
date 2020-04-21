package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActionTest {
    @Test
    public void getStatusTest() {

        Action action = new Action("Action");
        assertEquals(false, action.getStatus());
        action.set(true);
        assertEquals(true, action.getStatus());
        action = new Swap();
        int[] a = { 2, 2 };
        action.set(a, a, a, a, true);
        assertEquals(true, action.getStatus());

    }

    @Test
    public void setTest() {
        Action action = new Action("Action");
        action.set(true);
        assertEquals(true, action.getStatus());
    }

    @Test
    public void cloneTest() {
        assertTrue(true);
    }
}
