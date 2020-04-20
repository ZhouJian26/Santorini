package it.polimi.ingsw.model;

public class GodDemeter extends GodDecorator {
    int[][] size = new int[5][5];
    int[] position = new int[2];

    public GodDemeter(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getCurrentPlayer().equals(godPower.getName()) && events[0] == Event.ZERO) {
            godPower.activate(true);
        } else if (events[0].equals(Event.MOVE)) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    size[i][j] = map[i][j].getSize();
                }
            }
        } else if (events[0].equals(Event.BUILD)) {
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
        for (; (i <= Math.min(4,position[0] + 1)); i++) {
            j = position[1] - 1;
            if (j < 0) {
                j = 0;
            }
            for (; j <= Math.min(4,position[1] + 1); j++) {
                if (i != position[0] || j != position[1]) {
                    if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                        switch (map[i][j].getBlock(map[i][j].getSize()-1).getTypeBlock()) {
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
