package it.polimi.ingsw.model;

public class Block implements Cloneable {
    private TypeBlock typeBlock;
    private String owner;
    private Color color;

    public Block(TypeBlock typeBlock) {
        this.typeBlock = typeBlock;
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
        if (owner == null)
            return new Block(typeBlock);
        return new Block(typeBlock, owner, color);
    }

    public String getOwner() {
        if (owner == null) {
            return "Santorini";
        } else
            return owner;
    }

    public Color getColor() {
        return color;
    }
}
