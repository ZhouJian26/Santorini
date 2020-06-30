package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public void setup() {
        ArrayList<String> listName = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, listName);

        String godLikeUsername = game.getCurrentPlayer();

        Cell[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                assertEquals(0, board[i][j].getSize(), "Board Not empty");

        game.setGodList(God.APOLLO);
        game.setGodList(God.ARTEMIS);

        assertEquals(2, game.getGodList().size());
        assertTrue(game.getGodList().contains(God.APOLLO));
        assertTrue(game.getGodList().contains(God.ARTEMIS));

        game.setGod(God.ARTEMIS);
        game.choosePlayer(godLikeUsername);
        game.setColor(Color.BLUE);
        game.setWorkers(0);
        game.setWorkers(1);
        game.setColor(Color.BROWN);
        game.setWorkers(2);
        game.setWorkers(3);

        assertFalse(game.getColors().contains(Color.BLUE));
        assertFalse(game.getColors().contains(Color.BROWN));
        assertEquals(0, game.getGodList().size());
        assertEquals(godLikeUsername, game.getCurrentPlayer());
        assertEquals(GamePhase.CHOOSE_WORKER, game.getPhase());
        assertEquals(Color.BLUE, game.getBoard()[0][0].getBlock().getColor());
        assertEquals(Color.BLUE, game.getBoard()[0][1].getBlock().getColor());
        assertEquals(Color.BROWN, game.getBoard()[0][2].getBlock().getColor());
        assertEquals(Color.BROWN, game.getBoard()[0][3].getBlock().getColor());
        assertEquals(God.APOLLO, game.getPlayerList().stream().filter(e -> e.getUsername().equals(godLikeUsername))
                .map(e -> e.getGod()).collect(Collectors.toList()).get(0));
        assertEquals(God.ARTEMIS, game.getPlayerList().stream()
                .filter(e -> e.getUsername()
                        .equals(listName.get((listName.indexOf(godLikeUsername) + 1) % game.mode.getPlayersNum())))
                .map(e -> e.getGod()).collect(Collectors.toList()).get(0));

        assertEquals(godLikeUsername, game.getCurrentPlayer());
        assertEquals(GamePhase.CHOOSE_WORKER, game.getPhase());
        game.chooseWorker(0);
        assertEquals(GamePhase.PENDING, game.getPhase());
        assertFalse(game.canEndTurn());

        assertTrue(game.getActions()[1][0][0].getStatus());
        game.chooseAction(new int[] { 5, 0 });
        assertEquals(GamePhase.CHOOSE_ACTION, game.getPhase());

        assertTrue(game.getActions()[0][0][1].getStatus());
        game.chooseAction(new int[] { 0, 1 });

        assertEquals(GamePhase.CHOOSE_WORKER, game.getPhase());
        assertNotEquals(godLikeUsername, game.getCurrentPlayer());
        assertEquals(TypeBlock.LEVEL1, game.getBoard()[0][0].getBlock().getTypeBlock());
        assertEquals(Color.BLUE, game.getBoard()[1][0].getBlock().getColor());

    }

    @Test
    public void winner() {
        ArrayList<String> listName = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, listName);

        String godLikeUsername = game.getCurrentPlayer();

        game.setGodList(God.APOLLO);
        game.setGodList(God.ARTEMIS);

        game.setGod(God.ARTEMIS);
        game.choosePlayer(godLikeUsername);
        game.setColor(Color.BLUE);
        game.setWorkers(0);
        game.setWorkers(1);
        game.setColor(Color.BROWN);
        game.setWorkers(2);
        game.setWorkers(3);

        game.chooseWorker(0);
        game.chooseAction(new int[] { 5, 0 });
        game.chooseAction(new int[] { 6, 1 });

        game.chooseWorker(2);
        game.chooseAction(new int[] { 6, 0 });
        game.chooseAction(new int[] { 7, 1 });

        game.chooseWorker(1);
        game.chooseAction(new int[] { 2, 0 });
        game.chooseAction(new int[] { 7, 1 });

        game.chooseWorker(6);
        game.chooseAction(new int[] { 7, 0 });
        game.chooseAction(new int[] { 12, 1 });

        game.chooseWorker(5);
        game.chooseAction(new int[] { 11, 0 });
        game.chooseAction(new int[] { 12, 1 });

        game.chooseWorker(3);
        game.chooseAction(new int[] { 8, 0 });
        game.chooseAction(new int[] { 12, 1 });

        game.chooseWorker(2);
        game.chooseAction(new int[] { 3, 0 });
        game.chooseAction(new int[] { 2, 1 });

        game.chooseWorker(7);
        game.chooseAction(new int[] { 12, 0 });

        assertEquals(GamePhase.END, game.getPhase());
        assertNotEquals(godLikeUsername,
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.WIN)
                        .map(e -> e.getUsername()).collect(Collectors.toList()).get(0));
        assertEquals(StatusPlayer.LOSE,
                game.getPlayerList().stream().filter(e -> e.getUsername().equals(godLikeUsername))
                        .map(e -> e.getStatusPlayer()).collect(Collectors.toList()).get(0));

    }
}
