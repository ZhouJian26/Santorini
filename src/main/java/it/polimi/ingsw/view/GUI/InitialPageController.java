package it.polimi.ingsw.view.GUI;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController implements Controller {

    ObservableList<String> gameModes = FXCollections.observableArrayList("2 players", "3 players");
    private static MainController controller = new MainController();
    private Boolean state = false;
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);
    private static String IP = null;
    private static int PORT = 0;

    @FXML
    Pane pane;
    @FXML
    private ResourceBundle resources;
    @FXML
    public ImageView backGround, cloud;
    @FXML
    private URL location;
    @FXML
    private Label message, alert, usernameAlert;
    @FXML
    private TextField ip, port, username;
    @FXML
    private Button connect, sendMode, sendUsername, quit;
    @FXML
    private ChoiceBox<String> modes;

    public static void setController(MainController controller) {
        InitialPageController.controller = controller;
    }

    /**
     * Setup the connection for player
     */
    @FXML
    void setConnection() {
        alert.setVisible(false);
        state = false;
        IP = ip.getText();
        PORT = Integer.parseInt(port.getText());
        state = controller.setConnection(IP, PORT);
        if (state)
            changeScene();
        else {
            alert.setText("Wrong IP/Port");
            alert.setVisible(true);
        }
    }

    /**
     * Send the chosen mode to server
     */
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

    /**
     * Send username to server
     */
    @FXML
    void sendUsername() {
        if (username.getText().trim().equals("")) {
            usernameAlert.setText("Username not available!");
            usernameAlert.setVisible(true);
            username.clear();
            return;
        }
        state = controller.sendUsername(username.getText().trim());
        if (state) {
            username.setVisible(false);
            sendUsername.setVisible(false);
            message.setVisible(true);
            usernameAlert.setVisible(false);
        } else {
            usernameAlert.setText("Username not available!");
            usernameAlert.setVisible(true);
        }
    }

    private void changeScene() {
        ip.setVisible(false);
        port.setVisible(false);
        alert.setVisible(false);
        connect.setVisible(false);
        modes.setValue("2 players");
        modes.setItems(gameModes);
        modes.setVisible(true);
        sendMode.setVisible(true);
        quit.setOnAction(e -> {
            controller.closeConnection();
            initialize();
        });
    }

    @FXML
    private void initialize() {
        changePage(true);
        port.setText("9090");
        ip.setVisible(true);
        port.setVisible(true);
        alert.setVisible(false);
        connect.setVisible(true);
        modes.setVisible(false);
        sendMode.setVisible(false);
        username.setVisible(false);
        usernameAlert.setVisible(false);
        sendUsername.setVisible(false);
        message.setVisible(false);
        cloud.setVisible(false);
        cloud.setDisable(true);
        cloud.setImage(new Image(ImageEnum.getUrl("CLOUD")));
        if (IP != null && PORT != 0) {
            ip.setText(IP);
            port.setText(String.valueOf(PORT));
        }
        quit.setOnAction(e -> {
            Platform.exit();
        });
        port.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                setConnection();
            }
        });
        ip.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                setConnection();
            }
        });
        username.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                sendUsername();
            }
        });
        modes.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                sendMode();
            }
        });
        setUpDimension();
    }

    private void setUpDimension() {
        pane.prefHeightProperty().bind(height);
        pane.prefWidthProperty().bind(width);
        cloud.fitWidthProperty().bind(width);
        cloud.fitHeightProperty().bind(height);
        backGround.fitWidthProperty().bind(width);
        backGround.fitHeightProperty().bind(height);
        ip.layoutXProperty().bind(width.subtract(150).divide(2));
        ip.layoutYProperty().bind(height.multiply(0.7));
        alert.layoutXProperty().bind(width.subtract(150).divide(2).add(155));
        alert.layoutYProperty().bind(height.multiply(0.7).add(5));
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
        usernameAlert.layoutXProperty().bind(width.subtract(150).divide(2).add(155));
        usernameAlert.layoutYProperty().bind(height.multiply(0.7).add(45));
        sendUsername.layoutXProperty().bind(width.subtract(150).divide(2));
        sendUsername.layoutYProperty().bind(height.multiply(0.7).add(80));
        message.layoutXProperty().bind(width.subtract(150).divide(2));
        message.layoutYProperty().bind(height.multiply(0.7).add(40));
    }

    /**
     * Set width
     *
     * @param width width
     */
    @Override
    public void setDimension(double width, double height) {
        if (width * 720 / 1280 < height) {
            pane.setLayoutY((height - (width * 720 / 1280)) / 2);
            pane.setLayoutX(0);
            this.height.set(width * 720 / 1280);
            this.width.set(width);
        } else {
            pane.setLayoutX((width - (height * 1280 / 720)) / 2);
            pane.setLayoutY(0);
            this.width.set(height * 1280 / 720);
            this.height.set(height);
        }
    }


    /**
     * Change view
     *
     * @param state if it's allowed to change view
     */
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

    /**
     * Reload the board for all players
     */
    @Override
    public void reSet() {

    }
}