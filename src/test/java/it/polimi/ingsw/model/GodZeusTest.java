package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GodZeusTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodZeus(new GodPower(God.ZEUS, "abc"));
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

        board[3][3].addBlock(new Block(TypeBlock.WORKER, "abc", Color.WHITE));

    }

    /*
    verify zeus's power
    zeus's Worker can build under their worker
    zeus is active only in his worker's turn
     */
    @Test
    public void test() {
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("abc");
        god.setWorker(new int[]{3, 3});
        Event[] events = new Event[3];
        events[0] = Event.MOVE;
        god.getEvent(events, board, actions);
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (i == 3 && j == 3) {
                    assertTrue(actions[i][j][1].getStatus());
                } else {
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
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
        events[0] = Event.MOVE;
        god.getEvent(events, board, actions);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (i == 3 && j == 3) {
                    assertTrue(actions[i][j][1].getStatus());
                } else {
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
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
        events[0] = Event.MOVE;
        god.getEvent(events, board, actions);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (i == 3 && j == 3) {
                    assertTrue(actions[i][j][1].getStatus());
                } else {
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
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
        events[0] = Event.MOVE;
        god.getEvent(events, board, actions);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {

                assertTrue(!actions[i][j][1].getStatus());
                assertTrue(!actions[i][j][0].getStatus());
                assertTrue(!actions[i][j][2].getStatus());

            }
        }
    }
}
