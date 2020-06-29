package it.polimi.ingsw.model;

class GodMedusa extends GodDecorator {

    /**
     * God Medusa's class
     * @param godPower God's power
     */
    public GodMedusa(GodInterface godPower) {
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
        if (events[0].equals(Event.BUILD) && godPower.getName().equals(godPower.getCurrentPlayer())) {
            int[] position = godPower.getPositionWorker();
            setAction(map, position);
            for (int i = 0; i < 25; i++) {
                if ((map[i / 5][i % 5].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                        && map[i / 5][i % 5].getBlock().getOwner().equals(godPower.getCurrentPlayer()))
                        && (i / 5 != godPower.getPositionWorker()[0] || i % 5 != godPower.getPositionWorker()[1])) {
                    setAction(map, new int[] { i / 5, i % 5 });
                }
            }

        }
    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param position workers'positions
     */
    private void setAction(Cell[][] map, int[] position) {
        for (int i = Math.max(position[0] - 1, 0); i <= Math.min(position[0] + 1, 4); i++) {
            for (int j = Math.max(position[1] - 1, 0); j <= Math.min(position[1] + 1, 4); j++) {
                if (map[i][j].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                        && !map[i][j].getBlock().getOwner().equals(godPower.getName())
                        && map[i][j].getSize() < map[position[0]][position[1]].getSize()) {
                    map[i][j].popBlock();
                    switch (map[i][j].getSize()) {
                        case 0:
                            map[i][j].addBlock(new Block(TypeBlock.LEVEL1));
                            break;
                        case 1:
                            map[i][j].addBlock(new Block(TypeBlock.LEVEL2));
                            break;
                        case 2:
                            map[i][j].addBlock(new Block(TypeBlock.LEVEL3));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
