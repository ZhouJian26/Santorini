package it.polimi.ingsw.view.GUI;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Board implements Controller {
    @FXML
    private GridPane gridPane;

    @FXML
    private Pane player0, player1, player2, actionBox;

    private ImageView[][][] boardImages = new ImageView[5][5][3];
    private static MainController controller = new MainController();
    private Pane[] players = new Pane[3];
    private Cell[][] board = new Cell[5][5];
    private Swap[][] swaps = new Swap[5][5];
    private Build[][][] builds = new Build[5][5][2];
    private Pane[][] map = new Pane[5][5];
    private Lighting lighting = new Lighting();
    private Glow glow = new Glow();
    private int count = 3;

    public static void setController(MainController controller) {
        Board.controller = controller;
    }


    @FXML
    public void initialize() {
        for (int i = 0; i < 25; i++) {
            //System.out.println(i);
            boardImages[i / 5][i % 5][0] = (ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(0);
            boardImages[i / 5][i % 5][1] = (ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(1);
            boardImages[i / 5][i % 5][2] = (ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(2);
            map[i / 5][i % 5] = (Pane) gridPane.getChildren().get(i);
        }
        players[0] = player0;
        players[1] = player1;
        players[2] = player2;
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        Platform.runLater(() -> {
            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                if (e.username.equals(controller.getPlayer())) {
                    player0.setVisible(true);
                    ((Label) player0.getChildren().get(3)).setText(e.username);
                    ((ImageView) player0.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(e.god.toUpperCase() + "_PLAYER")));
                    ((ImageView) player0.getChildren().get(2)).setVisible(false);
                } else if (!player1.isVisible()) {
                    player1.setVisible(true);
                    ((Label) player1.getChildren().get(3)).setText(e.username);
                    ((ImageView) player1.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(e.god.toUpperCase() + "_PLAYER")));
                    ((ImageView) player1.getChildren().get(2)).setVisible(false);
                } else {
                    player2.setVisible(true);
                    ((Label) player2.getChildren().get(3)).setText(e.username);
                    ((ImageView) player2.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(e.god.toUpperCase() + "_PLAYER")));
                    ((ImageView) player2.getChildren().get(2)).setVisible(false);
                }
            });
        });

        ((ImageView) actionBox.getChildren().get(0)).setImage(new Image(ImageEnum.getUrl("BLUE")));
        ((ImageView) actionBox.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl("BROWN")));
        ((ImageView) actionBox.getChildren().get(2)).setImage(new Image(ImageEnum.getUrl("WHITE")));
        ((ImageView) actionBox.getChildren().get(0)).setVisible(false);
        ((ImageView) actionBox.getChildren().get(1)).setVisible(false);
        ((ImageView) actionBox.getChildren().get(2)).setVisible(false);
        ((ImageView) actionBox.getChildren().get(3)).setVisible(false);

        reSet();
    }

    public void showConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int[] i = new Gson().fromJson(node.getUserData().toString(), int[].class);
        //System.out.println("sadfqewr" + i.toString());
        switch (i[1]) {
            case 0:
                List<Integer> x1 = swaps[i[0] / 5][i[0] % 5].x1;
                List<Integer> x2 = swaps[i[0] / 5][i[0] % 5].x2;
                List<Integer> y1 = swaps[i[0] / 5][i[0] % 5].y1;
                List<Integer> y2 = swaps[i[0] / 5][i[0] % 5].y2;
                Image x = boardImages[x1.get(0)][x1.get(1)][1].getImage();
                if (!(y2.get(0) == y1.get(0) && y1.get(1) == y2.get(1))) {
                    boardImages[y2.get(0)][y2.get(1)][1].setImage(boardImages[y1.get(0)][y1.get(1)][1].getImage());
                    boardImages[y2.get(0)][y2.get(1)][1].setOpacity(0.7);
                    boardImages[y2.get(0)][y2.get(1)][1].setVisible(true);
                }
                boardImages[x2.get(0)][x2.get(1)][1].setImage(x);
                boardImages[x2.get(0)][x2.get(1)][1].setOpacity(0.7);
                boardImages[x2.get(0)][x2.get(1)][1].setVisible(true);
                break;
            case 1:
                List<Integer> position = builds[i[0] / 5][i[0] % 5][0].position;
                boardImages[position.get(0)][position.get(1)][0].setImage(new Image(ImageEnum.getUrl(builds[i[0] / 5][i[0] % 5][0].block.toUpperCase())));
                boardImages[position.get(0)][position.get(1)][0].setOpacity(0.7);
                boardImages[position.get(0)][position.get(1)][0].setVisible(true);
                break;
            case 2:
                List<Integer> position1 = builds[i[0] / 5][i[0] % 5][1].position;
                boardImages[position1.get(0)][position1.get(1)][1].setImage(new Image(ImageEnum.getUrl(builds[i[0] / 5][i[0] % 5][1].block.toUpperCase())));
                boardImages[position1.get(0)][position1.get(1)][1].setOpacity(0.7);
                boardImages[position1.get(0)][position1.get(1)][1].setVisible(true);
                break;
        }
    }

    public void closeConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int[] i = new Gson().fromJson(node.getUserData().toString(), int[].class);
        //System.out.println("asfdqwgrq" + i[1]);
        switch (i[1]) {
            case 0:
                List<Integer> x1 = swaps[i[0] / 5][i[0] % 5].x1;
                List<Integer> x2 = swaps[i[0] / 5][i[0] % 5].x2;
                List<Integer> y1 = swaps[i[0] / 5][i[0] % 5].y1;
                List<Integer> y2 = swaps[i[0] / 5][i[0] % 5].y2;
                boardImages[x2.get(0)][x2.get(1)][1].setVisible(false);
                Image x = boardImages[x2.get(0)][x2.get(1)][1].getImage();
                if (!(y2.get(0) == y1.get(0) && y1.get(1) == y2.get(1))) {
                    boardImages[y1.get(0)][y1.get(1)][1].setImage(boardImages[y2.get(0)][y2.get(1)][1].getImage());
                    boardImages[y2.get(0)][y2.get(1)][1].setOpacity(1);
                    boardImages[y1.get(0)][y1.get(1)][1].setVisible(true);
                    boardImages[y2.get(0)][y2.get(1)][1].setVisible(false);
                }
                boardImages[x1.get(0)][x1.get(1)][1].setImage(x);
                boardImages[x2.get(0)][x2.get(1)][1].setOpacity(1);
                boardImages[x1.get(0)][x1.get(1)][1].setVisible(true);
                break;

            case 1:
                List<Integer> position = builds[i[0] / 5][i[0] % 5][0].position;
                boardImages[position.get(0)][position.get(1)][0].setOpacity(1);
                if (board[position.get(0)][position.get(1)].getBlocks() == null || board[position.get(0)][position.get(1)].getBlocks().size() == 0) {
                    boardImages[position.get(0)][position.get(1)][0].setVisible(false);
                } else {
                    boardImages[position.get(0)][position.get(1)][0].setImage(new Image(ImageEnum.getUrl(board[position.get(0)][position.get(1)].getBlocks().get(board[position.get(0)][position.get(1)].getBlocks().size() - 1).typeBlock.toUpperCase())));
                    boardImages[position.get(0)][position.get(1)][0].setVisible(true);
                }

                break;
            case 2:
                List<Integer> position1 = builds[i[0] / 5][i[0] % 5][1].position;
                boardImages[position1.get(0)][position1.get(1)][1].setVisible(false);
                boardImages[position1.get(0)][position1.get(1)][1].setOpacity(1);
                break;
        }
    }


    @FXML
    public void chooseColor(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        controller.send(node.getUserData().toString());
    }

    @FXML
    public void chooseAction(MouseEvent event) {
        //System.out.println("1");
        ImageView node = (ImageView) event.getSource();
        node.setDisable(true);
        String string = node.getUserData().toString();
        controller.send(string);
    }

    @FXML
    public void chooseCell(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int i = new Gson().fromJson(node.getUserData().toString(), int.class);
        if (swaps[i / 5][i % 5] != null) {
            ((ImageView) actionBox.getChildren().get(0)).setDisable(false);
            ((ImageView) actionBox.getChildren().get(0)).setEffect(null);
            ((ImageView) actionBox.getChildren().get(0)).setUserData(new Gson().toJson(new int[]{i, 0}));
            ((ImageView) actionBox.getChildren().get(0)).setOnMouseClicked(event1 -> {
                closeConsequence(event1);
                chooseAction(event1);
            });
        } else {
            ((ImageView) actionBox.getChildren().get(0)).setDisable(true);
            ((ImageView) actionBox.getChildren().get(0)).setEffect(lighting);
        }
        for (int j = 1; j < 3; j++) {
            if (builds[i / 5][i % 5][j - 1] != null) {
                ((ImageView) actionBox.getChildren().get(j)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(j)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(j)).setUserData(new Gson().toJson(new int[]{i, j}));
                ((ImageView) actionBox.getChildren().get(j)).setOnMouseClicked(event1 -> {
                    closeConsequence(event1);
                    chooseAction(event1);
                });
            } else {
                ((ImageView) actionBox.getChildren().get(j)).setDisable(true);
                ((ImageView) actionBox.getChildren().get(j)).setEffect(lighting);
            }
        }
    }

    private void setPlayerInfo() {

        Platform.runLater(() -> {
            Arrays.stream(players).forEach(e -> {
                Label name = ((Label) e.getChildren().get(3));
                if (name.getText().equals(controller.getCurrentPlayer())) {
                    ((ImageView) e.getChildren().get(0)).setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl("PODIUM_GOLD"))));
                    e.setEffect(glow);
                } else {
                    ((ImageView) e.getChildren().get(0)).setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl("PODIUM"))));
                    Lighting lighting = new Lighting();
                    e.setEffect(lighting);
                }
            });
            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                if (e.status.equals("LOSE") || e.status.equals("WIN")) {
                    String state = e.status;
                    ((ImageView) Arrays.stream(players).filter(e1 -> ((Label) e1.getChildren().get(3)).getText().equals(e.username)).collect(Collectors.toList())
                            .get(0).getChildren().get(2)).setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl(state))));
                }
            });
        });
    }

    private void setBoard() {
        //System.out.println("2");
        Platform.runLater(() -> {
            Cell[][] map = controller.getBoard();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!new Gson().toJson(board[i][j]).equals(new Gson().toJson(map[i][j]))) {
                        board[i][j] = map[i][j];
                        List<Block> blocks = map[i][j].getBlocks();
                        int size = blocks.size();

                        boardImages[i][j][0].setVisible(false);
                        boardImages[i][j][1].setVisible(false);
                        if (size != 0) {
                            if (blocks.get(size - 1).typeBlock.equals("WORKER")) {
                                boardImages[i][j][1].setImage(new Image(ImageEnum.getUrl(blocks.get(size - 1).color.toUpperCase())));
                                boardImages[i][j][1].setVisible(true);
                                size--;
                            } else if (blocks.get(size - 1).typeBlock.equals("DOME")) {
                                boardImages[i][j][1].setImage(new Image(ImageEnum.getUrl(blocks.get(size - 1).typeBlock.toUpperCase())));
                                boardImages[i][j][1].setVisible(true);
                                size--;
                            }
                            if (size != 0) {
                                boardImages[i][j][0].setImage(new Image(ImageEnum.getUrl(blocks.get(size - 1).typeBlock.toUpperCase())));
                                boardImages[i][j][0].setVisible(true);
                            }
                        }
                    }
                }
            }
        });
    }

    private void setAction() {
        //System.out.println("3");
        Swap[][] swaps1 = new Swap[5][5];
        Build[][][] builds1 = new Build[5][5][2];
        //System.out.println(controller.getGamePhase());
        if (controller.getGamePhase().equals("SET_COLOR")) {
            setColor();
        } else if (controller.getGamePhase().equals("SET_WORKERS")) {
            setWorker();
        } else {
            List<Command> listCommand = controller.getCommand();
            listCommand.stream().forEach(e -> {
                if (e.funcName.equals("chooseWorker")) {
                    int i = Integer.parseInt(e.funcData);
                    boardImages[i / 5][i % 5][2].setOpacity(0);
                    boardImages[i / 5][i % 5][2].setDisable(false);
                    boardImages[i / 5][i % 5][2].setUserData(i);
                    boardImages[i / 5][i % 5][2].setOnMouseClicked(e1 -> chooseAction(e1));
                } else if (e.funcName.equals("chooseAction")) {
                    if (e.funcData == null) {
                        actionBox.getChildren().get(3).setDisable(false);
                        actionBox.getChildren().get(3).setEffect(null);
                        actionBox.getChildren().get(3).setUserData(null);
                        actionBox.getChildren().get(3).setOnMouseClicked(e1 -> chooseAction(e1));
                    } else {
                        String data = e.funcData;
                        int[] i = new Gson().fromJson(e.funcData, int[].class);
                        // //System.out.println(i[0] + i[1]);
                        boardImages[i[0] / 5][i[0] % 5][2].setOpacity(0);
                        boardImages[i[0] / 5][i[0] % 5][2].setDisable(false);
                        boardImages[i[0] / 5][i[0] % 5][2].setUserData(i[0]);
                        boardImages[i[0] / 5][i[0] % 5][2].setOnMouseClicked(e1 -> chooseCell(e1));
                        switch (i[1]) {
                            case 0:
                                swaps1[i[0] / 5][i[0] % 5] = new Gson().fromJson(e.info, Swap.class);
                                break;
                            case 1:
                                builds1[i[0] / 5][i[0] % 5][0] = new Gson().fromJson(e.info, Build.class);
                                break;
                            case 2:
                                builds1[i[0] / 5][i[0] % 5][1] = new Gson().fromJson(e.info, Build.class);
                                break;
                        }
                    }
                }
            });
        }
        swaps = swaps1;
        builds = builds1;
    }

    private void setColor() {
        count = 2;
        //System.out.println("4");
        Lighting lighting = new Lighting();
        List<Command> listCommand = controller.getCommand();

        ((ImageView) actionBox.getChildren().get(0)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(1)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(2)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(3)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(0)).setVisible(false);
        ((ImageView) actionBox.getChildren().get(1)).setVisible(false);
        ((ImageView) actionBox.getChildren().get(2)).setVisible(false);

        listCommand.stream().forEach(e -> {

            if (e.funcData.equals("BLUE")) {
                ((ImageView) actionBox.getChildren().get(0)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(0)).setVisible(true);
                ((ImageView) actionBox.getChildren().get(0)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(0)).setUserData("BLUE");
                ((ImageView) actionBox.getChildren().get(0)).setOnMouseClicked(e1 -> chooseColor(e1));
            } else if (e.funcData.equals("BROWN")) {
                ((ImageView) actionBox.getChildren().get(1)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(1)).setVisible(true);
                ((ImageView) actionBox.getChildren().get(1)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(1)).setUserData("BROWN");
                ((ImageView) actionBox.getChildren().get(1)).setOnMouseClicked(e1 -> chooseColor(e1));
            } else if (e.funcData.equals("WHITE")) {
                ((ImageView) actionBox.getChildren().get(2)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(2)).setVisible(true);
                ((ImageView) actionBox.getChildren().get(2)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(2)).setUserData("WHITE");
                ((ImageView) actionBox.getChildren().get(2)).setOnMouseClicked(e1 -> chooseColor(e1));
            }

        });
    }

    private void setWorker() {
        count--;
        //System.out.println("5");
        List<Command> listCommand = controller.getCommand();
        listCommand.stream().forEach(e -> {
            //System.out.println("6");
            int i = Integer.parseInt(e.funcData);
            boardImages[i / 5][i % 5][2].setOpacity(0);
            boardImages[i / 5][i % 5][2].setDisable(false);
            boardImages[i / 5][i % 5][2].setUserData(i);
            boardImages[i / 5][i % 5][2].setOnMouseClicked(e1 -> chooseAction(e1));
        });
    }

    private void setUp() {
        Platform.runLater(() -> {
            ((ImageView) actionBox.getChildren().get(0)).setImage(new Image(ImageEnum.getUrl("MOVE")));
            ((ImageView) actionBox.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl("BUILD")));
            ((ImageView) actionBox.getChildren().get(2)).setImage(new Image(ImageEnum.getUrl("BUILD_DOME")));
            ((ImageView) actionBox.getChildren().get(3)).setImage(new Image(ImageEnum.getUrl("END_TURN")));
            ((ImageView) actionBox.getChildren().get(0)).setVisible(true);
            ((ImageView) actionBox.getChildren().get(1)).setVisible(true);
            ((ImageView) actionBox.getChildren().get(2)).setVisible(true);
            ((ImageView) actionBox.getChildren().get(3)).setVisible(true);
            ((ImageView) actionBox.getChildren().get(0)).setOnMouseEntered(e -> showConsequence(e));
            ((ImageView) actionBox.getChildren().get(1)).setOnMouseEntered(e -> showConsequence(e));
            ((ImageView) actionBox.getChildren().get(2)).setOnMouseEntered(e -> showConsequence(e));
            ((ImageView) actionBox.getChildren().get(0)).setOnMouseExited(e -> closeConsequence(e));
            ((ImageView) actionBox.getChildren().get(1)).setOnMouseExited(e -> closeConsequence(e));
            ((ImageView) actionBox.getChildren().get(2)).setOnMouseExited(e -> closeConsequence(e));

            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                Arrays.stream(players).forEach(e1 -> {
                    //System.out.println(((Label) e1.getChildren().get(3)).getText());
                    //System.out.println(e.username);
                    if (((Label) e1.getChildren().get(3)).getText().equals(e.username)) {
                        //System.out.println("qwert" + e.color + "//////////" + ImageEnum.getUrl(e.color.toUpperCase()));
                        ((ImageView) e1.getChildren().get(2)).setImage(new Image(ImageEnum.getUrl(e.color.toUpperCase())));
                        ((ImageView) e1.getChildren().get(2)).setVisible(true);
                    }
                });
            });
        });
    }

    @Override
    public void reSet() {
        if (count == 0) {
            setUp();
        }
        Lighting lighting = new Lighting();
        for (int i = 0; i < 4; i++) {
            actionBox.getChildren().get(i).setEffect(lighting);
            actionBox.getChildren().get(i).setDisable(true);
        }
        for (int i = 0; i < 25; i++) {
            boardImages[i / 5][i % 5][2].setVisible(true);
            boardImages[i / 5][i % 5][2].setOpacity(0.4);
        }

        setPlayerInfo();
        setBoard();
        if (controller.getCurrentPlayer().equals(controller.getPlayer())) {
            setAction();
        }
    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void changePage(Boolean status) {
    controller.changeScene();
    }

    @FXML
    private void quit() {
        controller.quit(true);
    }
}
