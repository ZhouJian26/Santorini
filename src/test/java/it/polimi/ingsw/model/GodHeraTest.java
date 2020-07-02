package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodHeraTest {

    /*
    verify Hera's power
    opponent's worker can not win on the perimeter
    Hera is active only in opponent's turn
     */
    @Test
    public void getEventTest() {
        CurrentPlayer currentPlayer=new CurrentPlayer();
        GodInterface god = new GodHera(new GodPower(God.HERA, "bbb"));
        god.addInfo(currentPlayer);
        god.setCurrentPlayer("aaa");
        god.setStatusPlayer(StatusPlayer.GAMING);
        god.setLastGod(God.STANDARD);
        god.setWorker(new int[]{0,0});
        Event[] events = new Event[3];
        GodInterface god1 = new GodPan(new GodPower(God.PAN, "aaa"));
        god1.addInfo(currentPlayer);
        events[0] = Event.MOVE;
        events[1] = Event.DOWN;
        assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());
        events[2] = Event.TWO;
        god1.getEvent(events, null, null);
        assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
        god.getEvent(events,null,null);
        assertEquals(StatusPlayer.GAMING,god.getPlayerStatus());

    }
}
