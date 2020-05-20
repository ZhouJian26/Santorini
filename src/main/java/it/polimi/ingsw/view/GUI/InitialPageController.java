package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.socket.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController {
    private Connection connection;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ip, port;

    @FXML
    private Button connect;

    @FXML
    /**
     * Creating connection to server
     */
    void setConnection(ActionEvent event) {
        if(!ip.getText().equals("")&&!port.getText().equals("")){
            try{
                connection = new Connection(ip.getText(), Integer.parseInt(port.getText()));
                System.out.println("Connected");

            }catch (Exception e){

            }
        }

    }



}
