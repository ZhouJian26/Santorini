package it.polimi.ingsw.model;

/**
 * Interface used to implement different type of Player actions on the Board
 */
public interface Action {
    /**
     * Execute the action on the given game board
     * 
     * @param map where apply the action effects
     * @return result event of the current action
     */
    Event[] execute(Cell[][] map);

    /**
     * Set the last god that changed this action
     * 
     * @param god god to set as last god that changed this action
     */
    void setGod(God god);

    /**
     * Disable any further changes on this action
     * 
     * @param blocked disable this action from further changes
     */
    void setBlocked(boolean blocked);

    /**
     * Get the action type
     * 
     * @return action type
     */
    String getTypeAction();

    /**
     * Set action status
     * 
     * @param status status to set
     */
    void set(boolean status);

    /**
     * Get action status
     * 
     * @return action status
     */
    boolean getStatus();

    Action clone();

    /**
     * Get the last god that changed this action
     * 
     * @return last god that changed this action
     */
    God getGod();

}
