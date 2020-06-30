package it.polimi.ingsw.model;

/**
 * Class that is used to store a game board block information
 */
public class Block implements Cloneable {
    /**
     * Block Type
     */
    final private TypeBlock typeBlock;
    /**
     * Block Owner, Default null
     */
    final private String owner;
    /**
     * Block Color, Default null
     * 
     * @see it.polimi.ingsw.model.Color
     */
    final private Color color;

    /**
     * Create a Block instance of a specific typeBlock
     * 
     * @param typeBlock type to set
     */
    public Block(TypeBlock typeBlock) {
        this.typeBlock = typeBlock;
        this.owner = null;
        this.color = null;
    }

    /**
     * Create a Block instance of a specific typeBlock, owner and color
     * 
     * @param typeBlock type to set
     * @param owner     owner to set
     * @param color     color to set
     */
    public Block(TypeBlock typeBlock, String owner, Color color) {
        this.typeBlock = typeBlock;
        this.owner = owner;
        this.color = color;
    }

    /**
     * Get the block type
     * 
     * @return block type
     */
    public TypeBlock getTypeBlock() {
        return typeBlock;
    }

    @Override
    public Block clone() {
        return new Block(typeBlock, owner, color);
    }

    /**
     * Get the block owner
     * 
     * @return block owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Get the block color
     * 
     * @return block color
     */
    public Color getColor() {
        return color;
    }
}
