package it.polimi.ingsw.utils.model;

public class Notification extends Object {
    public final String username;
    public final String message;

    public Notification(String username, String input) {
        this.username = username;
        this.message = input;
    }
}
