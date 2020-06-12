package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.socket.Parser;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseGodController implements Controller {


    private static MainController controller = new MainController();
    private String[] players = new String[3];
    private int[] godActive = new int[14];
    private ImageView[] gods = new ImageView[14];
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private  DoubleProperty width = new SimpleDoubleProperty(1280);

    @FXML
    private GridPane gridPane;

    @FXML
    private Button button0, button1, button2;
    @FXML
    private ImageView god0, god1, god2, god3, god4, god5, god6, god7, god8, god9, god10, god11, god12, god13, backG0,
            backG1, backG2, backGround;
    @FXML
    private Pane camp0, camp1, camp2;
    @FXML
    private Label player0, player1, player2, turn0, turn1, turn2;

    private Image apollo = new Image("/GraphicSrc/Gods/Apollo.jpg"),
            artemis = new Image("/GraphicSrc/Gods/Artemis.jpg"), athena = new Image("/GraphicSrc/Gods/Athena.jpg"),
            atlas = new Image("/GraphicSrc/Gods/Atlas.jpg"), demeter = new Image("/GraphicSrc/Gods/Detemer.jpg"),
            hephaestus = new Image("/GraphicSrc/Gods/Hephaestus.jpg"),
            minotaur = new Image("/GraphicSrc/Gods/Minotaur.jpg"), pan = new Image("/GraphicSrc/Gods/Pan.jpg"),
            prometheus = new Image("/GraphicSrc/Gods/Prometheus.jpg"), back = new Image("/GraphicSrc/Gods/Back.jpg"),
            hera = new Image("/GraphicSrc/Gods/Hera.jpg"), medusa = new Image("/GraphicSrc/Gods/Medusa.jpg"),
            persephone = new Image("/GraphicSrc/Gods/Persephone.jpg"),
            poseidon = new Image("/GraphicSrc/Gods/Poseidon.jpg"), zeus = new Image("/GraphicSrc/Gods/Zeus.jpg"),
            backGround0 = new Image("/GraphicSrc/Plyayer/ContainerLOW.gif"),
            backGround1 = new Image("/GraphicSrc/Plyayer/Container.gif");

    @FXML
    public void chooseGod(MouseEvent event) {

        ImageView node = (ImageView) event.getSource();
        String string = (String) node.getUserData();
        controller.send(string);

    }

    @FXML
    public void choosePlayer(ActionEvent event) {
        //System.out.println("qqqqqqq");
        Button node = (Button) event.getSource();
        String string = (String) node.getUserData();
        int i = Integer.parseInt(string);
        controller.send(players[i]);

    }

    @FXML
    public void quit() {
        controller.quit();
    }

    @Override
    public void reSet() {
        resetPlayerInfo();
        resetAction();
    }
    @FXML
    public void resetPlayerInfo() {
        String currPlayer = controller.getCurrentPlayer();
        if (currPlayer.equals(players[0])) {
            backG0.setImage(backGround1);
            backG1.setImage(backGround0);
            backG2.setImage(backGround0);
            turn0.setVisible(true);
            turn1.setVisible(false);
            turn2.setVisible(false);
        } else if (currPlayer.equals(players[1])) {
            backG1.setImage(backGround1);
            backG0.setImage(backGround0);
            backG2.setImage(backGround0);
            turn1.setVisible(true);
            turn0.setVisible(false);
            turn2.setVisible(false);
        } else {
            backG2.setImage(backGround1);
            backG0.setImage(backGround0);
            backG1.setImage(backGround0);
            turn2.setVisible(true);
            turn0.setVisible(false);
            turn1.setVisible(false);
        }
    }
    @FXML
    public void resetAction() {
        for (int i = 0; i < 14; i++) {
            if (godActive[i] == 1) {
                godActive[i] = 2;
            }
        }
        List<Command> listCommand = controller.getCommand();
        listCommand.forEach(e -> {

            if (e.funcData.equals("APOLLO")) {
                if (godActive[0] == 0) {
                    god0.setDisable(false);
                    god0.setImage(apollo);
                    godActive[0] = 1;
                } else if (godActive[0] == 2) {
                    godActive[0] = 1;
                }
            } else if (e.funcData.equals("ARTEMIS")) {
                if (godActive[1] == 0) {
                    god1.setDisable(false);
                    god1.setImage(artemis);
                    godActive[1] = 1;
                } else if (godActive[1] == 2) {
                    godActive[1] = 1;
                }
            } else if (e.funcData.equals("ATHENA")) {
                if (godActive[2] == 0) {
                    god2.setDisable(false);
                    god2.setImage(athena);
                    godActive[2] = 1;
                } else if (godActive[2] == 2) {
                    godActive[2] = 1;
                }
            } else if (e.funcData.equals("ATLAS")) {
                if (godActive[3] == 0) {
                    god3.setDisable(false);
                    god3.setImage(atlas);
                    godActive[3] = 1;
                } else if (godActive[3] == 2) {
                    godActive[3] = 1;
                }
            } else if (e.funcData.equals("DEMETER")) {
                if (godActive[4] == 0) {
                    god4.setDisable(false);
                    god4.setImage(demeter);
                    godActive[4] = 1;
                } else if (godActive[4] == 2) {
                    godActive[4] = 1;
                }
            } else if (e.funcData.equals("HEPHAESTUS")) {
                if (godActive[5] == 0) {
                    god5.setDisable(false);
                    god5.setImage(hephaestus);
                    godActive[5] = 1;
                } else if (godActive[5] == 2) {
                    godActive[5] = 1;
                }
            } else if (e.funcData.equals("MINOTAUR")) {
                if (godActive[6] == 0) {
                    god6.setDisable(false);
                    god6.setImage(minotaur);
                    godActive[6] = 1;
                } else if (godActive[6] == 2) {
                    godActive[6] = 1;
                }
            } else if (e.funcData.equals("PAN")) {
                if (godActive[7] == 0) {
                    god7.setDisable(false);
                    god7.setImage(pan);
                    godActive[7] = 1;
                } else if (godActive[7] == 2) {
                    godActive[7] = 1;
                }
            } else if (e.funcData.equals("PROMETHEUS")) {
                if (godActive[8] == 0) {
                    god8.setDisable(false);
                    god8.setImage(prometheus);
                    godActive[8] = 1;
                } else if (godActive[8] == 2) {
                    godActive[8] = 1;
                }
            } else if (e.funcData.equals("HERA")) {
                if (godActive[9] == 0) {
                    god9.setDisable(false);
                    god9.setImage(hera);
                    godActive[9] = 1;
                } else if (godActive[9] == 2) {
                    godActive[9] = 1;
                }
            } else if (e.funcData.equals("MEDUSA")) {
                if (godActive[10] == 0) {
                    god10.setDisable(false);
                    god10.setImage(medusa);
                    godActive[10] = 1;
                } else if (godActive[10] == 2) {
                    godActive[10] = 1;
                }
            } else if (e.funcData.equals("PERSEPHONE")) {
                if (godActive[11] == 0) {
                    god11.setDisable(false);
                    god11.setImage(persephone);
                    godActive[11] = 1;
                } else if (godActive[11] == 2) {
                    godActive[11] = 1;
                }
            } else if (e.funcData.equals("POSEIDON")) {
                if (godActive[12] == 0) {
                    god12.setDisable(false);
                    god12.setImage(poseidon);
                    godActive[12] = 1;
                } else if (godActive[12] == 2) {
                    godActive[12] = 1;
                }
            } else if (e.funcData.equals("ZEUS")) {
                if (godActive[13] == 0) {
                    god13.setDisable(false);
                    god13.setImage(zeus);
                    godActive[13] = 1;
                } else if (godActive[13] == 2) {
                    godActive[13] = 1;
                }
            } else {
                if (controller.getPlayer().equals(controller.getCurrentPlayer())) {
                    if (e.funcData.equals(players[0])) {
                        backG0.setImage(backGround1);
                        button0.setDisable(false);
                        button0.setVisible(true);
                    } else if (e.funcData.equals(players[1])) {
                        backG1.setImage(backGround1);
                        button1.setDisable(false);
                        button1.setVisible(true);
                    } else if (e.funcData.equals(players[2])) {
                        backG2.setImage(backGround1);
                        button2.setDisable(false);
                        button2.setVisible(true);
                    }
                }
            }
        });
        for (int i = 0; i < 14; i++) {
            if (godActive[i] == 2) {
                godActive[i] = 0;
                gods[i].setImage(back);
                gods[i].setDisable(true);
            }
        }
    }

    public static void setController(MainController controller) {
        ChooseGodController.controller = controller;
    }


    public void setWidth(double width) {
        this.width.set(width * 1.01);
    }

    public void setHeight(double height) {
        this.height.set(height * 1.01);
    }


    @FXML
    private void initialize() {
        gods[0] = god0;
        gods[1] = god1;
        gods[2] = god2;
        gods[3] = god3;
        gods[4] = god4;
        gods[5] = god5;
        gods[6] = god6;
        gods[7] = god7;
        gods[8] = god8;
        gods[9] = god9;
        gods[10] = god10;
        gods[11] = god11;
        gods[12] = god12;
        gods[13] = god13;

        button0.setVisible(false);
        button0.setDisable(true);
        button1.setVisible(false);
        button1.setDisable(true);
        button2.setVisible(false);
        button2.setDisable(true);

        Arrays.stream(gods).forEach(e -> {
            e.setImage(back);
            e.setDisable(true);
        });
        camp0.setVisible(false);
        camp1.setVisible(false);
        camp2.setVisible(false);

        Arrays.stream(godActive).forEach(e -> e = 0);
        List<Player> listPlayer = controller.getUserInfo();

        listPlayer.stream().forEach(e -> {
            // System.out.println(e.username + "///////" + controller.getPlayer());
            if (e.username.equals(controller.getPlayer())) {
                // System.out.println("1");
                players[0] = e.username;
                camp0.setVisible(true);
                player0.setText("Player: " + e.username);
                turn0.setText("Your Turn");
            } else if (!camp1.isVisible()) {
                // System.out.println("2");
                players[1] = e.username;
                camp1.setVisible(true);
                player1.setText("Player: " + e.username);
                turn1.setText("Current Player");
            } else {
                // System.out.println("3");
                players[2] = e.username;
                camp2.setVisible(true);
                player2.setText("Player: " + e.username);
                turn2.setText("Current Player");

            }
        });
        reSet();

        backGround.fitWidthProperty().bind(width);
        backGround.fitHeightProperty().bind(height);
        gridPane.prefWidthProperty().bind(width.subtract(210));
        gridPane.prefHeightProperty().bind(height.subtract(160));
        Arrays.stream(gods).forEach(e -> {
            e.fitHeightProperty().bind(height.subtract(160).multiply(0.42));
        });

    }

}
