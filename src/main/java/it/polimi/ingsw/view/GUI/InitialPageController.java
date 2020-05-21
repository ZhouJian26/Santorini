package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.socket.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private TextField ip, port, username;

    @FXML
    private Button connect, playButton;

    @FXML
    private ChoiceBox<?> modes;

    @FXML
    /**
     * Creating connection to server
     */
    void setConnection(ActionEvent event) {
        if(!ip.getText().equals("")&&!port.getText().equals("")){
            try{
                connection = new Connection(ip.getText(), Integer.parseInt(port.getText()));
                System.out.println("Connected");
                changeScene();

            }catch (Exception e){

            }
        }

    }

    @FXML
    void sendPlayerInfo(ActionEvent event) {
        System.out.println("ok");
    }

    @FXML
    private void initialize(){
        ip.setVisible(true);
        port.setVisible(true);
        connect.setVisible(true);
        modes.setVisible(false);
        username.setVisible(false);
        playButton.setVisible(false);
    }

    private void changeScene(){
        ip.setVisible(false);
        port.setVisible(false);
        connect.setVisible(false);
        modes.setVisible(true);
        username.setVisible(true);
        playButton.setVisible(true);
    }






}
