package it.polimi.ingsw.view.GUI;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class BoardController implements Controller {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button button0;
    @FXML
    private VBox chooseBox;

    @FXML
    private HBox hBox0, hBox1, hBox2;

    @FXML
    private Pane pane00, pane01, pane02, pane03, pane04, pane10, pane11, pane12, pane13, pane14, pane20, pane21, pane22,
            pane23, pane24, pane30, pane31, pane32, pane33, pane34, pane40, pane41, pane42, pane43, pane44;

    @FXML
    private ImageView light00, light01, light02, light03, light04, light10, light11, light12, light13, light14, light20,
            light21, light22, light23, light24, light30, light31, light32, light33, light34, light40, light41, light42,
            light43, light44;

    @FXML
    private ImageView image00, image01, image02, image03, image04, image10, image11, image12, image13, image14, image20,
            image21, image22, image23, image24, image30, image31, image32, image33, image34, image40, image41, image42,
            image43, image44;

    @FXML
    private Label player0, player1, player2, godName0, godName1, godName2, worker0, worker1, worker2, turn0, turn1,
            turn2;

    @FXML
    private ImageView god0, god1, god2, godX, choice0, choice1, choice2, title, backG0, backG1, backG2, backGround;

    private static MainController controller = new MainController();
    private static ImageView[][] images = new ImageView[5][5];
    private static Pane[][] panes = new Pane[5][5];
    private static ImageView[][] lights = new ImageView[5][5];
    private String[] players = new String[3];

    private Image blue = new Image("GraphicSrc/Buildings/blue.gif"),
            blueLevel1 = new Image("GraphicSrc/Buildings/blueLevel1.gif"),
            blueLevel2 = new Image("GraphicSrc/Buildings/blueLevel2.gif"),
            blueLevel3 = new Image("GraphicSrc/Buildings/blueLevel3.gif"),
            brown = new Image("GraphicSrc/Buildings/brown.gif"),
            brownLevel1 = new Image("GraphicSrc/Buildings/brownLevel1.gif"),
            brownLevel2 = new Image("GraphicSrc/Buildings/brownLevel2.gif"),
            brownLevel3 = new Image("GraphicSrc/Buildings/brownLevel3.gif"),
            dome = new Image("GraphicSrc/Buildings/dome.gif"),
            domeLevel1 = new Image("GraphicSrc/Buildings/domeLevel1.gif"),
            domeLevel2 = new Image("GraphicSrc/Buildings/domeLevel2.gif"),
            domeLevel3 = new Image("GraphicSrc/Buildings/domeLevel3.gif"),
            level1 = new Image("GraphicSrc/Buildings/level1.gif"),
            level2 = new Image("GraphicSrc/Buildings/level2.gif"),
            level3 = new Image("GraphicSrc/Buildings/level3.gif"), white = new Image("GraphicSrc/Buildings/white.gif"),
            whiteLevel1 = new Image("GraphicSrc/Buildings/whiteLevel1.gif"),
            whiteLevel2 = new Image("GraphicSrc/Buildings/whiteLevel2.gif"),
            whiteLevel3 = new Image("GraphicSrc/Buildings/whiteLevel3.gif"),
            backGround0 = new Image("GraphicSrc/Plyayer/ContainerLOW.gif"),
            backGround1 = new Image("GraphicSrc/Plyayer/Container.gif"),
            chooseWorker = new Image("GraphicSrc/ExtraAssets/Workers.gif"),
            chooseAction = new Image("GraphicSrc/ExtraAssets/Actions.gif"),
            color0 = new Image("GraphicSrc/ExtraAssets/BLUE.gif"),
            color1 = new Image("GraphicSrc/ExtraAssets/BROWN.gif"),
            color2 = new Image("GraphicSrc/ExtraAssets/WHITE.gif"), move = new Image("GraphicSrc/ExtraAssets/MOVE.gif"),
            build = new Image("GraphicSrc/ExtraAssets/BUILD.gif"),
            buildDome = new Image("GraphicSrc/ExtraAssets/BuildDome.gif"),
            end = new Image("GraphicSrc/ExtraAssets/EndTurn.gif"), apollo = new Image("/GraphicSrc/Gods/Apollo.jpg"),
            artemis = new Image("/GraphicSrc/Gods/Artemis.jpg"), athena = new Image("/GraphicSrc/Gods/Athena.jpg"),
            atlas = new Image("/GraphicSrc/Gods/Atlas.jpg"), demeter = new Image("/GraphicSrc/Gods/Detemer.jpg"),
            hephaestus = new Image("/GraphicSrc/Gods/Hephaestus.jpg"),
            minotaur = new Image("/GraphicSrc/Gods/Minotaur.jpg"), pan = new Image("/GraphicSrc/Gods/Pan.jpg"),
            prometheus = new Image("/GraphicSrc/Gods/Prometheus.jpg"), hera = new Image("/GraphicSrc/Gods/Hera.jpg"),
            medusa = new Image("/GraphicSrc/Gods/Medusa.jpg"), persephone = new Image("/GraphicSrc/Gods/Persephone.jpg"),
            poseidon = new Image("/GraphicSrc/Gods/Poseidon.jpg"), zeus = new Image("/GraphicSrc/Gods/Zeus.jpg"),
            grid = new Image("/GraphicSrc/GridHighlight.jpg"), lose = new Image("/GraphicSrc/LOSE.gif"), win = new Image(("/GraphicSrc/WIN.gif"));

    private int[][][] count = new int[5][5][4];
    private int position;
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);

    @FXML
    public void chooseChoice() {
        // System.out.println(position);
        if (position == 26) {
            if (choice0.isPressed()) {
                controller.send("BLUE");
            } else if (choice1.isPressed()) {
                controller.send("BROWN");
            } else if (choice2.isPressed()) {
                controller.send("WHITE");
            }
        } else if (position < 25 && position >= 0) {
            int[] toSend = new int[2];
            toSend[0] = position;
            if (choice0.isPressed()) {
                toSend[1] = 0;
            } else if (choice1.isPressed()) {
                toSend[1] = 1;
            } else if (choice2.isPressed()) {
                toSend[1] = 2;
            }
            controller.send(new Gson().toJson(toSend));
        }
        closeConsequence();
    }

    @FXML
    public void showConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        String userData = (String) node.getUserData();
        if (position >= 0 && position <= 25) {
            Cell[][] cells = controller.getBoard();
            if (userData.equals("0")) {
                System.out.println("0");
                switch (worker0.getText()) {
                    case "Worker:BLUE":
                        lights[position / 5][position % 5].setImage(blue);
                        break;
                    case "Worker:BROWN":
                        lights[position / 5][position % 5].setImage(brown);
                        break;
                    case "Worker:WHITE":
                        lights[position / 5][position % 5].setImage(white);
                        break;
                    default:
                        break;
                }
            } else if (userData.equals("1")) {
                System.out.println("1");
                int size = cells[position / 5][position % 5].getBlocks().size();
                if (size > 0 && cells[position / 5][position % 5].getBlocks().get(size - 1).typeBlock.equals("WORKER")) {
                    size--;
                }
                switch (size) {
                    case 0:
                        lights[position / 5][position % 5].setImage(level1);
                        break;
                    case 1:
                        lights[position / 5][position % 5].setImage(level2);
                        break;
                    case 2:
                        lights[position / 5][position % 5].setImage(level3);
                        break;
                    case 3:
                        lights[position / 5][position % 5].setImage(domeLevel3);
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("2");
                int size = cells[position / 5][position % 5].getBlocks().size();
                switch (size) {
                    case 0:
                        lights[position / 5][position % 5].setImage(dome);
                        break;
                    case 1:
                        lights[position / 5][position % 5].setImage(domeLevel1);
                        break;
                    case 2:
                        lights[position / 5][position % 5].setImage(domeLevel2);
                        break;
                    case 3:
                        lights[position / 5][position % 5].setImage(domeLevel3);
                        break;
                    default:
                        break;
                }
            }
            lights[position / 5][position % 5].setVisible(true);
        }

    }

    @FXML
    public void closeConsequence() {
        if (position >= 0 && position <= 25) {
            lights[position / 5][position % 5].setImage(grid);
            lights[position / 5][position % 5].setVisible(false);
        }
    }

    private void resetAction() {
        choice0.setVisible(false);
        choice0.setDisable(true);
        choice1.setVisible(false);
        choice1.setDisable(true);
        choice2.setVisible(false);
        choice2.setDisable(true);
        title.setVisible(false);
    }

    @FXML
    public void chooseAction() {
        // System.out.println("clicked");
        for (int i = 0; i < 25; i++) {
            if (panes[i / 5][i % 5].isPressed()) {
                if (count[i / 5][i % 5][0] == 1) {
                    controller.send(String.valueOf(count[i / 5][i % 5][1]));
                    break;
                } else if (count[i / 5][i % 5][0] == 2) {
                    resetAction();
                    position = i;
                    if (count[i / 5][i % 5][1] == 1) {
                        choice0.setImage(move);
                        choice0.setDisable(false);
                        choice0.setVisible(true);
                    }
                    if (count[i / 5][i % 5][2] == 1) {
                        choice1.setImage(build);
                        choice1.setDisable(false);
                        choice1.setVisible(true);
                    }
                    if (count[i / 5][i % 5][3] == 1) {
                        choice2.setImage(buildDome);
                        choice2.setDisable(false);
                        choice2.setVisible(true);
                    }
                    chooseBox.setVisible(true);
                    chooseBox.setDisable(false);
                    title.setImage(chooseAction);
                    title.setVisible(true);
                }
            }
        }
        if (button0.isPressed()) {
            controller.send(null);
        }
    }

    @Override
    public void reSet() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                panes[i][j].setDisable(true);
                images[i][j].setDisable(true);
                images[i][j].setVisible(false);
                lights[i][j].setDisable(true);
                lights[i][j].setVisible(false);
                count[i][j][0] = 0;
                count[i][j][1] = 0;
                count[i][j][2] = 0;
                count[i][j][3] = 0;
            }
        }
        position = -1;
        resetAction();
        load();


    }


    private void load() {

        setPlayerInfo();
        setBoard();

        if (controller.getPlayer().equals(controller.getCurrentPlayer())) {
            //System.out.println("11");
            setAction();
        }
    }

    private void setPlayerInfo() {
        Platform.runLater(() -> {
            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                // System.out.println(e.username + "///////" + controller.getPlayer());
                if (e.username.equals(players[0])) {
                    worker0.setText("Worker:" + e.color);
                    if (e.status.equals("LOSE")) {
                        god0.setImage(lose);
                    } else if (e.status.equals("WIN")) {
                        god0.setImage(win);
                    }
                    if (controller.getCurrentPlayer().equals(e.username)) {
                        turn0.setVisible(true);
                        button0.setDisable(false);
                        button0.setVisible(true);
                        backG0.setImage(backGround1);
                    } else {
                        button0.setDisable(true);
                        button0.setVisible(false);
                        backG0.setImage(backGround0);
                        turn0.setVisible(false);
                    }
                } else if (e.username.equals(players[1])) {
                    worker1.setText("Worker:" + e.color);
                    if (e.status.equals("LOSE")) {
                        god1.setImage(lose);
                    } else if (e.status.equals("WIN")) {
                        god1.setImage(win);
                    }
                    if (controller.getCurrentPlayer().equals(e.username)) {
                        backG1.setImage(backGround1);
                        turn1.setVisible(true);
                    } else {
                        turn1.setVisible(false);
                        backG1.setImage(backGround0);
                    }
                } else {
                    worker2.setText("Worker:" + e.color);
                    if (e.status.equals("LOSE")) {
                        god2.setImage(lose);
                    } else if (e.status.equals("WIN")) {
                        god2.setImage(win);
                    }
                    if (controller.getCurrentPlayer().equals(e.username)) {
                        backG2.setImage(backGround1);
                        turn2.setVisible(true);
                    } else {
                        turn2.setVisible(false);
                        backG2.setImage(backGround0);
                    }
                }
            });
        });
    }

    private void setBoard() {
        Cell[][] board = controller.getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                images[i][j].setVisible(true);
                try {
                    switch (board[i][j].getBlocks().size()) {
                        case 0:
                            images[i][j].setVisible(false);
                        case 1:
                            if (!board[i][j].getBlocks().get(0).typeBlock.equals("WORKER")) {
                                if (!board[i][j].getBlocks().get(0).typeBlock.equals("DOME")) {
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
                                    case "WHITE":
                                        images[i][j].setImage(white);
                                        break;
                                }
                            }
                            break;
                        case 2:
                            if (!board[i][j].getBlocks().get(1).typeBlock.equals("WORKER")) {
                                if (!board[i][j].getBlocks().get(1).typeBlock.equals("DOME")) {
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
                                    case "WHITE":
                                        images[i][j].setImage(whiteLevel1);
                                        break;
                                }
                            }
                            break;
                        case 3:
                            if (!board[i][j].getBlocks().get(2).typeBlock.equals("WORKER")) {
                                if (!board[i][j].getBlocks().get(2).typeBlock.equals("DOME")) {
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
                                    case "WHITE":
                                        images[i][j].setImage(whiteLevel2);
                                        break;
                                }
                            }
                            break;
                        case 4:
                            if (!board[i][j].getBlocks().get(3).typeBlock.equals("WORKER")) {
                                images[i][j].setImage(domeLevel3);
                            } else {
                                switch (board[i][j].getBlocks().get(3).color) {
                                    case "BLUE":
                                        images[i][j].setImage(blueLevel3);
                                        break;
                                    case "BROWN":
                                        images[i][j].setImage(brownLevel3);
                                        break;
                                    case "WHITE":
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

    private void reSetLight() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                lights[i][j].setVisible(true);
            }
        }
    }

    private void setAction() {
        reSetLight();
        List<Command> listCommand = controller.getCommand();
        listCommand.forEach(e -> {
            //System.out.println(e.funcData + e.funcName);
            if (e.funcName.equals("setColor")) {
                // System.out.println("bbbbbbbb");
                position = 26;
                title.setVisible(true);
                title.setImage(chooseWorker);
                if (e.funcData.equals("BLUE")) {
                    choice0.setImage(color0);
                    choice0.setDisable(false);
                    choice0.setVisible(true);
                } else if (e.funcData.equals("BROWN")) {
                    choice1.setImage(color1);
                    choice1.setDisable(false);
                    choice1.setVisible(true);
                } else if (e.funcData.equals("WHITE")) {
                    choice2.setImage(color2);
                    choice2.setDisable(false);
                    choice2.setVisible(true);
                }
            } else if (e.funcName.equals("setWorkers")) {
                // System.out.println("bbbbbcccbb");
                int i = Integer.parseInt(e.funcData);
                panes[i / 5][i % 5].setDisable(false);
                panes[i / 5][i % 5].setVisible(true);
                count[i / 5][i % 5][0] = 1;
                count[i / 5][i % 5][1] = i;
                lights[i / 5][i % 5].setVisible(false);
            } else if (e.funcName.equals("chooseWorker")) {
                // System.out.println("bbbbbcccdddddddbb");
                int i = Integer.parseInt(e.funcData);
                panes[i / 5][i % 5].setDisable(false);
                panes[i / 5][i % 5].setVisible(true);
                count[i / 5][i % 5][0] = 1;
                count[i / 5][i % 5][1] = i;
                lights[i / 5][i % 5].setVisible(false);
            } else if (e.funcName.equals("chooseAction")) {
                // System.out.println("aaaaaaaaaaaa");
                if (e.funcData == null) {
                    button0.setDisable(false);
                    button0.setVisible(true);
                } else {
                    String data = e.funcData;
                    int[] i = new Gson().fromJson(e.funcData, int[].class);
                    // System.out.println(i[0] + i[1]);
                    panes[i[0] / 5][i[0] % 5].setDisable(false);
                    panes[i[0] / 5][i[0] % 5].setVisible(true);
                    count[i[0] / 5][i[0] % 5][0] = 2;
                    count[i[0] / 5][i[0] % 5][i[1] + 1] = 1;
                    lights[i[0] / 5][i[0] % 5].setVisible(false);
                }
            }
        });
    }

    private void loadGod(String god, ImageView image) {
        switch (god) {
            case "APOLLO":
                image.setImage(apollo);
                image.setVisible(true);
                break;
            case "ARTEMIS":
                image.setImage(artemis);
                image.setVisible(true);
                break;
            case "ATHENA":
                image.setImage(athena);
                image.setVisible(true);
                break;
            case "ATLAS":
                image.setImage(atlas);
                image.setVisible(true);
                break;
            case "DEMETER":
                image.setImage(demeter);
                image.setVisible(true);
                break;
            case "HEPHAESTUS":
                image.setImage(hephaestus);
                image.setVisible(true);
                break;
            case "MINOTAUR":
                image.setImage(minotaur);
                image.setVisible(true);
                break;
            case "PAN":
                image.setImage(pan);
                image.setVisible(true);
                break;
            case "PROMETHEUS":
                image.setImage(prometheus);
                image.setVisible(true);
                break;
            case "HERA":
                image.setImage(hera);
                image.setVisible(true);
                break;
            case "MEDUSA":
                image.setImage(medusa);
                image.setVisible(true);
                break;
            case "PERSEPHONE":
                image.setImage(persephone);
                image.setVisible(true);
                break;
            case "POSEIDON":
                image.setImage(poseidon);
                image.setVisible(true);
                break;
            case "ZEUS":
                image.setImage(zeus);
                image.setVisible(true);
                break;
        }

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

        hBox0.setVisible(false);
        hBox1.setVisible(false);
        hBox2.setVisible(false);

        chooseBox.setVisible(true);
        chooseBox.setDisable(false);
        godX.setVisible(false);

        List<Player> listPlayer = controller.getUserInfo();

        listPlayer.stream().forEach(e -> {
            // System.out.println(e.username + "///////" + controller.getPlayer());
            if (e.username.equals(controller.getPlayer())) {
                // System.out.println("1");
                players[0] = e.username;
                godName0.setText("God:" + e.god);
                loadGod(e.god, god0);
                hBox0.setVisible(true);
                player0.setText("Player: " + e.username);
                turn0.setText("Your Turn");
            } else if (!hBox1.isVisible()) {
                // System.out.println("2");
                players[1] = e.username;
                godName1.setText("God:" + e.god);
                loadGod(e.god, god1);
                hBox1.setVisible(true);
                player1.setText("Player: " + e.username);
                turn1.setText("Current Player");
            } else {
                // System.out.println("3");
                players[2] = e.username;
                godName2.setText("God:" + e.god);
                loadGod(e.god, god2);
                hBox2.setVisible(true);
                player2.setText("Player: " + e.username);
                turn2.setText("Current Player");
            }
        });

        reSet();

        backGround.fitWidthProperty().bind(width);
        backGround.fitHeightProperty().bind(height);
        gridPane.prefWidthProperty().bind(width.multiply(0.4102));
        gridPane.prefHeightProperty().bind(height.multiply(0.7292));
        gridPane.layoutXProperty().bind(width.multiply(0.2953));
        gridPane.layoutYProperty().bind(height.multiply(0.1389));
        chooseBox.layoutXProperty().bind(width.subtract(280));
        godX.fitWidthProperty().bind(width.multiply(0.2734));
        godX.fitHeightProperty().bind(height.multiply(0.8333));
        godX.layoutXProperty().bind(width.multiply(0.36));
        godX.layoutYProperty().bind(height.multiply(0.0833));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                panes[i][j].prefWidthProperty().bind(gridPane.prefWidthProperty().divide(5));
                panes[i][j].prefHeightProperty().bind(gridPane.prefHeightProperty().divide(5));
                images[i][j].fitWidthProperty().bind(gridPane.prefWidthProperty().divide(5));
                images[i][j].fitHeightProperty().bind(gridPane.prefHeightProperty().divide(5));
                lights[i][j].fitWidthProperty().bind(gridPane.prefWidthProperty().divide(5));
                lights[i][j].fitHeightProperty().bind(gridPane.prefHeightProperty().divide(5));
            }
        }
    }


    @Override
    public void setWidth(double width) {
        this.width.set(width * 1.01);
    }

    @Override
    public void setHeight(double height) {
        this.height.set(height * 1.01);
    }

    @FXML
    public void amplification(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        godX.setImage(node.getImage());
        godX.setVisible(true);
    }

    @FXML
    public void recovery(MouseEvent event) {
        godX.setVisible(false);
    }

    @FXML
    public void quit() {
        controller.quit();
    }

}
