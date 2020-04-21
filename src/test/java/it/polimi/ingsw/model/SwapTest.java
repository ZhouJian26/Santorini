package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwapTest {
    @Test
    public void executeTest() throws CloneNotSupportedException {
        Cell[][] map = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
        map[0][1].addBlock(new Block(TypeBlock.WORKER));
        Swap swap = new Swap();
        swap.set(new int[] { 0, 1 }, new int[] { 1, 0 }, new int[] { 2, 2 }, new int[] { 3, 3 }, true);
        swap.execute(map);
        assertEquals(map[1][0].getBlock(0).getTypeBlock(), TypeBlock.WORKER);
        assertEquals(map[0][1].getSize(), 0);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Swap swap = new Swap();
        Swap swap1 = (Swap) swap.clone();
        assertEquals(swap.getStatus(), swap1.getStatus());
        swap1.set(new int[] { 0, 1 }, new int[] { 1, 0 }, new int[] { 2, 2 }, new int[] { 3, 3 }, true);
        assertEquals(false, swap.getStatus());
    }

}
