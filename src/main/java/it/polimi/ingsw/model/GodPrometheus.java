package it.polimi.ingsw.model;

class GodPrometheus extends GodDecorator {
    /**
     * Worker's build times
     */
    int count = 0;

    /**
     * God Prometheus's class
     * @param godPower God's power
     */
    public GodPrometheus(GodInterface godPower) {
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
        if (events[0].equals(Event.ZERO) && godPower.getCurrentPlayer().equals(godPower.getName())) {
            godPower.activate(true);
            count = 1;
            setAction(map, actions);
            godPower.activate(false);
        } else if (events[0].equals(Event.BUILD)) {
            if (count == 1) {
                count = 0;
                setAction(map, actions);
            }
        } else if (events[0].equals((Event.MOVE)) && count == 1) {
            count = 0;
        }

    }
    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    public void setAction(Cell[][] map, Action[][][] actions) {
        if (count == 1) {
            build(map,actions,godPower.getPositionWorker());
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if ((map[i][j].getSize() > map[getPositionWorker()[0]][getPositionWorker()[1]].getSize())
                            || (map[i][j].getSize() == map[getPositionWorker()[0]][getPositionWorker()[1]].getSize()
                                    && !map[i][j].getBlock(map[i][j].getSize()).getTypeBlock()
                                            .equals(TypeBlock.WORKER))) {
                        actions[i][j][0].set(false);
                        actions[i][j][0].setGod(God.PROMETHEUS);
                        actions[i][j][0].setBlocked(true);
                    } else {
                        actions[i][j][0].setBlocked(false);
                        actions[i][j][0].setGod(God.PROMETHEUS);
                    }
                }
            }
        }
    }

}
