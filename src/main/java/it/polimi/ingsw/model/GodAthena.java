package it.polimi.ingsw.model;

public class GodAthena extends GodDecorator {
    public GodAthena(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events,Cell[][] map,Action[][][] actions){

        if(godPower.getName().equals(godPower.getCurrentPlayer())){
            if(events[0]==Event.ZERO){
                godPower.activate(false);
            }
            else if(events[0].equals(Event.MOVE)&&events[1].equals(Event.UP)){
                godPower.activate(true);
            }
        }
        else if (godPower.getStatus()){
            setAction(map,actions);
        }
    }

    @Override
    public void setAction(Cell[][] map, Action[][][] actions){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(map[i][j].getSize()>map[getPositionWorker()[0]][getPositionWorker()[1]].getSize()){
                    actions[i][j][0].set(false);
                    actions[i][j][0].setGod(God.ATHENA);
                    actions[i][j][0].setBlocked(true);

                }
                else if(map[i][j].getSize()==map[getPositionWorker()[0]][getPositionWorker()[1]].getSize()&&!map[i][j].getBlock(map[i][j].getSize()).getTypeBlock().equals(TypeBlock.WORKER)){
                    actions[i][j][0].set(false);
                    actions[i][j][0].setGod(God.ATHENA);
                    actions[i][j][0].setBlocked(true);


                }
            }
        }
    }

}
