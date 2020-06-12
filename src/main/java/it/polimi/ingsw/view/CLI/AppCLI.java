package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.socket.AppInterface;
import it.polimi.ingsw.view.socket.Connection;
import it.polimi.ingsw.view.socket.Parser;

import java.util.Scanner;

public class AppCLI extends Observable<String> implements Observer<String>, AppInterface {
    private Connection connection;
    private Parser parser = new Parser();
    private Boolean statusRequest;
    private final Scanner scanner;
    private ViewPrinter printer;
    private String username;

    public AppCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    private void setServer() {
        String in = "";
        connection = null;
        while (!in.toUpperCase().equals("QUIT")) {
            ViewPrinter.clearConsole();
            ViewPrinter.printLogo();

            System.out.println("   Type QUIT to exit from server setup");
            System.out.print("   Server IP & Port: ");
            in = scanner.nextLine();
            try {
                String[] in2 = in.split(" ");
                if (in2.length == 2) {
                    System.out.println("   Connecting..");
                    connection = new Connection(in2[0], Integer.parseInt(in2[1]));
                    printer = new ViewPrinter(parser);

                    parser.addObservers(printer);
                    this.addObservers(connection);
                    connection.addObservers(this);
                    connection.addObservers(parser);
                    connection.setMaster(this);
                    new Thread(connection).start();
                    break;
                }
            } catch (Exception e) {
                // fail parsing
            }
        }
    }

    private void setUsername() {
        ViewPrinter.clearConsole();
        ViewPrinter.printLogo();
        while (connection != null && connection.getStatus()) {
            System.out.print("   Insert username: ");
            String in = scanner.nextLine();
            if (in.length() != 0)
                try {
                    statusRequest = null;
                    notify(in);
                    System.out.println("   Waiting for server response...");
                    while (statusRequest == null) {
                        Thread.sleep(300);
                    }
                    if (statusRequest == true) {
                        username = in;
                        printer.setUsername(username);
                        break;
                    } else
                        System.out.println("   Username not available");

                } catch (Exception e) {
                }
        }
    }

    private void setMode() {

        ViewPrinter.clearConsole();
        ViewPrinter.printLogo();

        while (connection != null && connection.getStatus()) {
            System.out.print("   Choose a game mode (Type 1 or 2)\n    1) Two players\n    2) Three players\n    ");
            String in = scanner.nextLine();
            if (in.equals("1") || in.equals("2"))
                try {
                    statusRequest = null;
                    notify(in.equals("1") ? "TWO" : "THREE");
                    System.out.println("    Waiting for server response...");
                    while (statusRequest == null) {
                        Thread.sleep(300);
                    }
                    if (statusRequest == true)
                        break;
                } catch (Exception e) {
                    // fail parsing
                }
        }
    }

    public void start() {
        String in = "";
        while (!in.toUpperCase().equals("QUIT")) {
            setServer();
            setMode();
            setUsername();
            ViewPrinter.clearConsole();
            ViewPrinter.printLogo();

            if (connection != null && connection.getStatus()) {
                System.out.println("   Waiting for other players...");
                printer.addObservers(connection);
                printer.setStatus(true);
            }

            while (!in.toUpperCase().equals("QUIT") && connection != null && connection.getStatus()) {
                in = scanner.nextLine();
                printer.useAction(in);
            }

            if (connection != null && connection.getStatus())
                connection.close();
            System.out.print("\n   Type QUIT to close the CLI, or any other key to play again.\n    ");
            in = scanner.nextLine();
        }
    }

    @Override
    public void update(String in) {
        System.out.println("cli: "+ in);
        if (in.equals("ok"))
            statusRequest = true;

        if (in.equals("ko"))
            statusRequest = false;
    }

    @Override
    public void onDisconnection() {
        System.out.println("\n   Network error, Try later.");
    }
}