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

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;

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
}
