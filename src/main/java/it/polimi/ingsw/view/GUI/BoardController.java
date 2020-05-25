package it.polimi.ingsw.view.GUI;

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
    private Pane pane00, pane01, pane02, pane03, pane04, pane10, pane11, pane12, pane13, pane14, pane21, pane22, pane23, pane24, pane20, pane30, pane31, pane32, pane33, pane34, pane40, pane41, pane42, pane43, pane44;

    @FXML
    private ImageView light00, light01, light02, light03, light04, light10, light11, light12, light13, light14, light21, light22, light23, light24, light20, light30, light31, light32, light33, light34, light40, light41, light42, light43, light44;


    @FXML
    private ImageView image00, image01, image02, image03, image04, image10, image11, image12, image13, image14, image21, image22, image23, image24, image20, image30, image31, image32, image33, image34, image40, image41, image42, image43, image44;


    @FXML
    private Label player0, player1, player2, godName0, godName1, godName2, worker0, worker1, worker2, turn0, turn1, turn2, status0, status1, status2, lab0;

    @FXML
    private ImageView god0, god1, god2, color0, color1, color2;

    private static MainController controller = new MainController();

    private ImageView[][] images = new ImageView[5][5];

    private Image blue = new Image("@GraphicSrc/Buildings/blue.jpg"), blueLevel1 = new Image("@GraphicSrc/Buildings/blueLevel1.jpg"),
            blueLevel2 = new Image("@GraphicSrc/Buildings/blueLevel2.jpg"), blueLevel3 = new Image("@GraphicSrc/Buildings/blueLevel3.jpg"),
            brown = new Image("@GraphicSrc/Buildings/brown.jpg"), brownLevel1 = new Image("@GraphicSrc/Buildings/brownLevel1.jpg"),
            brownLevel2 = new Image("@GraphicSrc/Buildings/brownLevel2.jpg"), brownLevel3 = new Image("@GraphicSrc/Buildings/brownLevel3.jpg"),
            dome = new Image("@GraphicSrc/Buildings/dome.jpg"), domeLevel1 = new Image("@GraphicSrc/Buildings/domeLevel1.jpg"),
            domeLevel2 = new Image("@GraphicSrc/Buildings/domeLevel2.jpg"), domeLevel3 = new Image("@GraphicSrc/Buildings/domeLevel3.jpg"),
            level1 = new Image("@GraphicSrc/Buildings/level1.jpg"), level2 = new Image("@GraphicSrc/Buildings/level2.jpg"),
            level3 = new Image("@GraphicSrc/Buildings/level3.jpg"), white = new Image("@GraphicSrc/Buildings/white.jpg"),
            whiteLevel1 = new Image("@GraphicSrc/Buildings/whiteLevel1.jpg"), whiteLevel2 = new Image("@GraphicSrc/Buildings/whiteLevel2.jpg"),
            whiteLevel3 = new Image("@GraphicSrc/Buildings/whiteLevel3.jpg");

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

    private void reSet() {
        hBox0.setVisible(false);
        hBox1.setVisible(false);
        hBox2.setVisible(false);

        chooseWorkervBox.setVisible(false);
        chooseWorkervBox.setDisable(true);
        lab0.setVisible(false);
        color0.setDisable(true);
        color0.setVisible(false);

        god0.setVisible(false);
        god0.setDisable(true);
        god1.setVisible(false);
        god1.setDisable(true);
        god2.setVisible(false);
        god2.setDisable(true);

        light00.setVisible(false);
        light00.setDisable(true);
        light01.setVisible(false);
        light01.setDisable(true);
        light02.setVisible(false);
        light02.setDisable(true);
        light03.setVisible(false);
        light03.setDisable(true);
        light04.setVisible(false);
        light04.setDisable(true);
        light10.setVisible(false);
        light10.setDisable(true);
        light11.setVisible(false);
        light11.setDisable(true);
        light12.setVisible(false);
        light12.setDisable(true);
        light13.setVisible(false);
        light13.setDisable(true);
        light14.setVisible(false);
        light14.setDisable(true);
        light20.setVisible(false);
        light20.setDisable(true);
        light21.setVisible(false);
        light21.setDisable(true);
        light22.setVisible(false);
        light22.setDisable(true);
        light23.setVisible(false);
        light23.setDisable(true);
        light24.setVisible(false);
        light24.setDisable(true);
        light30.setVisible(false);
        light30.setDisable(true);
        light31.setVisible(false);
        light31.setDisable(true);
        light32.setVisible(false);
        light32.setDisable(true);
        light33.setVisible(false);
        light33.setDisable(true);
        light34.setVisible(false);
        light34.setDisable(true);
        light40.setVisible(false);
        light40.setDisable(true);
        light41.setVisible(false);
        light41.setDisable(true);
        light42.setVisible(false);
        light42.setDisable(true);
        light43.setVisible(false);
        light43.setDisable(true);
        light44.setVisible(false);
        light44.setDisable(true);


        image00.setVisible(false);
        image00.setDisable(true);
        image01.setVisible(false);
        image01.setDisable(true);
        image02.setVisible(false);
        image02.setDisable(true);
        image03.setVisible(false);
        image03.setDisable(true);
        image04.setVisible(false);
        image04.setDisable(true);
        image10.setVisible(false);
        image10.setDisable(true);
        image11.setVisible(false);
        image11.setDisable(true);
        image12.setVisible(false);
        image12.setDisable(true);
        image13.setVisible(false);
        image13.setDisable(true);
        image14.setVisible(false);
        image14.setDisable(true);
        image20.setVisible(false);
        image20.setDisable(true);
        image21.setVisible(false);
        image21.setDisable(true);
        image22.setVisible(false);
        image22.setDisable(true);
        image23.setVisible(false);
        image23.setDisable(true);
        image24.setVisible(false);
        image24.setDisable(true);
        image30.setVisible(false);
        image30.setDisable(true);
        image31.setVisible(false);
        image31.setDisable(true);
        image32.setVisible(false);
        image32.setDisable(true);
        image33.setVisible(false);
        image33.setDisable(true);
        image34.setVisible(false);
        image34.setDisable(true);
        image40.setVisible(false);
        image40.setDisable(true);
        image41.setVisible(false);
        image41.setDisable(true);
        image42.setVisible(false);
        image42.setDisable(true);
        image43.setVisible(false);
        image43.setDisable(true);
        image44.setVisible(false);
        image44.setDisable(true);

        pane00.setVisible(false);
        pane00.setDisable(true);
        pane01.setVisible(false);
        pane01.setDisable(true);
        pane02.setVisible(false);
        pane02.setDisable(true);
        pane03.setVisible(false);
        pane03.setDisable(true);
        pane04.setVisible(false);
        pane04.setDisable(true);
        pane10.setVisible(false);
        pane10.setDisable(true);
        pane11.setVisible(false);
        pane11.setDisable(true);
        pane12.setVisible(false);
        pane12.setDisable(true);
        pane13.setVisible(false);
        pane13.setDisable(true);
        pane14.setVisible(false);
        pane14.setDisable(true);
        pane20.setVisible(false);
        pane20.setDisable(true);
        pane21.setVisible(false);
        pane21.setDisable(true);
        pane22.setVisible(false);
        pane22.setDisable(true);
        pane23.setVisible(false);
        pane23.setDisable(true);
        pane24.setVisible(false);
        pane24.setDisable(true);
        pane30.setVisible(false);
        pane30.setDisable(true);
        pane31.setVisible(false);
        pane31.setDisable(true);
        pane32.setVisible(false);
        pane32.setDisable(true);
        pane33.setVisible(false);
        pane33.setDisable(true);
        pane34.setVisible(false);
        pane34.setDisable(true);
        pane40.setVisible(false);
        pane40.setDisable(true);
        pane41.setVisible(false);
        pane41.setDisable(true);
        pane42.setVisible(false);
        pane42.setDisable(true);
        pane43.setVisible(false);
        pane43.setDisable(true);
        pane44.setVisible(false);
        pane44.setDisable(true);

        load();
    }

    private void load() {
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

        setBoard();

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

    private void setBoard() {
        Cell[][] board = controller.getBoard();
        switch (board[0][0].getBlocks().size()) {
            case 1:
                if (!board[0][0].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[0][0].getBlocks().get(0).block.equals("DOME")) {
                        image00.setImage(level1);
                    } else {
                        image00.setImage(dome);
                    }
                } else {
                    switch (board[0][0].getBlocks().get(0).color) {
                        case "BLUE":
                            image00.setImage(blue);
                            break;
                        case "BROWN":
                            image00.setImage(brown);
                            break;
                        case "White":
                            image00.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[0][0].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[0][0].getBlocks().get(1).block.equals("DOME")) {
                        image00.setImage(level2);
                    } else {
                        image00.setImage(domeLevel1);
                    }
                } else {
                    switch (board[0][0].getBlocks().get(1).color) {
                        case "BLUE":
                            image00.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image00.setImage(brownLevel1);
                            break;
                        case "White":
                            image00.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[0][0].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[0][0].getBlocks().get(2).block.equals("DOME")) {
                        image00.setImage(level3);
                    } else {
                        image00.setImage(domeLevel2);
                    }
                } else {
                    switch (board[0][0].getBlocks().get(2).color) {
                        case "BLUE":
                            image00.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image00.setImage(brownLevel2);
                            break;
                        case "White":
                            image00.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[0][0].getBlocks().get(3).block.equals("WORKER")) {
                    image00.setImage(domeLevel3);
                } else {
                    switch (board[0][0].getBlocks().get(3).color) {
                        case "BLUE":
                            image00.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image00.setImage(brownLevel3);
                            break;
                        case "White":
                            image00.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image00.setVisible(true);

        switch (board[0][1].getBlocks().size()) {
            case 1:
                if (!board[0][1].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[0][1].getBlocks().get(0).block.equals("DOME")) {
                        image01.setImage(level1);
                    } else {
                        image01.setImage(dome);
                    }
                } else {
                    switch (board[0][1].getBlocks().get(0).color) {
                        case "BLUE":
                            image01.setImage(blue);
                            break;
                        case "BROWN":
                            image01.setImage(brown);
                            break;
                        case "White":
                            image01.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[0][1].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[0][1].getBlocks().get(1).block.equals("DOME")) {
                        image01.setImage(level2);
                    } else {
                        image01.setImage(domeLevel1);
                    }
                } else {
                    switch (board[0][1].getBlocks().get(1).color) {
                        case "BLUE":
                            image01.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image01.setImage(brownLevel1);
                            break;
                        case "White":
                            image01.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[0][1].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[0][1].getBlocks().get(2).block.equals("DOME")) {
                        image01.setImage(level3);
                    } else {
                        image01.setImage(domeLevel2);
                    }
                } else {
                    switch (board[0][1].getBlocks().get(2).color) {
                        case "BLUE":
                            image01.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image01.setImage(brownLevel2);
                            break;
                        case "White":
                            image01.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[0][1].getBlocks().get(3).block.equals("WORKER")) {
                    image01.setImage(domeLevel3);
                } else {
                    switch (board[0][1].getBlocks().get(3).color) {
                        case "BLUE":
                            image01.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image01.setImage(brownLevel3);
                            break;
                        case "White":
                            image01.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image01.setVisible(true);

        switch (board[0][2].getBlocks().size()) {
            case 1:
                if (!board[0][2].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[0][2].getBlocks().get(0).block.equals("DOME")) {
                        image02.setImage(level1);
                    } else {
                        image02.setImage(dome);
                    }
                } else {
                    switch (board[0][2].getBlocks().get(0).color) {
                        case "BLUE":
                            image02.setImage(blue);
                            break;
                        case "BROWN":
                            image02.setImage(brown);
                            break;
                        case "White":
                            image02.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[0][2].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[0][2].getBlocks().get(1).block.equals("DOME")) {
                        image02.setImage(level2);
                    } else {
                        image02.setImage(domeLevel1);
                    }
                } else {
                    switch (board[0][2].getBlocks().get(1).color) {
                        case "BLUE":
                            image02.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image02.setImage(brownLevel1);
                            break;
                        case "White":
                            image02.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[0][2].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[0][2].getBlocks().get(2).block.equals("DOME")) {
                        image02.setImage(level3);
                    } else {
                        image02.setImage(domeLevel2);
                    }
                } else {
                    switch (board[0][2].getBlocks().get(2).color) {
                        case "BLUE":
                            image02.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image02.setImage(brownLevel2);
                            break;
                        case "White":
                            image02.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[0][2].getBlocks().get(3).block.equals("WORKER")) {
                    image02.setImage(domeLevel3);
                } else {
                    switch (board[0][2].getBlocks().get(3).color) {
                        case "BLUE":
                            image02.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image02.setImage(brownLevel3);
                            break;
                        case "White":
                            image02.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image02.setVisible(true);

        switch (board[0][3].getBlocks().size()) {
            case 1:
                if (!board[0][3].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[0][3].getBlocks().get(0).block.equals("DOME")) {
                        image03.setImage(level1);
                    } else {
                        image03.setImage(dome);
                    }
                } else {
                    switch (board[0][3].getBlocks().get(0).color) {
                        case "BLUE":
                            image03.setImage(blue);
                            break;
                        case "BROWN":
                            image03.setImage(brown);
                            break;
                        case "White":
                            image03.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[0][3].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[0][3].getBlocks().get(1).block.equals("DOME")) {
                        image03.setImage(level2);
                    } else {
                        image03.setImage(domeLevel1);
                    }
                } else {
                    switch (board[0][3].getBlocks().get(1).color) {
                        case "BLUE":
                            image03.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image03.setImage(brownLevel1);
                            break;
                        case "White":
                            image03.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[0][3].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[0][3].getBlocks().get(2).block.equals("DOME")) {
                        image03.setImage(level3);
                    } else {
                        image03.setImage(domeLevel2);
                    }
                } else {
                    switch (board[0][3].getBlocks().get(2).color) {
                        case "BLUE":
                            image03.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image03.setImage(brownLevel2);
                            break;
                        case "White":
                            image03.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[0][3].getBlocks().get(3).block.equals("WORKER")) {
                    image03.setImage(domeLevel3);
                } else {
                    switch (board[0][3].getBlocks().get(3).color) {
                        case "BLUE":
                            image03.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image03.setImage(brownLevel3);
                            break;
                        case "White":
                            image03.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image03.setVisible(true);

        switch (board[0][4].getBlocks().size()) {
            case 1:
                if (!board[0][4].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[0][4].getBlocks().get(0).block.equals("DOME")) {
                        image04.setImage(level1);
                    } else {
                        image04.setImage(dome);
                    }
                } else {
                    switch (board[0][4].getBlocks().get(0).color) {
                        case "BLUE":
                            image04.setImage(blue);
                            break;
                        case "BROWN":
                            image04.setImage(brown);
                            break;
                        case "White":
                            image04.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[0][4].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[0][4].getBlocks().get(1).block.equals("DOME")) {
                        image04.setImage(level2);
                    } else {
                        image04.setImage(domeLevel1);
                    }
                } else {
                    switch (board[0][4].getBlocks().get(1).color) {
                        case "BLUE":
                            image04.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image04.setImage(brownLevel1);
                            break;
                        case "White":
                            image04.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[0][4].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[0][4].getBlocks().get(2).block.equals("DOME")) {
                        image04.setImage(level3);
                    } else {
                        image04.setImage(domeLevel2);
                    }
                } else {
                    switch (board[0][4].getBlocks().get(2).color) {
                        case "BLUE":
                            image04.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image04.setImage(brownLevel2);
                            break;
                        case "White":
                            image04.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[0][4].getBlocks().get(3).block.equals("WORKER")) {
                    image04.setImage(domeLevel3);
                } else {
                    switch (board[0][4].getBlocks().get(3).color) {
                        case "BLUE":
                            image04.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image04.setImage(brownLevel3);
                            break;
                        case "White":
                            image04.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image04.setVisible(true);

        switch (board[1][0].getBlocks().size()) {
            case 1:
                if (!board[1][0].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[1][0].getBlocks().get(0).block.equals("DOME")) {
                        image10.setImage(level1);
                    } else {
                        image10.setImage(dome);
                    }
                } else {
                    switch (board[1][0].getBlocks().get(0).color) {
                        case "BLUE":
                            image10.setImage(blue);
                            break;
                        case "BROWN":
                            image10.setImage(brown);
                            break;
                        case "White":
                            image10.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[1][0].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[1][0].getBlocks().get(1).block.equals("DOME")) {
                        image10.setImage(level2);
                    } else {
                        image10.setImage(domeLevel1);
                    }
                } else {
                    switch (board[1][0].getBlocks().get(1).color) {
                        case "BLUE":
                            image10.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image10.setImage(brownLevel1);
                            break;
                        case "White":
                            image10.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[1][0].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[1][0].getBlocks().get(2).block.equals("DOME")) {
                        image10.setImage(level3);
                    } else {
                        image10.setImage(domeLevel2);
                    }
                } else {
                    switch (board[1][0].getBlocks().get(2).color) {
                        case "BLUE":
                            image10.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image10.setImage(brownLevel2);
                            break;
                        case "White":
                            image10.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[1][0].getBlocks().get(3).block.equals("WORKER")) {
                    image10.setImage(domeLevel3);
                } else {
                    switch (board[1][0].getBlocks().get(3).color) {
                        case "BLUE":
                            image10.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image10.setImage(brownLevel3);
                            break;
                        case "White":
                            image10.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image10.setVisible(true);

        switch (board[1][1].getBlocks().size()) {
            case 1:
                if (!board[1][1].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[1][1].getBlocks().get(0).block.equals("DOME")) {
                        image11.setImage(level1);
                    } else {
                        image11.setImage(dome);
                    }
                } else {
                    switch (board[1][1].getBlocks().get(0).color) {
                        case "BLUE":
                            image11.setImage(blue);
                            break;
                        case "BROWN":
                            image11.setImage(brown);
                            break;
                        case "White":
                            image11.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[1][1].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[1][1].getBlocks().get(1).block.equals("DOME")) {
                        image11.setImage(level2);
                    } else {
                        image11.setImage(domeLevel1);
                    }
                } else {
                    switch (board[1][1].getBlocks().get(1).color) {
                        case "BLUE":
                            image11.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image11.setImage(brownLevel1);
                            break;
                        case "White":
                            image11.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[1][1].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[1][1].getBlocks().get(2).block.equals("DOME")) {
                        image11.setImage(level3);
                    } else {
                        image11.setImage(domeLevel2);
                    }
                } else {
                    switch (board[1][1].getBlocks().get(2).color) {
                        case "BLUE":
                            image11.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image11.setImage(brownLevel2);
                            break;
                        case "White":
                            image11.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[1][1].getBlocks().get(3).block.equals("WORKER")) {
                    image11.setImage(domeLevel3);
                } else {
                    switch (board[1][1].getBlocks().get(3).color) {
                        case "BLUE":
                            image11.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image11.setImage(brownLevel3);
                            break;
                        case "White":
                            image11.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image11.setVisible(true);

        switch (board[1][2].getBlocks().size()) {
            case 1:
                if (!board[1][2].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[1][2].getBlocks().get(0).block.equals("DOME")) {
                        image12.setImage(level1);
                    } else {
                        image12.setImage(dome);
                    }
                } else {
                    switch (board[1][2].getBlocks().get(0).color) {
                        case "BLUE":
                            image12.setImage(blue);
                            break;
                        case "BROWN":
                            image12.setImage(brown);
                            break;
                        case "White":
                            image12.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[1][2].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[1][2].getBlocks().get(1).block.equals("DOME")) {
                        image12.setImage(level2);
                    } else {
                        image12.setImage(domeLevel1);
                    }
                } else {
                    switch (board[1][2].getBlocks().get(1).color) {
                        case "BLUE":
                            image12.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image12.setImage(brownLevel1);
                            break;
                        case "White":
                            image12.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[1][2].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[1][2].getBlocks().get(2).block.equals("DOME")) {
                        image12.setImage(level3);
                    } else {
                        image12.setImage(domeLevel2);
                    }
                } else {
                    switch (board[1][2].getBlocks().get(2).color) {
                        case "BLUE":
                            image12.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image12.setImage(brownLevel2);
                            break;
                        case "White":
                            image12.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[1][2].getBlocks().get(3).block.equals("WORKER")) {
                    image12.setImage(domeLevel3);
                } else {
                    switch (board[1][2].getBlocks().get(3).color) {
                        case "BLUE":
                            image12.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image12.setImage(brownLevel3);
                            break;
                        case "White":
                            image12.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image12.setVisible(true);

        switch (board[1][3].getBlocks().size()) {
            case 1:
                if (!board[1][3].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[1][3].getBlocks().get(0).block.equals("DOME")) {
                        image13.setImage(level1);
                    } else {
                        image13.setImage(dome);
                    }
                } else {
                    switch (board[1][3].getBlocks().get(0).color) {
                        case "BLUE":
                            image13.setImage(blue);
                            break;
                        case "BROWN":
                            image13.setImage(brown);
                            break;
                        case "White":
                            image13.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[1][3].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[1][3].getBlocks().get(1).block.equals("DOME")) {
                        image13.setImage(level2);
                    } else {
                        image13.setImage(domeLevel1);
                    }
                } else {
                    switch (board[1][3].getBlocks().get(1).color) {
                        case "BLUE":
                            image13.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image13.setImage(brownLevel1);
                            break;
                        case "White":
                            image13.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[1][3].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[1][3].getBlocks().get(2).block.equals("DOME")) {
                        image13.setImage(level3);
                    } else {
                        image13.setImage(domeLevel2);
                    }
                } else {
                    switch (board[1][3].getBlocks().get(2).color) {
                        case "BLUE":
                            image13.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image13.setImage(brownLevel2);
                            break;
                        case "White":
                            image13.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[1][3].getBlocks().get(3).block.equals("WORKER")) {
                    image13.setImage(domeLevel3);
                } else {
                    switch (board[1][3].getBlocks().get(3).color) {
                        case "BLUE":
                            image13.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image13.setImage(brownLevel3);
                            break;
                        case "White":
                            image13.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image13.setVisible(true);

        switch (board[1][4].getBlocks().size()) {
            case 1:
                if (!board[1][4].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[1][4].getBlocks().get(0).block.equals("DOME")) {
                        image14.setImage(level1);
                    } else {
                        image14.setImage(dome);
                    }
                } else {
                    switch (board[1][4].getBlocks().get(0).color) {
                        case "BLUE":
                            image14.setImage(blue);
                            break;
                        case "BROWN":
                            image14.setImage(brown);
                            break;
                        case "White":
                            image14.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[1][4].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[1][4].getBlocks().get(1).block.equals("DOME")) {
                        image14.setImage(level2);
                    } else {
                        image14.setImage(domeLevel1);
                    }
                } else {
                    switch (board[1][4].getBlocks().get(1).color) {
                        case "BLUE":
                            image14.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image14.setImage(brownLevel1);
                            break;
                        case "White":
                            image14.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[1][4].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[1][4].getBlocks().get(2).block.equals("DOME")) {
                        image14.setImage(level3);
                    } else {
                        image14.setImage(domeLevel2);
                    }
                } else {
                    switch (board[1][4].getBlocks().get(2).color) {
                        case "BLUE":
                            image14.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image14.setImage(brownLevel2);
                            break;
                        case "White":
                            image14.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[1][4].getBlocks().get(3).block.equals("WORKER")) {
                    image14.setImage(domeLevel3);
                } else {
                    switch (board[1][4].getBlocks().get(3).color) {
                        case "BLUE":
                            image14.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image14.setImage(brownLevel3);
                            break;
                        case "White":
                            image14.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image14.setVisible(true);

        switch (board[2][0].getBlocks().size()) {
            case 1:
                if (!board[2][0].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[2][0].getBlocks().get(0).block.equals("DOME")) {
                        image20.setImage(level1);
                    } else {
                        image20.setImage(dome);
                    }
                } else {
                    switch (board[2][0].getBlocks().get(0).color) {
                        case "BLUE":
                            image20.setImage(blue);
                            break;
                        case "BROWN":
                            image20.setImage(brown);
                            break;
                        case "White":
                            image20.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[2][0].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[2][0].getBlocks().get(1).block.equals("DOME")) {
                        image20.setImage(level2);
                    } else {
                        image20.setImage(domeLevel1);
                    }
                } else {
                    switch (board[2][0].getBlocks().get(1).color) {
                        case "BLUE":
                            image20.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image20.setImage(brownLevel1);
                            break;
                        case "White":
                            image20.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[2][0].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[2][0].getBlocks().get(2).block.equals("DOME")) {
                        image20.setImage(level3);
                    } else {
                        image20.setImage(domeLevel2);
                    }
                } else {
                    switch (board[2][0].getBlocks().get(2).color) {
                        case "BLUE":
                            image20.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image20.setImage(brownLevel2);
                            break;
                        case "White":
                            image20.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[2][0].getBlocks().get(3).block.equals("WORKER")) {
                    image20.setImage(domeLevel3);
                } else {
                    switch (board[2][0].getBlocks().get(3).color) {
                        case "BLUE":
                            image20.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image20.setImage(brownLevel3);
                            break;
                        case "White":
                            image20.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image20.setVisible(true);

        switch (board[2][1].getBlocks().size()) {
            case 1:
                if (!board[2][1].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[2][1].getBlocks().get(0).block.equals("DOME")) {
                        image21.setImage(level1);
                    } else {
                        image21.setImage(dome);
                    }
                } else {
                    switch (board[2][1].getBlocks().get(0).color) {
                        case "BLUE":
                            image21.setImage(blue);
                            break;
                        case "BROWN":
                            image21.setImage(brown);
                            break;
                        case "White":
                            image21.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[2][1].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[2][1].getBlocks().get(1).block.equals("DOME")) {
                        image21.setImage(level2);
                    } else {
                        image21.setImage(domeLevel1);
                    }
                } else {
                    switch (board[2][1].getBlocks().get(1).color) {
                        case "BLUE":
                            image21.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image21.setImage(brownLevel1);
                            break;
                        case "White":
                            image21.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[2][1].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[2][1].getBlocks().get(2).block.equals("DOME")) {
                        image21.setImage(level3);
                    } else {
                        image21.setImage(domeLevel2);
                    }
                } else {
                    switch (board[2][1].getBlocks().get(2).color) {
                        case "BLUE":
                            image21.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image21.setImage(brownLevel2);
                            break;
                        case "White":
                            image21.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[2][1].getBlocks().get(3).block.equals("WORKER")) {
                    image21.setImage(domeLevel3);
                } else {
                    switch (board[2][1].getBlocks().get(3).color) {
                        case "BLUE":
                            image21.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image21.setImage(brownLevel3);
                            break;
                        case "White":
                            image21.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image21.setVisible(true);

        switch (board[2][2].getBlocks().size()) {
            case 1:
                if (!board[2][2].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[2][2].getBlocks().get(0).block.equals("DOME")) {
                        image22.setImage(level1);
                    } else {
                        image22.setImage(dome);
                    }
                } else {
                    switch (board[2][2].getBlocks().get(0).color) {
                        case "BLUE":
                            image22.setImage(blue);
                            break;
                        case "BROWN":
                            image22.setImage(brown);
                            break;
                        case "White":
                            image22.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[2][2].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[2][2].getBlocks().get(1).block.equals("DOME")) {
                        image22.setImage(level2);
                    } else {
                        image22.setImage(domeLevel1);
                    }
                } else {
                    switch (board[2][2].getBlocks().get(1).color) {
                        case "BLUE":
                            image22.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image22.setImage(brownLevel1);
                            break;
                        case "White":
                            image22.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[2][2].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[2][2].getBlocks().get(2).block.equals("DOME")) {
                        image22.setImage(level3);
                    } else {
                        image22.setImage(domeLevel2);
                    }
                } else {
                    switch (board[2][2].getBlocks().get(2).color) {
                        case "BLUE":
                            image22.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image22.setImage(brownLevel2);
                            break;
                        case "White":
                            image22.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[2][2].getBlocks().get(3).block.equals("WORKER")) {
                    image22.setImage(domeLevel3);
                } else {
                    switch (board[2][2].getBlocks().get(3).color) {
                        case "BLUE":
                            image22.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image22.setImage(brownLevel3);
                            break;
                        case "White":
                            image22.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image22.setVisible(true);

        switch (board[2][3].getBlocks().size()) {
            case 1:
                if (!board[2][3].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[2][3].getBlocks().get(0).block.equals("DOME")) {
                        image23.setImage(level1);
                    } else {
                        image23.setImage(dome);
                    }
                } else {
                    switch (board[2][3].getBlocks().get(0).color) {
                        case "BLUE":
                            image23.setImage(blue);
                            break;
                        case "BROWN":
                            image23.setImage(brown);
                            break;
                        case "White":
                            image23.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[2][3].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[2][3].getBlocks().get(1).block.equals("DOME")) {
                        image23.setImage(level2);
                    } else {
                        image23.setImage(domeLevel1);
                    }
                } else {
                    switch (board[2][3].getBlocks().get(1).color) {
                        case "BLUE":
                            image23.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image23.setImage(brownLevel1);
                            break;
                        case "White":
                            image23.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[2][3].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[2][3].getBlocks().get(2).block.equals("DOME")) {
                        image23.setImage(level3);
                    } else {
                        image23.setImage(domeLevel2);
                    }
                } else {
                    switch (board[2][3].getBlocks().get(2).color) {
                        case "BLUE":
                            image23.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image23.setImage(brownLevel2);
                            break;
                        case "White":
                            image23.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[2][3].getBlocks().get(3).block.equals("WORKER")) {
                    image23.setImage(domeLevel3);
                } else {
                    switch (board[2][3].getBlocks().get(3).color) {
                        case "BLUE":
                            image23.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image23.setImage(brownLevel3);
                            break;
                        case "White":
                            image23.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image23.setVisible(true);

        switch (board[2][4].getBlocks().size()) {
            case 1:
                if (!board[2][4].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[2][4].getBlocks().get(0).block.equals("DOME")) {
                        image24.setImage(level1);
                    } else {
                        image24.setImage(dome);
                    }
                } else {
                    switch (board[2][4].getBlocks().get(0).color) {
                        case "BLUE":
                            image24.setImage(blue);
                            break;
                        case "BROWN":
                            image24.setImage(brown);
                            break;
                        case "White":
                            image24.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[2][4].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[2][4].getBlocks().get(1).block.equals("DOME")) {
                        image24.setImage(level2);
                    } else {
                        image24.setImage(domeLevel1);
                    }
                } else {
                    switch (board[2][4].getBlocks().get(1).color) {
                        case "BLUE":
                            image24.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image24.setImage(brownLevel1);
                            break;
                        case "White":
                            image24.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[2][4].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[2][4].getBlocks().get(2).block.equals("DOME")) {
                        image24.setImage(level3);
                    } else {
                        image24.setImage(domeLevel2);
                    }
                } else {
                    switch (board[2][4].getBlocks().get(2).color) {
                        case "BLUE":
                            image24.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image24.setImage(brownLevel2);
                            break;
                        case "White":
                            image24.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[2][4].getBlocks().get(3).block.equals("WORKER")) {
                    image24.setImage(domeLevel3);
                } else {
                    switch (board[2][4].getBlocks().get(3).color) {
                        case "BLUE":
                            image24.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image24.setImage(brownLevel3);
                            break;
                        case "White":
                            image24.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image24.setVisible(true);

        switch (board[3][0].getBlocks().size()) {
            case 1:
                if (!board[3][0].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[3][0].getBlocks().get(0).block.equals("DOME")) {
                        image30.setImage(level1);
                    } else {
                        image30.setImage(dome);
                    }
                } else {
                    switch (board[3][0].getBlocks().get(0).color) {
                        case "BLUE":
                            image30.setImage(blue);
                            break;
                        case "BROWN":
                            image30.setImage(brown);
                            break;
                        case "White":
                            image30.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[3][0].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[3][0].getBlocks().get(1).block.equals("DOME")) {
                        image30.setImage(level2);
                    } else {
                        image30.setImage(domeLevel1);
                    }
                } else {
                    switch (board[3][0].getBlocks().get(1).color) {
                        case "BLUE":
                            image30.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image30.setImage(brownLevel1);
                            break;
                        case "White":
                            image30.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[3][0].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[3][0].getBlocks().get(2).block.equals("DOME")) {
                        image30.setImage(level3);
                    } else {
                        image30.setImage(domeLevel2);
                    }
                } else {
                    switch (board[3][0].getBlocks().get(2).color) {
                        case "BLUE":
                            image30.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image30.setImage(brownLevel2);
                            break;
                        case "White":
                            image30.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[3][0].getBlocks().get(3).block.equals("WORKER")) {
                    image30.setImage(domeLevel3);
                } else {
                    switch (board[3][0].getBlocks().get(3).color) {
                        case "BLUE":
                            image30.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image30.setImage(brownLevel3);
                            break;
                        case "White":
                            image30.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image30.setVisible(true);

        switch (board[3][1].getBlocks().size()) {
            case 1:
                if (!board[3][1].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[3][1].getBlocks().get(0).block.equals("DOME")) {
                        image31.setImage(level1);
                    } else {
                        image31.setImage(dome);
                    }
                } else {
                    switch (board[3][1].getBlocks().get(0).color) {
                        case "BLUE":
                            image31.setImage(blue);
                            break;
                        case "BROWN":
                            image31.setImage(brown);
                            break;
                        case "White":
                            image31.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[3][1].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[3][1].getBlocks().get(1).block.equals("DOME")) {
                        image31.setImage(level2);
                    } else {
                        image31.setImage(domeLevel1);
                    }
                } else {
                    switch (board[3][1].getBlocks().get(1).color) {
                        case "BLUE":
                            image31.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image31.setImage(brownLevel1);
                            break;
                        case "White":
                            image31.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[3][1].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[3][1].getBlocks().get(2).block.equals("DOME")) {
                        image31.setImage(level3);
                    } else {
                        image31.setImage(domeLevel2);
                    }
                } else {
                    switch (board[3][1].getBlocks().get(2).color) {
                        case "BLUE":
                            image31.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image31.setImage(brownLevel2);
                            break;
                        case "White":
                            image31.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[3][1].getBlocks().get(3).block.equals("WORKER")) {
                    image31.setImage(domeLevel3);
                } else {
                    switch (board[3][1].getBlocks().get(3).color) {
                        case "BLUE":
                            image31.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image31.setImage(brownLevel3);
                            break;
                        case "White":
                            image31.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image31.setVisible(true);

        switch (board[3][2].getBlocks().size()) {
            case 1:
                if (!board[3][2].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[3][2].getBlocks().get(0).block.equals("DOME")) {
                        image32.setImage(level1);
                    } else {
                        image32.setImage(dome);
                    }
                } else {
                    switch (board[3][2].getBlocks().get(0).color) {
                        case "BLUE":
                            image32.setImage(blue);
                            break;
                        case "BROWN":
                            image32.setImage(brown);
                            break;
                        case "White":
                            image32.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[3][2].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[3][2].getBlocks().get(1).block.equals("DOME")) {
                        image32.setImage(level2);
                    } else {
                        image32.setImage(domeLevel1);
                    }
                } else {
                    switch (board[3][2].getBlocks().get(1).color) {
                        case "BLUE":
                            image32.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image32.setImage(brownLevel1);
                            break;
                        case "White":
                            image32.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[3][2].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[3][2].getBlocks().get(2).block.equals("DOME")) {
                        image32.setImage(level3);
                    } else {
                        image32.setImage(domeLevel2);
                    }
                } else {
                    switch (board[3][2].getBlocks().get(2).color) {
                        case "BLUE":
                            image32.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image32.setImage(brownLevel2);
                            break;
                        case "White":
                            image32.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[3][2].getBlocks().get(3).block.equals("WORKER")) {
                    image32.setImage(domeLevel3);
                } else {
                    switch (board[3][2].getBlocks().get(3).color) {
                        case "BLUE":
                            image32.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image32.setImage(brownLevel3);
                            break;
                        case "White":
                            image32.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image32.setVisible(true);

        switch (board[3][3].getBlocks().size()) {
            case 1:
                if (!board[3][3].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[3][3].getBlocks().get(0).block.equals("DOME")) {
                        image33.setImage(level1);
                    } else {
                        image33.setImage(dome);
                    }
                } else {
                    switch (board[3][3].getBlocks().get(0).color) {
                        case "BLUE":
                            image33.setImage(blue);
                            break;
                        case "BROWN":
                            image33.setImage(brown);
                            break;
                        case "White":
                            image33.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[3][3].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[3][3].getBlocks().get(1).block.equals("DOME")) {
                        image33.setImage(level2);
                    } else {
                        image33.setImage(domeLevel1);
                    }
                } else {
                    switch (board[3][3].getBlocks().get(1).color) {
                        case "BLUE":
                            image33.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image33.setImage(brownLevel1);
                            break;
                        case "White":
                            image33.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[3][3].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[3][3].getBlocks().get(2).block.equals("DOME")) {
                        image33.setImage(level3);
                    } else {
                        image33.setImage(domeLevel2);
                    }
                } else {
                    switch (board[3][3].getBlocks().get(2).color) {
                        case "BLUE":
                            image33.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image33.setImage(brownLevel2);
                            break;
                        case "White":
                            image33.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[3][3].getBlocks().get(3).block.equals("WORKER")) {
                    image33.setImage(domeLevel3);
                } else {
                    switch (board[3][3].getBlocks().get(3).color) {
                        case "BLUE":
                            image33.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image33.setImage(brownLevel3);
                            break;
                        case "White":
                            image33.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image33.setVisible(true);

        switch (board[3][4].getBlocks().size()) {
            case 1:
                if (!board[3][4].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[3][4].getBlocks().get(0).block.equals("DOME")) {
                        image34.setImage(level1);
                    } else {
                        image34.setImage(dome);
                    }
                } else {
                    switch (board[3][4].getBlocks().get(0).color) {
                        case "BLUE":
                            image34.setImage(blue);
                            break;
                        case "BROWN":
                            image34.setImage(brown);
                            break;
                        case "White":
                            image34.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[3][4].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[3][4].getBlocks().get(1).block.equals("DOME")) {
                        image34.setImage(level2);
                    } else {
                        image34.setImage(domeLevel1);
                    }
                } else {
                    switch (board[3][4].getBlocks().get(1).color) {
                        case "BLUE":
                            image34.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image34.setImage(brownLevel1);
                            break;
                        case "White":
                            image34.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[3][4].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[3][4].getBlocks().get(2).block.equals("DOME")) {
                        image34.setImage(level3);
                    } else {
                        image34.setImage(domeLevel2);
                    }
                } else {
                    switch (board[3][4].getBlocks().get(2).color) {
                        case "BLUE":
                            image34.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image34.setImage(brownLevel2);
                            break;
                        case "White":
                            image34.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[3][4].getBlocks().get(3).block.equals("WORKER")) {
                    image34.setImage(domeLevel3);
                } else {
                    switch (board[3][4].getBlocks().get(3).color) {
                        case "BLUE":
                            image34.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image34.setImage(brownLevel3);
                            break;
                        case "White":
                            image34.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image34.setVisible(true);

        switch (board[4][0].getBlocks().size()) {
            case 1:
                if (!board[4][0].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[4][0].getBlocks().get(0).block.equals("DOME")) {
                        image40.setImage(level1);
                    } else {
                        image40.setImage(dome);
                    }
                } else {
                    switch (board[4][0].getBlocks().get(0).color) {
                        case "BLUE":
                            image40.setImage(blue);
                            break;
                        case "BROWN":
                            image40.setImage(brown);
                            break;
                        case "White":
                            image40.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[4][0].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[4][0].getBlocks().get(1).block.equals("DOME")) {
                        image40.setImage(level2);
                    } else {
                        image40.setImage(domeLevel1);
                    }
                } else {
                    switch (board[4][0].getBlocks().get(1).color) {
                        case "BLUE":
                            image40.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image40.setImage(brownLevel1);
                            break;
                        case "White":
                            image40.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[4][0].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[4][0].getBlocks().get(2).block.equals("DOME")) {
                        image40.setImage(level3);
                    } else {
                        image40.setImage(domeLevel2);
                    }
                } else {
                    switch (board[4][0].getBlocks().get(2).color) {
                        case "BLUE":
                            image40.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image40.setImage(brownLevel2);
                            break;
                        case "White":
                            image40.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[4][0].getBlocks().get(3).block.equals("WORKER")) {
                    image40.setImage(domeLevel3);
                } else {
                    switch (board[4][0].getBlocks().get(3).color) {
                        case "BLUE":
                            image40.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image40.setImage(brownLevel3);
                            break;
                        case "White":
                            image40.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image40.setVisible(true);

        switch (board[4][1].getBlocks().size()) {
            case 1:
                if (!board[4][1].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[4][1].getBlocks().get(0).block.equals("DOME")) {
                        image41.setImage(level1);
                    } else {
                        image41.setImage(dome);
                    }
                } else {
                    switch (board[4][1].getBlocks().get(0).color) {
                        case "BLUE":
                            image41.setImage(blue);
                            break;
                        case "BROWN":
                            image41.setImage(brown);
                            break;
                        case "White":
                            image41.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[4][1].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[4][1].getBlocks().get(1).block.equals("DOME")) {
                        image41.setImage(level2);
                    } else {
                        image41.setImage(domeLevel1);
                    }
                } else {
                    switch (board[4][1].getBlocks().get(1).color) {
                        case "BLUE":
                            image41.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image41.setImage(brownLevel1);
                            break;
                        case "White":
                            image41.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[4][1].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[4][1].getBlocks().get(2).block.equals("DOME")) {
                        image41.setImage(level3);
                    } else {
                        image41.setImage(domeLevel2);
                    }
                } else {
                    switch (board[4][1].getBlocks().get(2).color) {
                        case "BLUE":
                            image41.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image41.setImage(brownLevel2);
                            break;
                        case "White":
                            image41.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[4][1].getBlocks().get(3).block.equals("WORKER")) {
                    image41.setImage(domeLevel3);
                } else {
                    switch (board[4][1].getBlocks().get(3).color) {
                        case "BLUE":
                            image41.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image41.setImage(brownLevel3);
                            break;
                        case "White":
                            image41.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image41.setVisible(true);

        switch (board[4][2].getBlocks().size()) {
            case 1:
                if (!board[4][2].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[4][2].getBlocks().get(0).block.equals("DOME")) {
                        image42.setImage(level1);
                    } else {
                        image42.setImage(dome);
                    }
                } else {
                    switch (board[4][2].getBlocks().get(0).color) {
                        case "BLUE":
                            image42.setImage(blue);
                            break;
                        case "BROWN":
                            image42.setImage(brown);
                            break;
                        case "White":
                            image42.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[4][2].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[4][2].getBlocks().get(1).block.equals("DOME")) {
                        image42.setImage(level2);
                    } else {
                        image42.setImage(domeLevel1);
                    }
                } else {
                    switch (board[4][2].getBlocks().get(1).color) {
                        case "BLUE":
                            image42.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image42.setImage(brownLevel1);
                            break;
                        case "White":
                            image42.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[4][2].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[4][2].getBlocks().get(2).block.equals("DOME")) {
                        image42.setImage(level3);
                    } else {
                        image42.setImage(domeLevel2);
                    }
                } else {
                    switch (board[4][2].getBlocks().get(2).color) {
                        case "BLUE":
                            image42.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image42.setImage(brownLevel2);
                            break;
                        case "White":
                            image42.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[4][2].getBlocks().get(3).block.equals("WORKER")) {
                    image42.setImage(domeLevel3);
                } else {
                    switch (board[4][2].getBlocks().get(3).color) {
                        case "BLUE":
                            image42.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image42.setImage(brownLevel3);
                            break;
                        case "White":
                            image42.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image42.setVisible(true);

        switch (board[4][3].getBlocks().size()) {
            case 1:
                if (!board[4][3].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[4][3].getBlocks().get(0).block.equals("DOME")) {
                        image43.setImage(level1);
                    } else {
                        image43.setImage(dome);
                    }
                } else {
                    switch (board[4][3].getBlocks().get(0).color) {
                        case "BLUE":
                            image43.setImage(blue);
                            break;
                        case "BROWN":
                            image43.setImage(brown);
                            break;
                        case "White":
                            image43.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[4][3].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[4][3].getBlocks().get(1).block.equals("DOME")) {
                        image43.setImage(level2);
                    } else {
                        image43.setImage(domeLevel1);
                    }
                } else {
                    switch (board[4][3].getBlocks().get(1).color) {
                        case "BLUE":
                            image43.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image43.setImage(brownLevel1);
                            break;
                        case "White":
                            image43.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[4][3].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[4][3].getBlocks().get(2).block.equals("DOME")) {
                        image43.setImage(level3);
                    } else {
                        image43.setImage(domeLevel2);
                    }
                } else {
                    switch (board[4][3].getBlocks().get(2).color) {
                        case "BLUE":
                            image43.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image43.setImage(brownLevel2);
                            break;
                        case "White":
                            image43.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[4][3].getBlocks().get(3).block.equals("WORKER")) {
                    image43.setImage(domeLevel3);
                } else {
                    switch (board[4][3].getBlocks().get(3).color) {
                        case "BLUE":
                            image43.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image43.setImage(brownLevel3);
                            break;
                        case "White":
                            image43.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image43.setVisible(true);

        switch (board[4][4].getBlocks().size()) {
            case 1:
                if (!board[4][4].getBlocks().get(0).block.equals("WORKER")) {
                    if (!board[4][4].getBlocks().get(0).block.equals("DOME")) {
                        image44.setImage(level1);
                    } else {
                        image44.setImage(dome);
                    }
                } else {
                    switch (board[4][4].getBlocks().get(0).color) {
                        case "BLUE":
                            image44.setImage(blue);
                            break;
                        case "BROWN":
                            image44.setImage(brown);
                            break;
                        case "White":
                            image44.setImage(white);
                            break;
                    }
                }
            case 2:
                if (!board[4][4].getBlocks().get(1).block.equals("WORKER")) {
                    if (!board[4][4].getBlocks().get(1).block.equals("DOME")) {
                        image44.setImage(level2);
                    } else {
                        image44.setImage(domeLevel1);
                    }
                } else {
                    switch (board[4][4].getBlocks().get(1).color) {
                        case "BLUE":
                            image44.setImage(blueLevel1);
                            break;
                        case "BROWN":
                            image44.setImage(brownLevel1);
                            break;
                        case "White":
                            image44.setImage(whiteLevel1);
                            break;
                    }
                }
            case 3:
                if (!board[4][4].getBlocks().get(2).block.equals("WORKER")) {
                    if (!board[4][4].getBlocks().get(2).block.equals("DOME")) {
                        image44.setImage(level3);
                    } else {
                        image44.setImage(domeLevel2);
                    }
                } else {
                    switch (board[4][4].getBlocks().get(2).color) {
                        case "BLUE":
                            image44.setImage(blueLevel2);
                            break;
                        case "BROWN":
                            image44.setImage(brownLevel2);
                            break;
                        case "White":
                            image44.setImage(whiteLevel2);
                            break;
                    }
                }
            case 4:
                if (!board[4][4].getBlocks().get(3).block.equals("WORKER")) {
                    image44.setImage(domeLevel3);
                } else {
                    switch (board[4][4].getBlocks().get(3).color) {
                        case "BLUE":
                            image44.setImage(blueLevel3);
                            break;
                        case "BROWN":
                            image44.setImage(brownLevel3);
                            break;
                        case "White":
                            image44.setImage(whiteLevel3);
                            break;
                    }
                }
        }
        image44.setVisible(true);
    }

    public static void setController(MainController controller) {
        BoardController.controller = controller;
    }

    @FXML
    private void initialize() {


    }

}
