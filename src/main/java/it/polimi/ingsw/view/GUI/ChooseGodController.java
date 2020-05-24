package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.model.Player;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChooseGodController {

    private static MainController controller = new MainController();
    @FXML
    private Button button0, button1, button2, button3, button4, button5, button6, button7;
    @FXML
    private ImageView god0, god1, god2, god3, god4, god5, god6, god7, god8, color0, color1, color2;
    @FXML
    private HBox hBox0, hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8;
    @FXML
    private VBox camp0, camp1, camp2;
    @FXML
    private Label player0, player1, player2, godName0, godName1, godName2, worker0, worker1, worker2, turn0, turn1, turn2, status0, status1, status2, lab0;

    @FXML
    public void choose() {
        if (god0.isPressed()) {
            controller.set("APOLLO");
        } else if (god1.isPressed()) {
            controller.set("ARTEMIS");
        } else if (god2.isPressed()) {
            controller.set("ATHENA");
        } else if (god3.isPressed()) {
            controller.set("ATLAS");
        } else if (god4.isPressed()) {
            controller.set("DEMETER");
        } else if (god5.isPressed()) {
            controller.set("HEPHAESTUS");
        } else if (god6.isPressed()) {
            controller.set("MINOTAUR");
        } else if (god7.isPressed()) {
            controller.set("PAN");
        } else if (god8.isPressed()) {
            controller.set("PROMETHEUS");
        } else if (color0.isPressed()) {
            controller.set("BLUE");
        } else if (color1.isPressed()) {
            controller.set("BROWN");
        } else if (color2.isPressed()) {
            controller.set("WITHE");
        }
        reSet();
    }

    public void reSet() {
        god0.setDisable(true);
        god1.setDisable(true);
        god2.setDisable(true);
        god3.setDisable(true);
        god4.setDisable(true);
        god5.setDisable(true);
        god6.setDisable(true);
        god7.setDisable(true);
        god8.setDisable(true);

        hBox0.setVisible(false);
        hBox1.setVisible(false);
        hBox2.setVisible(false);
        hBox3.setVisible(false);
        hBox4.setVisible(false);
        hBox5.setVisible(false);
        hBox6.setVisible(false);
        hBox7.setVisible(false);
        hBox8.setVisible(false);

        camp0.setVisible(false);
        camp1.setVisible(false);
        camp2.setVisible(false);

        lab0.setVisible(false);

        color0.setDisable(true);
        color0.setVisible(false);
        color1.setDisable(true);
        color1.setVisible(false);
        color2.setDisable(true);
        color2.setVisible(false);


        ArrayList<String> listCommand = controller.getCommand();
        listCommand.stream().forEach(e -> {
            System.out.println(e);
            if (e.equals("APOLLO")) {
                god0.setDisable(false);
                hBox0.setVisible(true);
            } else if (e.equals("ARTEMIS")) {
                god1.setDisable(false);
                hBox1.setVisible(true);
            } else if (e.equals("ATHENA")) {
                god2.setDisable(false);
                hBox2.setVisible(true);
            } else if (e.equals("ATLAS")) {
                god3.setDisable(false);
                hBox3.setVisible(true);
            } else if (e.equals("DEMETER")) {
                god4.setDisable(false);
                hBox4.setVisible(true);
            } else if (e.equals("HEPHAESTUS")) {
                god5.setDisable(false);
                hBox5.setVisible(true);
            } else if (e.equals("MINOTAUR")) {
                god6.setDisable(false);
                hBox6.setVisible(true);
            } else if (e.equals("PAN")) {
                god7.setDisable(false);
                hBox7.setVisible(true);
            } else if (e.equals("PROMETHEUS")) {
                god8.setDisable(false);
                hBox8.setVisible(true);
            } else if (e.equals("BLUE")) {
                lab0.setVisible(true);
                color0.setDisable(false);
                color0.setVisible(true);
            } else if (e.equals("BROWN")) {
                lab0.setVisible(true);
                color1.setDisable(false);
                color1.setVisible(true);
            } else if (e.equals("WHITE")) {
                lab0.setVisible(true);
                color2.setDisable(false);
                color2.setVisible(true);
            }

        });

        ArrayList<Player> listPlayer = controller.getUserInfo();

        listPlayer.stream().forEach(e -> {
            System.out.println(e.username + "///////" + controller.getPlayer());
            if (e.username.equals(controller.getPlayer())) {
                System.out.println("1");
                camp0.setVisible(true);
                player0.setText("Player: " + e.username);
                godName0.setText("God:" + e.god);
                worker0.setText("Color:" + e.color);
                status0.setText("Status: " + e.status);
                if (controller.getCurrentPlayer().equals(e.username)) {
                    turn0.setText("your turn");
                } else {
                    turn0.setText(controller.getCurrentPlayer() + "'s turn");
                }
            } else if (!camp1.isVisible()) {
                System.out.println("2");
                camp1.setVisible(true);
                player1.setText("Player: " + e.username);
                godName1.setText("God:" + e.god);
                worker1.setText("Color:" + e.color);
                status1.setText("Status: " + e.status);
                turn1.setText(controller.getCurrentPlayer() + "'s turn");
            } else {
                System.out.println("3");
                camp2.setVisible(true);
                player2.setText("Player: " + e.username);
                godName2.setText("God:" + e.god);
                worker2.setText("Color:" + e.color);
                status2.setText("Status: " + e.status);
                turn2.setText(controller.getCurrentPlayer() + "'s turn");
            }
        });
    }

    public static void setController(MainController controller) {
        ChooseGodController.controller = controller;
    }

    @FXML
    private void initialize() {
        reSet();
    }

}
