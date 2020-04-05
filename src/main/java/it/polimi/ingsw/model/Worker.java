package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Block;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TypeBlock;

public class Worker extends Block {
    /*ownerPlayersId and WorkersColor*/
    private String owner;
    private Color color;


    public Worker(TypeBlock block, String owner, Color color) {
        super(TypeBlock.WORKER);
        this.owner = owner;
        this.color=color;
    }
    @Override
    public String getOwner(){
        String ownerCopy=owner;
        return ownerCopy;
    }
    @Override
    public Color getColor(){
        Color colorCopy=color;
        return colorCopy;
    }
}
