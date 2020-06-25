package it.polimi.ingsw.server;

import com.google.gson.Gson;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.ChatMessage;
import it.polimi.ingsw.utils.model.Notification;

class Chat extends Observable<String> implements Observer<Notification> {

    @Override
    public void update(Notification message) {
        try {
            ChatMessage chatMessage = new Gson().fromJson(message.message, ChatMessage.class);
            if (!message.username.equals(chatMessage.username) || chatMessage.message == null)
                return;
            notify(new Gson().toJson(new ChatMessage(message.username, chatMessage.message)));

        } catch (Exception e) {
            // Fail convert Json
        }
    }
}