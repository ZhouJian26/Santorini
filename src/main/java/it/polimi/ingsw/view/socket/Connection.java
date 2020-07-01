package it.polimi.ingsw.view.socket;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Pinger;

/**
 * Client Side socket connection handler class
 */
public class Connection extends Observable<String> implements Runnable, Observer<String>, Closeable {
    /**
     * Socket
     */
    private final Socket socket;
    /**
     * Receiver
     */
    private final Scanner receiver;
    /**
     * Sender
     */
    private final PrintWriter sender;
    /**
     * Pinger
     */
    private final Pinger pinger;
    /**
     * Socket connection
     */
    private boolean isActive = false;
    /**
     * Owner of the connection
     */
    private AppInterface master;

    /**
     * Connection Constructor
     * 
     * @param ip   server ip
     * @param port server port
     * @throws IOException
     */
    public Connection(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.receiver = new Scanner(socket.getInputStream());
        this.sender = new PrintWriter(socket.getOutputStream());
        socket.setSoTimeout(30000);
        pinger = new Pinger(this);
    }

    /**
     * Set Connection Owner
     * 
     * @param master owner
     */
    public void setMaster(AppInterface master) {
        this.master = master;
    }

    /**
     * Send a data in the socket
     * 
     * @param toSend data to send
     */
    public synchronized void send(String toSend) {
        if (!isActive)
            return;
        sender.println(toSend);
        sender.flush();
    }

    /**
     * Get Socket connection
     * 
     * @return socket connection
     */
    public boolean getStatus() {
        return isActive;
    }

    /**
     * close socket
     * 
     */
    @Override
    public void close() {
        isActive = false;
        try {
            pinger.stop();
            sender.close();
            receiver.close();
            socket.close();
        } catch (IOException e) {
            // Fail Close
        }
    }

    /**
     * Function to handle push data from server, then notify to all observers of
     * this connection
     */
    @Override
    public void run() {

        this.isActive = true;
        new Thread(pinger).start();

        try {
            while (isActive) {
                String serverPush = receiver.nextLine();
                notify(serverPush);
            }
        } catch (Exception e) {
            if (isActive)
                master.onDisconnection();
            close();
        }
    }

    /**
     * Function triggered when need send data to server
     */
    @Override
    public void update(String toSend) {
        send(toSend);
    }
}