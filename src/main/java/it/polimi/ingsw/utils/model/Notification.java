package it.polimi.ingsw.utils.model;

public class Notification extends Object {
    public final String username;
    public final String message;

    public Notification(String username, String input) {
        if (username == null || input == null || username.length() == 0 || input.length() == 0)
            throw new NullPointerException();
        this.username = username;
        this.message = input;
    }
}
