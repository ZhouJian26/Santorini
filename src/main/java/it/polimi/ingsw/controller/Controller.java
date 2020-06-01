package it.polimi.ingsw.controller;

import java.lang.reflect.Method;

import com.google.gson.Gson;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.utils.model.Notification;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.FuncCommand;

public class Controller implements Observer<Notification> {
    private final Game game;

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
    public void setGodList(String username, String god) {
        // convert string to god
        game.setGodList(username, God.strConverter(god));
    }

    /**
     * 
     * @param username player username
     * @param god      god that this player choose, it is the god (string)
     */
    public void setGod(String username, String god) {
        game.setGod(username, God.strConverter(god));
    }

    /**
     * 
     * @param username player username
     * @param position Position that player has choose
     */
    public void setWorkers(String username, String position) {
        game.setWorkers(username, Integer.parseInt(position));
    }

    /**
     * 
     * @param username player username
     * @param color    color player choosed
     */
    public void setColor(String username, String color) {
        game.setColor(username, Color.strConverter(color));
    }

    /**
     * 
     * @param username player username
     * @param position position of the woker that the player want use
     */
    public void chooseWorker(String username, String position) {

        game.chooseWorker(username, Integer.parseInt(position));
    }

    /**
     * 
     * @param username player username
     * @param position Json position of the action that the player want use in
     *                 format [t1,t2] that means t1 = y*5+x and t2 = z
     */
    public void chooseAction(String username, String position) {
        game.chooseAction(username, position == null ? null : new Gson().fromJson(position, int[].class));
    }

    public void setStartPlayer(String username, String targetUsername) {
        game.choosePlayer(username, targetUsername);
    }

    public void quitPlayer(String username) {
        game.quitPlayer(username);
    }

    /**
     * 
     * @param username     player username
     * @param functionName function name to use
     * @param data         data to use for the function
     */
    private synchronized void splitter(String username, String functionName, String data) {
        try {
            Method method;
            switch (FuncCommand.getParamClass(functionName)) {
                case 1:
                    method = this.getClass().getDeclaredMethod(functionName, String.class);
                    method.invoke(this, username);
                    break;
                case 2:
                    method = this.getClass().getDeclaredMethod(functionName, String.class, String.class);
                    method.invoke(this, username, data);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            // Just fail to search the function
        }
    }

    @Override
    public void update(Notification notification) {
        try {
            Command command = new Gson().fromJson(notification.message, Command.class);
            splitter(notification.username, command.funcName, command.funcData);
        } catch (Exception e) {
            // Just fail to parse
        }
    }
}