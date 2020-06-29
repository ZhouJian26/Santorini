package it.polimi.ingsw.model;

class GodZeus extends GodDecorator {

    /**
     * God Zeus's class
     * @param godPower God's power
     */
    public GodZeus(GodInterface godPower) {
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
        if (godPower.getCurrentPlayer().equals(godPower.getName()) && (events[0] == Event.MOVE)) {
            setAction(map, actions);
        }
    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    private void setAction(Cell[][] map, Action[][][] actions) {
        int[] position = godPower.getPositionWorker();
        if (map[position[0]][position[1]].getSize() < 4) {
            int i = position[0];
            int j = position[1];
            TypeBlock typeBlock = null;
            int[] destination = new int[2];
            switch (map[i][j].getSize()) {
                case 1:
                    typeBlock = TypeBlock.LEVEL1;
                    destination[0] = i;
                    destination[1] = j;
                    ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                    break;
                case 2:
                    typeBlock = TypeBlock.LEVEL2;
                    destination[0] = i;
                    destination[1] = j;
                    ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                    break;
                case 3:
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
