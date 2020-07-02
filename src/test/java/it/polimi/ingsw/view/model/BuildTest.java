package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BuildTest {
    @Test
    public void init() {
        Build b = new Build("block", Arrays.asList(0, 0));
        assertEquals("block", b.getBlock());
        assertEquals(0, b.getPosition().get(0));
        assertEquals(0, b.getPosition().get(1));
        List<String> raw = b.getRawData();
        assertEquals("Build", raw.get(0));
        assertEquals("Type Block: block", raw.get(1));
        assertEquals("Position: [0]", raw.get(2));
    }
}