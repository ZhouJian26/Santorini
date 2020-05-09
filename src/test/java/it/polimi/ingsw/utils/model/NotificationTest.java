package it.polimi.ingsw.utils.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class NotificationTest {
    @Test
    public void base() {
        Notification n = new Notification("username", "message");
        assertEquals("username", n.username);
        assertEquals("message", n.message);
    }

    @Test
    public void initNull() {
        assertThrows(NullPointerException.class, () -> {
            new Notification(null, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new Notification("username", null);
        });
        assertThrows(NullPointerException.class, () -> {
            new Notification(null, "message");
        });
    }

    @Test
    public void initValueEmpty() {
        assertThrows(NullPointerException.class, () -> {
            new Notification("", "");
        });
        assertThrows(NullPointerException.class, () -> {
            new Notification("username", "");
        });
        assertThrows(NullPointerException.class, () -> {
            new Notification("", "message");
        });
    }
}