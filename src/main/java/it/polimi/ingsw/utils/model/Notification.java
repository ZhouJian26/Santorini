package it.polimi.ingsw.utils.model;

/**
 * Class used to manage communication between Server Side Classes
 */
public class Notification extends Object {
    /**
     * Author of the notification. It equals to player's username
     */
    private final String username;
    /**
     * Value of the notification
     */
    private final String message;

    /**
     * Notification constructor
     * 
     * @param username author of this notification
     * @param input    message of the notification
     */
    public Notification(String username, String input) {
        this.username = username;
        this.message = input;
    }

    /**
     * Get notification author
     * 
     * @return notification author
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get notification value
     * 
     * @return notification value
     */
    public String getMessage() {
        return message;
    }
}
