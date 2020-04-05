package it.polimi.ingsw.model;

public class GodStandard extends GodDecorator {
    private boolean status;
    private int count = 0;

    public GodStandard(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void run(Action[][][] actions) {
        if (count == 2) {
            this.setStatusPlayer(StatusPlayer.END);
            count = 0;
        } else {
            this.setStatusPlayer(StatusPlayer.LOSE);
            if (count == 0) {
                for (int i = 0; i < 25; i++) {

                    if (actions[i / 5][i % 5][0].getStatus()) {
                        this.setStatusPlayer(StatusPlayer.GAMING);
                        break;

                    }
                }
            } else {
                for (int i = 0; i < 25; i++) {

                    if (actions[i / 5][i % 5][1].getStatus()) {
                        this.setStatusPlayer(StatusPlayer.GAMING);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        int[] position = godPower.getPositionWorker();
        if (events[0].equals(null)) {
            status = false;
            setAction(map, actions);
        } else if (events[0].equals(Event.MOVE)) {
            if (count == 0) {
                count = 1;
            }
            status = true;
            if (events[1].equals(Event.UP)) {
                if (map[position[0]][position[1]].getSize() == 4) {
                    godPower.setStatusPlayer(StatusPlayer.WIN);
                } else {
                    setAction(map, actions);
                }
            } else {
                setAction(map, actions);
            }
        } else {
            if (count == 1) {
                count = 2;
            }
        }

    }

    @Override
    public void setAction(Cell[][] map, Action[][][] actions) {
        int[] position = godPower.getPositionWorker();
        int i = position[0] - 1;
        int j = 0;
        int[] destination = new int[2];
        TypeBlock typeBlock = null;
        if (i < 0) {
            i = 0;
        }
        for (; (i <= position[0] + 1); i++) {
            j = position[1] - 1;
            if (j < 0) {
                j = 0;
            }
            for (; j <= position[1] + 1; j++) {
                if (!status) {
                    if ((map[i][j].getSize() <= map[position[0]][position[1]].getSize()) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                        destination[0] = i;
                        destination[1] = j;
                        actions[i][j][0].set(position, destination, destination, destination, true);
                    }
                } else {
                    if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                        switch (map[i][j].getBlock(map[i][j].getSize()).getTypeBlock()) {
                            case LEVEL1:
                                typeBlock = TypeBlock.LEVEL2;
                                break;
                            case LEVEL2:
                                typeBlock = TypeBlock.LEVEL3;
                                break;
                            case LEVEL3:
                                typeBlock = TypeBlock.DOME;
                                break;
                        }
                        destination[0] = i;
                        destination[1] = j;
                        actions[i][j][1].set(true, typeBlock, destination);
                    }
                }

            }
        }
    }

}
