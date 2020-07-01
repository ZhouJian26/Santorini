package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GodDecoratorTest {
    GodDecorator god = new GodDecorator(new GodPower(God.STANDARD, "abc"));

    @Test
    public void getNameTest() {
        assertEquals("abc", god.getName());
    }

    @Test
    public void setGetStatusTest() {
        god.activate(true);
        boolean status = god.getStatus();
        assertEquals(status, god.getStatus());
        status = false;
        assertEquals(!status, god.getStatus());
    }

    @Test
    public void setGetWorker() {
        god.addInfo(new CurrentPlayer());
        int[] position = new int[]{2, 3};
        god.setWorker(position);
        int[] position2 = god.getPositionWorker();
        assertEquals(position[0], position2[0]);
        assertEquals(position[1], position2[1]);
        position2[0] = 1;
        assertEquals(position[0], god.getPositionWorker()[0]);

    }

    @Test
    public void setGetCurrentPlayer() {
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("ale");
        String name = god.getCurrentPlayer();
        assertEquals(name, god.getCurrentPlayer());
        name = "b";
        assertEquals("ale", god.getCurrentPlayer());

    }

    @Test
    public void setGetPlayerStatus() {
        god.addInfo(new CurrentPlayer());
        god.setStatusPlayer(StatusPlayer.WIN);
        assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
        god.setStatusPlayer(StatusPlayer.IDLE);
        assertEquals(StatusPlayer.IDLE, god.getPlayerStatus());
        god.setStatusPlayer(StatusPlayer.LOSE);
        assertEquals(StatusPlayer.LOSE, god.getPlayerStatus());
        god.setStatusPlayer(StatusPlayer.GAMING);
        assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());

    }

    @Test
    public void getEventTest() {
        god.getEvent(null, null, null);
        assertTrue(true);
    }

    @Test
    public void setGetLastGodTest() {
        god.addInfo(new CurrentPlayer());
        god.setLastGod(God.APOLLO);
        assertEquals(God.APOLLO, god.getLastGod());
    }

    @Test
    public void addInfoTest() {
        CurrentPlayer currentPlayer = new CurrentPlayer(new int[]{2, 2}, "aaa", StatusPlayer.IDLE, God.APOLLO);
        GodPower godPower = new GodPower(God.STANDARD, null);
        godPower.addInfo(currentPlayer);
        assertEquals(StatusPlayer.IDLE, godPower.getPlayerStatus());
        assertEquals(God.APOLLO, godPower.getLastGod());
        assertEquals("aaa", godPower.getCurrentPlayer());
    }

    /*
    verity that move follows standard move rules(can't move on a dome, can't move two cells, can't move on a worker, can't move up two o three blocks,ecc)
     */
    @Test
    public void moveTest() {
        Cell[][] map = new Cell[5][5];
        Action[][][] actions = new Action[5][5][1];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
                actions[i][j][0] = new Swap();
            }
        }
        map[1][0].addBlock(new Block(TypeBlock.LEVEL1));
        map[1][0].addBlock(new Block(TypeBlock.WORKER));
        map[0][0].addBlock(new Block(TypeBlock.LEVEL1));
        map[0][1].addBlock(new Block(TypeBlock.LEVEL1));
        map[0][1].addBlock(new Block(TypeBlock.LEVEL2));
        map[1][1].addBlock(new Block(TypeBlock.LEVEL1));
        map[1][1].addBlock(new Block(TypeBlock.LEVEL2));
        map[1][1].addBlock(new Block(TypeBlock.LEVEL3));
        map[2][1].addBlock(new Block(TypeBlock.DOME));
        god.move(map, actions, new int[]{1, 0});
        assertTrue(actions[0][0][0].getStatus());
        actions[0][0][0].set(false);
        assertTrue(actions[0][1][0].getStatus());
        actions[0][1][0].set(false);
        assertTrue(!actions[1][1][0].getStatus());
        assertTrue(!actions[2][1][0].getStatus());
        assertTrue(actions[2][0][0].getStatus());
        actions[2][0][0].set(false);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(!actions[i][j][0].getStatus());
            }
        }
    }


    /*
    verity that build follows standard build rules
     */
    @Test
    public void buildTest() {
        Cell[][] map = new Cell[5][5];
        Action[][][] actions = new Action[5][5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
                actions[i][j][2] = new Build();
            }
        }
        map[1][0].addBlock(new Block(TypeBlock.LEVEL1));
        map[1][0].addBlock(new Block(TypeBlock.WORKER));

        map[0][0].addBlock(new Block(TypeBlock.LEVEL1));

        map[0][1].addBlock(new Block(TypeBlock.LEVEL1));
        map[0][1].addBlock(new Block(TypeBlock.LEVEL2));

        map[1][1].addBlock(new Block(TypeBlock.LEVEL1));
        map[1][1].addBlock(new Block(TypeBlock.LEVEL2));
        map[1][1].addBlock(new Block(TypeBlock.LEVEL3));

        map[2][1].addBlock(new Block(TypeBlock.DOME));

        god.build(map, actions, new int[]{1, 0});
        assertTrue(actions[0][0][1].getStatus());
        actions[0][0][1].set(false);
        assertTrue(actions[0][1][1].getStatus());
        actions[0][1][1].set(false);
        assertTrue(!actions[1][1][1].getStatus());
        assertTrue(actions[1][1][2].getStatus());
        actions[1][1][2].set(false);
        assertTrue(!actions[2][1][1].getStatus());
        assertTrue(actions[2][0][1].getStatus());
        actions[2][0][1].set(false);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(!actions[i][j][0].getStatus());
                assertTrue(!actions[i][j][1].getStatus());
                assertTrue(!actions[i][j][2].getStatus());
            }
        }
    }

}
