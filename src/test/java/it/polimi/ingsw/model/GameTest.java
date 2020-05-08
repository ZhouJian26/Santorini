package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;

class Report {
    Game game;

    public Report(Game game) {
        this.game = game;
    }

    public String getRaw() {
        return game.createReport(new ArrayList<Command>());
    }

    public ArrayList<Command> getParsed() {
        return new Gson().fromJson(game.createReport(new ArrayList<Command>()), new TypeToken<ArrayList<Command>>() {
        }.getType());
    }

    public ArrayList<Command> getDataFiltered(String filterParam) {
        if (getFilter().contains(filterParam))
            return (ArrayList<Command>) getParsed().stream().filter(e -> e.type.equals(filterParam))
                    .collect(Collectors.toList());
        return new ArrayList<Command>();
    }

    public ArrayList<String> getFilter() {
        return (ArrayList<String>) getParsed().stream().map(e -> e.type).distinct().collect(Collectors.toList());
    }

    public void printFilter() {
        ArrayList<String> filter = getFilter();
        System.out.println("\nFilters\n");
        for (String i : filter)
            System.out.println(i);
    }

    private void printer(ArrayList<Command> toPrint) {
        System.out.println("\nInfo:\n");
        for (Command i : toPrint)
            System.out.println("Type " + i.type + "\nInfo: " + i.info + "\nCommand: " + i.funcName + "\ntoSendData: "
                    + i.funcData + "\n");
    }

    public void printInfo() {
        printer(getParsed());
    }

    public void printInfo(String filter) {
        printer(getDataFiltered(filter));
    }

    public void printCommand() {
        printer(getCommand());
    }

    private ArrayList<Command> getCommand() {
        System.out.println("cosa vuoi fare?");
        return (ArrayList<Command>) getParsed().stream().filter(e -> {
            return e.funcName != null;
        }).collect(Collectors.toList());
    }
}

public class GameTest {
    @Test
    public void gameInizializationTWOException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Game(GameMode.TWO, null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList()));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "marco")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList()));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino", "pino")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino", "gino")));
        });
    }

    @Test
    public void gameInizializationTHREEException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Game(GameMode.THREE, null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList()));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "marco")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList()));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "pino")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "gino", "gino")));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "gino", "pallino")));
        });
    }

    @Test
    public void gameInitTWO() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, playerList);
        Report report = new Report(game);
        ArrayList<String> playerListComp = (ArrayList<String>) report.getDataFiltered("player").stream()
                .map(e -> new Gson().fromJson(e.info, Player.class)).map(e -> e.username).distinct()
                .collect(Collectors.toList());
        playerList.removeAll(playerListComp);
        assertTrue(playerList.size() == 0);
    }

    @Test
    public void gameInitTHREE() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino", "palla"));
        Game game = new Game(GameMode.THREE, playerList);
        Report report = new Report(game);
        ArrayList<String> playerListComp = (ArrayList<String>) report.getDataFiltered("player").stream()
                .map(e -> new Gson().fromJson(e.info, Player.class)).map(e -> e.username).distinct()
                .collect(Collectors.toList());
        playerList.removeAll(playerListComp);
        assertTrue(playerList.size() == 0);
    }

    @Test
    public void gameSetGodListTWOMultipleMore() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, playerList);
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;

        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ARTEMIS);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);

        ArrayList<String> godList = (ArrayList<String>) report.getDataFiltered("godList").stream().map(e -> e.info)
                .collect(Collectors.toList());
        godList.removeAll(new ArrayList<>(Arrays.asList("APOLLO", "ARTEMIS")));

        assertNotEquals(currentPlayer, report.getDataFiltered("currentPlayer").get(0).info);
        assertEquals(godList.size(), 0);
        assertEquals(report.getDataFiltered("gamePhase").get(0).info, "CHOOSE_GOD");
    }

    @Test
    public void gameSetGodListTHREEMultipleMore() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino", "palla"));
        Game game = new Game(GameMode.THREE, playerList);
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;

        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ARTEMIS);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);
        game.setGodList(currentPlayer, God.ATLAS);
        game.setGodList(currentPlayer, God.ATHENA);

        ArrayList<String> godList = (ArrayList<String>) report.getDataFiltered("godList").stream().map(e -> e.info)
                .collect(Collectors.toList());
        godList.removeAll(new ArrayList<>(Arrays.asList("APOLLO", "ARTEMIS", "ATHENA")));

        assertNotEquals(currentPlayer, report.getDataFiltered("currentPlayer").get(0).info);
        assertEquals(godList.size(), 0);
        assertEquals(report.getDataFiltered("gamePhase").get(0).info, "CHOOSE_GOD");
    }

    @Test
    public void simulationTWO() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, playerList);
        game.start();
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);
        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setGod(currentPlayer, God.ATHENA);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setColor(currentPlayer, Color.BLUE);
        game.setWorkers(currentPlayer, 0);
        game.setWorkers(currentPlayer, 1);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setColor(currentPlayer, Color.BROWN);
        game.setWorkers(currentPlayer, 2);
        game.setWorkers(currentPlayer, 3);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.chooseWorker(currentPlayer, 0);
        game.chooseWorker(currentPlayer, 1);
        game.chooseWorker(currentPlayer, 0);
        game.chooseAction(currentPlayer, new int[] { 5, 0 });

        game.chooseAction(currentPlayer, new int[] { 11, 1 });
        game.chooseAction(currentPlayer, null);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.chooseWorker(currentPlayer, 2);
        game.chooseWorker(currentPlayer, 3);
        game.chooseAction(currentPlayer, new int[] { 7, 0 });
        game.chooseAction(currentPlayer, new int[] { 13, 1 });
        game.chooseAction(currentPlayer, null);
    }

    @Test
    public void simulationTWO2() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, playerList);
        game.start();
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);
        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setGod(currentPlayer, God.ATHENA);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setColor(currentPlayer, Color.BLUE);
        game.setWorkers(currentPlayer, 1);
        game.setWorkers(currentPlayer, 0);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setColor(currentPlayer, Color.WHITE);
        game.setWorkers(currentPlayer, 3);
        game.setWorkers(currentPlayer, 10);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.chooseWorker(currentPlayer, 0);

        report.printCommand();
        report.getDataFiltered("board").stream().map(e -> new Gson().fromJson(e.info, Cell.class))
                .forEach(e -> e.printer());
        // report.printInfo("board");
    }
}
