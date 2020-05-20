package it.polimi.ingsw.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ip, port;

    @FXML
    private Button modeTwo, modeThree;

    @FXML
    void setModeThree(ActionEvent event) {
        System.out.println("OK3");

    }

    @FXML
    void setModeTwo(ActionEvent event) {
        System.out.println("OK2");

    }

    @FXML
    void initialize() {
        assert ip != null : "fx:id=\"ip\" was not injected: check your FXML file 'InitialPage.fxml'.";
        assert port != null : "fx:id=\"port\" was not injected: check your FXML file 'InitialPage.fxml'.";
        assert modeTwo != null : "fx:id=\"modeTwo\" was not injected: check your FXML file 'InitialPage.fxml'.";
        assert modeThree != null : "fx:id=\"modeThree\" was not injected: check your FXML file 'InitialPage.fxml'.";

    }
}
