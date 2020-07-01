package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GodPowerTest {
        GodPower god = new GodPower(God.STANDARD, "abc");

        @Test
        public void setGetLastGodTest() {
                god.addInfo(new CurrentPlayer());
                god.setLastGod(God.APOLLO);
                assertEquals(God.APOLLO, god.getLastGod());
        }

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
                god.activate(false);
                assertEquals(status, god.getStatus());
        }

        @Test
        public void setGetWorker() {
                god.addInfo(new CurrentPlayer());
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
                god.addInfo(new CurrentPlayer());
                god.setCurrentPlayer("ale");
                String name = god.getCurrentPlayer();
                assertEquals(name, god.getCurrentPlayer());
                name = "b";
                assertEquals("ale", god.getCurrentPlayer());

        }

        @Test
        public void setGetPlayerStatus() {
                god.addInfo(new CurrentPlayer());
                god.setStatusPlayer(StatusPlayer.WIN);
                assertEquals(StatusPlayer.WIN, god.getPlayerStatus());
                god.setStatusPlayer(StatusPlayer.IDLE);
                assertEquals(StatusPlayer.IDLE, god.getPlayerStatus());
                god.setStatusPlayer(StatusPlayer.LOSE);
                assertEquals(StatusPlayer.LOSE, god.getPlayerStatus());
                god.setStatusPlayer(StatusPlayer.GAMING);
                assertEquals(StatusPlayer.GAMING, god.getPlayerStatus());

        }

        @Test
        public void getEventTest() {
                god.getEvent(null, null, null);
                assertTrue(true);
        }

        /*
        verify that addInfo function adds the CurrentPlayer info
         */
        @Test
        public void addInfoTest() {
                CurrentPlayer currentPlayer = new CurrentPlayer(new int[] { 2, 2 },"aaa",StatusPlayer.IDLE,God.APOLLO);
                GodPower godPower = new GodPower(God.STANDARD, null);
                godPower.addInfo(currentPlayer);
                assertEquals(StatusPlayer.IDLE, godPower.getPlayerStatus());
                assertEquals(God.APOLLO, godPower.getLastGod());
                assertEquals("aaa", godPower.getCurrentPlayer());
        }
}
