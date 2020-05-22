package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.view.socket.Connection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private Connection connection;
    ObservableList<String> gameModes = FXCollections.observableArrayList("2 players", "3 players");
    private GameMode newMode = GameMode.TWO;
    private MessageBox alert;

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
    /**
     * Creating connection to server
     */
    void setConnection(ActionEvent event) {
        if(!ip.getText().equals("")&&!port.getText().equals("")){
            try{
                connection = new Connection(ip.getText(), Integer.parseInt(port.getText()));
                //System.out.println("Connected");
                changeScene();

            }catch (Exception e){

            }
        }

    }

    @FXML
    void sendPlayerInfo(ActionEvent event) {
        usernameCheck();
        modes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int val = newValue.intValue();
                if(val == 0) newMode = GameMode.TWO;
                else if (val == 1) newMode = GameMode.THREE;
            }

        });
        //@TODO send game mode chosen
        //@TODO send username
    }


    private void usernameCheck(){
        while(true) {
            if (username.getText().equals("")) alert.alert("Please insert your username!");
            //@TODO check availability of the username
            //else send the username and check if available
            //alert.alert("Sorry, username unavailable");
            break;
        }

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
