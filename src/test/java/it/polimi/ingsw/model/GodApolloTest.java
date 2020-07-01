package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GodApolloTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodApollo(new GodPower(God.APOLLO, "abc"));
    CurrentPlayer currentPlayer=new CurrentPlayer();

    /*
    initialize board and action ,add two worker,and add current player info
     */
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
        board[3][3].addBlock(new Block(TypeBlock.WORKER, "aaa", Color.BLUE));

        board[4][3].addBlock(new Block(TypeBlock.WORKER, "abc", Color.WHITE));

        god.addInfo(currentPlayer);
        god.setCurrentPlayer("abc");
        god.setWorker(new int[]{4, 3});
    }


    /*
    verify Apollo's power
    apollo's worker can swap his position with another worker
    apollo is active only in his worker's turn
     */
    @Test
    public void getEventTest() {
        Event[] event = new Event[3];
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        assertEquals(actions[3][3][0].getStatus(), true);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != 3 && j != 3) {
                    assertEquals(actions[i][j][0].getStatus(), false);
                }
                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }
        event[0] = Event.MOVE;
        god.getEvent(event, board, actions);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(false, actions[i][j][0].getStatus());
            }
        }
        god.setCurrentPlayer("aaa");
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(false, actions[i][j][0].getStatus());
            }
        }

    }
}
