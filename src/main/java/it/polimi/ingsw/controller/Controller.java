package it.polimi.ingsw.controller;

import java.lang.reflect.Method;

import com.google.gson.Gson;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.server.Notification;
import it.polimi.ingsw.view.Observer;

public class Controller implements Observer<Notification> {
    private final transient Game game;

    /**
     * @param game the reference to game
     */
    public Controller(Game game) {
        if (game == null)
            throw new NullPointerException();
        this.game = game;
    }

    /**
     * 
     * @param username player username
     * @param god      god (string) that the "godlike" choose
     */
    private void setGodList(String username, String god) {
        // convert string to god
        game.setGodList(username, God.strConverter(god));
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
     * @param position Position that player has choose
     */
    private void setWorkers(String username, String position) {
        game.setWorkers(username, Integer.parseInt(position));
    }

    /**
     * 
     * @param username player username
     * @param color    color player choosed
     */
    private void setColor(String username, String color) {
        game.setColor(username, Color.strConverter(color));
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
        game.chooseAction(username, new Gson().fromJson(position, position.equals("null") ? null : int[].class));
    }

    /**
     * 
     * @param username     player username
     * @param functionName function name to use
     * @param data         data to use for the function
     */
    synchronized void splitter(String username, String functionName, String data) {
        try {
            // todo add control on funcName
            Method method = this.getClass().getDeclaredMethod(functionName, String.class, String.class);
            method.setAccessible(true);
            method.invoke(this, username, data);
        } catch (Exception e) {
        }
    }

    @Override
    public void update(Notification notification) {
        Command command = new Gson().fromJson(notification.getMessage(), Command.class);
        splitter(notification.getUsername(), command.funcName, command.funcData);
    }
}