package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that is used to store a single board cell information
 */
public class Cell implements Cloneable {
    /**
     * Blocks in this Cell
     */
    private ArrayList<Block> blocks;

    /**
     * Create an instance of Cell with no blocks
     */
    public Cell() {
        blocks = new ArrayList<>();
    }

    /**
     * Create an instance of Cell with specified blocks
     * 
     * @param blocks blocks to put in this Cell
     */
    public Cell(List<Block> blocks) {
        this.blocks = (ArrayList<Block>) blocks.stream().map(Block::clone).collect(Collectors.toList());
    }

    /**
     * Add a block on top of the stack
     *
     * @param blockToAdd block to add
     */
    public void addBlock(Block blockToAdd) {
        if (blockToAdd == null)
            return;
        if (getBlock().getTypeBlock().equals(TypeBlock.WORKER)) {
            Block block = popBlock();
            blocks.add(blockToAdd);
            blocks.add(block);
        } else {
            blocks.add(blockToAdd);
        }
    }

    /**
     * Remove and Return the top block of the stack, null in case there is nothing
     *
     * @return top block on the stack
     */
    public Block popBlock() {
        if (!blocks.isEmpty())
            return blocks.remove(blocks.size() - 1);
        return null;
    }

    /**
     * Get the top block of the stack
     *
     * @return Top block of the stack
     */
    public Block getBlock() {
        if (blocks.isEmpty())
            return new Block(TypeBlock.LEVEL0);
        return blocks.get(blocks.size() - 1).clone();
    }

    /**
     * Get a specific block of the stack
     *
     * @param i position of the block on the stack
     * @return block selected, in case i is invalid, it is returned the closest
     *         block
     */
    public Block getBlock(int i) {
        if (blocks.isEmpty())
            return new Block(TypeBlock.LEVEL0);
        return blocks.get(Math.min(Math.max(0, i), blocks.size() - 1)).clone();

    }




    @Override
    public Cell clone() {
        return new Cell(blocks.stream().map(Block::clone).collect(Collectors.toList()));
    }




    /**
     * Get the number of blocks in the Cell
     * 
     * @return number of blocks in the Cell
     */
    public int getSize() {
        return blocks.size();
    }
}
