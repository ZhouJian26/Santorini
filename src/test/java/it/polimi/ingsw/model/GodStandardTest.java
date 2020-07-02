package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GodStandardTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodStandard(new GodPower(God.STANDARD, "abc"));

    @Before
    public void setUp() {
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0;
                 j < 5; j++) {
                board[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
                actions[i][j][2] = new Build();
            }
        }

        board[3][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[3][3].addBlock(new
                Block(TypeBlock.LEVEL2));
        board[3][3].addBlock(new Block(TypeBlock.WORKER));

        board[2][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][3].addBlock(new
                Block(TypeBlock.LEVEL2));

        board[4][3].addBlock(new Block(TypeBlock.WORKER));

        board[2][2].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][2].addBlock(new
                Block(TypeBlock.LEVEL2));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL3));

        board[2][4].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][4].addBlock(new
                Block(TypeBlock.LEVEL2));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL3));
        board[2][4].addBlock(new Block(TypeBlock.DOME));
        board[3][2].addBlock(new
                Block(TypeBlock.LEVEL1));
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("aaa");
        god.setWorker(new int[]{3, 3});
        god.setLastGod(God.STANDARD);
    }

    /*
    verify Standard's power
    Standard god is the basic rules
    every player have to complete move and build at their turn
    a player win when his worker move up to level three
    a player lose when his worker is not able to complete his turn
     */
    @Test
    public void getEventTest() {
        Event[] event = new Event[3];
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        assertEquals(actions[2][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getStatus(), false);
        assertEquals(actions[2][2][0].getStatus(), true);
        assertEquals(actions[2][4][0].getStatus(), false);
        assertEquals(actions[0][0][0].getStatus(), false);
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);
        assertEquals(actions[2][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getStatus(), false);
        assertEquals(actions[2][2][0].getStatus(), true);
        assertEquals(actions[2][4][0].getStatus(), false);
        assertEquals(actions[0][0][0].getStatus(), false);
        event[0] = Event.MOVE;
        event[1] = Event.UP;
        god.getEvent(event, board, actions);
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                if ((i == 3 && j
                        == 3) || (i == 2 && j == 4) || (i == 4 && j == 3)) {
                    assertEquals(actions[i][j][1].getStatus(), false);
                } else if (i == 2 && j ==
                        2) {
                    assertEquals(actions[i][j][2].getStatus(), true);
                } else {
                    assertEquals(actions[i][j][1].getStatus(), true);
                }
            }
        }
        board[2][2].addBlock(new Block(TypeBlock.WORKER));
        god.setWorker(new int[]{
                2, 2});
        event[0] = Event.MOVE;
        event[1] = Event.UP;
        god.getEvent(event,
                board, actions);
        assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);
        event[0] =
                Event.ONE;
        god.getEvent(event, board, actions);
        assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
        god.setStatusPlayer(StatusPlayer.GAMING);
        god.getEvent(event, board,
                actions);
        assertEquals(StatusPlayer.IDLE, god.getPlayerStatus());
        for (int i
             = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        event[0] = Event.ONE;
        god.getEvent(event, board, actions);
        assertEquals(StatusPlayer.LOSE, god.getPlayerStatus());
        event[0] = Event.ONE;
        actions[0][0][0].set(true);
        god.getEvent(event, board, actions);

        assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());
        event[0] =
                Event.MOVE;
        event[1] = Event.ZERO;
        god.getEvent(event, board, actions);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        actions[0][0][1].set(true);
        event[0] = Event.ONE;
        god.getEvent(event, board,
                actions);
        assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());
        event[0]
                = Event.MOVE;
        god.getEvent(event, board, actions);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }

        actions[0][0][2].set(true);

        event[0] = Event.ONE;
        god.getEvent(event, board, actions);
        assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());

        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);
        for (int i = 0;
             i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        event[0] = Event.TWO;
        god.getEvent(event,
                board, actions);
        assertEquals(StatusPlayer.IDLE, god.getPlayerStatus());

        god.setStatusPlayer(StatusPlayer.WIN);
        event[0] = Event.TWO;
        god.getEvent(event, board, actions);
        assertEquals(StatusPlayer.WIN,
                god.getPlayerStatus());
        event[0] = Event.ONE;
        god.getEvent(event, board,
                actions);
        assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
    }

}
