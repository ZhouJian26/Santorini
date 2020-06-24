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

import java.util.ArrayList;
import java.util.List;

public class MainController extends Observable<String> implements Observer<String> {
    private Connection connection;
    private MessageBox alert;
    private Boolean statusRequest;
    private Parser parser;
    private AppGUI appGUI;
    private String username;

    public void set(Parser parser, AppGUI appGUI) {
        this.appGUI = appGUI;
        this.parser = parser;
    }

    public Chat setChat(){
        Chat chat= new Chat(connection);
        chat.setUsername(username);
        return chat;
    }

    public void quit(Boolean state) {
        if (connection != null && connection.getStatus())
            connection.close();
        if(state){
        appGUI.reStart();}
    }

    public boolean sendUsername(String name) {
        try {
            statusRequest = null;
            notify(name);
            while (statusRequest == null) {
                Thread.sleep(300);
            }
            if (name.equals(""))
                statusRequest = false;
            if (statusRequest == false) {
                alert.alert("Username not available");
                return false;
            }

        } catch (Exception e) {

        }
        username = name;
        return statusRequest;
    }

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

    public boolean setConnection(String ip, int port) {
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

    public void send(String name) {
        String toSend = "";
        List<Command> commands = parser.getUsableCommandList();
        for (Command command : commands) {
            if (command.funcData == null) {
                if (name == null) {
                    toSend = new Gson().toJson(command);
                }
            } else if (command.funcData.equals(name)) {
                toSend = new Gson().toJson(command);
                break;
            }
        }
        // System.out.println(toSend);

        notify(toSend);


    }

    public Cell[][] getBoard() {
        return parser.getBoard();
    }

    public List<Command> getCommand() {
        // System.out.println("getCommand: " + new
        // Gson().toJson(parser.getUsableCommandList()));
        return parser.getUsableCommandList();
    }

    public List<Player> getUserInfo() {
        //System.out.println("aaaaaaa :");
        return parser.getPlayers();
    }

    public String getCurrentPlayer() {
        return parser.getCurrentPlayer();
    }

    public String getPlayer() {
        return username;
    }

    public String getGamePhase(){
        return parser.getGamePhase();
    }
    public void changeScene() {
        appGUI.changeScene();
    }

    @Override
    public void update(String message) {
        //System.out.println("MainController: " + message);
        if (message == null || message.equals("")) {
            return;
        }

        if (message.equals("ok"))
            statusRequest = true;
        if (message.equals("ko"))
            statusRequest = false;
    }

}
