package it.polimi.ingsw.model;

public class GodPrometheus extends GodDecorator {
    int count = 0;

    public GodPrometheus(GodInterface godPower) {
        super(godPower);
    }

    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (events[0].equals(Event.ZERO) && godPower.getCurrentPlayer().equals(godPower.getName())) {
            godPower.activate(true);
            count = 1;
            setAction(map, actions);
            godPower.activate(false);
        } else if (events[0].equals(Event.BUILD)) {
            if (count == 1) {
                count = 0;
                setAction(map, actions);
            }
        } else if (events[0].equals((Event.MOVE))) {
            if (count == 1) {
                count = 0;
            }
        }

    }

    public void setAction(Cell[][] map, Action[][][] actions) {
        if (count == 1) {
            int[] position = godPower.getPositionWorker();
            int i = position[0] - 1;
            int j = 0;
            int[] destination = new int[2];
            TypeBlock typeBlock = null;
            if (i < 0) {
                i = 0;
            }
            for (; (i <= Math.min(4, position[0] + 1)); i++) {
                j = position[1] - 1;
                if (j < 0) {
                    j = 0;
                }
                for (; j <= Math.min(4, position[1] + 1); j++) {
                    if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                        switch (map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock()) {
                            case LEVEL1:
                                typeBlock = TypeBlock.LEVEL2;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][1].set(true, typeBlock, destination);
                                actions[i][j][1].setGod(God.PROMETHEUS);
                                break;
                            case LEVEL2:
                                typeBlock = TypeBlock.LEVEL3;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][1].set(true, typeBlock, destination);
                                actions[i][j][1].setGod(God.PROMETHEUS);
                                break;
                            case LEVEL3:
                                typeBlock = TypeBlock.DOME;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][2].set(true, typeBlock, destination);
                                actions[i][j][2].setGod(God.PROMETHEUS);
                                break;
                            default:
                                typeBlock = TypeBlock.LEVEL1;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][1].set(true, typeBlock, destination);
                                actions[i][j][1].setGod(God.PROMETHEUS);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (map[i][j].getSize() > map[getPositionWorker()[0]][getPositionWorker()[1]].getSize()) {
                        actions[i][j][0].set(false);
                        actions[i][j][0].setBlocked(true);
                    } else if (map[i][j].getSize() == map[getPositionWorker()[0]][getPositionWorker()[1]].getSize() && !map[i][j].getBlock(map[i][j].getSize()).getTypeBlock().equals(TypeBlock.WORKER)) {
                        actions[i][j][0].set(false);
                        actions[i][j][0].setBlocked(true);

                    }
                }
            }
        }
    }

}
