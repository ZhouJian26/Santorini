package it.polimi.ingsw.model;

public class GodDecorator implements GodInterface {
    protected GodInterface godPower;

    public GodDecorator(GodInterface godPower){
        this.godPower=godPower;
    }
    @Override
    public void setCurrentPlayer(String name){
        godPower.setCurrentPlayer(name);
    }

    @Override
    public void setWorker(int[] positionWorker){
        godPower.setWorker(positionWorker);
    }

    @Override
    public void activate(boolean status) {
        godPower.activate(status);
    }

    @Override
    public void run(Action[][][] actions) {

    }

    @Override
    public StatusPlayer getPlayerStatus() {
        return godPower.getPlayerStatus();
    }

    @Override
    public void getEvent(Event[] events,Cell[][] map,Action[][][] actions) {
        godPower.getEvent(events,map,actions);
    }

    @Override
    public void setAction( Cell[][] map, Action[][][] actions) {
        godPower.setAction(map,actions);
    }

    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        godPower.setStatusPlayer(statusPlayer);
    }

    @Override
    public String getName() {
        return godPower.getName();
    }
    @Override
    public boolean getStatus(){
        return godPower.getStatus();
    }
    @Override
    public int[] getPositionWorker(){
        return godPower.getPositionWorker();
    }
    @Override
    public String getCurrentPlayer(){
        return godPower.getCurrentPlayer();
    }
}
