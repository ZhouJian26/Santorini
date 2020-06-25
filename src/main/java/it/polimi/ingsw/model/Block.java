package it.polimi.ingsw.model;

public class Block implements Cloneable {
    final private TypeBlock typeBlock;
    final private String owner;
    final private Color color;

    public Block(TypeBlock typeBlock) {
        this.typeBlock = typeBlock;
        this.owner = "Santorini";
        this.color = null;
    }

    public Block(TypeBlock typeBlock, String owner, Color color) {
        this.typeBlock = typeBlock;
        this.owner = owner;
        this.color = color;
    }

    public TypeBlock getTypeBlock() {
        return typeBlock;
    }

    public Block clone() {
        return new Block(typeBlock, owner, color);
    }

    public String getOwner() {
        return owner;
    }

    public Color getColor() {
        return color;
    }
}
