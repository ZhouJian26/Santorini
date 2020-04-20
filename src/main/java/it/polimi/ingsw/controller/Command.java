package it.polimi.ingsw.controller;

public class Command {
    public final String type;
    public final String info;
    public final String funcName;
    public final String funcData;

    public Command(String type, String info) {
        this.type = type;
        this.info = info;
        this.funcName = null;
        this.funcData = null;
    }

    public Command(String type, String funcName, String info, String funcData) {
        this.type = type;
        this.funcName = funcName;
        this.info = info;
        this.funcData = funcData;
    }
}