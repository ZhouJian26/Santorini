package it.polimi.ingsw.server;

import java.io.IOException;

class ServerMain {

    public static void main(String[] args) {
        Server server;
        try {
            // Create & Run the server
            server = new Server();
            server.run();
        } catch (IOException ex) {
            System.err.println("Server error!\n" + ex.getMessage());
        }
    }
}
