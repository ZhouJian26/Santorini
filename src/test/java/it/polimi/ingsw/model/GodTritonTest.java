package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GodTritonTest {
    Cell[][] board = new Cell[5][5];
    Action[][][] actions = new Action[5][5][3];
    GodInterface god = new GodTriton(new GodPower(God.TRITON, "aaa"));

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

        board[0][3].addBlock(new Block(TypeBlock.LEVEL1));
        board[0][3].addBlock(new Block(TypeBlock.LEVEL2));
        board[0][3].addBlock(new Block(TypeBlock.WORKER));

        board[2][0].addBlock(new Block(TypeBlock.LEVEL1));
        board[2][0].addBlock(new Block(TypeBlock.LEVEL2));
        board[2][0].addBlock(new Block(TypeBlock.LEVEL3));

    }

    /*
    verify Triton's power
    Triton's Worker can move one more time if it is on the perimeter
    Triton is active only in his worker's turn
     */
    @Test
    public void getEventTest(){
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("aaa");
        god.setWorker(new int[]{0,0});
        Event[] events=new Event[3];
        events[0]=Event.MOVE;
        god.getEvent(events,board,actions);
        assertTrue(actions[0][1][0].getStatus());
        assertTrue(actions[1][0][0].getStatus());
        assertTrue(actions[1][1][0].getStatus());
        for(int i=0;i<25;i++){
            assertTrue(!actions[i/5][i%5][1].getStatus());
            assertTrue(!actions[i/5][i%5][2].getStatus());
        }

        events=actions[0][1][0].execute(board);
        for(int i=0;i<25;i++){
            actions[i/5][i%5][0].set(false);
        }
        god.setWorker(new int[]{0,1});
        god.getEvent(events,board,actions);
        assertTrue(actions[0][2][0].getStatus());
        assertTrue(actions[0][0][0].getStatus());
        for(int i=0;i<25;i++){
            assertTrue(!actions[i/5][i%5][1].getStatus());
            assertTrue(!actions[i/5][i%5][2].getStatus());
        }
        events=actions[0][2][0].execute(board);
        for(int i=0;i<25;i++){
            actions[i/5][i%5][0].set(false);
        }
        god.setWorker(new int[]{0,2});
        god.getEvent(events,board,actions);
        assertTrue(actions[0][1][0].getStatus());
        assertTrue(!actions[0][3][0].getStatus());

        events=actions[0][1][0].execute(board);
        for(int i=0;i<25;i++){
            actions[i/5][i%5][0].set(false);
        }
        god.setWorker(new int[]{0,1});
        god.getEvent(events,board,actions);
        assertTrue(actions[0][2][0].getStatus());
        assertTrue(actions[0][0][0].getStatus());

        events=actions[1][1][0].execute(board);
        for(int i=0;i<25;i++){
            actions[i/5][i%5][0].set(false);
        }
        god.setWorker(new int[]{1,1});
        god.getEvent(events,board,actions);
        for(int i=0;i<25;i++){
            assertTrue(!actions[i/5][i%5][0].getStatus());
            assertTrue(!actions[i/5][i%5][1].getStatus());
            assertTrue(!actions[i/5][i%5][2].getStatus());
        }





    }


}
