package it.polimi.ingsw.model;

class GodHera extends GodDecorator {

    /**
     * God Hera's class
     * @param godPower God's power
     */
    public GodHera(GodInterface godPower) {
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

        if ((events[0].equals(Event.MOVE) && !godPower.getName().equals(godPower.getCurrentPlayer())) && (godPower.getPositionWorker()[0] == 0 || godPower.getPositionWorker()[0] == 4 || godPower.getPositionWorker()[1] == 0 || godPower.getPositionWorker()[1] == 4) && !godPower.getPlayerStatus().equals(StatusPlayer.LOSE)) {
            godPower.setStatusPlayer(StatusPlayer.GAMING);
            godPower.setLastGod(God.HERA);
        }
    }
}
