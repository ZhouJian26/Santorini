package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodMedusaTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodMedusa(new GodPower(God.MEDUSA, "abc"));
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
        board[2][3].addBlock(new Block(TypeBlock.WORKER, "bbb", Color.BROWN));

        board[4][3].addBlock(new Block(TypeBlock.WORKER, "abc", Color.WHITE));
        board[1][1].addBlock(new Block(TypeBlock.WORKER, "aaa", Color.BLUE));

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
        god.setWorker(new int[] { 3, 3 });
    }

    /*
    verify Medusa's power
    Medusa is active only in his worker's turn,after build, transforming neighboring opponent workers (at inferior level) in builds
     */
    @Test
    public void getEvetTest(){
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("abc");
        god.setWorker(new int[]{2,2});
        Event[] events=new Event[3];
        events[0]=Event.BUILD;
        god.getEvent(events,board,actions);
        assertEquals(board[1][1].getBlock().getTypeBlock(),TypeBlock.LEVEL1);
        assertEquals(board[2][3].getBlock().getTypeBlock(),TypeBlock.LEVEL2);
        assertEquals(board[3][3].getBlock().getTypeBlock(),TypeBlock.LEVEL3);
    }
}
