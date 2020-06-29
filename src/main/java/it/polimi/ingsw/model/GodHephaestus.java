package it.polimi.ingsw.model;

class GodHephaestus extends GodDecorator {

    /**
     * Board size
     */
    private int[][] size = new int[5][5];

    /**
     * First build position
     */
    private int[] position = new int[2];

    /**
     * God Hephaestus's class
     * @param godPower God's power
     */
    public GodHephaestus(GodInterface godPower) {
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
        if (map[position[0]][position[1]].getSize() < 3) {
            int i = position[0];
            int j = position[1];
            TypeBlock typeBlock = null;
            int[] destination = new int[2];
            switch (map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock()) {
                case LEVEL1:
                    typeBlock = TypeBlock.LEVEL2;
                    destination[0] = i;
                    destination[1] = j;
                    ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                    break;
                case LEVEL2:
                    typeBlock = TypeBlock.LEVEL3;
                    destination[0] = i;
                    destination[1] = j;
                    ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                    break;
                default:
                    break;
            }
        }
    }
}
