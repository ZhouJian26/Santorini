package it.polimi.ingsw.view.GUI;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.ChatMessage;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.*;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.socket.Chat;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Board implements Controller, Observer<ChatMessage> {
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane player0, player1, player2, actionBox;
    private Chat chat;
    private ImageView[][][] boardImages = new ImageView[5][5][3];
    private static MainController controller = new MainController();
    private Pane[] players = new Pane[3];
    private Cell[][] board = new Cell[5][5];
    private Swap[][] swaps = new Swap[5][5];
    private Build[][][] builds = new Build[5][5][2];
    private Pane[][] map = new Pane[5][5];
    private Lighting lighting = new Lighting();
    private Glow glow = new Glow();
    private boolean setUp = false;
    private String color;
    @FXML
    private ImageView cloud, god, backGround, door;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField textField;
    @FXML
    private Button send;

    private double x = 6, y = 6;

    public static void setController(MainController controller) {
        Board.controller = controller;
    }

    @FXML
    public void sendMessage() {
        //System.out.println("send");
        String message = textField.getText().trim();
        textField.clear();
        textField.requestFocus();
        if (!message.equals(""))
            chat.sendMessage(message);
    }

    private void setUp() {
        textField.setOnKeyPressed(e -> {
            // System.out.println("tab"+e.getCode().toString());
            if (e.getCode().toString().equals("ENTER")) {
                // System.out.println("3");
                sendMessage();
            }
        });
        backGround.setDisable(false);
        backGround.setOnKeyPressed(e -> {
            System.out.println(e.getCode());
            if (e.getCode().equals("S")) {
                if (!((ImageView) actionBox.getChildren().get(0)).isDisabled()) {
                    MouseEvent event = (MouseEvent) ((ImageView) actionBox.getChildren().get(0)).getOnMouseClicked();
                    chooseAction(event);
                }

            } else if (e.getCode().equals("D")) {
                if (!((ImageView) actionBox.getChildren().get(1)).isDisabled()) {
                    MouseEvent event = (MouseEvent) ((ImageView) actionBox.getChildren().get(1)).getOnMouseClicked();
                    chooseAction(event);
                }
            } else if (e.getCode().equals("A")) {
                if (!((ImageView) actionBox.getChildren().get(2)).isDisabled()) {
                    MouseEvent event = (MouseEvent) ((ImageView) actionBox.getChildren().get(2)).getOnMouseClicked();
                    chooseAction(event);
                }
            }
        });
        listView.setCellFactory(lv -> new ListCell<String>() {
            private final Text text;

            {
                text = new Text();
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(text);
                // bind wrapping width to available size
                text.wrappingWidthProperty().bind(Bindings.createDoubleBinding(() -> {
                    Insets padding = getPadding();
                    return getWidth() - padding.getLeft() - padding.getRight();
                }, widthProperty(), paddingProperty()));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    text.setText(null);
                } else {
                    text.setText(item);
                }
            }
        });
        listView.setVisible(false);
        listView.setStyle("-fx-control-inner-background: rgba(255,255,255,0);");
        listView.setMouseTransparent(true);
        listView.setFocusTraversable(false);
    }

    @FXML
    public void initialize() {
        door.setDisable(false);
        door.setOnMouseClicked(e -> quit());
        textField.setFocusTraversable(false);
        chat = controller.setChat();
        chat.addObservers(this);
        gridPane.setVisible(true);
        god.setVisible(false);
        god.setDisable(true);
        cloud.setVisible(false);
        cloud.setDisable(true);
        send.setOnAction(e -> sendMessage());
        for (int i = 0; i < 25; i++) {
            // System.out.println(i);
            boardImages[i / 5][i % 5][0] = (ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(0);
            boardImages[i / 5][i % 5][1] = (ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(1);
            boardImages[i / 5][i % 5][2] = (ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(2);
            map[i / 5][i % 5] = (Pane) gridPane.getChildren().get(i);
            boardImages[i / 5][i % 5][0].setVisible(false);
            boardImages[i / 5][i % 5][1].setVisible(false);
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
                    ((ImageView) player0.getChildren().get(1))
                            .setImage(new Image(ImageEnum.getUrl(e.god.toUpperCase() + "_PLAYER")));
                    ((ImageView) player0.getChildren().get(1)).setUserData(e.god);
                    ((ImageView) player0.getChildren().get(2)).setVisible(false);
                } else if (!player1.isVisible()) {
                    player1.setVisible(true);
                    ((Label) player1.getChildren().get(3)).setText(e.username);
                    ((ImageView) player1.getChildren().get(1))
                            .setImage(new Image(ImageEnum.getUrl(e.god.toUpperCase() + "_PLAYER")));
                    ((ImageView) player1.getChildren().get(1)).setUserData(e.god);
                    ((ImageView) player1.getChildren().get(2)).setVisible(false);
                } else {
                    player2.setVisible(true);
                    ((Label) player2.getChildren().get(3)).setText(e.username);
                    ((ImageView) player2.getChildren().get(1))
                            .setImage(new Image(ImageEnum.getUrl(e.god.toUpperCase() + "_PLAYER")));
                    ((ImageView) player2.getChildren().get(1)).setUserData(e.god);
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
        setUp();
    }

    private void animation(ImageView imageView, boolean state, double fromValue, double toValue) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        if (state) {
            imageView.setVisible(true);
            fade.setFromValue(fromValue);
            fade.setToValue(toValue);
        } else {
            fade.setFromValue(fromValue);
            fade.setToValue(toValue);
            fade.setOnFinished(e->imageView.setVisible(false));
        }
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(imageView);
        fade.play();
    }

    private void translation(ImageView source, ImageView dest, double x, double y) {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setToX(x);
        translate.setToY(y);
        translate.setAutoReverse(false);
        translate.setNode(source);
        translate.play();
        translate.setOnFinished(e -> {
            dest.setImage(source.getImage());
            source.setVisible(false);
            source.setX(this.x);
            source.setY(this.y);
        });
    }

    public void showWorker(MouseEvent e) {
        ImageView node = (ImageView) e.getSource();
        int i = Integer.parseInt(node.getUserData().toString());
        boardImages[i / 5][i % 5][1].setImage(new Image(ImageEnum.getUrl(color.toUpperCase())));
        boardImages[i / 5][i % 5][1].setVisible(true);
        boardImages[i / 5][i % 5][1].setOpacity(0.8);
    }

    public void closeWorker(MouseEvent e) {
        ImageView node = (ImageView) e.getSource();
        int i = Integer.parseInt(node.getUserData().toString());
        boardImages[i / 5][i % 5][1].setVisible(false);
        boardImages[i / 5][i % 5][1].setOpacity(1);
    }

    public void showConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int[] i = new Gson().fromJson(node.getUserData().toString(), int[].class);
        // System.out.println("sadfqewr" + i.toString());
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
                boardImages[position.get(0)][position.get(1)][0]
                        .setImage(new Image(ImageEnum.getUrl(builds[i[0] / 5][i[0] % 5][0].block.toUpperCase())));
                boardImages[position.get(0)][position.get(1)][0].setOpacity(0.7);
                boardImages[position.get(0)][position.get(1)][0].setVisible(true);
                break;
            case 2:
                List<Integer> position1 = builds[i[0] / 5][i[0] % 5][1].position;
                boardImages[position1.get(0)][position1.get(1)][1]
                        .setImage(new Image(ImageEnum.getUrl(builds[i[0] / 5][i[0] % 5][1].block.toUpperCase())));
                boardImages[position1.get(0)][position1.get(1)][1].setOpacity(0.7);
                boardImages[position1.get(0)][position1.get(1)][1].setVisible(true);
                break;
        }
    }

    public void closeConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int[] i = new Gson().fromJson(node.getUserData().toString(), int[].class);
        // System.out.println("asfdqwgrq" + i[1]);
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
                int size = board[position.get(0)][position.get(1)].getBlocks().size();
                if (size > 0 && board[position.get(0)][position.get(1)].getBlocks().get(size - 1).typeBlock.equals("WORKER")) {
                    size--;
                }
                if (size <= 0) {
                    boardImages[position.get(0)][position.get(1)][0].setVisible(false);
                } else {
                    boardImages[position.get(0)][position.get(1)][0].setImage(new Image(ImageEnum.getUrl(
                            board[position.get(0)][position.get(1)].getBlocks().get(size - 1).typeBlock.toUpperCase())));
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
    public void showGod(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        god.setImage(new Image(ImageEnum.getUrl(node.getUserData() + "_DEF")));
        animation(god, true, 0, 10);
    }

    @FXML
    public void closeGod(MouseEvent event) {
        animation(god, true, 10, 0);
    }

    @FXML
    public void chooseColor(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        color = node.getUserData().toString();
        controller.send(color);
    }

    @FXML
    public void chooseAction(MouseEvent event) {
        // System.out.println("1");
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
                    ((ImageView) e.getChildren().get(0))
                            .setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl("PODIUM_GOLD"))));
                    e.setEffect(glow);
                } else {
                    ((ImageView) e.getChildren().get(0))
                            .setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl("PODIUM"))));
                    Lighting lighting = new Lighting();
                    e.setEffect(lighting);
                }
            });
            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                if (e.status.equals("LOSE") || e.status.equals("WIN")) {
                    String state = e.status;
                    ((ImageView) Arrays.stream(players)
                            .filter(e1 -> ((Label) e1.getChildren().get(3)).getText().equals(e.username))
                            .collect(Collectors.toList()).get(0).getChildren().get(2))
                            .setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl(state))));
                }
            });
        });
    }

    private void setBoard() {
        //System.out.println("setBoard");
        Platform.runLater(() -> {
            Cell[][] map = controller.getBoard();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    //System.out.println(i + "; " + j);
                    if (!equal(map[i][j], board[i][j])) {
                        //System.out.println("non uguali");

                        if (map[i][j].getBlocks().size() == 0) {
                           // System.out.println("size cell 0");
                            if (boardImages[i][j][1].isVisible()) {
                                animation(boardImages[i][j][1], false, 10, 0);
                                //boardImages[i][j][1].setVisible(false);
                            }
                            if (boardImages[i][j][0].isVisible()) {
                                animation(boardImages[i][j][0], false, 10, 0);
                                //boardImages[i][j][0].setVisible(false);
                            }
                        } else {
                            //System.out.println("size cell no 0");
                            int size = map[i][j].getBlocks().size();
                            if (map[i][j].getBlocks().get(size - 1).typeBlock.toUpperCase().equals("WORKER")) {
                                //System.out.println("worker");
                                String url = ImageEnum.getUrl(map[i][j].getBlocks().get(size - 1).color.toUpperCase());
                                if (!boardImages[i][j][1].isVisible() || !board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 1).color.equals(map[i][j].getBlocks().get(size - 1).color)) {
                                    //System.out.println("in");
                                    if (boardImages[i][j][1].isVisible()) {
                                        //System.out.println("visible");
                                        animation(boardImages[i][j][1], true, 10, 0);
                                    }
                                    boardImages[i][j][1].setImage(new Image(url));
                                    animation(boardImages[i][j][1], true, 0, 10);
                                }
                                size--;
                            } else if (map[i][j].getBlocks().get(size - 1).typeBlock.toUpperCase().equals("DOME")) {
                                //System.out.println("dome");
                                String url = ImageEnum.getUrl(map[i][j].getBlocks().get(size - 1).typeBlock.toUpperCase());
                                if (!boardImages[i][j][1].isVisible() || !board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 1).typeBlock.equals(map[i][j].getBlocks().get(size - 1).typeBlock)) {
                                    //System.out.println("in");
                                    if (boardImages[i][j][1].isVisible()) {
                                        //System.out.println("visible");
                                        animation(boardImages[i][j][1], true, 10, 0);
                                    }
                                    boardImages[i][j][1].setImage(new Image(url));
                                    animation(boardImages[i][j][1], true, 0, 10);
                                }
                                size--;
                            } else {
                                //System.out.println("nessuno");
                                if (boardImages[i][j][1].isVisible()) {
                                    //System.out.println("visible");
                                    animation(boardImages[i][j][1], false, 10, 0);
                                    //boardImages[i][j][1].setVisible(false);
                                }
                            }
                            if (size > 0) {
                                //System.out.println("level" + size);
                                String url = ImageEnum.getUrl(map[i][j].getBlocks().get(size - 1).typeBlock.toUpperCase());
                                String typeBlock;
                                try {
                                    typeBlock = board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 1).typeBlock;
                                    if (typeBlock.toUpperCase().equals("WORKER") || typeBlock.toUpperCase().equals("DOME")) {
                                        typeBlock = board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 2).typeBlock;
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    typeBlock = "LEVEL0";
                                }


                                if (!boardImages[i][j][0].isVisible() || !typeBlock.equals(map[i][j].getBlocks().get(size - 1).typeBlock)) {
                                    //System.out.println("in" + url);
                                    if (boardImages[i][j][0].isVisible()) {
                                        //System.out.println("visible");
                                        animation(boardImages[i][j][0], true, 10, 0);
                                    }
                                    boardImages[i][j][0].setImage(new Image(url));
                                    animation(boardImages[i][j][0], true, 0, 10);
                                }
                            }
                        }
                        board[i][j] = map[i][j];
                    }
                }
            }
        });
    }

    private boolean equal(Cell a, Cell b) {
        try {
            if (a.getBlocks().size() != b.getBlocks().size()) {
                return false;
            }
            int size = a.getBlocks().size();
            for (int i = 0; i < size; i++) {
                if (!a.getBlocks().get(i).typeBlock.equals(b.getBlocks().get(i).typeBlock)) {
                    return false;
                } else if (a.getBlocks().get(i).typeBlock.toUpperCase().equals("WORKER") && !a.getBlocks().get(i).color.equals(b.getBlocks().get(i).color)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setAction() {
        // System.out.println("3");
        Swap[][] swaps1 = new Swap[5][5];
        Build[][][] builds1 = new Build[5][5][2];
        int[] count = new int[25];
        Arrays.stream(count).forEach(e -> e = 0);
        if (controller.getGamePhase().equals("SET_COLOR")) {
            setColor();
        } else if (controller.getGamePhase().equals("SET_WORKERS")) {
            setWorker(count);
        } else {
            List<Command> listCommand = controller.getCommand();

            listCommand.stream().forEach(e -> {
                if (e.funcData == null) {
                    actionBox.getChildren().get(3).setDisable(false);
                    actionBox.getChildren().get(3).setEffect(null);
                    actionBox.getChildren().get(3).setOnMouseClicked(e1 -> controller.send(null));
                } else {
                    if (e.funcName.equals("chooseWorker")) {
                        int i = Integer.parseInt(e.funcData);
                        count[i] = 1;
                        //animation(boardImages[i / 5][i % 5][2], false, 0.4, 0);
                        boardImages[i / 5][i % 5][2].setDisable(false);
                        boardImages[i / 5][i % 5][2].setUserData(i);
                        boardImages[i / 5][i % 5][2].setOnMouseClicked(e1 -> chooseAction(e1));
                    } else if (e.funcName.equals("chooseAction")) {
                        String data = e.funcData;
                        int[] i = new Gson().fromJson(e.funcData, int[].class);
                        count[i[0]] = 1;
                        //animation(boardImages[i[0] / 5][i[0] % 5][2], false, 0.4, 0);
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
        for (int i = 0; i < 25; i++) {
            int e = count[i];
            if (e == 1) {
                //System.out.println("null"+i);
                if (boardImages[i / 5][i % 5][2].getOpacity() == 0.4) {
                    animation(boardImages[i / 5][i % 5][2], true, 0.4, 0);
                }
            } else {
                //System.out.println("reset"+i);
                if (boardImages[i / 5][i % 5][2].getOpacity() == 0) {
                    animation(boardImages[i / 5][i % 5][2], true, 0, 0.4);
                }
            }
            boardImages[i / 5][i % 5][2].setVisible(true);
        }
        swaps = swaps1;
        builds = builds1;
    }

    private void setColor() {
        setUp = true;
        //System.out.println("4");
        Lighting lighting = new Lighting();
        List<Command> listCommand = controller.getCommand();

        ((ImageView) actionBox.getChildren().get(0)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(1)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(2)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(3)).setDisable(true);
        ((ImageView) actionBox.getChildren().get(0)).setEffect(lighting);
        ((ImageView) actionBox.getChildren().get(1)).setEffect(lighting);
        ((ImageView) actionBox.getChildren().get(2)).setEffect(lighting);


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

    private void setWorker(int[] cout) {
        // System.out.println("5");
        List<Command> listCommand = controller.getCommand();
        listCommand.stream().forEach(e -> {
            // System.out.println("6");
            int i = Integer.parseInt(e.funcData);
            cout[i] = 1;
            boardImages[i / 5][i % 5][2].setDisable(false);
            boardImages[i / 5][i % 5][2].setUserData(i);
            boardImages[i / 5][i % 5][2].setOnMouseEntered(e1 -> showWorker(e1));
            boardImages[i / 5][i % 5][2].setOnMouseExited(e1 -> closeWorker(e1));
            boardImages[i / 5][i % 5][2].setOnMouseClicked(e1 -> {
                closeWorker(e1);
                chooseAction(e1);
            });
        });
    }

    private void setUpPlayerInfo() {
        Platform.runLater(() -> {
            animation(((ImageView) actionBox.getChildren().get(0)), true, 10, 0);
            animation(((ImageView) actionBox.getChildren().get(1)), true, 10, 0);
            animation(((ImageView) actionBox.getChildren().get(2)), true, 10, 0);
            animation(((ImageView) actionBox.getChildren().get(3)), true, 10, 0);
            ((ImageView) actionBox.getChildren().get(0)).setImage(new Image(ImageEnum.getUrl("MOVE")));
            ((ImageView) actionBox.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl("BUILD")));
            ((ImageView) actionBox.getChildren().get(2)).setImage(new Image(ImageEnum.getUrl("BUILD_DOME")));
            ((ImageView) actionBox.getChildren().get(3)).setImage(new Image(ImageEnum.getUrl("END_TURN")));
            animation(((ImageView) actionBox.getChildren().get(0)), true, 0, 10);
            animation(((ImageView) actionBox.getChildren().get(1)), true, 0, 10);
            animation(((ImageView) actionBox.getChildren().get(2)), true, 0, 10);
            animation(((ImageView) actionBox.getChildren().get(3)), true, 0, 10);
            ((ImageView) actionBox.getChildren().get(0)).setOnMouseEntered(e -> showConsequence(e));
            ((ImageView) actionBox.getChildren().get(1)).setOnMouseEntered(e -> showConsequence(e));
            ((ImageView) actionBox.getChildren().get(2)).setOnMouseEntered(e -> showConsequence(e));
            ((ImageView) actionBox.getChildren().get(0)).setOnMouseExited(e -> closeConsequence(e));
            ((ImageView) actionBox.getChildren().get(1)).setOnMouseExited(e -> closeConsequence(e));
            ((ImageView) actionBox.getChildren().get(2)).setOnMouseExited(e -> closeConsequence(e));

            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                Arrays.stream(players).forEach(e1 -> {
                    if (((Label) e1.getChildren().get(3)).getText().equals(e.username)) {
                        ((ImageView) e1.getChildren().get(2))
                                .setImage(new Image(ImageEnum.getUrl(e.color.toUpperCase())));
                        animation(((ImageView) e1.getChildren().get(2)), true, 0, 10);
                    }
                });
            });
        });
        setUp = false;
    }

    @Override
    public void reSet() {
        if (controller.getGamePhase().equals("CHOOSE_WORKER") && setUp) {
            setUpPlayerInfo();
        }
        for (int i = 0; i < 4; i++) {
            actionBox.getChildren().get(i).setEffect(lighting);
            actionBox.getChildren().get(i).setDisable(true);
        }
        setPlayerInfo();
        setBoard();
        if (controller.getCurrentPlayer().equals(controller.getPlayer())) {
            for (int i = 0; i < 25; i++) {
                boardImages[i / 5][i % 5][2].setOnMouseExited(null);
                boardImages[i / 5][i % 5][2].setOnMouseEntered(null);
                boardImages[i / 5][i % 5][2].setOnMouseClicked(null);
            }
            setAction();
        } else {
            for (int i = 0; i < 25; i++) {
                if (boardImages[i / 5][i % 5][2].getOpacity() == 0.4) {
                    animation(boardImages[i / 5][i % 5][2], false, 0.4, 0);
                }
                //boardImages[i / 5][i % 5][2].setVisible(false);
                boardImages[i / 5][i % 5][2].setOnMouseExited(null);
                boardImages[i / 5][i % 5][2].setOnMouseEntered(null);
                boardImages[i / 5][i % 5][2].setOnMouseClicked(null);
            }
        }

    }

    @Override
    public void setWidth(double width) {
        //System.out.println("3");

    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void changePage(Boolean status) {
        //System.out.println("1");
        cloud.setVisible(true);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        if (!status) {
            fade.setFromValue(0);
            fade.setToValue(10);
            fade.setOnFinished(e -> {
                controller.changeScene();
            });
        } else {
            fade.setFromValue(10);
            fade.setToValue(0);
        }
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(cloud);
        fade.play();
    }

    @FXML
    private void quit() {
        animation(cloud,true,0,10);
        controller.quit();
    }

    @Override
    public void update(ChatMessage message) {
        listView.setVisible(true);
        listView.getItems().add("< " + message.username + " > " + message.message);
        Platform.runLater(() -> {
            listView.scrollTo(listView.getItems().size() - 1);
            Background background = Background.EMPTY;
            listView.setBackground(background);
        });

    }

}
