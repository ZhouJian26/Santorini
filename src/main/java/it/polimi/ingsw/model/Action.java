package it.polimi.ingsw.model;

public class Action implements Cloneable{
    /*status this action will be executed o not*/
    private boolean status;


    public void esecute(Cell[][] map){}

    public void set(boolean status){
        this.status=status;
    }

    public boolean getStatus(){
        boolean status1=status;
        return status1;
    }

    public Action clone() throws CloneNotSupportedException {
        Action action=null;
        action=(Action)super.clone();
        return action;
    }
}
