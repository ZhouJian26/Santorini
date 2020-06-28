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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void goodRun2() {
        int j = 1;
        while (j > 0) {
            ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pallino"));
            simulator(playerList);
        }
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

        // run a game
        while (game.getPhase() != GamePhase.END) {
            playerMap.forEach((k, v) -> {
                // Clients Check
                assertEquals(game.getPlayerList().size(), v.getPlayers().size(), "Different players list");
                assertEquals(game.getCurrentPlayer(), v.getCurrentPlayer(), "Different current player");
                assertFalse(v.getPlayers().stream().anyMatch(e -> e.status.equals(StatusPlayer.WIN.toString())),
                        "A player Win the game, but game isn't ended");
                assertTrue(v.getPlayers().stream().anyMatch(e -> e.status.equals(StatusPlayer.IDLE.toString())),
                        "No in idle Player");
                assertTrue(v.getPlayers().stream().anyMatch(e -> e.status.equals(StatusPlayer.GAMING.toString())),
                        "No in Gaming Player");
                assertEquals(1,
                        v.getPlayers().stream().filter(e -> e.status.equals(StatusPlayer.GAMING.toString())).count(),
                        "More than one player in gaming mode");
            });
            // Current player & game checks
            Parser currentParser = playerMap.get(game.getCurrentPlayer());
            assertTrue(currentParser.getUsableCommandList().size() > 0,
                    "Gaming player doesn't have any usable command");
            assertEquals(StatusPlayer.GAMING.toString(),
                    currentParser.getPlayers().stream().filter(e -> e.username.equals(game.getCurrentPlayer()))
                            .map(e -> e.status).collect(Collectors.toList()).get(0),
                    "Different Status of current player");
            ArrayList<Command> commandList = (ArrayList<Command>) currentParser.getUsableCommandList();
            // todo switch case to study the command
            String command = Parser.toString(commandList.get(new Random().nextInt(commandList.size())));
            // todo add a verifier to each command used based on behaviour
            controller.update(new Notification(game.getCurrentPlayer(), command));
            assertEquals(game.getCurrentPlayer(), playerMap.get(game.getCurrentPlayer()).getCurrentPlayer(),
                    "Dismatch Current Player");
        }
        // todo controlla stato finale gioco
    }

    @Test
    public void goodRun3() {
        int j = 1;
        while (j > 0) {
            HashMap<String, Parser> playerMap = new HashMap<>();
            ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pallino", "pluto"));
            Game game = new Game(GameMode.THREE, playerList);
            Controller controller = new Controller(game);
            for (String x : playerList)
                playerMap.put(x, new Parser());
            playerMap.forEach((k, v) -> {
                controller.addObservers(k, v);
            });
            controller.startGame();
            while (playerMap.get(game.getCurrentPlayer()).getUsableCommandList().size() > 0
                    && game.getPhase() != GamePhase.END) {
                String command = Parser.toString(playerMap.get(game.getCurrentPlayer()).getUsableCommandList().get(
                        new Random().nextInt(playerMap.get(game.getCurrentPlayer()).getUsableCommandList().size())));
                // todo add a verifier to each command used based on behaviour
                controller.update(new Notification(game.getCurrentPlayer(), command));
                assertEquals(game.getCurrentPlayer(), playerMap.get(game.getCurrentPlayer()).getCurrentPlayer(),
                        "Dismatch Current Player");

            }
            // Check if there is a Winner
            assertEquals(1,
                    game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.WIN)
                            .collect(Collectors.toList()).size(),
                    game.getPlayerList().stream().map(e -> e.getGod() + " " + e.getStatusPlayer()).reduce("",
                            (p, e) -> p + " - " + e));
            // Check if all others are Loser
            assertEquals(game.mode.playersNum - 1, game.getPlayerList().stream()
                    .filter(e -> e.getStatusPlayer() == StatusPlayer.LOSE).collect(Collectors.toList()).size());

            // Check Status Game
            assertEquals(GamePhase.END, game.getPhase());
            j--;
        }
    }

    @Test
    public void extremeCases() {
        Controller controller = new Controller(new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino"))));

        controller.update(null);
        controller.update(new Notification("username",
                Parser.toString(new Command("something", "setGod", "lore ipsum", "wrong data fun"))));
    }
}