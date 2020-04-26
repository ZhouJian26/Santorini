package it.polimi.ingsw.model;

public class GodArtemis extends GodDecorator {
    private int count = 0;
    private int[] startPosition=new int[2];

    public GodArtemis(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if(godPower.getName().equals(godPower.getCurrentPlayer())){
            if (events[0] == Event.ZERO) {
                count = 1;
                startPosition=godPower.getPositionWorker();
            }
            else if (events[0].equals(Event.MOVE) && count == 1) {
                count = 0;
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
        for (; (i <= Math.min(4,position[0] + 1)); i++) {
            j = position[1] - 1;
            if (j < 0) {
                j = 0;
            }
            for (; j <= Math.min(4,position[1] + 1); j++) {
                if ((map[i][j].getSize() <= map[position[0]][position[1]].getSize()) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                   if(i!=startPosition[0]||j!=startPosition[1]){
                       destination[0] = i;
                       destination[1] = j;
                       actions[i][j][0].set(position, destination, destination, destination, true);
                       actions[i][j][0].setGod(God.ARTEMIS);
                   }
                }
            }
        }
    }
}
