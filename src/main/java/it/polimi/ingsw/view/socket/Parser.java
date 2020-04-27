package it.polimi.ingsw.view.socket;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;

class Parser implements Observer<ArrayList<Command>> {
    private ArrayList<Command> commandList;

    public Parser() {
        commandList = new ArrayList<Command>();
    }

    private synchronized void setCommandList(ArrayList<Command> commandList) {
        if (commandList == null) {
            commandList = new ArrayList<>();
            return;
        }
        this.commandList = commandList;
    }

    @Override
    public void update(ArrayList<Command> commandList) {
        setCommandList(commandList);
    }

    public ArrayList<String> getFilters() {
        return (ArrayList<String>) commandList.stream().map(e -> e.type).distinct().collect(Collectors.toList());
    }

    public ArrayList<Command> getCommandList() {
        return new Gson().fromJson(new Gson().toJson(commandList), new TypeToken<ArrayList<Command>>() {
        }.getType());
    }

    public ArrayList<Command> getCommandList(String filter) {
        if (getFilters().contains(filter))
            return (ArrayList<Command>) commandList.stream().filter(e -> e.type.equals(filter))
                    .map(e -> new Gson().fromJson(new Gson().toJson(e), Command.class)).collect(Collectors.toList());
        return new ArrayList<Command>();
    }
}