package it.polimi.ingsw.utils.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class ChatMessageTest {
    @Test
    public void init() {
        ChatMessage cm = new ChatMessage("marco", "marco text");
        assertEquals("marco", cm.getUsername());
        assertEquals("marco text", cm.getMessage());
    }
}