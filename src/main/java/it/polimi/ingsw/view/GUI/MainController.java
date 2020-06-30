package it.polimi.ingsw.view.GUI;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.socket.Chat;
import it.polimi.ingsw.view.socket.Connection;
import it.polimi.ingsw.view.socket.Parser;

import java.util.List;

public class MainController extends Observable<String> implements Observer<String> {
    private Connection connection;
    private MessageBox alert;
    private Boolean statusRequest;
    private Parser parser;
    private AppGUI appGUI;
    private String username = null;

    /**
     * Set parser and application to controllers
     * @param parser parser
     * @param appGUI application
     */
    public void set(Parser parser, AppGUI appGUI) {
        this.appGUI = appGUI;
        this.parser = parser;
    }

    /**
     * Set chat
     * @return chat set
     */
    public Chat setChat() {
        Chat chat = new Chat(connection);
        chat.setUsername(username);
        return chat;
    }

    /**
     * Restart the application
     */
    public void quit() {
        appGUI.reStart();

    }

    /**
     * Close the connection
     */
    public void closeConnection() {
        if (connection != null && connection.getStatus())
            connection.close();
        connection = null;
    }

    /**
     * Send username to server
     * @param name username
     * @return if username is valid
     */
    public synchronized boolean sendUsername(String name) {
        if (username != null)
            return true;
        try {
            statusRequest = null;
            notify(name);
            while (statusRequest == null) {
                Thread.sleep(300);
            }

            if (statusRequest == false) {
                alert.alert("Username not available");
                return false;
            }

            username = name;
            return true;
        } catch (Exception e) {
            // Socker error
        }
        return false;
    }

    /**
     * Send chosen game mode to server
     * @param mode game mode
     */
    public void setMode(String mode) {

        try {
            statusRequest = null;
            notify(mode);
            while (statusRequest == null) {
                Thread.sleep(300);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Setup connection
     * @param ip server ip
     * @param port server port
     * @return if the conenction is set successfully
     */
    public synchronized boolean setConnection(String ip, int port) {
        if (connection != null && connection.getStatus())
            return false;
        try {
            connection = new Connection(ip, port);
            this.addObservers(connection);
            connection.addObservers(this);
            connection.addObservers(parser);
            connection.setMaster(appGUI);
            new Thread(connection).start();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Send players'actions to server
     * @param name player's username
     */
    public void send(String name) {
        String toSend = "";
        List<Command> commands = parser.getUsableCommandList();
        for (Command command : commands) {
            if (command.getFuncData() == null) {
                if (name == null) {
                    toSend = new Gson().toJson(command);
                }
            } else if (command.getFuncData().equals(name)) {
                toSend = new Gson().toJson(command);
                break;
            }
        }
        // System.out.println(toSend);

        notify(toSend);

    }

    /**
     * Get updated board
     * @return Refreshed board
     */
    public Cell[][] getBoard() {
        return parser.getBoard();
    }

    /**
     * Get commands
     * @return List of available commands
     */
    public List<Command> getCommand() {
        // System.out.println("getCommand: " + new
        // Gson().toJson(parser.getUsableCommandList()));
        return parser.getUsableCommandList();
    }

    /**
     * Get player's information
     * @return List of players'new information
     */
    public List<Player> getUserInfo() {
        // System.out.println("aaaaaaa :");
        return parser.getPlayers();
    }

    /**
     * Get the current player
     * @return Player
     */
    public String getCurrentPlayer() {
        return parser.getCurrentPlayer();
    }

    /**
     * Get player's username
     * @return player's username
     */
    public String getPlayer() {
        return username;
    }

    /**
     * Get Game Phase
     * @return Current game phase
     */
    public String getGamePhase() {
        return parser.getGamePhase();
    }

    /**
     * Change the scene
     */
    public void changeScene() {
        appGUI.changeScene();
    }

    /**
     * Receive answers from server
     * @param message message sent
     */
    @Override
    public void update(String message) {
        // System.out.println("MainController: " + message);
        if (message == null || message.equals("")) {
            return;
        }

        if (message.equals("ok"))
            statusRequest = true;
        if (message.equals("ko"))
            statusRequest = false;
    }

}
