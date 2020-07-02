package it.polimi.ingsw.view.socket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Client parser of Game Info
 */
public class Parser extends Observable<ArrayList<Command>> implements Observer<String> {
    /**
     * Client Game State
     */
    private ArrayList<String> commandList = new ArrayList<>();

    /**
     * Get a copy of Client Game State
     * 
     * @return game state
     */
    private ArrayList<Command> duplicateCommandList() {
        return (ArrayList<Command>) commandList.stream().map(e -> new Gson().fromJson(e, Command.class))
                .collect(Collectors.toList());
    }

    /**
     * Update Client Game State
     * 
     * @param commandList new data to add or delete
     */
    private synchronized void setCommandList(ArrayList<Command> commandList) {

        // Discard all Actions
        this.commandList = (ArrayList<String>) this.commandList.stream()
                .filter(e -> !(new Gson().fromJson(e, Command.class)).getType().equals("action"))
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
     * Get Current available Type Command
     * 
     * @return an array list of string of actual filter available (type of command)
     */
    public List<String> getFilters() {
        return duplicateCommandList().stream().map(e -> e.getType()).distinct().collect(Collectors.toList());
    }

    /**
     * Get an arraylist of command of a certain type
     * 
     * @param filter a string to filter commands (type field)
     * @return array list with filtered command (type field)
     */
    public List<Command> getCommandList(String filter) {
        if (getFilters().contains(filter))
            return duplicateCommandList().stream().filter(e -> e.getType().equals(filter)).collect(Collectors.toList());
        return new ArrayList<>();
    }

    /**
     * Get all player available command
     * 
     * @return array list of all commands usable from users
     */
    public List<Command> getUsableCommandList() {
        return duplicateCommandList().stream().filter(e -> {
            return e.getFuncName() != null;
        }).collect(Collectors.toList());
    }

    /**
     * Convert a Command into a String to send to the server
     * 
     * @param command to parse into string
     * @return command parsed into string
     */
    public static String toString(Command command) {
        return new Gson().toJson(command);
    }

    /**
     * Get Game Board
     * 
     * @return Game Board
     */
    public Cell[][] getBoard() {
        List<Command> boardInfo = getCommandList("board");
        Cell[][] boardParsed = new Cell[5][5];
        boardInfo.forEach(e -> {
            try {
                boardParsed[Integer.parseInt(e.getFuncData()) / 5][Integer.parseInt(e.getFuncData()) % 5] = new Gson()
                        .fromJson(e.getInfo(), Cell.class);
            } catch (Exception err) {
                // Fail String to Int
            }
        });
        return boardParsed;
    }

    /**
     * Get Player Info
     * 
     * @return Arraylist of Player Info
     */
    public List<Player> getPlayers() {
        return getCommandList("player").stream().map(e -> new Gson().fromJson(e.getInfo(), Player.class))
                .collect(Collectors.toList());
    }

    /**
     * Get current player username
     * 
     * @return current player username
     */
    public String getCurrentPlayer() {
        return getCommandList("currentPlayer").stream().map(e -> e.getInfo()).reduce("", (p, e) -> p + e);
    }

    /**
     * Get game phase
     * 
     * @return game phase
     */
    public String getGamePhase() {
        return getCommandList("gamePhase").stream().map(e -> e.getInfo()).reduce("", (p, e) -> p + e);
    }
}