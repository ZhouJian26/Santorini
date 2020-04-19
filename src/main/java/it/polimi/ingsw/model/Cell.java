package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;


public class Cell implements Cloneable {

    private List<Block> blocks = new ArrayList<>();


    public void addBlock(Block blockToAdd) {

        blocks.add(blockToAdd);

    }

    /*remove & return the top block*/
    public Block popBlock() {
        if (blocks.size() > 0) {
            Block block = blocks.get(blocks.size()-1);
            blocks.remove(blocks.size()-1);
            return block;
        }
        return null;

    }

    public Cell clone() {
        Cell blockCopy = new Cell();

        for (int i = 0; i < blocks.size(); i++) {
            blockCopy.addBlock(this.getBlock(i));
        }
        return blockCopy;
    }

    /*return selected block*/
    public Block getBlock(int i) {
        if (blocks.size() == 0) {
            return new Block(TypeBlock.LEVEL0);
        }
        Block blockCopy = null;
        blockCopy = blocks.get(blocks.size()-1).clone();
        return blockCopy;
    }

    public int getSize() {
        return blocks.size();
    }
}
