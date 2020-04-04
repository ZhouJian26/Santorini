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

    public Block clone() throws CloneNotSupportedException {
        Block blockCopy = (Block) super.clone();
        return blockCopy;
    }
}
