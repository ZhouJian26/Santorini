package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GodApolloTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodApollo(new GodPower(God.APOLLO, "aaa"));
    CurrentPlayer currentPlayer=new CurrentPlayer();

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
        board[3][3].addBlock(new Block(TypeBlock.WORKER, "aaa", Color.BLUE));

        board[2][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][3].addBlock(new Block(TypeBlock.LEVEL2));

        board[4][3].addBlock(new Block(TypeBlock.WORKER, "abc", Color.WHITE));

        board[2][2].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL3));

        board[2][4].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL3));
        board[2][4].addBlock(new Block(TypeBlock.DOME));
        god.addInfo(currentPlayer);
        god.setCurrentPlayer("aaa");
        god.setWorker(new int[]{3, 3});
    }

    @Test
    public void getEventTest() {
        Event[] event = new Event[3];
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        assertEquals(actions[4][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getGod(), God.APOLLO);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != 4 && j != 3) {
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
        god.setCurrentPlayer("abc");
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(false, actions[i][j][0].getStatus());
            }
        }

    }
}
