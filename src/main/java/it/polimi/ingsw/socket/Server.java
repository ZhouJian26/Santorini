package it.polimi.ingsw.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 18146;
    private ServerSocket serverSocket;
    private ExecutorService runExecutor = Executors.newFixedThreadPool(128);
    private List<Connection> listOfConnections = new ArrayList<>();


    private synchronized void addConnection (Connection c){
        listOfConnections.add(c);
    }


    /**
     *
     * @param c connection to be closed
     *
     * Todo need to complete
     *          check how to remove all connection of different game modes
     */
    protected synchronized void removeConnection (Connection c){
        //control the mode then check if need to delete 2 or 3 connections from the list

        listOfConnections.remove(c);
/*        Connection toBeClosed = playingList.get(c);
        if (toBeClosed != null){
       //     toBeClosed.closeConection();
            playingList.remove(c);
            */

        }

     public Server() throws IOException{
        this.serverSocket = new ServerSocket(PORT);
     }

     public void run (){
        System.out.println("Server is ready on port: " + PORT);
        while (true){
            try{
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                addConnection(connection);
                runExecutor.submit(connection);

            }catch ( IOException ex){
                System.err.println("Connection failed!");
            }
        }
     }



    }

