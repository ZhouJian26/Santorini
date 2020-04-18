package it.polimi.ingsw.controller;

public class Command {
    String type;
    String command;
    String info;
    String dataFunc;

    public Command(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public Command(String type, String command, String info, String dataFunc) {
        this.type = type;
        this.command = command;
        this.info = info;
        this.dataFunc = dataFunc;
    }

    public String getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }

    public String getInfo() {
        return info;
    }

    public String getDataFunc() {
        return dataFunc;
    }
}