package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GodPowerTest {
        GodInterface god = new GodPower(God.STANDARD, "abc");

        @Test
        public void getNameTest() {
                assertEquals("abc", god.getName());
        }

        @Test
        public void setGetStatusTest() {
                god.activate(true);
                boolean status = god.getStatus();
                assertEquals(status, god.getStatus());
                status = false;
                assertEquals(!status, god.getStatus());
        }

        @Test
        public void setGetWorker() {
                int[] position = new int[] { 2, 3 };
                god.setWorker(position);
                int[] position2 = god.getPositionWorker();
                assertEquals(position[0], position2[0]);
                assertEquals(position[1], position2[1]);
                position2[0] = 1;
                assertEquals(position[0], god.getPositionWorker()[0]);

        }

        @Test
        public void setGetCurrentPlayer() {
                god.setCurrentPlayer("ale");
                String name = god.getCurrentPlayer();
                assertEquals(name, god.getCurrentPlayer());
                name = "b";
                assertEquals("ale", god.getCurrentPlayer());
                GodInterface god2 = new GodApollo(new GodPower(God.APOLLO, "b"));
                assertEquals("ale", god2.getCurrentPlayer());
                god2.setCurrentPlayer("b");
                assertEquals("b", god.getCurrentPlayer());
        }

        @Test
        public void setGetPlayerStatus() {
                god.setStatusPlayer(StatusPlayer.WIN);
                assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
        }
}
