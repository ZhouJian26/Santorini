package it.polimi.ingsw.view.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.model.Cell;

public class Parser extends Observable<ArrayList<Command>> implements Observer<String> {
    private ArrayList<String> commandList = new ArrayList<>();

    private ArrayList<Command> duplicateCommandList() {
        return (ArrayList<Command>) commandList.stream().map(e -> new Gson().fromJson(e, Command.class))
                .collect(Collectors.toList());
    }

    private synchronized void setCommandList(ArrayList<Command> commandList) {

        // Discard all Actions
        this.commandList = (ArrayList<String>) this.commandList.stream()
                .filter(e -> !(new Gson().fromJson(e, Command.class)).type.equals("action"))
                .collect(Collectors.toList());

        commandList.forEach(e -> {
            if (e.getStatus() == true) {
                e.setStatus(null);
                this.commandList.add(new Gson().toJson(e));
            } else {
                e.setStatus(null);
                this.commandList.remove(new Gson().toJson(e));
            }
        });
    }

    @Override
    public void update(String commandList) {
        try {
            ArrayList<Command> parsed = new Gson().fromJson(commandList, new TypeToken<ArrayList<Command>>() {
            }.getType());
            if (parsed == null || parsed.isEmpty())
                return;
            setCommandList(parsed);
            notify(duplicateCommandList());
        } catch (JsonSyntaxException e) {
            // Fail Json Convert
        }
    }

    /**
     * 
     * @return an array list of string of actual filter available (type of command)
     */
    public List<String> getFilters() {
        return duplicateCommandList().stream().map(e -> e.type).distinct().collect(Collectors.toList());
    }

    /**
     * 
     * @param filter a string to filter commands (type field)
     * @return array list with filtered command (type field)
     */
    public List<Command> getCommandList(String filter) {
        if (getFilters().contains(filter))
            return duplicateCommandList().stream().filter(e -> e.type.equals(filter)).collect(Collectors.toList());
        return new ArrayList<>();
    }

    /**
     * 
     * @return array list of all commands usable from users
     */
    public List<Command> getUsableCommandList() {
        return duplicateCommandList().stream().filter(e -> {
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
        List<Command> boardInfo = getCommandList("board");
        Cell[][] boardParsed = new Cell[5][5];
        boardInfo.forEach(e -> {
            try {
                boardParsed[Integer.parseInt(e.funcData) / 5][Integer.parseInt(e.funcData) % 5] = new Gson()
                        .fromJson(e.info, Cell.class);
                if (e.funcName != null)
                    boardParsed[Integer.parseInt(e.funcData) / 5][Integer.parseInt(e.funcData) % 5].setToSend(e);
            } catch (Exception err) {
                // Fail String to Int
            }
        });
        return boardParsed;
    }

    public List<Player> getPlayers() {
        return getCommandList("player").stream().map(e -> {
            Player obj = new Gson().fromJson(e.info, Player.class);
            if (e.funcName != null)
                obj.setToSend(e);
            return obj;
        }).collect(Collectors.toList());
    }

    public String getCurrentPlayer() {
        return getCommandList("currentPlayer").stream().map(e -> e.info).reduce("", (p, e) -> p + e);
    }

    public String getGamePhase() {
        return getCommandList("gamePhase").stream().map(e -> e.info).reduce("", (p, e) -> p + e);
    }
}