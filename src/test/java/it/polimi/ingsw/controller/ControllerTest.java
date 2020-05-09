package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.Notification;
import it.polimi.ingsw.view.socket.Parser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
        int j = 10;
        while (j > 0) {
            Game game = new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pallino")));
            Controller controller = new Controller(game);
            Parser parser = new Parser();
            game.addObservers(parser);
            game.start();
            int i = 250;
            while (i >= 0 && parser.getUsableCommandList().size() > 0) {
                String command = Parser.toString(
                        parser.getUsableCommandList().get(new Random().nextInt(parser.getUsableCommandList().size())));
                controller.update(new Notification(parser.getCommandList("currentPlayer").get(0).info, command));
                /* parser.getCommandList("player").forEach(e -> System.out.println(e.info)); */
                i--;
            }
            j--;
        }
    }

    @Test
    public void goodRun3() {
        int j = 10;
        while (j > 0) {
            Game game = new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "pallino")));
            Controller controller = new Controller(game);
            Parser parser = new Parser();
            game.addObservers(parser);
            game.start();
            int i = 500;
            while (i >= 0 && parser.getUsableCommandList().size() > 0) {
                String command = Parser.toString(
                        parser.getUsableCommandList().get(new Random().nextInt(parser.getUsableCommandList().size())));
                controller.update(new Notification(parser.getCommandList("currentPlayer").get(0).info, command));
                /* parser.getCommandList("player").forEach(e -> System.out.println(e.info)); */
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