package it.polimi.ingsw.model;

class GodPan extends GodDecorator {

    /**
     * God Pan's class
     * @param godPower God's power
     */
    public GodPan(GodInterface godPower) {
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
        if (godPower.getCurrentPlayer().equals(godPower.getName()) && events[0].equals(Event.MOVE)
                && events[1].equals(Event.DOWN) && !events[2].equals(Event.ONE)
                && godPower.getLastGod().equals(God.STANDARD)) {
            godPower.setStatusPlayer(StatusPlayer.WIN);
            godPower.setLastGod(God.PAN);
        }
    }
}
