package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CellTest {
    @Test
    public void init() {
        Cell cell = new Cell(Arrays.asList(new Block("LEVEL1", null, null), new Block("WORKER", "pippo", "BLUE")));
        List<Block> lb = cell.getBlocks();
        assertEquals("LEVEL1", lb.get(0).typeBlock);
        assertNull(lb.get(0).owner);
        assertNull(lb.get(0).color);
        assertEquals("WORKER", lb.get(1).typeBlock);
        assertEquals("pippo", lb.get(1).owner);
        assertEquals("BLUE", lb.get(1).color);
        List<String> raw = cell.getRawData();
        assertEquals("- W -", raw.get(0));
        assertEquals("BLUE", raw.get(1));
        assertEquals("LEVEL1", raw.get(2));
    }
}