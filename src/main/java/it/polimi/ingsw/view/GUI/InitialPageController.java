package it.polimi.ingsw.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController {

    ObservableList<String> gameModes = FXCollections.observableArrayList("2 players", "3 players");
    private MessageBox alert = new MessageBox();
    private static MainController controller=new MainController();
    private boolean state = false;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label message;

    @FXML
    private TextField ip, port, username;

    @FXML
    private Button connect, sendMode, sendUsername;

    @FXML
    private ChoiceBox modes;

    @FXML
    void setConnection() {

        //if(!ip.getText().equals("")&&!port.getText().equals("")){
               controller.setConnection(ip.getText(), Integer.parseInt(port.getText()));
               changeScene();

        //}

    }

    @FXML
    void sendMode() {

        if(modes.getValue() == "2 players") controller.setMode("TWO");
        else controller.setMode("THREE");

        modes.setVisible(false);
        sendMode.setVisible(false);
        username.setVisible(true);
        sendUsername.setVisible(true);


    }


    @FXML
    void sendUsername(){
        state = controller.sendUsername(username.getText());
        if(state) {
            username.setVisible(false);
            sendUsername.setVisible(false);
            message.setVisible(true);
        }

    }


    @FXML
    private void initialize(){
        ip.setVisible(true);
        port.setVisible(true);
        connect.setVisible(true);
        modes.setVisible(false);
        sendMode.setVisible(false);
        username.setVisible(false);
        sendUsername.setVisible(false);
        message.setVisible(false);
    }

    private void changeScene(){
        ip.setVisible(false);
        port.setVisible(false);
        connect.setVisible(false);
        modes.setValue("2 players");
        modes.setItems(gameModes);
        modes.setVisible(true);
        sendMode.setVisible(true);

    }

    public static void setController(MainController controller){
        InitialPageController.controller =controller;
    }

}