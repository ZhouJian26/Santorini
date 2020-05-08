package it.polimi.ingsw.view;

import java.util.Scanner;

import it.polimi.ingsw.view.CLI.AppCLI;
import it.polimi.ingsw.view.GUI.AppGUI;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Santorini!\n What interface do you prefer?");
        String in;
        while (true) {
            System.out.println("Please input 'CLI' (Command Line Interface) or 'GUI' (Graphic User Interface) ");
            in = scanner.nextLine();
            in = in.toUpperCase();
            if (in.equals("CLI") || in.equals("GUI"))
                break;
        }
        scanner.close();
        if (in.equals("CLI"))
            new Thread(new AppCLI()).start();
        else
            Application.launch(AppGUI.class));
    }
}