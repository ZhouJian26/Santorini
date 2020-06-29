package it.polimi.ingsw.utils.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class NotificationTest {
    @Test
    public void base() {
        Notification n = new Notification("username", "message");
        assertEquals("username", n.getUsername());
        assertEquals("message", n.getMessage());
    }
}