package it.polimi.ingsw.view;

import java.util.Scanner;

import it.polimi.ingsw.view.CLI.AppCLI;
import it.polimi.ingsw.view.CLI.ViewPrinter;
import it.polimi.ingsw.view.GUI.AppGUI;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String in;
        while (true) {
            ViewPrinter.clearConsole();
            ViewPrinter.printLogo();
            System.out.format("%125s\n%128s\n\n", "Welcome to Santorini!", " What interface do you prefer?");
            System.out.format("%153s\n\n%114s",
                    "Please input 'CLI' (Command Line Interface) or 'GUI' (Graphic User Interface)", "");
            in = scanner.nextLine();
            in = in.toUpperCase();
            if (in.equals("CLI") || in.equals("GUI"))
                break;
        }
        if (in.equals("CLI"))
            new Thread(new AppCLI(scanner)).start();
        else
            Application.launch(AppGUI.class);
    }
}