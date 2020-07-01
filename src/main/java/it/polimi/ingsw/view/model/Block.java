package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Block of the Board
 */
public class Block implements RawObj {
    /**
     * Type of the block
     */
    private final String typeBlock;
    /**
     * Owner of the block, default null
     */
    private final String owner;
    /**
     * color of the worker if it is a worker, default null
     */
    private final String color;

    /**
     * Block Constructor
     * 
     * @param block type block
     * @param owner block owner
     * @param color block color
     */
    public Block(String block, String owner, String color) {
        this.owner = owner;
        this.typeBlock = block;
        this.color = color;
    }

    /**
     * Block Constructor from another Block
     * 
     * @param toClone block to clone
     */
    public Block(Block toClone) {
        this.owner = toClone.owner;
        this.typeBlock = toClone.typeBlock;
        this.color = toClone.color;
    }

    @Override
    public List<String> getRawData() {
        String toRes = typeBlock;
        if (owner != null)
            toRes = color;
        return new ArrayList<>(Arrays.asList(toRes));
    }

    /**
     * Get block type
     * 
     * @return block type
     */
    public String getTypeBlock() {
        return typeBlock;
    }

    /**
     * Get block owner
     * 
     * @return block owner, if it is not a worker is null
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Get block color
     * 
     * @return block color, if it is not a worker is null
     */
    public String getColor() {
        return color;
    }
}