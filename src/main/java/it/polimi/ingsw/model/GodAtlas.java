package it.polimi.ingsw.model;

public class GodAtlas extends GodDecorator {
    public GodAtlas(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getCurrentPlayer().equals(godPower.getName()) && events[0].equals(Event.MOVE)) {
            godPower.activate(true);
            setAction(map, actions);
            godPower.activate(false);
        }
    }

    private void setAction(Cell[][] map, Action[][][] actions) {
        int[] position = godPower.getPositionWorker();

        int[] destination = new int[2];
        TypeBlock typeBlock = null;

        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {

            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {
                if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER)
                        && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {

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
