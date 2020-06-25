package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuildTest {

    @Test
    public void executeTest() throws CloneNotSupportedException {
        Cell[][] map = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
        int[] position = { 2, 3 };
        Build build = new Build();
        build.set(true, TypeBlock.LEVEL1, position);
        build.execute(map);
        assertEquals(TypeBlock.LEVEL1, map[2][3].getBlock(0).getTypeBlock());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Build build = new Build();
        build.set(true, TypeBlock.WORKER, new int[] { 3, 3 });
        Build build1 = (Build) build.clone();
        assertEquals(build.getStatus(), build1.getStatus());
        build1.set(true, TypeBlock.WORKER, new int[] { 2, 3 });
        Cell[][] map = new Cell[5][5];
        map[3][3] = new Cell();
        map[2][3] = new Cell();
        build.execute(map);
        assertEquals(map[3][3].getBlock().getTypeBlock(), TypeBlock.WORKER);
        build1.execute(map);
        assertEquals(map[2][3].getBlock().getTypeBlock(), TypeBlock.WORKER);
    }

    @Test
    public void getStatusTest() {

        Build build = new Build();
        assertEquals(false, build.getStatus());
        build.set(true);
        assertEquals(true, build.getStatus());

    }

    @Test
    public void setTest() {
        Build build = new Build();
        build.set(true);
        assertEquals(true, build.getStatus());
    }

    @Test
    public void setGetGodTest() {
        Build build = new Build();
        build.setGod(God.STANDARD);
        assertEquals(God.STANDARD, build.getGod());
    }

    @Test
    public void test() {
        Build build = new Build();
        build.set(null, null, null, null, false);
        build.setBlocked(true);
        build.set(true);
        assertFalse(build.getStatus());
        assertTrue(true);
    }
}
