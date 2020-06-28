package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class GameTest {
    @Test
    public void gameInizializationTWOException() {
        assertThrows(NullPointerException.class, () -> {
            new Game(GameMode.TWO, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "marco")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino", "pino")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino", "gino")));
        });
    }

    @Test
    public void gameInizializationTHREEException() {
        assertThrows(NullPointerException.class, () -> {
            new Game(GameMode.THREE, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "marco")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "pino")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "gino", "gino")));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "gino", "pallino")));
        });
    }

    @Test
    public void startQuitPlayerTWO() {
        Game game = new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino")));
        game.quitPlayer();
        assertTrue(game.getPlayerList().stream().allMatch(e -> e.getStatusPlayer() == StatusPlayer.IDLE));
    }

    @Test
    public void startQuitPlayerTHREE() {
        Game game = new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "pluto")));
        game.quitPlayer();
        assertTrue(game.getPlayerList().stream().allMatch(e -> e.getStatusPlayer() == StatusPlayer.IDLE));
    }

    @Test
    public void goodInitTWO() {
        ArrayList<String> listName = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, listName);

        assertTrue(listName.stream().allMatch(
                e -> game.getPlayerList().stream().map(p -> p.username).collect(Collectors.toList()).contains(e)));

        assertEquals(2, game.getPlayerList().size());

        assertEquals(1, game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                .collect(Collectors.toList()).size());

        assertEquals(GameMode.TWO, game.mode);

        assertEquals(GamePhase.SET_GOD_LIST, game.getPhase());

        Cell[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                assertEquals(0, board[i][j].getSize(), "Board Not empty");

        assertEquals(game.getCurrentPlayer(),
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                        .map(e -> e.username).collect(Collectors.toList()).get(0));

        game.setGodList(God.APOLLO);
        assertTrue(game.getGodList().contains(God.APOLLO));
        game.setGodList(God.ARTEMIS);
        assertTrue(game.getGodList().contains(God.ARTEMIS));
        game.choosePlayer("marco");
        assertEquals("marco", game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                .map(e -> e.username).collect(Collectors.toList()).get(0));
        game.setGod(God.APOLLO);
        assertTrue(game.getPlayerList().stream().filter(e -> e.username.equals("marco") && e.getGod() == God.APOLLO)
                .collect(Collectors.toList()).size() == 1);
        assertEquals("pino", game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                .map(e -> e.username).collect(Collectors.toList()).get(0));
        assertTrue(game.getPlayerList().stream().filter(e -> e.username.equals("pino") && e.getGod() == God.ARTEMIS)
                .collect(Collectors.toList()).size() == 1);

    }

    @Test
    public void goodInitTHREE() {
        ArrayList<String> listName = new ArrayList<>(Arrays.asList("marco", "pino", "pluto"));
        Game game = new Game(GameMode.THREE, listName);
        assertTrue(listName.stream().allMatch(
                e -> game.getPlayerList().stream().map(p -> p.username).collect(Collectors.toList()).contains(e)));

        assertEquals(3, game.getPlayerList().size());

        assertEquals(1, game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                .collect(Collectors.toList()).size());

        assertEquals(GameMode.THREE, game.mode);

        assertEquals(GamePhase.SET_GOD_LIST, game.getPhase());

        assertEquals(game.getCurrentPlayer(),
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                        .map(e -> e.username).collect(Collectors.toList()).get(0));
        game.setGodList(God.APOLLO);
        assertTrue(game.getGodList().contains(God.APOLLO));
        game.setGodList(God.ARTEMIS);
        assertTrue(game.getGodList().contains(God.ARTEMIS));
        game.setGodList(God.ZEUS);
        assertTrue(game.getGodList().contains(God.ZEUS));
    }
}
