package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuildTest {

    /*verify that class Build has typeAction Build*/
    @Test
    public void getTypeActionTest() {
        assertEquals("Build", new Build().getTypeAction());
    }

    /*
    verify two set functions and get function
     */
    @Test
    public void setGetTest() {
        Build build = new Build();
        build.set(true);
        assertEquals(true, build.getStatus());
        build.set(false, TypeBlock.LEVEL1, new int[]{0, 0});
        assertEquals(false, build.getStatus());
    }

    /*
    verify that Build execute only with status true and return Event.Build, when build has status false execute fails and return Event.Four
     */
    @Test
    public void executeTest() {
        Cell[][] map = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }
        int[] position = {2, 3};
        Build build = new Build();
        build.set(false, TypeBlock.LEVEL1, position);
        Event[] events = build.execute(map);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(TypeBlock.LEVEL0, map[2][3].getBlock(0).getTypeBlock());
            }
        }
        assertEquals(Event.FOUR, events[0]);
        build.set(true, TypeBlock.LEVEL1, position);
        events = build.execute(map);
        assertEquals(TypeBlock.LEVEL1, map[2][3].getBlock(0).getTypeBlock());
        assertEquals(Event.BUILD, events[0]);
    }


    /*
    verify setGod and getGod  tests
     */
    @Test
    public void setGetGodTest() {
        Build build = new Build();
        build.setGod(God.STANDARD);
        assertEquals(God.STANDARD, build.getGod());
    }

    /*
    verify that when build is blocked doesn't modify it's status
    */
    @Test
    public void setBlockedTest() {
        Build build = new Build();
        build.setBlocked(true);
        build.set(true);
        assertFalse(build.getStatus());
        build.setBlocked(false);
        build.set(true);
        assertTrue(build.getStatus());
        build.setBlocked(true);
        assertFalse(build.getStatus());
    }


    /*
    verify that build and its clone have same status(if build is blocked its clone has status false), typeBlock and position
     */
    @Test
    public void cloneTest() {
        Build build = new Build();
        build.set(true, TypeBlock.WORKER, new int[]{3, 3});
        Build build1 = (Build) build.clone();
        assertEquals(build.getStatus(), build1.getStatus());
        Cell[][] map = new Cell[5][5];
        map[3][3] = new Cell();
        build.execute(map);
        assertEquals(map[3][3].getBlock().getTypeBlock(), TypeBlock.WORKER);
        map[3][3] = new Cell();
        build1.execute(map);
        assertEquals(map[3][3].getBlock().getTypeBlock(), TypeBlock.WORKER);
    }


}
