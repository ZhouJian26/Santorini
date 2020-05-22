package it.polimi.ingsw.view.GUI;

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
    private MessageBox alert;
    private MainController controller = new MainController();
    private boolean state = false;


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
        modes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int val = newValue.intValue();
                if(val == 0) controller.setMode("TWO");
                else if (val == 1) controller.setMode("THREE");
            }

        });
        modes.setVisible(false);

        while(true){
        if(!username.getText().equals("")) {
            state = controller.sendUsername(username.getText());
            if(state) break;
            else alert.alert("Username unavailable");
            break;
        }else alert.alert("Please insert an username");
        }
        ControllerMatching controllerMatching = ControllerMatching.getInstance();
        controllerMatching.setUsername(username.getText(), controller);
    }


    private void usernameCheck(){
        while(true) {
            if (username.getText().equals("")) {
                alert.alert("Please insert your username!");
            }
            else break;
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
