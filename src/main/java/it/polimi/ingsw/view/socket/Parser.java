package it.polimi.ingsw.view.socket;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;

class Parser extends Observable<ArrayList<Command>> implements Observer<String> {
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
}