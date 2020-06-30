package it.polimi.ingsw.server;

import com.google.gson.Gson;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.ChatMessage;
import it.polimi.ingsw.utils.model.Notification;

class Chat extends Observable<String> implements Observer<Notification> {
    /**
     * Broadcast the message if it is valid
     * @param message message that needs to be updated
     */
    @Override
    public void update(Notification message) {
        try {
            ChatMessage chatMessage = new Gson().fromJson(message.getMessage(), ChatMessage.class);
            if (!message.getUsername().equals(chatMessage.getUsername()) || chatMessage.getMessage() == null)
                return;
            notify(new Gson().toJson(new ChatMessage(message.getUsername(), chatMessage.getMessage())));

        } catch (Exception e) {
            // Fail convert Json
        }
    }
}