package it.polimi.ingsw.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlockTest {

    @Test
    public void getTypeBlockTest() {
        assertEquals(TypeBlock.WORKER, new Block(TypeBlock.WORKER).getTypeBlock());
        assertEquals(TypeBlock.LEVEL1, new Block(TypeBlock.LEVEL1).getTypeBlock());
        assertEquals(TypeBlock.LEVEL2, new Block(TypeBlock.LEVEL2).getTypeBlock());
        assertEquals(TypeBlock.LEVEL3, new Block(TypeBlock.LEVEL3).getTypeBlock());
        assertEquals("Santorini", new Block(TypeBlock.LEVEL3).getOwner());
        Block block1 = new Block(TypeBlock.WORKER, "aaa", Color.WHITE);
        assertEquals(TypeBlock.WORKER, block1.getTypeBlock());
        assertEquals("aaa", block1.getOwner());
        assertEquals(Color.WHITE, block1.getColor());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Block block1 = new Block(TypeBlock.WORKER, "aaa", Color.WHITE);
        assertEquals(block1.clone().getTypeBlock(), block1.getTypeBlock());
        Block block2 = new Block(TypeBlock.LEVEL1);
        assertEquals(block2.clone().getTypeBlock(), block2.getTypeBlock());
    }

}
