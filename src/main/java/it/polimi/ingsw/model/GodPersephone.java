package it.polimi.ingsw.model;

class GodPersephone extends GodDecorator {
    public GodPersephone(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {

        if (!godPower.getName().equals(godPower.getCurrentPlayer())) {
            if (!setEvent(map, actions, godPower.getPositionWorker(), false)) {
                for (int i = 0; i < 25; i++) {
                    if ((map[i / 5][i % 5].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                            && map[i / 5][i % 5].getBlock().getOwner().equals(godPower.getCurrentPlayer()))
                            && (i / 5 != godPower.getPositionWorker()[0] || i % 5 != godPower.getPositionWorker()[1])) {
                        if (setEvent(map, actions, new int[] { i / 5, i % 5 }, false)) {
                            setEvent(map, actions, godPower.getPositionWorker(), true);
                        }
                        break;
                    }
                }
            } else {
                setEvent(map, actions, godPower.getPositionWorker(), true);
            }
        }
    }

    private boolean setEvent(Cell[][] map, Action[][][] actions, int[] position, boolean flag) {
        boolean stato = false;
        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {
            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {

                if (i != position[0] || j != position[1]) {
                    if (map[i][j].getSize() == map[position[0]][position[1]].getSize()
                            && !map[i][j].getBlock().getTypeBlock().equals(TypeBlock.WORKER)) {
                        stato = true;
                    } else {
                        if (flag) {
                            if (actions[i][j][0].getGod().equals(God.STANDARD)) {
                                actions[i][j][0].setGod(God.PERSEPHONE);
                                actions[i][j][0].setBlocked(true);
                            }
                        }
                    }
                }
            }
        }
        return stato;
    }
}
