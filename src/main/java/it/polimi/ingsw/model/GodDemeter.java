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
        build(map,actions,godPower.getPositionWorker());
        actions[position[0]][position[1]][1].set(false);
        actions[position[0]][position[1]][2].set(false);
    }
}
