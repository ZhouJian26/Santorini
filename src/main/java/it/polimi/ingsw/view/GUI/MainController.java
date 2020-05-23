package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.socket.Connection;

public class MainController  extends Observable<String> implements Observer<String> {
    private Connection connection;
    private MessageBox alert;
    private Boolean statusRequest;


    public boolean sendUsername(String name){
        try {
            statusRequest = null;
            notify(name);
            while (statusRequest == null) {
                Thread.sleep(300);
            }
            if (statusRequest == false) {
                alert.alert("Username not available");
            return false;}

        } catch (Exception e) {
        }
        return statusRequest;
    }

    public void setMode(String mode){
        try {
            statusRequest = null;
            notify(mode);
            while (statusRequest == null) {
                Thread.sleep(300);
            }
        } catch (Exception e) {
        }
    }


    public void setConnection(String ip, int port) {
    try{
        connection = new Connection(ip, port);
        this.addObservers(connection);
        connection.addObservers(this);
        new Thread(connection).start();

    }catch (Exception e){};

    }


    @Override
    public void update(String message) {
        if (message.equals("ok"))
            statusRequest = true;

        if (message.equals("ko"))
            statusRequest = false;
    }

    }

