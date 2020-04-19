package it.polimi.ingsw.model;

public class Block implements Cloneable {
    private TypeBlock block;
    private String owner;
    private Color color;

    public Block(TypeBlock block) {
        this.block = block;
    }

    public Block(TypeBlock block, String owner, Color color) {
        this.block = block;
        this.owner = owner;
        this.color = color;
    }

    public TypeBlock getTypeBlock() {
        return block;
    }

    public Block clone() {
        if (owner == null)
            return new Block(block);
        return new Block(block, owner, color);
    }

    public String getOwner() {
        return owner;
    }

    public Color getColor() {
        return color;
    }
}
