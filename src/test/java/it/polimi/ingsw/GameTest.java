package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.utils.model.Player;

class Report {
    Game game;

    public Report(Game game) {
        this.game = game;
    }

    public String getRaw() {
        return game.createReport();
    }

    public ArrayList<Command> getParsed() {
        return new Gson().fromJson(game.createReport(), new TypeToken<ArrayList<Command>>() {
        }.getType());
    }

    public ArrayList<Command> getDataFiltered(String filterParam) {
        if (getFilter().contains(filterParam))
            return (ArrayList<Command>) getParsed().stream().filter(e -> e.getType().equals(filterParam))
                    .collect(Collectors.toList());
        return new ArrayList<Command>();
    }

    public ArrayList<String> getFilter() {
        return (ArrayList<String>) getParsed().stream().map(e -> e.getType()).distinct().collect(Collectors.toList());
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
            System.out.println(i.getType() + ": " + i.getInfo());
    }

    public void printInfo() {
        printer(getParsed());
    }

    public void printInfo(String filter) {
        printer(getDataFiltered(filter));
    }
}

public class GameTest {
    @Test
    public void gameInizializationTWOException() {
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
                .map(e -> new Gson().fromJson(e.getInfo(), Player.class)).map(e -> e.username).distinct()
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
                .map(e -> new Gson().fromJson(e.getInfo(), Player.class)).map(e -> e.username).distinct()
                .collect(Collectors.toList());
        playerList.removeAll(playerListComp);
        assertTrue(playerList.size() == 0);
    }

    @Test
    public void gameSetGodListTWOMultipleMore() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, playerList);
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).getInfo();

        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ARTEMIS);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);

        ArrayList<String> godList = (ArrayList<String>) report.getDataFiltered("godList").stream().map(e -> e.getInfo())
                .collect(Collectors.toList());
        godList.removeAll(new ArrayList<>(Arrays.asList("APOLLO", "ARTEMIS")));

        assertNotEquals(currentPlayer, report.getDataFiltered("currentPlayer").get(0).getInfo());
        assertEquals(godList.size(), 0);
        assertEquals(report.getDataFiltered("gamePhase").get(0).getInfo(), "CHOOSE_GOD");
    }

    @Test
    public void gameSetGodListTHREEMultipleMore() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino", "palla"));
        Game game = new Game(GameMode.THREE, playerList);
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).getInfo();

        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ARTEMIS);
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);
        game.setGodList(currentPlayer, God.ATLAS);
        game.setGodList(currentPlayer, God.ATHENA);

        ArrayList<String> godList = (ArrayList<String>) report.getDataFiltered("godList").stream().map(e -> e.getInfo())
                .collect(Collectors.toList());
        godList.removeAll(new ArrayList<>(Arrays.asList("APOLLO", "ARTEMIS", "ATHENA")));

        assertNotEquals(currentPlayer, report.getDataFiltered("currentPlayer").get(0).getInfo());
        assertEquals(godList.size(), 0);
        assertEquals(report.getDataFiltered("gamePhase").get(0).getInfo(), "CHOOSE_GOD");
    }
}
