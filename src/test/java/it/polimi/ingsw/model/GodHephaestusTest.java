package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GodHephaestusTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodHephaestus(new GodPower(God.HEPHAESTUS, "abc"));

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
        board[3][3].addBlock(new Block(TypeBlock.WORKER));

        board[2][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][3].addBlock(new Block(TypeBlock.LEVEL2));

        board[4][3].addBlock(new Block(TypeBlock.WORKER));

        board[2][2].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][2].addBlock(new Block(TypeBlock.LEVEL3));

        board[2][4].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][4].addBlock(new Block(TypeBlock.LEVEL3));
        board[2][4].addBlock(new Block(TypeBlock.DOME));

        god.addInfo(currentPlayer);
        god.setCurrentPlayer("abc");
        god.setWorker(new int[] { 3, 3 });
    }

    /*
    verify Hephaestus's power
    Hephaestus's worker can build one more time, not dome, at same position
    Hephaestus is active only in his worker's turn
     */
    @Test
    public void getEventTest() {
        Event[] event = new Event[3];
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        event[0] = Event.MOVE;
        god.getEvent(event, board, actions);

        board[4][4].addBlock(new Block(TypeBlock.LEVEL1));
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);
        assertEquals(actions[4][4][1].getStatus(), true);

        actions[4][4][1].set(false);

        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        event[0] = Event.MOVE;
        god.getEvent(event, board, actions);

        board[4][4].addBlock(new Block(TypeBlock.LEVEL2));
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);
        assertEquals(actions[4][4][1].getStatus(), true);

        actions[4][4][1].set(false);
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);

        event[0] = Event.MOVE;
        god.getEvent(event, board, actions);
        board[4][4].addBlock(new Block(TypeBlock.LEVEL2));
        board[4][4].addBlock(new Block(TypeBlock.LEVEL3));
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);
        assertEquals(actions[4][4][1].getStatus(), false);
        assertEquals(actions[4][4][2].getStatus(), false);


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
            }
        }

        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        event[0] = Event.MOVE;
        event[1] = Event.UP;
        god.getEvent(event, board, actions);
        board[1][1].addBlock(new Block(TypeBlock.LEVEL1));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(actions[i][j][2].getStatus(), false);
                assertEquals(actions[i][j][0].getStatus(), false);
                assertEquals(actions[i][j][1].getStatus(), false);
            }
        }


        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        board[2][2].addBlock(new Block(TypeBlock.LEVEL1));
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(actions[i][j][2].getStatus(), false);
                assertEquals(actions[i][j][0].getStatus(), false);
                assertEquals(actions[i][j][1].getStatus(), false);
            }
        }

        god.setCurrentPlayer("aaa");
        event[0] = Event.ZERO;
        god.getEvent(event, board, actions);
        event[0] = Event.MOVE;
        event[1] = Event.UP;
        god.getEvent(event, board, actions);
        board[4][4].addBlock(new Block(TypeBlock.LEVEL1));
        event[0] = Event.BUILD;
        god.getEvent(event, board, actions);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(actions[i][j][2].getStatus(), false);
                assertEquals(actions[i][j][0].getStatus(), false);
                assertEquals(actions[i][j][1].getStatus(), false);
            }
        }
    }
}
