package it.polimi.ingsw.view.socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Build;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Color;
import it.polimi.ingsw.view.model.God;
import it.polimi.ingsw.view.model.Swap;

class TypeAction {
    public final String TypeAction;

    public TypeAction(String type) {
        this.TypeAction = type;
    }
}

public class Parser extends Observable<ArrayList<Command>> implements Observer<String> {
    private ArrayList<Command> commandList;

    /**
     * Parser constructor
     */
    public Parser() {
        commandList = new ArrayList<Command>();
    }

    private ArrayList<Command> duplicateCommandList() {
        return new Gson().fromJson(new Gson().toJson(commandList), new TypeToken<ArrayList<Command>>() {
        }.getType());
    }

    private synchronized void setCommandList(ArrayList<Command> commandList) {
        if (commandList == null)
            throw new NullPointerException();
        this.commandList = commandList;
    }

    @Override
    public void update(String commandList) {
        try {
            //System.out.println("parser: " + commandList);
            setCommandList(new Gson().fromJson(commandList, new TypeToken<ArrayList<Command>>() {
            }.getType()));
            notify(duplicateCommandList());
        } catch (JsonSyntaxException e) {
        }
    }

    /**
     * 
     * @return an array list of string of actual filter available (type of command)
     */
    public ArrayList<String> getFilters() {
        return (ArrayList<String>) duplicateCommandList().stream().map(e -> e.type).distinct()
                .collect(Collectors.toList());
    }

    /**
     * 
     * @return full array list of commands
     */
    public ArrayList<Command> getCommandList() {
        return duplicateCommandList();
    }

    /**
     * 
     * @param filter a string to filter commands (type field)
     * @return array list with filtered command (type field)
     */
    public ArrayList<Command> getCommandList(String filter) {
        if (getFilters().contains(filter))
            return (ArrayList<Command>) duplicateCommandList().stream().filter(e -> e.type.equals(filter))
                    .collect(Collectors.toList());
        return new ArrayList<Command>();
    }

    /**
     * 
     * @return array list of all commands usable from users
     */
    public ArrayList<Command> getUsableCommandList() {
        return (ArrayList<Command>) duplicateCommandList().stream().filter(e -> {
            return e.funcName != null;
        }).collect(Collectors.toList());
    }




    /**
     * 
     * @param command to parse into string
     * @return command parsed into string
     */
    public static String toString(Command command) {
        return new Gson().toJson(command);
    }

    public Cell[][] getBoard() {
        ArrayList<Command> boardInfo = getCommandList("board");
        Cell[][] boardParsed = new Cell[5][5];
        boardInfo.forEach(e -> {
            try {
                boardParsed[Integer.parseInt(e.funcData) / 5][Integer.parseInt(e.funcData) % 5] = new Gson()
                        .fromJson(e.info, Cell.class);
                if (e.funcName != null)
                    boardParsed[Integer.parseInt(e.funcData) / 5][Integer.parseInt(e.funcData) % 5]
                            .setToSend(new Gson().toJson(e));
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
        return boardParsed;
    }

    public HashMap<Integer, ArrayList<Swap>> getSwaps() {
        ArrayList<Command> swapsInfo = (ArrayList<Command>) getCommandList("action").stream()
                .filter(e -> e.info != null && new Gson().fromJson(e.info, TypeAction.class).TypeAction.equals("Swap"))
                .collect(Collectors.toList());
        HashMap<Integer, ArrayList<Swap>> swapsParsed = new HashMap<>();
        swapsInfo.forEach(e -> {
            try {
                Swap toAdd = new Gson().fromJson(e.info, Swap.class);
                if (e.funcName != null)
                    toAdd.setToSend(new Gson().toJson(e));
                Integer index = new Gson().fromJson(e.funcData, int[].class)[0];
                if (swapsParsed.get(index) == null)
                    swapsParsed.put(index, new ArrayList<Swap>(Arrays.asList(toAdd)));
                else
                    swapsParsed.get(index).add(toAdd);
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
        return swapsParsed;
    }

    public HashMap<Integer, ArrayList<Build>> getBuilds() {
        ArrayList<Command> buildsInfo = (ArrayList<Command>) getCommandList("action").stream()
                .filter(e -> e.info != null && new Gson().fromJson(e.info, TypeAction.class).TypeAction.equals("Build"))
                .collect(Collectors.toList());
        HashMap<Integer, ArrayList<Build>> buildsParsed = new HashMap<>();
        buildsInfo.forEach(e -> {
            try {
                Build toAdd = new Gson().fromJson(e.info, Build.class);
                if (e.funcName != null)
                    toAdd.setToSend(new Gson().toJson(e));
                Integer index = new Gson().fromJson(e.funcData, int[].class)[0];
                if (buildsParsed.get(index) == null)
                    buildsParsed.put(index, new ArrayList<Build>(Arrays.asList(toAdd)));
                else
                    buildsParsed.get(index).add(toAdd);
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
        return buildsParsed;
    }

    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) getCommandList("player").stream().map(e -> new Gson().fromJson(e.info, Player.class))
                .collect(Collectors.toList());
    }

    public String getCurrentPlayer() {
        return getCommandList("currentPlayer").stream().map(e -> e.info).reduce("", (p, e) -> p + e);
    }

    public String getGamePhase() {
        return getCommandList("gamePhase").stream().map(e -> e.info).reduce("", (p, e) -> p + e);
    }

    public String getGameMode() {
        return getCommandList("gameMode").stream().map(e -> e.info).reduce("", (p, e) -> p + e);
    }

    public ArrayList<God> getListGod() {
        return (ArrayList<God>) getCommandList("god").stream()
                .map(e -> e.funcName == null ? new God(e.info) : new God(e.info, new Gson().toJson(e)))
                .collect(Collectors.toList());
    }

    public ArrayList<God> getChoosableGods() {
        return (ArrayList<God>) getCommandList("godList").stream()
                .map(e -> e.funcName == null ? new God(e.info) : new God(e.info, new Gson().toJson(e)))
                .collect(Collectors.toList());
    }

    public ArrayList<Color> getChoosableColors() {
        return (ArrayList<Color>) getCommandList("color").stream()
                .map(e -> e.funcName == null ? new Color(e.info) : new Color(e.info, new Gson().toJson(e)))
                .collect(Collectors.toList());
    }
}