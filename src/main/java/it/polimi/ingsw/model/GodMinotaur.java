package it.polimi.ingsw.model;

class GodMinotaur extends GodDecorator {
    public GodMinotaur(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        String namePlayer = godPower.getCurrentPlayer();
        if (events[0] == Event.ZERO && namePlayer.equals(godPower.getName())) {
            godPower.activate(true);
            setAction(map, actions);
            godPower.activate(false);
        }
    }

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
                        && !block.getOwner().equals(name)
<<<<<<< HEAD
                ) {
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
                    if ((destination[0] >= 0 && destination[0] <= 4 && destination[1] >= 0
                            && destination[1] <= 4)
                            && (!map[destination[0]][destination[1]]
                            .getBlock(map[destination[0]][destination[1]].getSize()).getTypeBlock()
                            .equals(TypeBlock.DOME)
                            && !map[destination[0]][destination[1]]
                            .getBlock(map[destination[0]][destination[1]].getSize())
                            .getTypeBlock().equals(TypeBlock.WORKER))) {
                        actions[i][j][0].set(position, new int[]{i,j}, new int[]{i,j}, destination, true);
                    }
=======
                        && (2 * i - position[0] >= 0 && 2 * i - position[0] <= 4 && 2 * j - position[1] >= 0
                                && 2 * j - position[1] <= 4)
                        && (!map[2 * i - position[0]][2 * j - position[1]]
                                .getBlock(map[2 * i - position[0]][2 * j - position[1]].getSize()).getTypeBlock()
                                .equals(TypeBlock.DOME)
                                && !map[2 * i - position[0]][2 * j - position[1]]
                                        .getBlock(map[2 * i - position[0]][2 * j - position[1]].getSize())
                                        .getTypeBlock().equals(TypeBlock.WORKER))) {
                    destination[0] = i;
                    destination[1] = j;
                    ((Swap) actions[i][j][0]).set(position, destination, destination, position, true);
>>>>>>> dcafb10dbc29b76570ded174dbf57132e3699f8f
                }
            }
        }
    }
}
