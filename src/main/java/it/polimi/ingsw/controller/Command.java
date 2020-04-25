package it.polimi.ingsw.controller;

public class Command {
    public final String type;
    public final String info;
    public final String funcName;
    public final String funcData;

    /**
     * 
     * @param type
     * @param info
     */
    public Command(String type, String info) {
        if (type == null || info == null)
            throw new NullPointerException();
        this.type = type;
        this.info = info;
        this.funcName = null;
        this.funcData = null;
    }

    /**
     * 
     * @param type
     * @param funcName
     * @param info
     * @param funcData
     */
    public Command(String type, String funcName, String info, String funcData) {
        if (type == null || info == null)
            throw new NullPointerException();
        this.type = type;
        this.funcName = funcName;
        this.info = info;
        this.funcData = funcData;
    }
}