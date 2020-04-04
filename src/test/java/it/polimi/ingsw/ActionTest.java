package it.polimi.ingsw;
import it.polimi.ingsw.model.Action;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionTest {
    @Test
    public void getStatusTest(){

        Action action=new Action();
        assertEquals(false,action.getStatus());
        action.set(true);
        assertEquals(true,action.getStatus());
    }
    @Test
    public void setTest(){
        Action action=new Action();
        action.set(true);
        assertEquals(true,action.getStatus());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Action action=new Action();
        Action action1=action.clone();
        assertEquals(action.getStatus(),action1.getStatus());
        action1.set(true);
        assertEquals(false,action.getStatus());
    }
}
