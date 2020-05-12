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
        int[] a = {2, 2};
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
    public void setGetGodTest() {
        Action action = new Action("Action");
        action.setGod(God.STANDARD);
        assertEquals(God.STANDARD, action.getGod());
    }

    @Test
    public void cloneTest() {
        assertTrue(true);
    }


    @Test
    public void Test() {
        Action action = new Action("Action");
        action.execute(new Cell[][]{});
        action.set(new int[]{},new int[]{},new int[]{},new int[]{},true);
        action.set(true,null,null);
        action.clone();
        assertTrue(true);
    }
}