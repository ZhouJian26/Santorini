package it.polimi.ingsw.model;


import java.util.concurrent.BrokenBarrierException;

import static it.polimi.ingsw.model.TypeBlock.allowedLiftUp;

public class Cell implements Cloneable {

    private Block[] blocks=new Block[4];
    private int size;/*size of array blocks*/

    public Cell()
    {
        size=0;
    }

    public void addBlock(Block blockToAdd){
        if(size==0||allowedLiftUp(blocks[size-1].getTypeBlock(),blockToAdd.getTypeBlock())){
        blocks[size]=blockToAdd;
        size++;}

    }
    /*remove & return the top block*/
    public Block popBlock(){

        size = size - 1;
        return blocks[size];

    }

    public Cell clone() throws CloneNotSupportedException {
        Cell blockCopy=null;
        blockCopy=(Cell)super.clone();
        for(int i=0;i<size;i++)
        {
            blockCopy.blocks[i]=(Block)blocks[i].clone();
        }
        return blockCopy;
    }
    /*return selected block*/
    public Block getBlock(int i) throws CloneNotSupportedException {
        if(size==0){return null;}
        Block blockCopy=null;
        blockCopy=(Block) blocks[i].clone();
        return blockCopy;
    }

    public int getSize(){
        int sizeCopy=size;
        return sizeCopy;
    }
}
