package it.polimi.ingsw.model;

public class Action implements Cloneable{
    /*status this action will be executed or not*/
    private boolean status;


    public void esecute(Cell[][] map){}

    public void set(boolean status){
        this.status=status;
    }
    public void set(int[] x1, int[ ] x2, int[] y1, int[] y2,boolean status){}
    public void set(boolean status,TypeBlock block, int[] position){}

    public boolean getStatus(){
        boolean status1=status;
        return status1;
    }

    public Action clone()  {
      return null;
    }
}
