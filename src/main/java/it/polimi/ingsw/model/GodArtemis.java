package it.polimi.ingsw.model;

class GodArtemis extends GodDecorator {

    /**
     * Count god's moves
     */
    private int count = 0;

    /**
     * Mark god's initial position
     */
    private int[] startPosition = new int[2];

    /**
     * God Apollo's class
     * @param godPower God's power
     */
    public GodArtemis(GodInterface godPower) {
        super(godPower);
    }

    /**
     * Get event
     * @param events event to be updated
     * @param map board situation at the moment
     * @param actions action of the event
     */
    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getName().equals(godPower.getCurrentPlayer())) {
            if (events[0] == Event.ZERO) {
                count = 1;
                startPosition = godPower.getPositionWorker();
            } else if (events[0].equals(Event.MOVE) && count == 1) {
                count = 0;
                setAction(map, actions);
            }
        }
    }
    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    private void setAction(Cell[][] map, Action[][][] actions) {
        move(map,actions,godPower.getPositionWorker());
        actions[startPosition[0]][startPosition[1]][0].set(false);
    }
}
