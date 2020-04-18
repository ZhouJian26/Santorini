package it.polimi.ingsw.controller;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.socket.Notification;
import it.polimi.ingsw.view.Observer;

public class Controller implements Observer<Notification> {
    Game game;

    /**
     * @param game the reference to game
     */
    public Controller(Game game) {
        this.game = game;
    }

    /**
     * 
     * @param username    player username
     * @param godListJson json array of gods (string) that the "godlike" choose
     */
    private void setGodList(String username, String godListJson) {
        // convert string to god
        God[] godList = (God[]) Arrays.stream(new Gson().fromJson(godListJson, String[].class))
                .map(god -> God.strConverter(god)).collect(Collectors.toList()).toArray();
        game.setGodList(username, godList);
    }

    /**
     * 
     * @param username player username
     * @param god      god that this player choose, it is the god (string)
     */
    private void setGod(String username, String god) {
        game.setGod(username, God.strConverter(god));
    }

    /**
     * 
     * @param username player username
     * @param dataJson contains Color and Position array that player has choose
     */
    private void setWorkers(String username, String dataJson) {
        // convert string to color
        // check positions value
        SetWorkersStruct dataParsed = new Gson().fromJson(dataJson, SetWorkersStruct.class);
        game.setWorkers(Color.strConverter(dataParsed.getColor()), username, dataParsed.getPositions());
    }

    /**
     * 
     * @param username player username
     * @param position position of the woker that the player want use
     */
    private void chooseWorker(String username, String position) {

        game.chooseWorker(username, Integer.parseInt(position));
    }

    /**
     * 
     * @param username player username
     * @param position Json position of the action that the player want use in
     *                 format [t1,t2] that means t1 = y*5+x and t2 = z
     */
    private void chooseAction(String username, String position) {
        game.chooseAction(username, new Gson().fromJson(position, int[].class));
    }

    @Override
    public void update(Notification notification) {
        try {
            Command command = new Gson().fromJson(notification.getMessage(), Command.class);
            Method method = this.getClass().getDeclaredMethod(command.getCommand(), String.class, String.class);
            method.setAccessible(true);
            method.invoke(this, notification.getUsername(), command.getMessage());
        } catch (Exception e) {
        }
    }
}