package it.polimi.ingsw.model;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Cell implements Cloneable {

    private ArrayList<Block> blocks;

    public Cell() {
        blocks = new ArrayList<>();
    }

    public Cell(ArrayList<Block> blocks) {
        this.blocks = (ArrayList<Block>) blocks.stream().map(e -> e.clone()).collect(Collectors.toList());
    }

    /**
     * Add a block on top of the stack
     * 
     * @param blockToAdd
     */
    public void addBlock(Block blockToAdd) {
        if (blockToAdd != null)
            blocks.add(blockToAdd);
    }

    /**
     * Remove and Return the top block of the stack, null in case there is nothing
     * 
     * @return top block on the stack
     */
    public Block popBlock() {
        if (blocks.size() > 0)
            return blocks.remove(blocks.size() - 1);
        return null;
    }

    public Cell clone() {
        return new Cell((ArrayList<Block>) blocks.stream().map(e -> e.clone()).collect(Collectors.toList()));
    }

    /**
     * 
     * @return Top block of the stack
     */
    public Block getBlock() {
        if (blocks.size() > 0)
            return blocks.get(blocks.size() - 1).clone();
        return new Block(TypeBlock.LEVEL0);
    }

    /**
     * 
     * @param i position of the block on the stack
     * @return block selected, in case i is invalid, it is returned the closest
     *         block
     */
    public Block getBlock(int i) {
        if (blocks.size() == 0)
            return new Block(TypeBlock.LEVEL0);
        return blocks.get(Math.min(Math.max(0, i), blocks.size() - 1)).clone();

    }

    public int getSize() {
        return blocks.size();
    }
}
