package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodPanTest {
    /*
    verify Pan's power
    pan's Worker also win if your Worker moves down two or more levels.
    pan is active only in his worker's turn
     */
    @Test
    public void getEventTest() {
        GodInterface god = new GodPan(new GodPower(God.PAN, "aaa"));
        god.addInfo(new CurrentPlayer());
        god.setCurrentPlayer("aaa");
        god.setStatusPlayer(StatusPlayer.GAMING);
        god.setLastGod(God.STANDARD);
        Event[] events = new Event[3];
        events[0] = Event.MOVE;
        events[1] = Event.DOWN;
        assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());
        events[2] = Event.TWO;
        god.getEvent(events, null, null);
        assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
    }
}
