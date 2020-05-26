package it.polimi.ingsw.model;

public class GodPoseidon extends GodDecorator {
    int count=0;

    public GodPoseidon(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getCurrentPlayer().equals(godPower.getName())) {
            if(events[0].equals(Event.BUILD)&&count>0){
                for(int i=0;i<25;i++){
                    if(map[i/5][i%5].getBlock().getTypeBlock().equals(TypeBlock.WORKER)&&map[i/5][i%5].getBlock().getOwner().equals(godPower.getName())){
                        if(i/5!=godPower.getPositionWorker()[0]||i%5!=godPower.getPositionWorker()[1])
                        {
                            setAction(map,actions,new int[]{i/5,i%5});
                            count--;
                            break;
                        }
                    }
                }
            }else if(events[0]==Event.ZERO){
                count=3;
            }
        }

    }


    private void setAction(Cell[][] map, Action[][][] actions,int[] positionWorker) {



        int[] destination = new int[2];
        TypeBlock typeBlock = null;

        for (int i = Math.max(0, positionWorker[0] - 1); (i <= Math.min(4, positionWorker[0] + 1)); i++) {

            for (int j = Math.max(0, positionWorker[1] - 1); j <= Math.min(4, positionWorker[1] + 1); j++) {

                if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER) && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                    switch (map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock()) {
                        case LEVEL1:
                            typeBlock = TypeBlock.LEVEL2;
                            destination[0] = i;
                            destination[1] = j;
                            actions[i][j][1].set(true, typeBlock, destination);
                            actions[i][j][1].setGod(God.POSEIDON);
                            break;
                        case LEVEL2:
                            typeBlock = TypeBlock.LEVEL3;
                            destination[0] = i;
                            destination[1] = j;
                            actions[i][j][1].set(true, typeBlock, destination);
                            actions[i][j][1].setGod(God.POSEIDON);
                            break;
                        case LEVEL3:
                            typeBlock = TypeBlock.DOME;
                            destination[0] = i;
                            destination[1] = j;
                            actions[i][j][2].set(true, typeBlock, destination);
                            actions[i][j][2].setGod(God.POSEIDON);
                            break;
                        default:
                            typeBlock = TypeBlock.LEVEL1;
                            destination[0] = i;
                            destination[1] = j;
                            actions[i][j][1].set(true, typeBlock, destination);
                            actions[i][j][1].setGod(God.POSEIDON);
                    }
                }
            }
        }
    }
}

