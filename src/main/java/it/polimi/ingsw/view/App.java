package it.polimi.ingsw.view;

import it.polimi.ingsw.view.CLI.AppCLI;
import it.polimi.ingsw.view.GUI.AppGUI;
import javafx.application.Application;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            Application.launch(AppGUI.class);
        } else {
            new AppCLI(new Scanner(System.in)).start();
        }
    }
}