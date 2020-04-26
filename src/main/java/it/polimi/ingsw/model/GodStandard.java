package it.polimi.ingsw.model;

public class GodStandard extends GodDecorator {
    private boolean status;
    private int count = 0;

    public GodStandard(GodInterface godPower) {
        super(godPower);
    }


    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (events[0] == Event.ONE) {
            if (count == 2) {
                godPower.setStatusPlayer(StatusPlayer.END);
                godPower.setLastGod(God.STANDARD);
                count = 0;

            } else {
                godPower.setStatusPlayer(StatusPlayer.LOSE);
                godPower.setLastGod(God.STANDARD);
                if (count == 0) {
                    for (int i = 0; i < 25; i++) {

                        if (actions[i / 5][i % 5][0].getStatus()) {
                            godPower.setStatusPlayer(StatusPlayer.GAMING);
                            break;

                        }
                    }
                } else {
                    for (int i = 0; i < 25; i++) {

                        if (actions[i / 5][i % 5][1].getStatus()) {
                            godPower.setStatusPlayer(StatusPlayer.GAMING);
                            break;
                        } else if (actions[i / 5][i % 5][2].getStatus()) {
                            godPower.setStatusPlayer(StatusPlayer.GAMING);
                            break;
                        }
                    }
                }
            }
            return;
        }
        int[] position = godPower.getPositionWorker();
        if (events[0] == Event.ZERO) {
            status = false;
            count = 0;
            setAction(map, actions);
        } else if (events[0].equals(Event.MOVE)) {
            if (count == 0) {
                count = 1;
            }
            status = true;
            if (events[1].equals(Event.UP)) {
                if (map[position[0]][position[1]].getSize() == 4) {
                    godPower.setStatusPlayer(StatusPlayer.WIN);
                    godPower.setLastGod(God.STANDARD);
                } else {
                    setAction(map, actions);
                }
            } else {
                setAction(map, actions);
            }
        } else {
            if (count == 1) {
                count = 2;
            } else if (count == 0) {
                setAction(map, actions);
            }
        }

    }


    private void setAction(Cell[][] map, Action[][][] actions) {
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
                if (!status) {
                    if ((map[i][j].getSize() <= map[position[0]][position[1]].getSize()) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                        destination[0] = i;
                        destination[1] = j;
                        actions[i][j][0].set(position, destination, destination, destination, true);
                    }
                } else {
                    if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                        switch (map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock()) {
                            case LEVEL1:
                                typeBlock = TypeBlock.LEVEL2;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][1].set(true, typeBlock, destination);
                                break;
                            case LEVEL2:
                                typeBlock = TypeBlock.LEVEL3;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][1].set(true, typeBlock, destination);
                                break;
                            case LEVEL3:
                                typeBlock = TypeBlock.DOME;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][2].set(true, typeBlock, destination);
                                break;
                            default:
                                typeBlock = TypeBlock.LEVEL1;
                                destination[0] = i;
                                destination[1] = j;
                                actions[i][j][1].set(true, typeBlock, destination);
                        }
                    }
                }

            }
        }
    }

}
