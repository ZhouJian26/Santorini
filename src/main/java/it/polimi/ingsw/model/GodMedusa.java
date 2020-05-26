package it.polimi.ingsw.model;

public class GodMedusa extends GodDecorator {
    public GodMedusa(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (events[0].equals(Event.BUILD) && godPower.getName().equals(godPower.getCurrentPlayer())) {
            int[] position = godPower.getPositionWorker();
            for (int i = Math.min(position[0] - 1, 0); i <= Math.max(position[0] + 1, 4); i++) {
                for (int j = Math.min(position[1] - 1, 0); j <= Math.max(position[1] + 1, 4); j++) {
                    if (map[i][j].getBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock().getOwner().equals(godPower.getName())) {
                        if (map[i][j].getSize() < map[position[0]][position[1]].getSize()) {
                            map[i][j].popBlock();
                            switch (map[i][j].getSize()) {
                                case 0:
                                    map[i][j].addBlock(new Block(TypeBlock.LEVEL1));
                                    break;
                                case 1:
                                    map[i][j].addBlock(new Block(TypeBlock.LEVEL2));
                                    break;
                                case 2:
                                    map[i][j].addBlock(new Block(TypeBlock.LEVEL3));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
}
