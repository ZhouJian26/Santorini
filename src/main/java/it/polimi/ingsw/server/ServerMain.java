package it.polimi.ingsw.server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {
        Server server; // Create the server
        try {
            server = new Server();
            server.run();
        } catch (IOException ex) {
            System.err.println("Server error!\n" + ex.getMessage());
        }
    }
}
