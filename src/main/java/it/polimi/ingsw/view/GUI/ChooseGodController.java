package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChooseGodController {

    private static MainController controller = new MainController();
    @FXML
    private Button button0, button1, button2, button3, button4, button5, button6, button7;
    @FXML
    private ImageView god0, god1, god2, god3, god4, god5, god6, god7, god8;
    @FXML
    private Pane camp0, camp1, camp2;
    @FXML
    private Label player0, player1, player2, turn0, turn1, turn2;

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


        camp0.setVisible(false);
        camp1.setVisible(false);
        camp2.setVisible(false);



        ArrayList<Command> listCommand = controller.getCommand();
        listCommand.stream().forEach(e -> {
            System.out.println(e.funcData);
            if (e.funcData.equals("APOLLO")) {
                god0.setDisable(false);
            } else if (e.funcData.equals("ARTEMIS")) {
                god1.setDisable(false);
            } else if (e.funcData.equals("ATHENA")) {
                god2.setDisable(false);
            } else if (e.funcData.equals("ATLAS")) {
                god3.setDisable(false);
            } else if (e.funcData.equals("DEMETER")) {
                god4.setDisable(false);
            } else if (e.funcData.equals("HEPHAESTUS")) {
                god5.setDisable(false);
            } else if (e.funcData.equals("MINOTAUR")) {
                god6.setDisable(false);
            } else if (e.funcData.equals("PAN")) {
                god7.setDisable(false);
            } else if (e.funcData.equals("PROMETHEUS")) {
                god8.setDisable(false);
            }

        });

        ArrayList<Player> listPlayer = controller.getUserInfo();

        listPlayer.stream().forEach(e -> {
            System.out.println(e.username + "///////" + controller.getPlayer());
            if (e.username.equals(controller.getPlayer())) {
                System.out.println("1");
                camp0.setVisible(true);
                player0.setText("Player: " + e.username);
                if (controller.getCurrentPlayer().equals(e.username)) {
                    turn0.setText("your turn");
                } else {
                    turn0.setText(controller.getCurrentPlayer() + "'s turn");
                }
            } else if (!camp1.isVisible()) {
                System.out.println("2");
                camp1.setVisible(true);
                player1.setText("Player: " + e.username);
                turn1.setText(controller.getCurrentPlayer() + "'s turn");
            } else {
                System.out.println("3");
                camp2.setVisible(true);
                player2.setText("Player: " + e.username);
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
