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

    private void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Salve! Inserire IP & Port server.");
            String[] in = scanner.nextLine().split(" ");
            try {
                if (in.length == 2) {
                    connection = new Connection(in[0], Integer.parseInt(in[1]));
                    this.addObservers(connection);
                    connection.addObservers(this);
                    connection.addObservers(parser);
                    new Thread(connection).start();
                    break;
                }
            } catch (Exception e) {
            }
        }
        System.out.println("Benvenuto su Santorini CLI.");
        while (true) {
            System.out.println("Inserisci l'username");
            String in = scanner.nextLine();
            if (in.length() != 0)
                try {
                    statusRequest = null;
                    notify(in);
                    System.out.println("Attesa server...");
                    while (statusRequest == null) {
                        Thread.sleep(1000);
                    }
                    if (statusRequest == true)
                        break;
                    else
                        System.out.println("Username non disponibile");

                } catch (Exception e) {
                }
        }
        while (true) {
            System.out.println("Scegli la modalità di gioco\n 1) Due giocatori\n 2) Tre giocatori");
            String in = scanner.nextLine();
            if (in.equals("1") || in.equals("2"))
                try {
                    statusRequest = null;
                    notify(in.equals("1") ? "TWO" : "THREE");
                    System.out.println("Attesa server...");
                    while (statusRequest == null) {
                        Thread.sleep(1000);
                    }
                    if (statusRequest == true)
                        break;
                } catch (Exception e) {
                }
        }
        System.out.println("Attesa altri giocatori...");
        scanner.close();
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
        game();
    }

    @Override
    public void update(String in) {
        if (in.equals("ok"))
            statusRequest = true;

        if (in.equals("ko"))
            statusRequest = false;
    }
}