package it.polimi.ingsw;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.TypeBlock;
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
