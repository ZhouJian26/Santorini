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
                if (count == 0) {


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
