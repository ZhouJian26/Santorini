package it.polimi.ingsw.utils.model;

public class Notification extends Object {
    private final String username;
    private final String message;

    public Notification(String username, String input) {
        this.username = username;
        this.message = input;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
