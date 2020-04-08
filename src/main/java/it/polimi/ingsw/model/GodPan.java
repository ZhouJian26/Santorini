package it.polimi.ingsw.model;

public class GodPan extends GodDecorator {
    public GodPan(GodInterface godPower) {
        super(godPower);
    }
    @Override
    public void getEvent(Event[] events,Cell[][] map,Action[][][] actions){
        if(events[0]!=null&&events[0].equals(Event.MOVE)&&events[1].equals(Event.DOWN)&&!events[2].equals(Event.ONE)){
            godPower.setStatusPlayer(StatusPlayer.WIN);
        }
    }

}
