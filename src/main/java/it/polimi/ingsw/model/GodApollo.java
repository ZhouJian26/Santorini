package it.polimi.ingsw.model;

public class GodApollo extends GodDecorator {

    public GodApollo(GodInterface god) {
        super(god);
    }


    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        String namePlayer = godPower.getCurrentPlayer();
        if (events[0] == Event.ZERO && namePlayer.equals(godPower.getName())) {
            godPower.activate(true);
        }
        if (godPower.getStatus()) {
            setAction(map, actions);
            godPower.activate(false);
        }
    }

    @Override
    public void setAction(Cell[][] map, Action[][][] actions) {
        int[] position = godPower.getPositionWorker();
        String name = godPower.getName();
        Block block = null;
        int[] destination = new int[2];
        int i = position[0] - 1;
        if (i < 0) {
            i = 0;
        }
        int j = 0;
        for (; (i <= Math.min(4, position[0] + 1)); i++) {
            j = position[1] - 1;
            if (j < 0) {
                j = 0;
            }
            for (; j <= Math.min(4, position[1] + 1); j++) {
                block = map[i][j].getBlock(map[i][j].getSize());
                if (block.getTypeBlock().equals(TypeBlock.WORKER) && map[i][j].getSize() <= map[position[0]][position[1]].getSize() + 1) {
                    if (!block.getOwner().equals(name)) {
                        destination[0] = i;
                        destination[1] = j;
                        actions[i][j][0].set(position, destination, destination, position, true);
                        actions[i][j][0].setGod(God.APOLLO);
                    }
                }
            }
        }
    }
}

