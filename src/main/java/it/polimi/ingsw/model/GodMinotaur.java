package it.polimi.ingsw.model;

class GodMinotaur extends GodDecorator {

    /**
     * God Minotaur's class
     * @param godPower God's power
     */
    public GodMinotaur(GodInterface godPower) {
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
                    if (position[0] > i) {
                        destination[0] = i - 1;
                    } else if (position[0] < i) {
                        destination[0] = i + 1;
                    } else {
                        destination[0] = i;
                    }
                    if (position[1] > j) {
                        destination[1] = j - 1;
                    } else if (position[1] < j) {
                        destination[1] = j + 1;
                    } else {
                        destination[1] = j;
                    }
                    if ((destination[0] >= 0 && destination[0] <= 4 && destination[1] >= 0 && destination[1] <= 4)
                            && (!map[destination[0]][destination[1]]
                                    .getBlock(map[destination[0]][destination[1]].getSize()).getTypeBlock()
                                    .equals(TypeBlock.DOME)
                                    && !map[destination[0]][destination[1]]
                                            .getBlock(map[destination[0]][destination[1]].getSize()).getTypeBlock()
                                            .equals(TypeBlock.WORKER))) {
                        ((Swap) actions[i][j][0]).set(position, new int[] { i, j }, new int[] { i, j }, destination,
                                true);
                    }
                }
            }
        }
    }
}
