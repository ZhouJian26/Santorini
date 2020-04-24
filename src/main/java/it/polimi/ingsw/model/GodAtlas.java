package it.polimi.ingsw.model;

public class GodAtlas extends GodDecorator {
    public GodAtlas(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getCurrentPlayer().equals(godPower.getName())) {
            if (events[0].equals(Event.MOVE)) {
                godPower.activate(true);
            }
        }
        if (godPower.getStatus()) {
            godPower.activate(false);
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
                if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {

                    typeBlock = TypeBlock.DOME;
                    destination[0] = i;
                    destination[1] = j;
                    actions[i][j][2].set(true, typeBlock, destination);
                    actions[i][j][2].setGod(God.ATLAS);
                }
            }
        }
    }
}


