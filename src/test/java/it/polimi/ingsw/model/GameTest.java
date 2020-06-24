package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/*
class Report implements Observer<String> {
    Game game;
    private String data;

    public Report(Game game) {
        this.game = game;
    }

    public String getRaw() {
        return data;
    }

    public ArrayList<Command> getParsed() {
        return new Gson().fromJson(data, new TypeToken<ArrayList<Command>>() {
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

    @Override
    public void update(String message) {
        data = message;
    }
}
*/
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
    public void startQuitPlayerTWO() {
        Game game = new Game(GameMode.TWO, new ArrayList<>(Arrays.asList("marco", "pino")));
        game.quitPlayer();
        Assertions.assertTrue(game.getPlayerList().stream().allMatch(e -> e.getStatusPlayer() == StatusPlayer.IDLE));
    }

    @Test
    public void startQuitPlayerTHREE() {
        Game game = new Game(GameMode.THREE, new ArrayList<>(Arrays.asList("marco", "pino", "pluto")));
        game.quitPlayer();
        Assertions.assertTrue(game.getPlayerList().stream().allMatch(e -> e.getStatusPlayer() == StatusPlayer.IDLE));
    }

    @Test
    public void goodInitTWO() {
        ArrayList<String> listName = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, listName);

        Assertions.assertTrue(listName.stream().allMatch(
                e -> game.getPlayerList().stream().map(p -> p.username).collect(Collectors.toList()).contains(e)));

        Assertions.assertEquals(2, game.getPlayerList().size());

        Assertions.assertEquals(1, game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                .collect(Collectors.toList()).size());

        Assertions.assertEquals(GameMode.TWO, game.mode);

        Assertions.assertEquals(GamePhase.SET_GOD_LIST, game.getPhase());
        Assertions.assertEquals(game.getCurrentPlayer(),
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                        .map(e -> e.username).collect(Collectors.toList()).get(0));

        game.setGodList(God.APOLLO);
        Assertions.assertTrue(game.getGodList().contains(God.APOLLO));
        game.setGodList(God.ARTEMIS);
        Assertions.assertTrue(game.getGodList().contains(God.ARTEMIS));
        game.choosePlayer("marco");
        Assertions.assertEquals("marco",
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                        .map(e -> e.username).collect(Collectors.toList()).get(0));
        game.setGod(God.APOLLO);
        Assertions.assertTrue(
                game.getPlayerList().stream().filter(e -> e.username.equals("marco") && e.getGod() == God.APOLLO)
                        .collect(Collectors.toList()).size() == 1);
        Assertions.assertEquals("pino",
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                        .map(e -> e.username).collect(Collectors.toList()).get(0));
        Assertions.assertTrue(
                game.getPlayerList().stream().filter(e -> e.username.equals("pino") && e.getGod() == God.ARTEMIS)
                        .collect(Collectors.toList()).size() == 1);

    }

    @Test
    public void goodInitTHREE() {
        ArrayList<String> listName = new ArrayList<>(Arrays.asList("marco", "pino", "pluto"));
        Game game = new Game(GameMode.THREE, listName);
        Assertions.assertTrue(listName.stream().allMatch(
                e -> game.getPlayerList().stream().map(p -> p.username).collect(Collectors.toList()).contains(e)));

        Assertions.assertEquals(3, game.getPlayerList().size());

        Assertions.assertEquals(1, game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                .collect(Collectors.toList()).size());

        Assertions.assertEquals(GameMode.THREE, game.mode);

        Assertions.assertEquals(GamePhase.SET_GOD_LIST, game.getPhase());

        Assertions.assertEquals(game.getCurrentPlayer(),
                game.getPlayerList().stream().filter(e -> e.getStatusPlayer() == StatusPlayer.GAMING)
                        .map(e -> e.username).collect(Collectors.toList()).get(0));
        game.setGodList(God.APOLLO);
        Assertions.assertTrue(game.getGodList().contains(God.APOLLO));
        game.setGodList(God.ARTEMIS);
        Assertions.assertTrue(game.getGodList().contains(God.ARTEMIS));
        game.setGodList(God.ZEUS);
        Assertions.assertTrue(game.getGodList().contains(God.ZEUS));
    }
}
