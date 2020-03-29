package it.polimi.ingsw;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TypeBlock;
import it.polimi.ingsw.model.Worker;
import org.junit.Test;
import static org.junit.Assert.*;


public class WorkerTest {
    private String name= "abcdefg";
    @Test
    public void getOwnerTest()
    {
        assertEquals(name,new Worker(TypeBlock.WORKER,name, Color.BLUE).getOwner());
    }

    @Test
    public void getColorTest(){
        assertEquals(Color.BLUE,new Worker(TypeBlock.WORKER,name, Color.BLUE).getColor());
        assertEquals(Color.BROWN,new Worker(TypeBlock.WORKER,name, Color.BROWN).getColor());
        assertEquals(Color.WHITE,new Worker(TypeBlock.WORKER,name, Color.WHITE).getColor());
    }
}
