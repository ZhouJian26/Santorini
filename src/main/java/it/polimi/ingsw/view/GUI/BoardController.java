package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;


public class BoardController {

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox vBox0, vBox1, vBox2, chooseWorkervBox;

    @FXML
    private Pane pane00, pane01, pane02, pane03, pane04, pane10, pane11, pane12, pane13, pane14, pane21, pane22, pane23, pane24, pane20, pane30, pane31, pane32, pane33, pane34, pane40, pane41, pane42, pane43, pane44;

    @FXML
    private ImageView light00, light01, light02, light03, light04, light10, light11, light12, light13, light14, light21, light22, light23, light24, light20, light30, light31, light32, light33, light34, light40, light41, light42, light43, light44;


    @FXML
    private ImageView image01, image02, image03, image04, image10, image11, image12, image13, image14, image21, image22, image23, image24, image20, image30, image31, image32, image33, image34, image40, image41, image42, image43, image44;


    @FXML
    private Label player0, player1, player2, godName0, godName1, godName2, worker0, worker1, worker2, turn0, turn1, turn2, status0, status1, status2, lab0;

    @FXML
    private ImageView god0, god1, god2, color0, color1, color2;

    private static MainController controller = new MainController();

    @FXML
    public void choose() {
        if (color0.isPressed()) {
            controller.set("BLUE");
        } else if (color1.isPressed()) {
            controller.set("BROWN");
        } else if (color2.isPressed()) {
            controller.set("WITHE");
        }

    }

    private void reSet() {
        vBox0.setVisible(false);
        vBox1.setVisible(false);
        vBox2.setVisible(false);

        chooseWorkervBox.setVisible(false);
        chooseWorkervBox.setDisable(true);
        lab0.setVisible(false);
        color0.setDisable(true);
        color0.setVisible(false);

        ArrayList<Player> listPlayer = controller.getUserInfo();

        listPlayer.stream().forEach(e -> {
            System.out.println(e.username + "///////" + controller.getPlayer());
            if (e.username.equals(controller.getPlayer())) {
                System.out.println("1");
                vBox0.setVisible(true);
                player0.setText("Player: " + e.username);
                godName0.setText("God:" + e.god);
                worker0.setText("Color:" + e.color);
                status0.setText("Status: " + e.status);
                if (controller.getCurrentPlayer().equals(e.username)) {
                    turn0.setText("your turn");
                } else {
                    turn0.setText(controller.getCurrentPlayer() + "'s turn");
                }
            } else if (!vBox1.isVisible()) {
                System.out.println("2");
                vBox1.setVisible(true);
                player1.setText("Player: " + e.username);
                godName1.setText("God:" + e.god);
                worker1.setText("Color:" + e.color);
                status1.setText("Status: " + e.status);
                turn1.setText(controller.getCurrentPlayer() + "'s turn");
            } else {
                System.out.println("3");
                vBox2.setVisible(true);
                player2.setText("Player: " + e.username);
                godName2.setText("God:" + e.god);
                worker2.setText("Color:" + e.color);
                status2.setText("Status: " + e.status);
                turn2.setText(controller.getCurrentPlayer() + "'s turn");
            }
        });

        if (controller.getPlayer().equals(controller.getCurrentPlayer())) {
            ArrayList<String> listCommand = controller.getCommand();
            listCommand.stream().forEach(e -> {
                System.out.println(e);
                if (e.equals("BLUE")) {
                    chooseWorkervBox.setVisible(true);
                    chooseWorkervBox.setDisable(false);
                    lab0.setVisible(true);
                    color0.setDisable(false);
                    color0.setVisible(true);
                } else if (e.equals("BROWN")) {
                    chooseWorkervBox.setVisible(true);
                    chooseWorkervBox.setDisable(false);
                    lab0.setVisible(true);
                    color1.setDisable(false);
                    color1.setVisible(true);
                } else if (e.equals("WHITE")) {
                    chooseWorkervBox.setVisible(true);
                    chooseWorkervBox.setDisable(false);
                    lab0.setVisible(true);
                    color2.setDisable(false);
                    color2.setVisible(true);
                }

            });
        }
    }

    public static void setController(MainController controller) {
        BoardController.controller = controller;
    }

}
