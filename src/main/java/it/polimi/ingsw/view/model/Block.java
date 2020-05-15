package it.polimi.ingsw.view.model;

public class Block {
    public final String block;
    public final String owner;
    public final String color;

    public Block(String block, String owner, String color) {
        this.owner = owner;
        this.block = block;
        this.color = color;
    }
}