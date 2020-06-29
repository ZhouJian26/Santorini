package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server {

    /**
     * Server's port
     */
    private static final int PORT = 9090;

    /**
     * Server socket
     */
    private ServerSocket serverSocket;

    /**
     * Executor
     */
    private ExecutorService runExecutor = Executors.newFixedThreadPool(64);

    /**
     * Server
     * @throws IOException
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Server run
     * @throws UnknownHostException
     */
    public void run() throws UnknownHostException {
        System.out.println("Server IP: " + Inet4Address.getLocalHost().getHostAddress() + "\nServer Port: " + PORT);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                runExecutor.submit(connection);

            } catch (IOException ex) {
                System.err.println("Connection failed!");
            }
        }
    }

}
