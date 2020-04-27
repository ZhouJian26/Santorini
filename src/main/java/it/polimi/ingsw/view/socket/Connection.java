package it.polimi.ingsw.view.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class Connection extends Observable<String> implements Runnable, Observer<String> {
    public final transient String ip;
    public final transient int port;
    private final transient Socket socket;
    private final transient Scanner receiver;
    private final transient PrintWriter sender;
    private boolean isActive;

    /**
     * 
     * @param ip   server
     * @param port server
     * @throws IOException
     */
    public Connection(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.socket = new Socket(ip, port);
        this.receiver = new Scanner(socket.getInputStream());
        this.sender = new PrintWriter(socket.getOutputStream());
        this.isActive = true;
    }

    /**
     * 
     * @param toSend data to send to the server
     */
    private synchronized void send(String toSend) {
        if (!isActive)
            return;
        sender.println(toSend);
        sender.flush();
    }

    public boolean getStatus() {
        return isActive;
    }

    public void close() throws IOException {
        isActive = false;
        sender.close();
        receiver.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                String serverPush = receiver.nextLine();
                notify(serverPush);
            }
        } catch (NoSuchElementException e) {
            isActive = false;
        }
    }

    @Override
    public void update(String toSend) {
        if (toSend == null)
            throw new NullPointerException();
        send(toSend);
    }
}