package it.polimi.ingsw.view.socket;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.ChatMessage;

public class Chat extends Observable<ChatMessage> implements Observer<String> {
    private ArrayList<ChatMessage> chatHistory = new ArrayList<>();
    private final Connection connection;
    private String username;
    private int limit = 50;

    public Chat(Connection connection) {
        this.connection = connection;
        connection.addObservers(this);
    }

    private synchronized void addMessage(ChatMessage message) {
        chatHistory.add(message);
        if (chatHistory.size() > limit)
            chatHistory.remove(0);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<ChatMessage> getHistory() {
        return new Gson().fromJson(new Gson().toJson(chatHistory), new TypeToken<ArrayList<ChatMessage>>() {
        }.getType());
    }

    public boolean sendMessage(String message) {
        if (username == null)
            return false;
        System.out.println("sendMessage");
        connection.send(new Gson().toJson(new ChatMessage(username, message)));
        return true;
    }

    @Override
    public void update(String message) {
        try {
            // System.out.println("updateChat"+message);
            ChatMessage parsed = new Gson().fromJson(message, ChatMessage.class);
            if (parsed == null || parsed.getUsername() == null || parsed.getMessage() == null)
                return;
            // System.out.println(parsed.username+parsed.message);
            addMessage(parsed);
            notify(parsed);
        } catch (JsonSyntaxException e) {
            // Fail Json Convert
        }
    }
}