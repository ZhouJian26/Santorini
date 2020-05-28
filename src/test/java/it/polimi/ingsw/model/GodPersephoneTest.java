package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GodPersephoneTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodPersephone(new GodPower(God.PERSEPHONE, "aaa"));
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

    @Test
    public void test() {
        CurrentPlayer currentPlayer = new CurrentPlayer();
        god.addInfo(currentPlayer);
        GodInterface god2 = new GodStandard(new GodPower(God.STANDARD, null));
        god.setCurrentPlayer("abc");
        god.setWorker(new int[]{2, 2});
        Event[] events = new Event[3];
        events[0] = Event.ZERO;
        god2.addInfo(currentPlayer);
        god2.getEvent(events, board, actions);


        int i, j;
        for (i = 1; i < 3; i++) {
            for (j = 1; j < 3; j++) {
                if (i != 2 || j != 2) {
                    assertTrue(actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                }
                else {
                    assertTrue(!actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                }
            }
        }

        god.getEvent(events, board, actions);
        for (i = 1; i < 3; i++) {
            for (j = 1; j < 3; j++) {
                if (i != 2 || j != 2) {
                    assertTrue(actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                } else {
                    assertTrue(!actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                }
            }
        }
        board[4][4].addBlock(new Block(TypeBlock.LEVEL1));
        god.getEvent(events, board, actions);
        for (i = 1; i < 3; i++) {
            for (j = 1; j < 3; j++) {

                assertTrue(!actions[i][j][0].getStatus());
                assertTrue(!actions[i][j][1].getStatus());
                assertTrue(!actions[i][j][2].getStatus());

            }
        }
        god.setWorker(new int[]{4, 3});
        god2.getEvent(events, board, actions);
        god.getEvent(events, board, actions);
        for (i = 3; i < 5; i++) {
            for (j = 2; j < 5; j++) {
                if (i != 4 || j != 4) {
                    assertTrue(!actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                } else {
                    assertTrue(actions[i][j][0].getStatus());
                    assertTrue(!actions[i][j][1].getStatus());
                    assertTrue(!actions[i][j][2].getStatus());
                }
            }
        }
    }
}
