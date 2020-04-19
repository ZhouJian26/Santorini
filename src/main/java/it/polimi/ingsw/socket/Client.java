package it.polimi.ingsw.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;

    public Client (String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    /**
     * Start run the client
     */

    public void runClient () throws  IOException{
        Socket socket = new Socket(ip, port);       //connection
        System.out.println("Connection succeed!");
        Scanner socketReceiver = new Scanner(socket.getInputStream());
        PrintWriter socketSender = new PrintWriter(socket.getOutputStream());
        Scanner clientInput = new Scanner(System.in); //client input
        String socketMessage;

        try{
            socketMessage = socketReceiver.nextLine(); //Save received message in string
            System.out.println(socketMessage); //Print message received
            while (true) {
                String newMessage = clientInput.nextLine(); //save client input
                socketSender.println(newMessage); //send the client input
                socketSender.flush();
                socketMessage = socketReceiver.nextLine();
                System.out.println(socketMessage);
            }
        } catch (NoSuchElementException ex){
            System.out.println("Connection closed by client");
        } finally {
            clientInput.close();
            socketReceiver.close();
            socketSender.close();
            socket.close();

        }

    }

}

