package it.polimi.ingsw.model;

public interface Action {
    /**
     * 
     * @param map where apply the action
     * @return result event of the current action
     */
    Event[] execute(Cell[][] map);

    /**
     * 
     * @param god set the last god that changed this action
     */
    void setGod(God god);

    /**
     * 
     * @param blocked disable this action from futher changes
     */
    void setBlocked(boolean blocked);

    /**
     * 
     * @return Build or Swap, depending on the type of action
     */
    String getTypeAction();

    /**
     * 
     * @param status set action status
     */
    void set(boolean status);

    boolean getStatus();

    Action clone();

    God getGod();

}
