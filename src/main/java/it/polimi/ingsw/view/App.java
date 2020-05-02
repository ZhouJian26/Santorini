package it.polimi.ingsw.view;

import java.util.Scanner;

import it.polimi.ingsw.view.CLI.AppCLI;
import it.polimi.ingsw.view.GUI.AppGUI;

public class App {
    public static void main(String[] args) {
        // TODO far scegliere al client CLI o GUI
        Scanner scanner = new Scanner(System.in);
        String in;
        while (true) {
            System.out.println("Salve! CLI o GUI?");
            in = scanner.nextLine();
            in = in.toUpperCase();
            if (in.equals("CLI") || in.equals("GUI"))
                break;
        }
        scanner.close();
        if (in.equals("CLI"))
            new Thread(new AppCLI()).start();
        else
            new Thread(new AppGUI()).start();
    }
}