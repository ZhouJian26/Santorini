package it.polimi.ingsw.utils.model;

public class Command {
    private final String type;
    private final String info;
    private final String funcName;
    private final String funcData;
    private Boolean status;

    /**
     * 
     * @param type
     * @param info
     */
    public Command(String type, String info) {
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
        this.type = type;
        this.funcName = funcName;
        this.info = info;
        this.funcData = funcData;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public String getFuncData() {
        return funcData;
    }

    public String getFuncName() {
        return funcName;
    }
}