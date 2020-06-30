package it.polimi.ingsw.model;

class GodDemeter extends GodDecorator {

    /**
     * Board size
     */
    private int[][] size = new int[5][5];

    /**
     * First build position
     */
    private int[] position = null;

    /**
     * God GodDemeter's class
     * @param godPower God's power
     */
    public GodDemeter(GodInterface godPower) {
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

            if (events[0] == Event.ZERO) {
                position = null;
                godPower.activate(true);
            } else if (godPower.getStatus()) {
                if (events[0].equals(Event.MOVE)) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            size[i][j] = map[i][j].getSize();
                        }
                        position = new int[2];
                    }
                } else if (events[0].equals(Event.BUILD) && position != null) {
                    godPower.activate(false);
                    for (int i = 0; i < 25; i++) {
                        if (map[i / 5][i % 5].getSize() > size[i / 5][i % 5]) {
                            position[0] = i / 5;
                            position[1] = i % 5;
                            break;
                        }
                    }
                    setAction(map, actions);
                }
            }
        }

    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    private void setAction(Cell[][] map, Action[][][] actions) {
        build(map,actions,godPower.getPositionWorker());
        actions[position[0]][position[1]][1].set(false);
        actions[position[0]][position[1]][2].set(false);
    }
}
