package it.polimi.ingsw.utils.model;

/**
 * Command Class used between to manage data between server and client
 */
public class Command {
    /**
     * Info Type that this Command contains
     * 
     * @see it.polimi.ingsw.utils.model.TypeCommand
     */
    private final String type;
    /**
     * Info
     * 
     */
    private final String info;
    /**
     * If this command can be used then this is not equals to null. This indicates
     * the function to use.
     */
    private final String funcName;
    /**
     * Data used on Server Side to launch function
     */
    private final String funcData;
    /**
     * Status used to decide if add or remove this information on Client Side. True
     * => add, False => Remove
     */
    private Boolean status;

    /**
     * Simple Command Constructor
     * 
     * @param type command type
     * @param info command info
     */
    public Command(String type, String info) {
        this.type = type;
        this.info = info;
        this.funcName = null;
        this.funcData = null;
    }

    /**
     * Usable Command Constructor
     * 
     * @param type     command type
     * @param funcName command function to launch
     * @param info     command info
     * @param funcData command data to use in the function
     */
    public Command(String type, String funcName, String info, String funcData) {
        this.type = type;
        this.funcName = funcName;
        this.info = info;
        this.funcData = funcData;
    }

    /**
     * Set Command status
     * 
     * @param status command status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Get Command status
     * 
     * @return command status true = add, false = delete
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Get Command type
     * 
     * @return aggregation type of command
     */
    public String getType() {
        return type;
    }

    /**
     * Get Command info
     * 
     * @return command info stored
     */
    public String getInfo() {
        return info;
    }

    /**
     * Get Command data to use with the function on server
     * 
     * @return function data to use
     */
    public String getFuncData() {
        return funcData;
    }

    /**
     * Get Command function to launch on server
     * 
     * @return function to run on server
     */
    public String getFuncName() {
        return funcName;
    }
}