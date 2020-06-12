package it.polimi.ingsw.view.GUI;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.beans.property.*;


import java.net.URL;
import java.util.ResourceBundle;


public class InitialPageController implements Controller {


    ObservableList<String> gameModes = FXCollections.observableArrayList("2 players", "3 players");
    private MessageBox alert = new MessageBox();
    private static MainController controller = new MainController();
    private Boolean state = false;
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);
    private static String IP=null;
    private static int PORT=0;
    @FXML
    private ResourceBundle resources;

    @FXML
    public ImageView backGround;

    @FXML
    private URL location;

    @FXML
    private Label message;

    @FXML
    private TextField ip, port, username;

    @FXML
    private Button connect, sendMode, sendUsername, quit;

    @FXML
    private ChoiceBox modes;

    @FXML
    void setConnection() {
        state = false;
        IP=ip.getText();
        PORT=Integer.parseInt(port.getText());
        state = controller.setConnection(IP,PORT);
        if (state)
            changeScene();
        else
            alert.alert("Wrong IP/Port");

        // }

    }

    @FXML
    void sendMode() {

        if (modes.getValue() == "2 players")
            controller.setMode("TWO");
        else
            controller.setMode("THREE");

        modes.setVisible(false);
        sendMode.setVisible(false);
        username.setVisible(true);
        sendUsername.setVisible(true);

    }

    @FXML
    void sendUsername() {
        state = controller.sendUsername(username.getText());
        if (state) {
            username.setVisible(false);
            sendUsername.setVisible(false);
            message.setVisible(true);
        }

    }

    @FXML
    private void initialize() {
        ip.setVisible(true);
        port.setVisible(true);
        connect.setVisible(true);
        modes.setVisible(false);
        sendMode.setVisible(false);
        username.setVisible(false);
        sendUsername.setVisible(false);
        message.setVisible(false);
        if(IP!=null&&PORT!=0){
            state = controller.setConnection(IP,PORT);
            if (state)
                changeScene();
        }
        backGround.fitWidthProperty().bind(width);
        backGround.fitHeightProperty().bind(height);
        ip.layoutXProperty().bind(width.subtract(150).divide(2));
        ip.layoutYProperty().bind(height.multiply(0.7));
        port.layoutXProperty().bind(width.subtract(150).divide(2));
        port.layoutYProperty().bind(height.multiply(0.7).add(40));
        connect.layoutXProperty().bind(width.subtract(150).divide(2));
        connect.layoutYProperty().bind(height.multiply(0.7).add(80));
        quit.layoutXProperty().bind(width.subtract(150).divide(2).add(105));
        quit.layoutYProperty().bind(height.multiply(0.7).add(80));
        modes.layoutXProperty().bind(width.subtract(150).divide(2));
        modes.layoutYProperty().bind(height.multiply(0.7).add(40));
        sendMode.layoutXProperty().bind(width.subtract(150).divide(2));
        sendMode.layoutYProperty().bind(height.multiply(0.7).add(80));
        username.layoutXProperty().bind(width.subtract(150).divide(2));
        username.layoutYProperty().bind(height.multiply(0.7).add(40));
        sendUsername.layoutXProperty().bind(width.subtract(150).divide(2));
        sendUsername.layoutYProperty().bind(height.multiply(0.7).add(80));
        message.layoutXProperty().bind(width.subtract(150).divide(2));
        message.layoutYProperty().bind(height.multiply(0.7).add(40));
    }

    @FXML
    public void quit() {
        controller.quit();
        Platform.exit();
    }

    private void changeScene() {
        ip.setVisible(false);
        port.setVisible(false);
        connect.setVisible(false);
        modes.setValue("2 players");
        modes.setItems(gameModes);
        modes.setVisible(true);
        sendMode.setVisible(true);
    }


    public static void setController(MainController controller) {
        InitialPageController.controller = controller;
    }

    @Override
    public void setWidth(double width) {
        this.width.set(width * 1.01);
    }

    @Override
    public void setHeight(double height) {
        this.height.set(height * 1.01);
    }


    @Override
    public void reSet() {

    }
}