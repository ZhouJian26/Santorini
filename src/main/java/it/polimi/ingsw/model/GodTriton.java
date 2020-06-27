package it.polimi.ingsw.model;

public class GodTriton extends GodDecorator {
    public GodTriton(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if ((events[0].equals(Event.MOVE) && godPower.getName().equals(godPower.getCurrentPlayer()))
                && (godPower.getPositionWorker()[0] == 0 || godPower.getPositionWorker()[0] == 4
                || godPower.getPositionWorker()[1] == 0 || godPower.getPositionWorker()[1] == 4)) {
            setAction(map,actions);
        }
    }

    private void setAction(Cell[][] map, Action[][][] actions){
        move(map,actions,godPower.getPositionWorker());
    }

}
