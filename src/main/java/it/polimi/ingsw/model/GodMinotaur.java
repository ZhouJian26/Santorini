package it.polimi.ingsw.model;

public class GodMinotaur extends GodDecorator {
    public GodMinotaur(GodInterface godPower) {
        super(godPower);
    }

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
                        if (2 * i - position[0] >= 0 && 2 * i - position[0] <= 4 && 2 * j - position[1] >= 0 && 2 * j - position[1] <= 4) {
                            if (!map[2 * i - position[0]][2 * j - position[1]].getBlock(map[2 * i - position[0]][2 * j - position[1]].getSize()).getTypeBlock().equals(TypeBlock.DOME) && !map[2 * i - position[0]][2 * j - position[1]].getBlock(map[2 * i - position[0]][2 * j - position[1]].getSize()).getTypeBlock().equals(TypeBlock.WORKER)) {
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][0].set(position, destination, destination, position, true);
                                actions[i][j][0].setGod(God.MINOTAUR);
                            }
                        }
                    }

                }

            }
        }

    }

}
