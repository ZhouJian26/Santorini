package it.polimi.ingsw.view.CLI;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.view.socket.Connection;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.socket.*;

public class AppCLI extends Observable<String> implements Runnable, Observer<String> {
    // todo codice CLI di Santorini
    private Connection connection;
    private Parser parser = new Parser();
    private Boolean statusRequest;
    private final Scanner scanner;
    private ViewPrinter printer;
    private String username;

    public AppCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    private void start() {
        while (true) {
            System.out.println("Hi! Server IP & Port, please");
            String[] in = scanner.nextLine().split(" ");
            try {
                if (in.length == 2) {
                    System.out.println("Connection..");
                    connection = new Connection(in[0], Integer.parseInt(in[1]));
                    printer = new ViewPrinter(parser);
                    parser.addObservers(printer);
                    this.addObservers(connection);
                    connection.addObservers(this);
                    connection.addObservers(parser);
                    new Thread(connection).start();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Welcome on Santorini CLI.");
        while (true) {
            System.out.println("Choose game mode\n1) Two players\n2) Three players");
            String in = scanner.nextLine();
            if (in.equals("1") || in.equals("2"))
                try {
                    statusRequest = null;
                    notify(in.equals("1") ? "TWO" : "THREE");
                    System.out.println("Waiting server response...");
                    while (statusRequest == null) {
                        Thread.sleep(300);
                    }
                    if (statusRequest == true)
                        break;
                } catch (Exception e) {
                }
        }
        while (true) {
            System.out.println("Insert username");
            String in = scanner.nextLine();
            if (in.length() != 0)
                try {
                    statusRequest = null;
                    notify(in);
                    System.out.println("Waiting server response...");
                    while (statusRequest == null) {
                        Thread.sleep(300);
                    }
                    if (statusRequest == true) {
                        username = in;
                        break;
                    } else
                        System.out.println("Username not available");

                } catch (Exception e) {
                }
        }
        System.out.println("Waiting for other players...");
    }

    private void game() {
        /*
         * todo implementa un observer su parser che in update fa print su console
         * 
         * Game renderizza una di queste, e gestisce l'input da utente quindi invio di
         * comandi e cambio di pagina (print forzato)
         */
    }

    @Override
    public void run() {
        start();
        // game();
    }

    @Override
    public void update(String in) {
        if (in.equals("ok"))
            statusRequest = true;

        if (in.equals("ko"))
            statusRequest = false;
    }
}