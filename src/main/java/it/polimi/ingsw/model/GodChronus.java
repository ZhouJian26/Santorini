package it.polimi.ingsw.model;

public class GodChronus extends GodDecorator {
    public GodChronus(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if(godPower.getCurrentPlayer().equals(godPower.getName())&&(events[0]==Event.ZERO||events[0]==Event.BUILD)){
            calculateState(map);
        }
    }

    private void calculateState(Cell[][] map){
        int count=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(map[i][j].getSize()==4&&map[i][j].getBlock().getTypeBlock().equals(TypeBlock.DOME)){
                    count++;
                }
            }
        }
        if(count>=5){
            godPower.setStatusPlayer(StatusPlayer.WIN);
            //godPower.setLastGod(God.CHRONUS);
        }
    }

}
