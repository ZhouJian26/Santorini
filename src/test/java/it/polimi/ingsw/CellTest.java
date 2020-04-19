package it.polimi.ingsw;
import it.polimi.ingsw.model.Block;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.TypeBlock;
import org.junit.Test;

import static org.junit.Assert.*;
public class CellTest {

    @Test
    public void addBlockTest() throws CloneNotSupportedException {
        Cell cell=new Cell();
        Block block=new Block(TypeBlock.WORKER);
        cell.addBlock(block);
        assertEquals(1,cell.getSize());
        assertEquals(block.getTypeBlock(),cell.getBlock(0).getTypeBlock());
    }
    @Test
    public void popBlockTest(){
        Cell cell=new Cell();
        Block block=new Block(TypeBlock.WORKER);
        cell.addBlock(block);
        assertEquals(block,cell.popBlock());
        assertEquals(0,cell.getSize());
    }

    @Test
    public void getBlocksTest() throws CloneNotSupportedException {
        Cell cell=new Cell();
        Block[] blocks=new Block[4];
        cell.addBlock(new Block(TypeBlock.LEVEL1));
        cell.addBlock(new Block(TypeBlock.LEVEL2));
        cell.addBlock(new Block(TypeBlock.LEVEL3));
        cell.addBlock(new Block(TypeBlock.DOME));

        Cell cellCopy=cell.clone();
        for(int i=0;i<cell.getSize();i++)
        {
            assertEquals(cell.getBlock(i).getTypeBlock(),cellCopy.getBlock(i).getTypeBlock());
        }
    }

    @Test
    public void getBlockTest() throws CloneNotSupportedException {
        Cell cell=new Cell();
        Block block=new Block(TypeBlock.DOME);
        cell.addBlock(block);
        assertEquals(cell.clone().getBlock(0).getTypeBlock(),block.getTypeBlock());
    }

    @Test
    public void getSizeTest(){
        int size=0;
        Cell cell=new Cell();
        size=cell.getSize();
        Block block=new Block(TypeBlock.WORKER);
        cell.addBlock(block);
        assertEquals(size,cell.getSize()-1);
        size=cell.getSize();
        Block block1=cell.popBlock();
        assertEquals(size,cell.getSize()+1);
    }
}
