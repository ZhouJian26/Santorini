package it.polimi.ingsw.view.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class Connection extends Observable<String> implements Runnable, Observer<String> {
    public final String ip;
    public final int port;
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
        System.out.println("send: " + toSend);
        if (!isActive)
            return;
        sender.println(toSend);
        sender.flush();
    }

    public boolean getStatus() {
        return isActive;
    }

    /**
     * Function to send data string to server
     * 
     */
    public void close() {
        isActive = false;
        sender.close();
        receiver.close();
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    /**
     * Function to handle push data from server, then norify to all observer of this
     * connection
     */
    @Override
    public void run() {
        try {
            while (isActive) {
                String serverPush = receiver.nextLine();
                System.out.println("receiver:" + serverPush);
                notify(serverPush);
            }
        } catch (NoSuchElementException e) {
            close();
        }
    }

    /**
     * Function triggered when need send data to server
     */
    @Override
    public void update(String toSend) {

        System.out.println("connection : " + toSend);
        if (toSend == null)
            throw new NullPointerException();
        send(toSend);
    }
}