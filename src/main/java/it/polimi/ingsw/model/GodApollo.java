package it.polimi.ingsw.model;

class GodApollo extends GodDecorator {

    public GodApollo(GodInterface god) {
        super(god);
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
                        && !block.getOwner().equals(name)) {
                    destination[0] = i;
                    destination[1] = j;
                    actions[i][j][0].set(position, destination, destination, position, true);
                }
            }
        }
    }
}
