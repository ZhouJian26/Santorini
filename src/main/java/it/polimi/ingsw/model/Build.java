package it.polimi.ingsw.model;

/**
 * Implements the Action -- BUILD
 */

class Build implements Action {

    /**
     * Type of the block
     */
    private TypeBlock block;

    /**
     * Position
     */
    private int[] position = new int[2];// useless

    /**
     * status
     */
    private boolean status;

    /**
     * if blocked
     */
    private boolean blocked;

    /**
     * Type of Action
     */
    private final String typeAction;

    /**
     * God
     */
    private God god;

    /**
     * Set type of Action
     */
    public Build() {
        typeAction = "Build";
    }

    /**
     * Get type of the action
     *
     * @return type of the action
     */
    @Override
    public String getTypeAction() {
        return typeAction;
    }

    /**
     * Set action status
     *
     * @param status status to set
     */
    @Override
    public void set(boolean status) {
        if (!blocked) {
            this.status = status;
        }
    }

    /**
     * Put the block on the board
     *
     * @param status   status
     * @param block    block
     * @param position position
     */
    public void set(boolean status, TypeBlock block, int[] position) {
        this.block = block;
        this.position[0] = position[0];
        this.position[1] = position[1];
        if (!blocked) {
            this.status = status;
        }
    }


    /**
     * Get the status
     *
     * @return the status
     */
    @Override
    public boolean getStatus() {
        return status && !blocked;
    }

    /**
     * @param map where to apply the action effects
     * @return result event of the action(Event.Four means that execution fails)
     */
    @Override
    public Event[] execute(Cell[][] map) {
        Event[] events = new Event[1];
        events[0] = Event.FOUR;
        if (getStatus()) {
            Block newBlock = new Block(block);
            map[position[0]][position[1]].addBlock(newBlock);
            events[0] = Event.BUILD;
        }
        return events;
    }

    /**
     * Set the last god that changed this action
     *
     * @param god god to set as last god that changed this action
     */
    @Override
    public void setGod(God god) {
        this.god = god;
    }

    /**
     * Get the last god that changed this action
     *
     * @return last god that changed this action
     */
    @Override
    public God getGod() {
        return god;
    }

    /**
     * Disable any further changes on this action
     *
     * @param blocked disable this action from further changes
     */
    @Override
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


    /**
     * Clone the action
     *
     * @return cloned action
     */
    @Override
    public Action clone() {
        Build build = new Build();
        build.set(this.getStatus(), block, position);
        return build;
    }


}
