package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwapTest {

    /*
   verify that Swap execute only with status true and return Event.Move with information of type of move(move up o move down o ecc..), when Swap has status false execute fails and return Event.Four
    */
    @Test
    public void executeTest() {
        Cell[][] map = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
        Event[] events=new Event[3];
        map[0][1].addBlock(new Block(TypeBlock.WORKER,"bbb",Color.BLUE));
        Swap swap = new Swap();
        swap.set(new int[] { 0, 1 }, new int[] { 1, 0 }, new int[] { 2, 2 }, new int[] { 3, 3 }, false);
        events=swap.execute(map);
        assertEquals(map[0][1].getBlock(0).getTypeBlock(), TypeBlock.WORKER);
        assertEquals(map[1][0].getSize(), 0);
        assertEquals(events[0],Event.FOUR);
        swap.set(new int[] { 0, 1 }, new int[] { 1, 0 }, new int[] { 2, 2 }, new int[] { 3, 3 }, true);
        events=swap.execute(map);
        assertEquals(map[1][0].getBlock(0).getTypeBlock(), TypeBlock.WORKER);
        assertEquals(map[0][1].getSize(), 0);
        assertEquals(events[0],Event.MOVE);
        assertEquals(events[1],Event.ZERO);
        map[1][1].addBlock(new Block(TypeBlock.LEVEL1));
        map[1][1].addBlock(new Block(TypeBlock.WORKER,"aaa",Color.WHITE));
        swap=new Swap();
        swap.set(new int[] { 1, 0 }, new int[] { 1, 1 }, new int[] { 1, 1 }, new int[] { 1, 0 }, true);
        events=swap.execute(map);
        assertEquals(events[0],Event.MOVE);
        assertEquals(events[1],Event.UP);
        assertEquals(map[1][0].getBlock().getTypeBlock(), TypeBlock.WORKER);
        assertEquals(map[1][0].getBlock().getOwner(), "aaa");
        assertEquals(map[1][0].getBlock().getColor(), Color.WHITE);
        assertEquals(map[1][1].getBlock().getTypeBlock(), TypeBlock.WORKER);
        assertEquals(map[1][1].getBlock().getOwner(), "bbb");
        assertEquals(map[1][1].getBlock().getColor(), Color.BLUE);
    }

     /*
    verify that swap and its clone have same status(if swap is blocked its clone has status false) and positions
     */
    @Test
    public void cloneTest() {
        Swap swap = new Swap();
        swap.set(new int[] { 3, 3 }, new int[] { 2, 2 }, new int[] { 2, 2 }, new int[] { 3, 3 }, true);
        Swap swap1 = (Swap) swap.clone();
        assertEquals(swap.getStatus(), swap1.getStatus());
        Cell[][] map = new Cell[5][5];
        map[3][3] = new Cell();
        map[2][2] = new Cell();
        map[3][3].addBlock(new Block(TypeBlock.WORKER));
        swap.execute(map);
        assertEquals(map[2][2].getBlock().getTypeBlock(),TypeBlock.WORKER);
        map[3][3] = new Cell();
        map[2][2] = new Cell();
        map[3][3].addBlock(new Block(TypeBlock.WORKER));
        swap1.execute(map);
        assertEquals(map[2][2].getBlock().getTypeBlock(),TypeBlock.WORKER);
    }

    /*verify that class Swap has typeAction Swap*/
    @Test
    public void getTypeActionTest() {
        assertEquals("Swap", new Swap().getTypeAction());
    }

    /*
   verify two set functions and get function
    */
    @Test
    public void setGetTest() {
        Swap swap = new Swap();
        swap.set(true);
        assertEquals(true, swap.getStatus());
        swap.set(new int[] { 3, 3 }, new int[] { 2, 2 }, new int[] { 2, 2 }, new int[] { 3, 3 }, true);
        assertEquals(true, swap.getStatus());
    }

    /*
    verify setGod and getGod tests
     */
    @Test
    public void setGetGodTest() {
        Swap swap = new Swap();
        swap.setGod(God.STANDARD);
        assertEquals(God.STANDARD, swap.getGod());
    }

    /*
    verify that when swap is blocked doesn't modify it's status
    */
    @Test
    public void setBlockedTest() {
        Swap swap = new Swap();
        swap.setBlocked(true);
        swap.set(true);
        assertFalse(swap.getStatus());
        swap.setBlocked(false);
        swap.set(true);
        assertTrue(swap.getStatus());
        swap.setBlocked(true);
        assertFalse(swap.getStatus());
    }


}
