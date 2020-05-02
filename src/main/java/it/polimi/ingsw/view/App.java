package it.polimi.ingsw.view;

import it.polimi.ingsw.view.GUI.AppGUI;

public class App {
    public static void main(String[] args) {
        // TODO far scegliere al client CLI o GUI
        System.out.println("Hello");
        // per avviare la GUI su un altro thread rispetto a questo
        new Thread(new AppGUI()).start();
    }
}