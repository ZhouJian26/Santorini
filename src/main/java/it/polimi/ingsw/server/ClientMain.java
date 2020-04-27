package it.polimi.ingsw.server;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 18146);
        try {
            client.runClient();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
