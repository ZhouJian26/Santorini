package it.polimi.ingsw.utils.model;

public class ChatMessage {
    private final String username;
    private final String message;

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

}