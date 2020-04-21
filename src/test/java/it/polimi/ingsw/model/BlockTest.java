package it.polimi.ingsw.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.NoSuchElementException;

public class BlockTest {
    private static int a = 0;
    private TypeBlock block;

    @BeforeEach
    public void setUp() {
        switch (a % 5) {
            case 0:
                block = TypeBlock.WORKER;
                break;
            case 1:
                block = TypeBlock.LEVEL1;
                break;
            case 2:
                block = TypeBlock.LEVEL2;
                break;
            case 3:
                block = TypeBlock.LEVEL3;
                break;
            case 4:
                block = TypeBlock.DOME;
                break;
            default:
                throw new NoSuchElementException();
        }
        a++;
    }

    @Test
    public void getTypeBlockTest() {
        assertEquals(block, new Block(block).getTypeBlock());
        assertEquals(block, new Block(block).getTypeBlock());
        assertEquals(block, new Block(block).getTypeBlock());
        assertEquals(block, new Block(block).getTypeBlock());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Block block1 = new Block(block);
        assertEquals(block1.clone().getTypeBlock(), block1.getTypeBlock());
    }

}
