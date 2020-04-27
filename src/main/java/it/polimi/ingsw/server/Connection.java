package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.view.Observable;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.Notification;
import it.polimi.ingsw.view.Observer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;

public class Connection extends Observable<Notification> implements Runnable, Observer {

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
            send("Welcome to Santorini! In which mode do you prefer to play? Please input 'two' or 'three'");
            this.mode = GameMode.strConverter(receiver.nextLine());
            send("Now please give us your username");
            username = receiver.nextLine();
            new Lobby(this, username, mode);
            while (isActive()) {
                String clientInput = receiver.nextLine(); // Start getting moves from players
                Notification notification = new Notification(username, clientInput);
                notify(notification);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void update(Object message) {
        System.out.println("Received: " + message);
    }
}