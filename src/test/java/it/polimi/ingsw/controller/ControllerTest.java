package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.Notification;
import it.polimi.ingsw.view.socket.Parser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
            HashMap<String, Parser> playerMap = new HashMap<>();
            Game game = new Game(GameMode.TWO, playerList);
            Controller controller = new Controller(game);
            for (String x : playerList)
                playerMap.put(x, new Parser());
            playerMap.forEach((k, v) -> {
                controller.addObservers(k, v);
            });
            controller.startGame();
            int i = 250;
            while (i >= 0 && playerMap.get(game.getCurrentPlayer()).getUsableCommandList().size() > 0) {
                String command = Parser.toString(playerMap.get(game.getCurrentPlayer()).getUsableCommandList().get(
                        new Random().nextInt(playerMap.get(game.getCurrentPlayer()).getUsableCommandList().size())));
                controller.update(new Notification(game.getCurrentPlayer(), command));
                i--;
            }
            j--;
        }
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
            int i = 500;
            while (i >= 0 && playerMap.get(game.getCurrentPlayer()).getUsableCommandList().size() > 0) {
                String command = Parser.toString(playerMap.get(game.getCurrentPlayer()).getUsableCommandList().get(
                        new Random().nextInt(playerMap.get(game.getCurrentPlayer()).getUsableCommandList().size())));
                controller.update(new Notification(game.getCurrentPlayer(), command));
                i--;
            }
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