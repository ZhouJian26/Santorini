package it.polimi.ingsw.view.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;
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
    private Boolean needUpDate;

    public void set(Parser parser, AppGUI appGUI) {
        this.appGUI = appGUI;
        this.parser = parser;
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
            new Thread(connection).start();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public void set(String name) {
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
        try {
            needUpDate = null;
            notify(toSend);
            while (needUpDate == null) {
                Thread.sleep(300);
            }
        } catch (Exception ignored) {
        }
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
        // System.out.println("aaaaaaa :" + new Gson().toJson(parser.getPlayers()));
        return parser.getPlayers();
    }

    public String getCurrentPlayer() {
        return parser.getCurrentPlayer();
    }

    public String getPlayer() {
        return username;
    }

    @Override
    public void update(String message) {
        // System.out.println("MainController: " + message);
        if (message == null) {
            return;
        }
        needUpDate = true;
        if (message.equals("ok"))
            statusRequest = true;

        if (message.equals("ko"))
            statusRequest = false;
    }

}
