package it.polimi.ingsw.utils.model;

/**
 * Chat Message Data Structure
 */
public class ChatMessage {
    /**
     * Author of the message
     */
    private final String username;
    /**
     * Message value
     */
    private final String message;

    /**
     * ChatMessage Constructor
     * 
     * @param username author of the message
     * @param message  message value
     */
    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    /**
     * Get message author
     * 
     * @return author of the message
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get message value
     * 
     * @return message value
     */
    public String getMessage() {
        return message;
    }

}