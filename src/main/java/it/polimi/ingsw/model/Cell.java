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

    public void addBlock(Block blockToAdd) {
        if (blockToAdd != null && (getBlock() == null
                || (getBlock().getTypeBlock() != TypeBlock.DOME && getBlock().getTypeBlock() != TypeBlock.WORKER)))
            blocks.add(blockToAdd);
    }

    /* remove & return the top block */
    public Block popBlock() {
        if (blocks.size() > 0)
            return blocks.remove(blocks.size() - 1);
        return null;
    }

    public Cell clone() {
        return new Cell((ArrayList<Block>) blocks.stream().map(e -> e.clone()).collect(Collectors.toList()));
    }

    public Block getBlock() {
        if (blocks.size() > 0)
            return blocks.get(blocks.size() - 1);
        return new Block(TypeBlock.LEVEL0);
    }

    /* return selected block */
    public Block getBlock(int i) {
        if (blocks.size() == 0)
            return new Block(TypeBlock.LEVEL0);
        return blocks.get(Math.min(Math.max(0, i), blocks.size() - 1)).clone();
    }

    public int getSize() {
        return blocks.size();
    }
}
