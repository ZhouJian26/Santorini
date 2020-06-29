package it.polimi.ingsw.model;

class GodPoseidon extends GodDecorator {

    /**
     * Count build times
     */
    int count = 0;

    /**
     * Unmoved worker's position
     */
    int[] position = new int[2];

    /**
     * God Poseidon's class
     * @param godPower God's power
     */
    public GodPoseidon(GodInterface godPower) {
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
        if (godPower.getCurrentPlayer().equals(godPower.getName())) {
            if (events[0].equals(Event.BUILD) && count > 0) {
                setAction(map, actions, position);
                count--;
            } else if (events[0] == Event.ZERO) {
                count = 3;
                for (int i = 0; i < 25; i++) {
                    if ((map[i / 5][i % 5].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                            && map[i / 5][i % 5].getBlock().getOwner().equals(godPower.getName()))
                            && (i / 5 != godPower.getPositionWorker()[0] || i % 5 != godPower.getPositionWorker()[1])) {
                        position[0] = i / 5;
                        position[1] = i % 5;
                        break;
                    }
                }
            }
        }

    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     * @param positionWorker unmoved worker's position
     */
    private void setAction(Cell[][] map, Action[][][] actions, int[] positionWorker) {
        build(map, actions, positionWorker);
    }
}
