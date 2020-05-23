package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.socket.Connection;

public class MainController  extends Observable<String> implements Observer<String> {
    private Connection connection;
    private MessageBox alert;
    private boolean statusRequest = false;


    public boolean sendUsername(String name){
        notify(name);
        return(statusRequest);
    }

    public void setMode(String mode){
        notify(mode);
        System.out.println(statusRequest);
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

