package it.polimi.ingsw;
import it.polimi.ingsw.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class IslandBoardTest {
    IslandBoard board=new IslandBoard();

    @Test
    public void getBoardTest()
    {
    }

    @Test
    public void getActionsTest()
    {
        board.addWorker("ciao",Color.BLUE,new int[]{2,3});
        God god=God.APOLLO;
        board.addGod("ciao",god);
        board.addGod("a",God.ATLAS);
        board.chooseWorker("ciao",new int[]{2,3});
    }

    @Test
    public void addWalkerTest() throws CloneNotSupportedException {

        int[] position1={2,3};
        int[] position2={3,3};
        board.addWorker("name", Color.BLUE,position1);
        board.addWorker("name", Color.BLUE,position2);
        assertEquals(TypeBlock.WORKER,board.getBoard()[2][3].getBlock(0).getTypeBlock());
        assertEquals(TypeBlock.WORKER,board.getBoard()[3][3].getBlock(0).getTypeBlock());
    }


}
