package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.StatusPlayer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.Notification;
import it.polimi.ingsw.view.socket.Parser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerTest {
        @Test
        public void init() {
                new Controller(new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino"))));
        }

        @Test
        public void initNull() {
                assertThrows(NullPointerException.class, () -> {
                        new Controller(null);
                });
        }

        @Test
        public void quitPlayer() {
                Game game = new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pallino")));
                Controller controller = new Controller(game);
                controller.update(new Notification("marco",
                                new Gson().toJson(new Command("quitPlayer", "quitPlayer", null, null))));
                assertEquals(GamePhase.END, game.getPhase(), "Game not ended");
        }

        @Test
        public void falseQuitPlayer() {
                Game game = new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pallino")));
                Controller controller = new Controller(game);
                controller.update(new Notification("marco 2",
                                new Gson().toJson(new Command("quitPlayer", "quitPlayer", null, null))));
                assertNotEquals(GamePhase.END, game.getPhase(), "False Quit");
        }

        @Test
        public void nullCommand() {
                Game game = new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pallino")));
                Controller controller = new Controller(game);
                controller.update(new Notification("marco", null));
                assertTrue(true, "Skip on null command");
        }

        @Test
        public void simulationsTwo() {
                for (int i = 0; i < 5; i++) {
                        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pallino"));
                        simulator(playerList);
                }
        }

        @Test
        public void simulationsThree() {
                for (int i = 0; i < 5; i++) {
                        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pallino", "pollo"));
                        simulator(playerList);
                }
        }

        @Test
        public void extremeCases() {
                Controller controller = new Controller(
                                new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino"))));
                controller.update(null);
                controller.update(new Notification("username",
                                Parser.toString(new Command("something", "setGod", "lore ipsum", "wrong data fun"))));
        }

        public void simulator(ArrayList<String> playerList) {
                // initializate a game
                HashMap<String, Parser> playerMap = new HashMap<>();
                Game game = new Game(playerList.size() == 2 ? GameMode.TWO : GameMode.THREE, playerList);
                Controller controller = new Controller(game);

                for (String x : playerList)
                        playerMap.put(x, new Parser());

                playerMap.forEach((k, v) -> {
                        controller.addObservers(k, v);
                });

                controller.startGame();
                int turn = 500;
                // run a game
                while (game.getPhase() != GamePhase.END && turn-- > 0) {
                        playerMap.forEach((k, v) -> {
                                // Clients Check
                                assertEquals(game.getPlayerList().size(), v.getPlayers().size(),
                                                "Different players list");
                                assertEquals(game.getCurrentPlayer(), v.getCurrentPlayer(), "Different current player");
                                assertFalse(v.getPlayers().stream()
                                                .anyMatch(e -> e.getStatus().equals(StatusPlayer.WIN.toString())),
                                                "A player Win the game, but game isn't ended");
                                assertTrue(v.getPlayers().stream()
                                                .anyMatch(e -> e.getStatus().equals(StatusPlayer.IDLE.toString())),
                                                "No in idle Player");
                                assertTrue(v.getPlayers().stream()
                                                .anyMatch(e -> e.getStatus().equals(StatusPlayer.GAMING.toString())),
                                                "No in Gaming Player");
                                assertEquals(1, v.getPlayers().stream()
                                                .filter(e -> e.getStatus().equals(StatusPlayer.GAMING.toString()))
                                                .count(), "More than one player in gaming mode");
                        });
                        // Current player & game checks
                        Parser currentParser = playerMap.get(game.getCurrentPlayer());
                        assertTrue(currentParser.getUsableCommandList().size() > 0,
                                        "Gaming player doesn't have any usable command");
                        assertEquals(StatusPlayer.GAMING.toString(),
                                        currentParser.getPlayers().stream()
                                                        .filter(e -> e.getUsername().equals(game.getCurrentPlayer()))
                                                        .map(e -> e.getStatus()).collect(Collectors.toList()).get(0),
                                        "Different Status of current player");
                        ArrayList<Command> commandList = (ArrayList<Command>) currentParser.getUsableCommandList();
                        String command = Parser.toString(commandList.get(new Random().nextInt(commandList.size())));
                        controller.update(new Notification(game.getCurrentPlayer(), command));
                        assertEquals(game.getCurrentPlayer(), playerMap.get(game.getCurrentPlayer()).getCurrentPlayer(),
                                        "Dismatch Current Player");
                }
                assertTrue(turn > 0, "Game Loop");
                // Check final game state for Clients
                playerMap.forEach((k, v) -> {
                        // Clients Check
                        assertEquals(game.getPlayerList().size(), v.getPlayers().size(), "Different players list");
                        assertEquals(1, v.getPlayers().stream()
                                        .filter(e -> e.getStatus().equals(StatusPlayer.WIN.toString())).count(),
                                        "More than one winner");
                        assertEquals(game.mode.getPlayersNum() - 1, v.getPlayers().stream()
                                        .filter(e -> e.getStatus().equals(StatusPlayer.LOSE.toString())).count(),
                                        "Not all other on Lose Status");
                        assertEquals(game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.WIN)
                                        .collect(Collectors.toList()).get(0).getUsername(),
                                        v.getPlayers().stream()
                                                        .filter(e -> e.getStatus().equals(StatusPlayer.WIN.toString()))
                                                        .collect(Collectors.toList()).get(0).getUsername(),
                                        "Different Winner Player");
                        assertEquals(0, v.getUsableCommandList().size(),
                                        "Still usable command list event if game ended");
                });
        }
}