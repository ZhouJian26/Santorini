package it.polimi.ingsw.utils.model;

import java.util.Date;

public class ChatMessage implements Comparable<ChatMessage> {
    public final String username;
    public final String message;
    private final Date date = new Date();

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public int compareTo(ChatMessage t) {
        return (int) (this.date.getTime() - t.date.getTime());
    }
}