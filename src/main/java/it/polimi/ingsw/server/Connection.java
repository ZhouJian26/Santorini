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

    public String getUsername() {
        return username;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Send messages
     * 
     * @param message
     */

    public synchronized void send(String message) {
        if (Boolean.FALSE.equals(active))
            return;
        sender.println(message);
        sender.flush();
    }

    /**
     * To close the current connection
     *
     */

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException ex) {
            // Ex on close
        }
    }

    /**
     * Close all
     */

    @Override
    public void close() {
        if (Boolean.FALSE.equals(active))
            return;
        active = false;
        notify(new Notification(username, new Gson().toJson(new Command("quitPlayer", "quitPlayer", null, null))));
        closeConnection();
        pinger.stop();
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
                // notify(new Notification(username, ""));
                if (input.equals(""))
                    continue;
                else if (GameMode.strConverter(input) == null) {
                    send("ko");
                }
                this.mode = GameMode.strConverter((input));
                break;
            }
            send("ok");
            while (true) {
                username = receiver.nextLine();
                // notify(new Notification(username, ""));
                if (username.equals(""))
                    continue;
                username.trim();
                if (Lobby.getInstance().putOnWaiting(this, username, mode))
                    break;
                send("ko");
            }
            send("ok");

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