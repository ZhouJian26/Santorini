package it.polimi.ingsw.view.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;

public class Connection extends Observable<ArrayList<Command>> implements Runnable, Observer<Command> {
    private final transient String ip;
    private final transient int port;
    private transient Socket socket;
    private transient Scanner receiver;
    private transient PrintWriter sender;
    private boolean isActive = false;

    public Connection(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        connectToServer();
    }

    private synchronized void connectToServer() {
        while (!isActive) {
            try {
                socket = new Socket(ip, port);
                receiver = new Scanner(socket.getInputStream());
                sender = new PrintWriter(socket.getOutputStream());
                setStatus(true);
            } catch (IOException e) {
                setStatus(false);
            }
        }
    }

    private synchronized void send(String toSend) {
        if (!isActive)
            return;
        sender.println(toSend);
        sender.flush();
    }

    private synchronized void setStatus(boolean status) {
        this.isActive = status;
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                String serverPush = receiver.nextLine();
                notify(new Gson().fromJson(serverPush, new TypeToken<ArrayList<Command>>() {
                }.getType()));
            }
        } catch (NoSuchElementException e) {
            setStatus(false);
            connectToServer();
        }
    }

    @Override
    public void update(Command command) {
        if (command == null || command.funcName == null || command.funcName.equals(""))
            throw new NullPointerException();
        send(new Gson().toJson(command));
    }
}