package it.polimi.ingsw.model;

class Swap implements Action {
    /**
     * Initial position 1
     */
    private int[] x1 = new int[2];

    /**
     * Finale position 1
     */
    private int[] x2 = new int[2];

    /**
     * Initial position 2
     */
    private int[] y1 = new int[2];

    /**
     * Final position 2
     */
    private int[] y2 = new int[2];

    /**
     * swap's status (if it can be executed)
     */
    private boolean status;

    /**
     * if any god prohibits the swap
     */
    private boolean blocked;

    /**
     * type of the action
     */
    private final String typeAction;

    /**
     * last god that changed its status
     */
    private God god;

    /**
     * Swap
     */
    public Swap() {
        this.typeAction = "Swap";
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


    public void set(int[] x1, int[] x2, int[] y1, int[] y2, boolean status) {
        for (int i = 0; i < 2; i++) {
            this.x1[i] = x1[i];
            this.x2[i] = x2[i];
            this.y1[i] = y1[i];
            this.y2[i] = y2[i];
        }
        if (!blocked) {
            set(status);
        }
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
     * Get action status
     *
     * @return action status
     */
    @Override
    public boolean getStatus() {
        return status && (!blocked);
    }


    /**
     * Execute the action on the given game board
     *
     * @param map where to apply the action effects
     * @return result event of the current action
     */
    @Override
    public Event[] execute(Cell[][] map) {
        Event[] events = new Event[3];
        events[0] = Event.FOUR;
        if (getStatus()) {
            events[0] = Event.MOVE;
            if (y2[0] == y1[0] && y2[1] == y1[1]) {
                switch (map[x1[0]][x1[1]].getSize() - map[x2[0]][x2[1]].getSize()) {
                    case 1:
                        events[1] = Event.ZERO;
                        break;
                    case 0:
                        events[1] = Event.UP;
                        break;
                    case 2:
                        events[1] = Event.DOWN;
                        events[2] = Event.ONE;
                        break;
                    case 3:
                        events[1] = Event.DOWN;
                        events[2] = Event.TWO;
                        break;
                    case 4:
                        events[1] = Event.DOWN;
                        events[2] = Event.THREE;
                        break;
                    default:
                        break;
                }
            } else {
                if (map[x2[0]][x2[1]].getSize() - map[x1[0]][x1[1]].getSize() >= 1)
                    events[1] = Event.UP;
                else
                    events[1] = Event.ZERO;
            }
            Block block1 = map[x1[0]][x1[1]].popBlock();
            Block block2 = map[y1[0]][y1[1]].popBlock();
            map[y2[0]][y2[1]].addBlock(block2);
            map[x2[0]][x2[1]].addBlock(block1);
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
     * Clone
     *
     * @return clone of the action
     */
    @Override
    public Action clone() {
        Swap swapCopy = new Swap();
        swapCopy.set(x1, x2, y1, y2, this.getStatus());
        return swapCopy;

    }

}
