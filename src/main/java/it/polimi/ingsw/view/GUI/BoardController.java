package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Action;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;


public class BoardController {

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox chooseWorkervBox;

    @FXML
    private HBox hBox0, hBox1, hBox2;

    @FXML
    private Pane pane00, pane01, pane02, pane03, pane04, pane10, pane11, pane12, pane13, pane14, pane20, pane21, pane22, pane23, pane24, pane30, pane31, pane32, pane33, pane34, pane40, pane41, pane42, pane43, pane44;

    @FXML
    private ImageView light00, light01, light02, light03, light04, light10, light11, light12, light13, light14, light20, light21, light22, light23, light24, light30, light31, light32, light33, light34, light40, light41, light42, light43, light44;


    @FXML
    private ImageView image00, image01, image02, image03, image04, image10, image11, image12, image13, image14, image20, image21, image22, image23, image24, image30, image31, image32, image33, image34, image40, image41, image42, image43, image44;


    @FXML
    private Label player0, player1, player2, godName0, godName1, godName2, worker0, worker1, worker2, turn0, turn1, turn2, status0, status1, status2, lab0;

    @FXML
    private ImageView god0, god1, god2, color0, color1, color2;

    private static MainController controller = new MainController();

    private static ImageView[][] images = new ImageView[5][5];
    private static Pane[][] panes = new Pane[5][5];
    private static ImageView[][] lights = new ImageView[5][5];

    private Image blue = new Image("GraphicSrc/Buildings/blue.gif");
    private Image blueLevel1 = new Image("GraphicSrc/Buildings/blueLevel1.gif");
    private Image blueLevel2 = new Image("GraphicSrc/Buildings/blueLevel2.gif");
    private Image blueLevel3 = new Image("GraphicSrc/Buildings/blueLevel3.gif");
    private Image brown = new Image("GraphicSrc/Buildings/brown.gif");
    private Image brownLevel1 = new Image("GraphicSrc/Buildings/brownLevel1.gif");
    private Image brownLevel2 = new Image("GraphicSrc/Buildings/brownLevel2.gif");
    private Image brownLevel3 = new Image("GraphicSrc/Buildings/brownLevel3.gif");
    private Image dome = new Image("GraphicSrc/Buildings/dome.gif");
    private Image domeLevel1 = new Image("GraphicSrc/Buildings/domeLevel1.gif");
    private Image domeLevel2 = new Image("GraphicSrc/Buildings/domeLevel2.gif");
    private Image domeLevel3 = new Image("GraphicSrc/Buildings/domeLevel3.gif");
    private Image level1 = new Image("GraphicSrc/Buildings/level1.gif");
    private Image level2 = new Image("GraphicSrc/Buildings/level2.gif");
    private Image level3 = new Image("GraphicSrc/Buildings/level3.gif");
    private Image white = new Image("GraphicSrc/Buildings/white.gif");
    private Image whiteLevel1 = new Image("GraphicSrc/Buildings/whiteLevel1.gif");
    private Image whiteLevel2 = new Image("GraphicSrc/Buildings/whiteLevel2.gif");
    private Image whiteLevel3 = new Image("GraphicSrc/Buildings/whiteLevel3.gif");

    private int[][][] count = new int[5][5][2];


    @FXML
    public void chooseColor() {
        if (color0.isPressed()) {
            controller.set("BLUE");
        } else if (color1.isPressed()) {
            controller.set("BROWN");
        } else if (color2.isPressed()) {
            controller.set("WITHE");
        }
    }

    @FXML
    public void chooseAction() {
        System.out.println("clicked");
        for (int i = 0; i < 25; i++) {
            if (panes[i / 5][i % 5].isPressed()) {
                if (count[i / 5][i % 5][0] == 1) {
                    controller.set(String.valueOf(count[i / 5][i % 5][1]));
                }
            }
        }
    }


