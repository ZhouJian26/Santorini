package it.polimi.ingsw.model;

class GodApollo extends GodDecorator {
    /**
     * God Artemis's class
     * @param godPower God's power
     */
    public GodApollo(GodInterface godPower) {
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
        String namePlayer = godPower.getCurrentPlayer();
        if (events[0] == Event.ZERO && namePlayer.equals(godPower.getName())) {
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
        String name = godPower.getName();
        Block block = null;
        int[] destination = new int[2];

        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {

            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {
                block = map[i][j].getBlock(map[i][j].getSize());
                if (block.getTypeBlock().equals(TypeBlock.WORKER)
                        && map[i][j].getSize() <= map[position[0]][position[1]].getSize() + 1
                        && !block.getOwner().equals(name)) {
                    destination[0] = i;
                    destination[1] = j;
                    ((Swap) actions[i][j][0]).set(position, destination, destination, position, true);
                }
            }
        }
    }
}
