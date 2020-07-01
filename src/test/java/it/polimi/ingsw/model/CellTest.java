package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    /*
    verify addBlock, popBlock, getBlock function:
    TypeBlock.Worker is always on the Top
    getBlock(i)returns the block in the position i, i> size returns the top block, when cell has size 0 get Block returns TypeBlock.Level0
    getBlock() returns the top block
    popBlock returns the top Block, when cell has size 0 returns null
     */
    @Test
    public void addPopGetBlockTest(){
        Cell cell = new Cell();
        Block block = new Block(TypeBlock.WORKER);
        cell.addBlock(block);
        assertEquals(block.getTypeBlock(), cell.getBlock(0).getTypeBlock());
        assertEquals(block.getTypeBlock(), cell.getBlock().getTypeBlock());
        cell.addBlock(null);
        assertEquals(1, cell.getSize());
        assertEquals(block.getTypeBlock(), cell.getBlock(0).getTypeBlock());
        assertEquals(TypeBlock.WORKER, cell.getBlock(3).getTypeBlock());
        assertEquals(TypeBlock.WORKER, cell.getBlock().getTypeBlock());
        cell.addBlock(new Block(TypeBlock.LEVEL1));
        assertEquals(2, cell.getSize());
        assertEquals(block.getTypeBlock(), cell.getBlock().getTypeBlock());
        assertEquals(block, cell.popBlock());
        assertEquals(1, cell.getSize());
        assertEquals(TypeBlock.LEVEL1, cell.popBlock().getTypeBlock());
        assertEquals(0, cell.getSize());
        assertEquals(null, cell.popBlock());
    }

    /*
    verify that cell and its clone has same size and same blocks(with same order)
     */
    @Test
    public void cloneTest() {
        Cell cell = new Cell();
        cell.addBlock(new Block(TypeBlock.LEVEL1));
        cell.addBlock(new Block(TypeBlock.LEVEL2));
        cell.addBlock(new Block(TypeBlock.LEVEL3));
        cell.addBlock(new Block(TypeBlock.DOME));
        Cell cellCopy = cell.clone();
        for (int i = 0; i < cell.getSize(); i++) {
            assertEquals(cell.getBlock(i).getTypeBlock(), cellCopy.getBlock(i).getTypeBlock());
        }
    }


    /*
    verity getSize function
     */
    @Test
    public void getSizeTest() {
        int size = 0;
        Cell cell = new Cell();
        size = cell.getSize();
        Block block = new Block(TypeBlock.WORKER);
        cell.addBlock(block);
        assertEquals(size, cell.getSize() - 1);
        size = cell.getSize();
        cell.popBlock();
        assertEquals(size, cell.getSize() + 1);
    }
}
