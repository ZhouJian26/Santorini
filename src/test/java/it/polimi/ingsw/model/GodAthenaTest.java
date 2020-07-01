package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GodAthenaTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodAthena(new GodPower(God.ATHENA, "abc"));
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
    }

    /*
    verify Athena's power
    if Athena's worker move up, opponent can not move up
    Athena is active only in opponent's turn
     */
    @Test
    public void getEventTest() {
        Event[] event = new Event[3];
        event[0] = Event.MOVE;
        event[1] = Event.UP;
        god.setCurrentPlayer("abc");
        god.getEvent(event, board, actions);
        assertEquals(true,god.getStatus());
        event[0]=Event.ZERO;
        god.getEvent(event, board, actions);
        assertEquals(false,god.getStatus());

        event[0] = Event.MOVE;
        event[1] = Event.UP;
        god.setCurrentPlayer("abc");
        god.setWorker(new int[] { 3, 3 });
        god.getEvent(event, board, actions);

        god.setCurrentPlayer("aaa");
        god.setWorker(new int[] { 3, 3 });
        event[0] = Event.ZERO;
        GodInterface god1 = new GodStandard(new GodPower(God.STANDARD, null));
        god1.addInfo(currentPlayer);
        god1.getEvent(event, board, actions);
        assertEquals(actions[2][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getStatus(), false);
        assertEquals(actions[2][2][0].getStatus(), true);
        assertEquals(actions[2][4][0].getStatus(), false);
        assertEquals(actions[0][0][0].getStatus(), false);

        god.getEvent(event, board, actions);

        assertEquals(actions[2][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getStatus(), false);
        assertEquals(actions[2][2][0].getStatus(), false);
        assertEquals(actions[2][4][0].getStatus(), false);
        assertEquals(actions[0][0][0].getStatus(), false);
        assertEquals(actions[2][2][0].getGod(), God.ATHENA);


        god.setCurrentPlayer("abc");
        event[0]=Event.ZERO;
        god.getEvent(event, board, actions);
        assertEquals(false,god.getStatus());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (true) {
                    actions[i][j][0].setBlocked(false);
                    actions[i][j][1].setBlocked(false);
                    actions[i][j][2].setBlocked(false);
                }
                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
                actions[i][j][0].setGod(null);
                actions[i][j][1].setGod(null);
                actions[i][j][2].setGod(null);

            }
        }

        god.setCurrentPlayer("aaa");
        god1.getEvent(event, board, actions);
        assertEquals(actions[2][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getStatus(), false);
        assertEquals(actions[2][2][0].getStatus(), true);
        assertEquals(actions[2][4][0].getStatus(), false);
        assertEquals(actions[0][0][0].getStatus(), false);

        god.getEvent(event, board, actions);
        assertEquals(actions[2][3][0].getStatus(), true);
        assertEquals(actions[4][3][0].getStatus(), false);
        assertEquals(actions[2][2][0].getStatus(), true);
        assertEquals(actions[2][4][0].getStatus(), false);
        assertEquals(actions[0][0][0].getStatus(), false);

    }
}
