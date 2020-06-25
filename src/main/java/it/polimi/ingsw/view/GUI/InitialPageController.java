package it.polimi.ingsw.view.GUI;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController implements Controller {

    ObservableList<String> gameModes = FXCollections.observableArrayList("2 players", "3 players");
    private MessageBox alert = new MessageBox();
    private static MainController controller = new MainController();
    private Boolean state = false;
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);
    private static String IP = null;
    private static int PORT = 0;
    @FXML
    private ResourceBundle resources;

    @FXML
    public ImageView backGround, cloud;

    @FXML
    private URL location;

    @FXML
    private Label message;

    @FXML
    private TextField ip, port, username;

    @FXML
    private Button connect, sendMode, sendUsername, quit;

    @FXML
    private ChoiceBox<String> modes;

    @FXML
    void setConnection() {
        state = false;
        IP = ip.getText();
        PORT = Integer.parseInt(port.getText());
        state = controller.setConnection(IP, PORT);
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
        if (username.getText().trim().equals("")) {
            username.clear();
            return;
        }
        state = controller.sendUsername(username.getText().trim());
        if (state) {
            username.setVisible(false);
            sendUsername.setVisible(false);
            message.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        port.setText("9090");
        changePage(true);
        ip.setVisible(true);
        port.setVisible(true);
        connect.setVisible(true);
        modes.setVisible(false);
        sendMode.setVisible(false);
        username.setVisible(false);
        sendUsername.setVisible(false);
        message.setVisible(false);
        if (IP != null && PORT != 0) {
            state = controller.setConnection(IP, PORT);
            if (state)
                changeScene();
        }
        cloud.fitWidthProperty().bind(width.add(10));
        cloud.fitHeightProperty().bind(height.add(5));
        cloud.setVisible(false);
        cloud.setDisable(true);
        cloud.setImage(new Image(ImageEnum.getUrl("CLOUD")));
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
        controller.quit(true);
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
        this.height.set(width * 720 / 1280);
    }

    @Override
    public void setHeight(double height) {
        this.height.set(height * 1.01);
        this.width.set(height * 1280 / 720);
    }

    @Override
    public void changePage(Boolean state) {
        cloud.setVisible(true);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        if (!state) {
            fade.setFromValue(0);
            fade.setToValue(10);
            fade.setOnFinished(e -> {
                controller.changeScene();
            });
        } else {
            fade.setToValue(0);
            fade.setFromValue(10);
        }
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(cloud);
        fade.play();

    }

    @Override
    public void reSet() {

    }
}