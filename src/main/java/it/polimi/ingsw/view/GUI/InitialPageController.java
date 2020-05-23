package it.polimi.ingsw.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController {

    ObservableList<String> gameModes = FXCollections.observableArrayList("2 players", "3 players");
    private MessageBox alert = new MessageBox();
    private MainController controller = new MainController();
    private boolean state = false;
    private String modal = "FOUR";


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ip, port, username;

    @FXML
    private Button connect, playButton;

    @FXML
    private ChoiceBox modes;

    @FXML
    void setConnection(ActionEvent event) {
        if(!ip.getText().equals("")&&!port.getText().equals("")){
               controller.setConnection(ip.getText(), Integer.parseInt(port.getText()));
               changeScene();
        }

    }

    @FXML
    void sendPlayerInfo(ActionEvent event) {

        if(modes.getValue() == "2 players") controller.setMode("TWO");
        else controller.setMode("THREE");

        modes.setVisible(false);

        while(true){
        if(!username.getText().equals("")) {
            state = controller.sendUsername(username.getText());
            if(state) break;
            else alert.alert("Username unavailable");
            break;
        }else alert.alert("Please insert an username");
        }
    }


    public void set(MainController controller){
        this.controller = controller;
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
        modes.setValue("2 players");
        modes.setItems(gameModes);
        modes.setVisible(true);
        username.setVisible(true);
        playButton.setVisible(true);

    }






}
