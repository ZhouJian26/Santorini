package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cell implements Cloneable {

    private ArrayList<Block> blocks;

    public Cell() {
        blocks = new ArrayList<>();
    }

    public Cell(List<Block> blocks) {
        this.blocks = (ArrayList<Block>) blocks.stream().map(Block::clone).collect(Collectors.toList());
    }

    /**
     * Add a block on top of the stack
     *
     * @param blockToAdd
     */
    public void addBlock(Block blockToAdd) {
        if (blockToAdd != null) {
            if (getBlock().getTypeBlock().equals(TypeBlock.WORKER)) {
                Block block = popBlock();
                blocks.add(blockToAdd);
                blocks.add(block);
            } else {
                blocks.add(blockToAdd);
            }
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

    @Override
    public Cell clone() {
        return new Cell((ArrayList<Block>) blocks.stream().map(Block::clone).collect(Collectors.toList()));
    }

    /**
     * @return Top block of the stack
     */
    public Block getBlock() {
        if (!blocks.isEmpty())
            return blocks.get(blocks.size() - 1).clone();
        return new Block(TypeBlock.LEVEL0);
    }

    /**
     * @param i position of the block on the stack
     * @return block selected, in case i is invalid, it is returned the closest
     *         block
     */
    public Block getBlock(int i) {
        if (blocks.isEmpty())
            return new Block(TypeBlock.LEVEL0);
        return blocks.get(Math.min(Math.max(0, i), blocks.size() - 1)).clone();

    }

    public int getSize() {
        return blocks.size();
    }
}
