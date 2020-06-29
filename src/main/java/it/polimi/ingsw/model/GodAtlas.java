package it.polimi.ingsw.model;

class GodAtlas extends GodDecorator {

    /**
     * God Atlas's class
     * @param godPower God's power
     */
    public GodAtlas(GodInterface godPower) {
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
        if (godPower.getCurrentPlayer().equals(godPower.getName()) && events[0].equals(Event.MOVE)) {
            godPower.activate(true);
            setAction(map, actions);
            godPower.activate(false);
        }
    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    private void setAction(Cell[][] map, Action[][][] actions) {
        int[] position = godPower.getPositionWorker();
        int[] destination = new int[2];
        TypeBlock typeBlock = null;
        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {
            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {
                if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER)
                        && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                    typeBlock = TypeBlock.DOME;
                    destination[0] = i;
                    destination[1] = j;
                    ((Build) actions[i][j][2]).set(true, typeBlock, destination);
                }
            }
        }
    }
}
