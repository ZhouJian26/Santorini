package it.polimi.ingsw.model;


import java.util.concurrent.BrokenBarrierException;


public class Cell implements Cloneable {

    private Block[] blocks=new Block[4];
    private int size;/*size of array blocks*/

    public Cell()
    {
        size=0;
    }

    public void addBlock(Block blockToAdd){

        blocks[size]=blockToAdd;
        size++;

    }
    /*remove & return the top block*/
    public Block popBlock(){
        if(size>0){
        size = size - 1;
        return blocks[size];}
        return null;

    }

    public Cell clone() {
        Cell blockCopy=new Cell();

        for(int i=0;i<size;i++)
        {
            blockCopy.addBlock(this.getBlock(i));
        }
        return blockCopy;
    }
    /*return selected block*/
    public Block getBlock(int i)  {
        if(size==0){return new Block(TypeBlock.LEVEL0);}
        Block blockCopy=null;
        blockCopy=blocks[i].clone();
        return blockCopy;
    }

    public int getSize(){
        int sizeCopy=size;
        return sizeCopy;
    }
}
