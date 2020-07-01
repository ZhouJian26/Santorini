package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class SwapTest {
    @Test
    public void initMove() {
        Swap s = new Swap(Arrays.asList(0, 0), Arrays.asList(0, 0), Arrays.asList(0, 0), Arrays.asList(0, 0));
        ArrayList<String> res = s.getRawData();
        assertEquals("Move", res.get(0));
        assertEquals("[0] => [0]", res.get(1));
    }

    @Test
    public void initGod() {
        Swap s = new Swap(Arrays.asList(0, 0), Arrays.asList(0, 0), Arrays.asList(0, 0), Arrays.asList(0, 1));
        ArrayList<String> res = s.getRawData();
        assertEquals("Swap", res.get(0));
        assertEquals("[0] => [0]", res.get(1));
        assertEquals("[0] => [1]", res.get(2));
    }
}