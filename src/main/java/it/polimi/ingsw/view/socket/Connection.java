package it.polimi.ingsw.view.socket;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Pinger;

public class Connection extends Observable<String> implements Runnable, Observer<String>, Closeable {
    private final Socket socket;
    private final Scanner receiver;
    private final PrintWriter sender;
    private final Pinger pinger;
    private boolean isActive = false;
    private AppInterface master;

    /**
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

    public void setMaster(AppInterface master) {
        this.master = master;
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

    /**
     * Function to send data string to server
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
     * Function to handle push data from server, then norify to all observer of this
     * connection
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