package it.polimi.ingsw.model;

import it.polimi.ingsw.model.TypeBlock;

public class Block implements Cloneable{
    private TypeBlock block;

    public Block(TypeBlock block){
        this.block=block;
    }

    public TypeBlock getTypeBlock(){
        return block;
    }
    @Override
    public Block clone() {
        Block blockCopy = new Block(block);
        return blockCopy;
    }
    public String getOwner(){
        return null;
    }
    public Color getColor(){return null; }
}
