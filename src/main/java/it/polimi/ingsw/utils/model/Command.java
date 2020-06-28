package it.polimi.ingsw.utils.model;

public class Command {
    public final String type;
    public final String info;
    public final String funcName;
    public final String funcData;
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
}