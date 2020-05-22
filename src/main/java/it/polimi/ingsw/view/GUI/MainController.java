package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.CLI.ViewPrinter;
import it.polimi.ingsw.view.socket.Connection;
import it.polimi.ingsw.view.socket.Parser;

public class MainController  extends Observable<String> implements Observer<String> {
    private Connection connection;
    private Parser parser = new Parser();
    private Boolean statusRequest;
    private ViewPrinter printer;
    private String username;
    private GameMode gamemode;
    private MessageBox alert;


    public void sendUsername(String name){
    while (true) {
            try {
                statusRequest = null;
                notify(name);
                while (statusRequest == null) {
                    Thread.sleep(300);
                }
                if (statusRequest == true) {
                    username = name;
//                    printer.setUsername(username);
                    break;
                } else
                    alert.alert("Username unavailable");

            } catch (Exception e) {
            }
    }
}

    public void setMode(String mode){
    notify(mode);
    }


    public void setConnection(String ip, int port) {
    try{
        connection = new Connection(ip, port);
        parser.addObservers(printer);
        this.addObservers(connection);
        connection.addObservers(this);
        connection.addObservers(parser);
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

