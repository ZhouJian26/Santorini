package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodPanTest {
    @Test
    public void test(){
        GodInterface god=new GodPan(new GodPower(God.PAN,"aaa"));
        Event[] events=new Event[3];
        events[0]=Event.MOVE;
        events[1]=Event.DOWN;
        events[2]=Event.TWO;
        god.getEvent(events,null,null);
        assertEquals(StatusPlayer.WIN,god.getPlayerStatus());
    }
}
