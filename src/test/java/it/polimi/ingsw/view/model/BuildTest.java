package it.polimi.ingsw.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import org.junit.Test;

import it.polimi.ingsw.utils.model.Command;

public class BuildTest {
    @Test
    public void init() {
        Build b = new Build("block", Arrays.asList(0, 0), true);
        assertEquals("block", b.block);
        assertEquals(0, b.position.get(0));
        assertEquals(0, b.position.get(1));
        assertEquals(true, b.status);
        List<String> raw = b.getRawData();
        assertEquals("Build", raw.get(0));
        assertEquals("Type Block: block", raw.get(1));
        assertEquals("Position: [0]", raw.get(2));
        b.setToSend(new Command("action", "choose_action", "ingo", "0"));
        assertEquals(new Gson().toJson(new Command("action", "choose_action", "ingo", "0")), b.getToSend());
    }
}