    private void reSet() {
        hBox0.setVisible(false);
        hBox1.setVisible(false);
        hBox2.setVisible(false);

        chooseWorkervBox.setVisible(false);
        chooseWorkervBox.setDisable(true);

        lab0.setVisible(false);

        color0.setDisable(true);
        color0.setVisible(false);
        color1.setDisable(true);
        color1.setVisible(false);
        color2.setDisable(true);
        color2.setVisible(false);

        god0.setVisible(false);
        god0.setDisable(true);
        god1.setVisible(false);
        god1.setDisable(true);
        god2.setVisible(false);
        god2.setDisable(true);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                panes[i][j].setDisable(true);
                panes[i][j].setVisible(false);
                images[i][j].setDisable(true);
                images[i][j].setVisible(false);
                lights[i][j].setDisable(true);
                lights[i][j].setVisible(false);
                count[i][j][0] = 0;
                count[i][j][1] = 0;
            }
        }

        load();
    }

    private void load() {

        setPlayerInfo();
        setBoard();

        if (controller.getPlayer().equals(controller.getCurrentPlayer())) {
            setAction();
        }
    }

    private void setPlayerInfo() {
        ArrayList<Player> listPlayer = controller.getUserInfo();

        listPlayer.stream().forEach(e -> {
            System.out.println(e.username + "///////" + controller.getPlayer());
            if (e.username.equals(controller.getPlayer())) {
                System.out.println("1");
                hBox0.setVisible(true);
                player0.setText("Player: " + e.username);
                godName0.setText("God:" + e.god);
                worker0.setText("Color:" + e.color);
                status0.setText("Status: " + e.status);
                if (controller.getCurrentPlayer().equals(e.username)) {
                    turn0.setText("your turn");
                } else {
                    turn0.setText(controller.getCurrentPlayer() + "'s turn");
                }
            } else if (!hBox1.isVisible()) {
                System.out.println("2");
                hBox1.setVisible(true);
                player1.setText("Player: " + e.username);
                godName1.setText("God:" + e.god);
                worker1.setText("Color:" + e.color);
                status1.setText("Status: " + e.status);
                turn1.setText(controller.getCurrentPlayer() + "'s turn");
            } else {
                System.out.println("3");
                hBox2.setVisible(true);
                player2.setText("Player: " + e.username);
                godName2.setText("God:" + e.god);
                worker2.setText("Color:" + e.color);
                status2.setText("Status: " + e.status);
                turn2.setText(controller.getCurrentPlayer() + "'s turn");
            }
        });
    }

    private void setBoard() {
        Cell[][] board = controller.getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                panes[i][j].setVisible(true);
                images[i][j].setVisible(true);
                try {
                    switch (board[i][j].getBlocks().size()) {
                        case 0:
                            images[i][j].setVisible(false);
                        case 1:
                            if (!board[i][j].getBlocks().get(0).block.equals("WORKER")) {
                                if (!board[i][j].getBlocks().get(0).block.equals("DOME")) {
                                    images[i][j].setImage(level1);
                                } else {
                                    images[i][j].setImage(dome);
                                }
                            } else {
                                switch (board[i][j].getBlocks().get(0).color) {
                                    case "BLUE":
                                        images[i][j].setImage(blue);
                                        break;
                                    case "BROWN":
                                        images[i][j].setImage(brown);
                                        break;
                                    case "White":
                                        images[i][j].setImage(white);
                                        break;
                                }
                            }
                            break;
                        case 2:
                            if (!board[i][j].getBlocks().get(1).block.equals("WORKER")) {
                                if (!board[i][j].getBlocks().get(1).block.equals("DOME")) {
                                    images[i][j].setImage(level2);
                                } else {
                                    images[i][j].setImage(domeLevel1);
                                }
                            } else {
                                switch (board[i][j].getBlocks().get(1).color) {
                                    case "BLUE":
                                        images[i][j].setImage(blueLevel1);
                                        break;
                                    case "BROWN":
                                        images[i][j].setImage(brownLevel1);
                                        break;
                                    case "White":
                                        images[i][j].setImage(whiteLevel1);
                                        break;
                                }
                            }
                            break;
                        case 3:
                            if (!board[i][j].getBlocks().get(2).block.equals("WORKER")) {
                                if (!board[i][j].getBlocks().get(2).block.equals("DOME")) {
                                    images[i][j].setImage(level3);
                                } else {
                                    images[i][j].setImage(domeLevel2);
                                }
                            } else {
                                switch (board[i][j].getBlocks().get(2).color) {
                                    case "BLUE":
                                        images[i][j].setImage(blueLevel2);
                                        break;
                                    case "BROWN":
                                        images[i][j].setImage(brownLevel2);
                                        break;
                                    case "White":
                                        images[i][j].setImage(whiteLevel2);
                                        break;
                                }
                            }
                            break;
                        case 4:
                            if (!board[i][j].getBlocks().get(3).block.equals("WORKER")) {
                                images[i][j].setImage(domeLevel3);
                            } else {
                                switch (board[i][j].getBlocks().get(3).color) {
                                    case "BLUE":
                                        images[i][j].setImage(blueLevel3);
                                        break;
                                    case "BROWN":
                                        images[i][j].setImage(brownLevel3);
                                        break;
                                    case "White":
                                        images[i][j].setImage(whiteLevel3);
                                        break;
                                }
                            }
                            break;
                        default:
                            images[i][j].setVisible(false);
                            break;
                    }
                } catch (Exception e) {
                }

            }
        }

    }

    private void setAction() {
        ArrayList<Command> listCommand = controller.getCommand();
        listCommand.forEach(e -> {
            System.out.println(e.funcData + e.funcName);
            if (e.funcName.equals("setColor")) {
                System.out.println("bbbbbbbb");
                if (e.funcData.equals("BLUE")) {
                    chooseWorkervBox.setVisible(true);
                    chooseWorkervBox.setDisable(false);
                    lab0.setVisible(true);
                    color0.setDisable(false);
                    color0.setVisible(true);
                } else if (e.funcData.equals("BROWN")) {
                    chooseWorkervBox.setVisible(true);
                    chooseWorkervBox.setDisable(false);
                    lab0.setVisible(true);
                    color1.setDisable(false);
                    color1.setVisible(true);
                } else if (e.funcData.equals("WHITE")) {
                    chooseWorkervBox.setVisible(true);
                    chooseWorkervBox.setDisable(false);
                    lab0.setVisible(true);
                    color2.setDisable(false);
                    color2.setVisible(true);
                }
            }
            else if (e.funcName.equals("setWorkers")) {
                System.out.println("bbbbbcccbb" );
                int i = Integer.parseInt(e.funcData);
                panes[i / 5][i % 5].setDisable(false);
                panes[i / 5][i % 5].setVisible(true);
                images[i / 5][i % 5].setVisible(true);
                images[i / 5][i % 5].setDisable(false);
                count[i / 5][i % 5][0] = 1;
                count[i / 5][i % 5][1] = i;

            }
        });
    }

    public static void setController(MainController controller) {
        BoardController.controller = controller;
    }

    @FXML
    private void initialize() {
        images[0][0] = image00;
        images[0][1] = image01;
        images[0][2] = image02;
        images[0][3] = image03;
        images[0][4] = image04;
        images[1][0] = image10;
        images[1][1] = image11;
        images[1][2] = image12;
        images[1][3] = image13;
        images[1][4] = image14;
        images[2][0] = image20;
        images[2][1] = image21;
        images[2][2] = image22;
        images[2][3] = image23;
        images[2][4] = image24;
        images[3][0] = image30;
        images[3][1] = image31;
        images[3][2] = image32;
        images[3][3] = image33;
        images[3][4] = image34;
        images[4][0] = image40;
        images[4][1] = image41;
        images[4][2] = image42;
        images[4][3] = image43;
        images[4][4] = image44;

        panes[0][0] = pane00;
        panes[0][1] = pane01;
        panes[0][2] = pane02;
        panes[0][3] = pane03;
        panes[0][4] = pane04;
        panes[1][0] = pane10;
        panes[1][1] = pane11;
        panes[1][2] = pane12;
        panes[1][3] = pane13;
        panes[1][4] = pane14;
        panes[2][0] = pane20;
        panes[2][1] = pane21;
        panes[2][2] = pane22;
        panes[2][3] = pane23;
        panes[2][4] = pane24;
        panes[3][0] = pane30;
        panes[3][1] = pane31;
        panes[3][2] = pane32;
        panes[3][3] = pane33;
        panes[3][4] = pane34;
        panes[4][0] = pane40;
        panes[4][1] = pane41;
        panes[4][2] = pane42;
        panes[4][3] = pane43;
        panes[4][4] = pane44;

        lights[0][0] = light00;
        lights[0][1] = light01;
        lights[0][2] = light02;
        lights[0][3] = light03;
        lights[0][4] = light04;
        lights[1][0] = light10;
        lights[1][1] = light11;
        lights[1][2] = light12;
        lights[1][3] = light13;
        lights[1][4] = light14;
        lights[2][0] = light20;
        lights[2][1] = light21;
        lights[2][2] = light22;
        lights[2][3] = light23;
        lights[2][4] = light24;
        lights[3][0] = light30;
        lights[3][1] = light31;
        lights[3][2] = light32;
        lights[3][3] = light33;
        lights[3][4] = light34;
        lights[4][0] = light40;
        lights[4][1] = light41;
        lights[4][2] = light42;
        lights[4][3] = light43;
        lights[4][4] = light44;

        reSet();
    }

}
