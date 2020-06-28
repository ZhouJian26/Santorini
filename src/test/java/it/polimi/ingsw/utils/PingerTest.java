package it.polimi.ingsw.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class PingerTest {
    @Test
    public void init() throws InterruptedException {
        Pinger pinger = new Pinger(null);
        Thread th = new Thread(pinger);

        assertTrue(pinger.getStatus());
        th.run();
        Thread.sleep(25000);
        pinger.stop();
        assertFalse(pinger.getStatus());
    }
}