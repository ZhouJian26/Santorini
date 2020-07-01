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
        assertEquals(TypeBlock.DOME, new Block(TypeBlock.DOME).getTypeBlock());
        assertEquals(null, new Block(TypeBlock.LEVEL3).getOwner());
        Block block1 = new Block(TypeBlock.WORKER, "aaa", Color.WHITE);
        assertEquals(TypeBlock.WORKER, block1.getTypeBlock());
        assertEquals("aaa", block1.getOwner());
        assertEquals(Color.WHITE, block1.getColor());
    }

    /*verify that the origin block and the its clone have same typeBlock, color and owner*/
    @Test
    public void cloneTest(){
        Block block1 = new Block(TypeBlock.WORKER, "aaa", Color.WHITE);
        assertEquals(block1.clone().getTypeBlock(), block1.getTypeBlock());
        assertEquals(block1.clone().getColor(), block1.getColor());
        assertEquals(block1.clone().getOwner(), block1.getOwner());
        Block block2 = new Block(TypeBlock.LEVEL1);
        assertEquals(block2.clone().getTypeBlock(), block2.getTypeBlock());
        assertEquals(block2.clone().getOwner(), null);
        assertEquals(block2.clone().getColor(), null);
    }
}
