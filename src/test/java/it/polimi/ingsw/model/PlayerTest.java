package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class PlayerTest {
    @Test
    public void inizialization() {
        Player player = new Player("pluto");
        assertEquals("pluto", player.getUsername());
        assertEquals(null, player.getGod());
        assertEquals(null, player.getColor());
        assertEquals(StatusPlayer.IDLE, player.getStatusPlayer());
    }

    @Test
    public void inizializationNullName() {
        assertThrows(NullPointerException.class, () -> {
            new Player((String) null);
        });
        assertThrows(NullPointerException.class, () -> {
            new Player((Player) null);
        });
    }

    @Test
    public void setStatus() {
        Player player = new Player("pluto");
        player.setStatusPlayer(StatusPlayer.GAMING);
        assertEquals(StatusPlayer.GAMING, player.getStatusPlayer());
    }

    @Test
    public void setStatusNull() {
        Player player = new Player("pluto");
        assertThrows(NullPointerException.class, () -> {
            player.setStatusPlayer(null);
        });
    }

    @Test
    public void setColor() {
        Player player = new Player("pluto");
        player.setColor(Color.BLUE);
        assertEquals(Color.BLUE, player.getColor());
    }

    @Test
    public void setColorNull() {
        Player player = new Player("pluto");
        assertThrows(NullPointerException.class, () -> {
            player.setColor(null);
        });
    }

    @Test
    public void setGod() {
        Player player = new Player("pluto");
        player.setGod(God.APOLLO);
        assertEquals(God.APOLLO, player.getGod());
    }

    @Test
    public void setGodNull() {
        Player player = new Player("pluto");
        assertThrows(NullPointerException.class, () -> {
            player.setGod(null);
        });
    }

    @Test
    public void placeWorker() {
        Player player = new Player("pluto");
        int i = player.placeWoker();
        assertEquals(1, i);
    }

    @Test
    public void placeWorkerMore() {
        Player player = new Player("pluto");
        player.placeWoker();
        player.placeWoker();
        assertThrows(IllegalAccessError.class, () -> {
            player.placeWoker();
        });
    }
}