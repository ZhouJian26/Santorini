package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Pinger;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.Notification;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable<Notification> implements Runnable, Observer<String>, Closeable {

    private Socket socket;
    private PrintWriter sender;
    private String username;
    private Boolean active = true;
    private GameMode mode;

    private final Pinger pinger;
    Lobby lobby = Lobby.getInstance();

    /**
     * Set the connection
     *
     * @param socket socket
     * @param server server
     */

    public Connection(Socket socket, Server server) {
        this.socket = socket;

        pinger = new Pinger(this);
    }

    private synchronized boolean isActive() {
        return active;
    }

    public boolean isConnected() {
        try {
            socket.sendUrgentData(0xFF);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Send messages
     * 
     * @param message
     */

    public void send(String message) {
        sender.println(message);
        sender.flush();
    }

    /**
     * To close the current connection
     *
     */

    public synchronized void closeConnection() {
        send("Connection closed");
        try {
            socket.close();
        } catch (IOException ex) {
        }
        active = false;
    }

    /**
     * Close all
     */

    @Override
    public void close() {
        if (Boolean.FALSE.equals(active))
            return;
        notify(new Notification(username, new Gson().toJson(new Command("quitPlayer", "quitPlayer", null, null))));
        closeConnection();
        pinger.stop();
        System.out.println("Closing connection");
        System.out.println("Done");
    }

    @Override
    public void run() {
        try {

            Scanner receiver = new Scanner(socket.getInputStream());
            sender = new PrintWriter(socket.getOutputStream());

            socket.setSoTimeout(30000);
            new Thread(pinger).start();

            while (true) {
                String input = receiver.nextLine();
                notify(new Notification(username, ""));
                if (GameMode.strConverter(input) == null) {
                    send("ko");
                }
                if (input.equals(" ") || GameMode.strConverter(input) == null)
                    continue;
                this.mode = GameMode.strConverter((input));
                break;
            }
            send("ok");
            while (true) {
                username = receiver.nextLine();
                notify(new Notification(username, ""));
                if (username.equals(" "))
                    continue;
                boolean check = lobby.addPlayer(username);
                if (check)
                    break;
                send("ko");
            }
            send("ok");
            // First check if added successfully -> boolean

            // Then trie to start a game -> boolean
            int added;
            added = lobby.putOnWaiting(this, username, mode);
            if (added == 2)
                send("Waiting for other players");
            if (added == 3)
                send("Waiting for other players");
            if (added == 1)
                send("Loading game");
            send("Start");

            while (isActive()) {
                String clientInput = receiver.nextLine(); // Start getting moves from players
                Notification notification = new Notification(username, clientInput);
                notify(notification);
            }
        } catch (Exception e) {
            System.out.println("Connection lost: " + username);
        } finally {
            close();
        }
    }

    @Override
    public void update(String message) {
        send(message);
    }

}