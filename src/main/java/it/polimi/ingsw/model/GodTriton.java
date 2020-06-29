package it.polimi.ingsw.model;

public class GodTriton extends GodDecorator {

    /**
     * God Triton's class
     * @param godPower God's power
     */
    public GodTriton(GodInterface godPower) {
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
        if ((events[0].equals(Event.MOVE) && godPower.getName().equals(godPower.getCurrentPlayer()))
                && (godPower.getPositionWorker()[0] == 0 || godPower.getPositionWorker()[0] == 4
                || godPower.getPositionWorker()[1] == 0 || godPower.getPositionWorker()[1] == 4)) {
            setAction(map,actions);
        }
    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    private void setAction(Cell[][] map, Action[][][] actions){
        move(map,actions,godPower.getPositionWorker());
    }

}
