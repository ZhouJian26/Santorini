package it.polimi.ingsw.server;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Notification;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable<Notification> implements Runnable, Observer<String> {

    private Socket socket;
    private Scanner receiver;
    private PrintWriter sender;
    private Server server;
    private String username;
    private boolean active = true;
    private GameMode mode;

    /**
     * Set the connection
     *
     * @param socket socket
     * @param server server
     */

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
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
            System.err.println(ex.getMessage());
        }
        active = false;
    }

    /**
     * Close all
     */

    private void close() {
        closeConnection();
        System.out.println("Closing connection");
        server.removeConnection(this);
        System.out.println("Done");

    }

    @Override
    public void run() {
        try {
            receiver = new Scanner(socket.getInputStream());
            sender = new PrintWriter(socket.getOutputStream());
            // send("Welcome to Santorini! ");
            while (true) {
                // send("In which mode do you prefer to play? Please input 'two' or 'three'");
                String input = receiver.nextLine();
                if (GameMode.strConverter(input) == null) {
                    send("ko");
                    continue;
                }
                this.mode = GameMode.strConverter((input));
                break;
            }

            send("ok");
            while (true) {
                // send("Please give us your username");
                username = receiver.nextLine();
                boolean check = server.addPlayer(username);
                if (check)
                    break;
                // send("username unavailable!"
                send("ko");
            }

            send("ok");
            Lobby lobby = Lobby.getInstance();
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
            while (isActive()) {
                String clientInput = receiver.nextLine(); // Start getting moves from players
                Notification notification = new Notification(username, clientInput);
                notify(notification);
            }
        } catch (Exception e) {
            System.out.println("Connection lost: " + username);
            e.printStackTrace();
        } finally {
            close();
        }

    }

    @Override
    public void update(String message) {
        send(message);
    }

}