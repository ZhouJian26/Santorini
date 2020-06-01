package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.AppCLI;
import it.polimi.ingsw.view.gui.AppGUI;
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