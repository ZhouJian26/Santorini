package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GodPoseidonTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodPoseidon(new GodPower(God.POSEIDON, "abc"));
    CurrentPlayer currentPlayer = new CurrentPlayer();

    @Before
    public void setUp() {
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                board[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
                actions[i][j][2] = new Build();
            }
        }

        board[3][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[3][3].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[4][3].addBlock(new Block(TypeBlock.WORKER, "abc", Color.WHITE));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL3));
        board[2][2].addBlock(new Block(TypeBlock.WORKER, "abc", Color.WHITE));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL3));
        board[2][4].addBlock(new Block(TypeBlock.DOME));
        god.addInfo(currentPlayer);
        god.setCurrentPlayer("aaa");
        god.setWorker(new int[]{3, 3});
    }

    /*
    verify Poseidon's power
    poseidon's unmoved Worker, if is on the ground level, it may build up to three times
    poseidon is active only in his worker's turn
     */
    @Test
    public void getEventTest() {
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("abc");
        god.setWorker(new int[]{
                2, 2
        });
        Event[] events = new Event[3];
        events[0] = Event.ZERO;
        god.getEvent(events, board, actions);

        events[0] = Event.BUILD;
        god.getEvent(events, board, actions);

        int i, j;
        for (i = 3; i < 5; i++) {
            for (j = 2; j < 5; j++) {
                if (i != 4 || j != 3) {
                    assertTrue(actions[i][j][1].getStatus());
                }
                else
                    assertTrue(!actions[i][j][1].getStatus());
            }
        }
        actions[4][4][1].execute(board);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        events[0] = Event.BUILD;
        god.getEvent(events, board, actions);
        for (i = 3; i < 5; i++) {
            for (j = 2; j < 5; j++) {
                if (i != 4 || j != 3) {
                    assertTrue(actions[i][j][1].getStatus());
                }
            }
        }
        actions[3][3][1].execute(board);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        events[0] = Event.BUILD;
        god.getEvent(events, board, actions);

        for (i = 3; i < 5; i++) {
            for (j = 2; j < 5; j++) {
                if (i == 3 && j == 3) {
                    assertTrue(actions[i][j][2].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][0].getStatus());
                } else if (i != 4 || j != 3) {
                    assertTrue(actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                    assertTrue(!actions[i][j][0].getStatus());
                }
            }
        }
        actions[3][3][1].execute(board);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        events[0] = Event.BUILD;
        god.getEvent(events, board, actions);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                assertTrue(!actions[i][j][0].getStatus());
                assertTrue(!actions[i][j][1].getStatus());
                assertTrue(!actions[i][j][2].getStatus());
            }
        }
    }
}
