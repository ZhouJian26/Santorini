package it.polimi.ingsw.model;

class GodDemeter extends GodDecorator {
    private int[][] size = new int[5][5];
    private int[] position = null;

    public GodDemeter(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getCurrentPlayer().equals(godPower.getName())) {

            if (events[0] == Event.ZERO) {
                position = null;
                godPower.activate(true);
            } else if (godPower.getStatus()) {
                if (events[0].equals(Event.MOVE)) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            size[i][j] = map[i][j].getSize();
                        }
                        position = new int[2];
                    }
                } else if (events[0].equals(Event.BUILD) && position != null) {
                    godPower.activate(false);
                    for (int i = 0; i < 25; i++) {
                        if (map[i / 5][i % 5].getSize() > size[i / 5][i % 5]) {
                            position[0] = i / 5;
                            position[1] = i % 5;
                            break;
                        }
                    }
                    setAction(map, actions);
                }
            }
        }

    }

    private void setAction(Cell[][] map, Action[][][] actions) {
        int[] positionWorker = godPower.getPositionWorker();

        int[] destination = new int[2];
        TypeBlock typeBlock = null;

        for (int i = Math.max(0, positionWorker[0] - 1); (i <= Math.min(4, positionWorker[0] + 1)); i++) {

            for (int j = Math.max(0, positionWorker[1] - 1); j <= Math.min(4, positionWorker[1] + 1); j++) {
                if ((i != position[0] || j != position[1]) && (!map[i][j].getBlock(map[i][j].getSize() - 1)
                        .getTypeBlock().equals(TypeBlock.WORKER)
                        && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME))) {
                    int pos = 1;
                    switch (map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock()) {
                        case LEVEL1:
                            typeBlock = TypeBlock.LEVEL2;
                            break;
                        case LEVEL2:
                            typeBlock = TypeBlock.LEVEL3;
                            break;
                        case LEVEL3:
                            typeBlock = TypeBlock.DOME;
                            pos = 2;
                            break;
                        default:
                            typeBlock = TypeBlock.LEVEL1;
                    }
                    destination[0] = i;
                    destination[1] = j;
                    ((Build) actions[i][j][pos]).set(true, typeBlock, destination);
                }
            }
        }
    }
}
