package it.polimi.ingsw;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class GodPrometheusTest {
    Cell[][] board=new Cell[5][5];
    Action[][][] actions=new Action[5][5][3];
    GodInterface god=new GodPrometheus(new GodPower(God.PROMETHEUS,"abc"));

    @Before
    public void setUp(){
        int i, j, k;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                board[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
                actions[i][j][2]=new Build();
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

        god.setCurrentPlayer("abc");
        god.setWorker(new int[]{3,3});
    }

    @Test
    public void getEventTest(){
        Event[] event=new Event[3];
        event[0]=Event.ZERO;
        god.getEvent(event,board,actions);

        for(int i=2;i<5;i++){
            for(int j=2;j<5;j++){
                if((i==3&&j==3)||(i==2&&j==4)||(i==4&&j==3)){
                    assertEquals(actions[i][j][1].getStatus(),false);
                }
                else if(i==2&&j==2){
                    assertEquals(actions[i][j][2].getStatus(),true);
                }
                else {
                    assertEquals(actions[i][j][1].getStatus(),true);
                }
            }
        }
        GodInterface god2=new GodStandard(new GodPower(God.STANDARD,null));
        event[0]=Event.BUILD;
        god2.getEvent(event,board,actions);
        assertEquals(actions[2][2][0].getStatus(),true);
        god.getEvent(event,board,actions);
        assertEquals(actions[2][2][0].getStatus(),false);


    }
}