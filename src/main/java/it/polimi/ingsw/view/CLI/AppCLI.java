package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.socket.Connection;
import it.polimi.ingsw.view.socket.Parser;

import java.util.Scanner;

public class AppCLI extends Observable<String> implements Observer<String> {
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

    private void setUp() {

        while (true) {
            ViewPrinter.clearConsole();
            ViewPrinter.printLogo();
            System.out.format("\n%113s", "Hi! Server IP & Port: ");
            String[] in = scanner.nextLine().split(" ");
            try {
                if (in.length == 2) {
                    System.out.format("%121s", "Connecting..");
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
            }
        }
        // System.out.println("Welcome on Santorini CLI.");
        while (true) {

            ViewPrinter.clearConsole();
            ViewPrinter.printLogo();
            System.out.format("\n%122s\n%121s\n%122s\n\n%114s", "Choose a game mode", "1) Two players",
                    "2) Three players", "");
            String in = scanner.nextLine();
            if (in.equals("1") || in.equals("2"))
                try {
                    statusRequest = null;
                    notify(in.equals("1") ? "TWO" : "THREE");
                    System.out.format("%128s", "Waiting for server response...");
                    while (statusRequest == null) {
                        Thread.sleep(300);
                    }
                    if (statusRequest == true)
                        break;
                } catch (Exception e) {
                }
        }
        while (true) {

            ViewPrinter.clearConsole();
            ViewPrinter.printLogo();
            System.out.format("%114s", "Insert username: ");
            String in = scanner.nextLine();
            if (in.length() != 0)
                try {
                    statusRequest = null;
                    notify(in);
                    System.out.format("%127s", "Waiting for server response...");
                    while (statusRequest == null) {
                        Thread.sleep(300);
                    }
                    if (statusRequest == true) {
                        username = in;
                        printer.setUsername(username);
                        break;
                    } else
                        System.out.println("Username not available");

                } catch (Exception e) {
                }
        }

        ViewPrinter.clearConsole();
        ViewPrinter.printLogo();
        System.out.format("%128s", "Waiting for other players...");
    }

    public void start() {
        setUp();
        printer.addObservers(connection);
        printer.setStatus(true);
        while (true) {
            String in = scanner.nextLine();
            try {
                printer.useAction(Integer.parseInt(in));
            } catch (Exception e) {
                printer.useAction(-1);
            }
        }

    }

    @Override
    public void update(String in) {
        System.out.println("Received: " + in);
        if (in.equals("ok"))
            statusRequest = true;

        if (in.equals("ko"))
            statusRequest = false;
    }
}