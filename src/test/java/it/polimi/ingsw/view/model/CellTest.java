package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CellTest {
    @Test
    public void init() {
        Cell cell = new Cell(Arrays.asList(new Block("LEVEL1", null, null), new Block("WORKER", "pippo", "BLUE")));
        List<Block> lb = cell.getBlocks();
        assertEquals("LEVEL1", lb.get(0).getTypeBlock());
        assertNull(lb.get(0).getOwner());
        assertNull(lb.get(0).getColor());
        assertEquals("WORKER", lb.get(1).getTypeBlock());
        assertEquals("pippo", lb.get(1).getOwner());
        assertEquals("BLUE", lb.get(1).getColor());
        List<String> raw = cell.getRawData();
        assertEquals("- W -", raw.get(0));
        assertEquals("BLUE", raw.get(1));
        assertEquals("LEVEL1", raw.get(2));
    }

    @Test
    public void equalsTest() {
        Cell cell = new Cell(Arrays.asList(new Block("LEVEL1", null, null), new Block("WORKER", "pippo", "BLUE")));
        Cell cell2 = new Cell(Arrays.asList(new Block("LEVEL1", null, null)));
        assertFalse(cell.equals(cell2));

        cell2 = new Cell(Arrays.asList(new Block("LEVEL1", null, null), new Block("LEVEL2", null, null)));
        assertFalse(cell.equals(cell2));

        cell2 = new Cell(Arrays.asList(new Block("LEVEL1", null, null), new Block("WORKER", "pippo", "RED")));
        assertFalse(cell.equals(cell2));
        cell = new Cell(Arrays.asList());
        cell2 = new Cell(Arrays.asList());
        assertTrue(cell.equals(cell2));
    }

    @Test
    public void equalsError() {
        Cell cell = new Cell(Arrays.asList(new Block("LEVEL1", null, null), new Block("WORKER", "pippo", "BLUE")));
        assertFalse(cell.equals(null));
    }
}