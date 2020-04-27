package it.polimi.ingsw.server;

public class Notification extends Object {
    private String username;
    private String message;

    public Notification(String username, String input) {
        this.username = username;
        this.message = input;
    }

    public String getUsername() {
        return this.username;
    }

    public String getMessage() {
        return this.message;
    }

}
