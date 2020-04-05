package it.polimi.ingsw.model;

public class GodAthena extends GodDecorator {
    public GodAthena(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events,Cell[][] map,Action[][][] actions){}

    @Override
    public void setAction( Cell[][] map, Action[][][] actions){}

}
