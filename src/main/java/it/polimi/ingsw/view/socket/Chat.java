package it.polimi.ingsw.view.socket;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.ChatMessage;

/**
 * Client Chat Manager
 */
public class Chat extends Observable<ChatMessage> implements Observer<String> {
    /**
     * Chat History
     */
    private ArrayList<ChatMessage> chatHistory = new ArrayList<>();
    /**
     * Socket Connection
     */
    private final Connection connection;
    /**
     * History limit
     */
    final private int limit = 50;

    /**
     * Chat constructor
     * 
     * @param connection socket connection
     */
    public Chat(Connection connection) {
        this.connection = connection;
        connection.addObservers(this);
    }

    /**
     * Add a new message to history
     * 
     * @param message message to add
     */
    private synchronized void addMessage(ChatMessage message) {
        chatHistory.add(message);
        if (chatHistory.size() > limit)
            chatHistory.remove(0);
    }

    /**
     * Get Chat History
     * 
     * @return chat history
     */
    public ArrayList<ChatMessage> getHistory() {
        return new Gson().fromJson(new Gson().toJson(chatHistory), new TypeToken<ArrayList<ChatMessage>>() {
        }.getType());
    }

    /**
     * Send a message
     * 
     * @param message message to send
     */
    public void sendMessage(String message) {
        connection.send(new Gson().toJson(new ChatMessage(null, message)));
    }

    /**
     * Listen on new message coming from socket
     */
    @Override
    public void update(String message) {
        try {
            ChatMessage parsed = new Gson().fromJson(message, ChatMessage.class);
            if (parsed == null || parsed.getUsername() == null || parsed.getMessage() == null)
                return;
            addMessage(parsed);
            notify(parsed);
        } catch (JsonSyntaxException e) {
            // Fail Json Convert
        }
    }
}