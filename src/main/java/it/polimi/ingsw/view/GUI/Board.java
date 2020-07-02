package it.polimi.ingsw.view.GUI;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.ChatMessage;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Build;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.model.Swap;
import it.polimi.ingsw.view.socket.Chat;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Board implements Controller, Observer<ChatMessage> {
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane player0, player1, player2, actionBox, pane;
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
    private String color, currentPlayer;

    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);

    @FXML
    private ImageView cloud, god, backGround, door, turn;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField textField;
    @FXML
    private Button send;
    public static void setController(MainController controller) {
        Board.controller = controller;
    }

    /**
     * Message to send
     */
    @FXML
    public void sendMessage() {
        String message = textField.getText().trim();
        textField.clear();
        textField.requestFocus();
        if (!message.equals(""))
            chat.sendMessage(message);
    }

    /**
     * Set window's dimension
     */
    private void setUpDimension() {
        pane.prefHeightProperty().bind(height);
        pane.prefWidthProperty().bind(width);

        backGround.fitWidthProperty().bind(width);
        backGround.fitHeightProperty().bind(height);
        cloud.fitWidthProperty().bind(width);
        cloud.fitHeightProperty().bind(height);

        player0.layoutYProperty().bind(height.multiply(25).divide(720));
        player0.layoutXProperty().bind(width.multiply(30).divide(1280));
        player1.layoutYProperty().bind(height.multiply(240).divide(720));
        player1.layoutXProperty().bind(width.multiply(30).divide(1280));
        player2.layoutYProperty().bind(height.multiply(455).divide(720));
        player2.layoutXProperty().bind(width.multiply(30).divide(1280));

        Arrays.stream(players).forEach(e -> {
            ((ImageView) e.getChildren().get(0)).fitHeightProperty().bind(height.multiply(84).divide(720));
            ((ImageView) e.getChildren().get(0)).fitWidthProperty().bind(width.multiply(150).divide(1280));
            ((ImageView) e.getChildren().get(1)).fitHeightProperty().bind(height.multiply(150).divide(720));
            ((ImageView) e.getChildren().get(1)).fitWidthProperty().bind(width.multiply(116).divide(1280));
            ((ImageView) e.getChildren().get(2)).fitHeightProperty().bind(height.multiply(120).divide(720));
            ((ImageView) e.getChildren().get(2)).fitWidthProperty().bind(width.multiply(120).divide(1280));
            ((ImageView) e.getChildren().get(0)).layoutXProperty().bind(width.multiply(20).divide(1280));
            ((ImageView) e.getChildren().get(0)).layoutYProperty().bind(height.multiply(130).divide(720));
            ((ImageView) e.getChildren().get(1)).layoutXProperty().bind(width.multiply(30).divide(1280));
            ((ImageView) e.getChildren().get(1)).layoutYProperty().bind(height.multiply(15).divide(720));
            ((ImageView) e.getChildren().get(2)).layoutXProperty().bind(width.multiply(120).divide(1280));
            ((ImageView) e.getChildren().get(2)).layoutYProperty().bind(height.multiply(30).divide(720));
        });
        door.fitWidthProperty().bind(width.multiply(70).divide(1280));
        door.fitHeightProperty().bind(height.multiply(70).divide(720));
        door.layoutYProperty().bind(height.multiply(650).divide(720));
        door.layoutXProperty().bind(width.multiply(15).divide(1280));

        actionBox.layoutXProperty().bind(width.multiply(980).divide(1280));
        actionBox.layoutYProperty().bind(height.multiply(100).divide(720));
        ((ImageView) actionBox.getChildren().get(0)).layoutYProperty().bind(height.multiply(10).divide(720));
        ((ImageView) actionBox.getChildren().get(0)).layoutXProperty().bind(width.multiply(10).divide(1280));
        ((ImageView) actionBox.getChildren().get(1)).layoutYProperty().bind(height.multiply(90).divide(720));
        ((ImageView) actionBox.getChildren().get(1)).layoutXProperty().bind(width.multiply(10).divide(1280));
        ((ImageView) actionBox.getChildren().get(2)).layoutYProperty().bind(height.multiply(10).divide(720));
        ((ImageView) actionBox.getChildren().get(2)).layoutXProperty().bind(width.multiply(140).divide(1280));
        ((ImageView) actionBox.getChildren().get(3)).layoutYProperty().bind(height.multiply(90).divide(720));
        ((ImageView) actionBox.getChildren().get(3)).layoutXProperty().bind(width.multiply(140).divide(1280));

        ((ImageView) actionBox.getChildren().get(0)).fitHeightProperty().bind(height.multiply(56).divide(720));
        ((ImageView) actionBox.getChildren().get(0)).fitWidthProperty().bind(width.multiply(140).divide(1280));
        ((ImageView) actionBox.getChildren().get(1)).fitHeightProperty().bind(height.multiply(56).divide(720));
        ((ImageView) actionBox.getChildren().get(1)).fitWidthProperty().bind(width.multiply(140).divide(1280));
        ((ImageView) actionBox.getChildren().get(2)).fitHeightProperty().bind(height.multiply(56).divide(720));
        ((ImageView) actionBox.getChildren().get(2)).fitWidthProperty().bind(width.multiply(140).divide(1280));
        ((ImageView) actionBox.getChildren().get(3)).fitHeightProperty().bind(height.multiply(56).divide(720));
        ((ImageView) actionBox.getChildren().get(3)).fitWidthProperty().bind(width.multiply(140).divide(1280));

        listView.prefWidthProperty().bind(width.multiply(275).divide(1280));
        listView.prefHeightProperty().bind(height.multiply(320).divide(720));
        listView.layoutXProperty().bind(width.multiply(980).divide(1280));
        listView.layoutYProperty().bind(height.multiply(320).divide(720));

        textField.prefWidthProperty().bind(width.multiply(220).divide(1280));
        textField.prefHeightProperty().bind(height.multiply(26).divide(720));
        textField.layoutXProperty().bind(width.multiply(980).divide(1280));
        textField.layoutYProperty().bind(height.multiply(657).divide(720));

        send.prefWidthProperty().bind(width.multiply(50).divide(1280));
        send.prefHeightProperty().bind(height.multiply(28).divide(720));
        send.layoutXProperty().bind(width.multiply(1202).divide(1280));
        send.layoutYProperty().bind(height.multiply(657).divide(720));

        god.fitWidthProperty().bind(width.multiply(320).divide(1280));
        god.fitHeightProperty().bind(height.multiply(550).divide(720));
        god.layoutXProperty().bind(width.multiply(470).divide(1280));
        god.layoutYProperty().bind(height.multiply(70).divide(720));

        turn.fitWidthProperty().bind(width.multiply(400).divide(1280));
        turn.fitHeightProperty().bind(height.multiply(200).divide(720));
        turn.layoutXProperty().bind(width.multiply(450).divide(1280));
        turn.layoutYProperty().bind(height.multiply(250).divide(720));

        gridPane.prefWidthProperty().bind(width.multiply(460).divide(1280));
        gridPane.prefHeightProperty().bind(height.multiply(460).divide(720));
        gridPane.layoutXProperty().bind(width.multiply(402).divide(1280));
        gridPane.layoutYProperty().bind(height.multiply(128).divide(720));

        for (int i = 0; i < 25; i++) {
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(0)).fitWidthProperty()
                    .bind(width.multiply(80).divide(1280));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(0)).fitHeightProperty()
                    .bind(height.multiply(80).divide(720));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(0)).layoutXProperty()
                    .bind(width.multiply(6).divide(1280));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(0)).layoutYProperty()
                    .bind(height.multiply(6).divide(720));

            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(1)).fitWidthProperty()
                    .bind(width.multiply(80).divide(1280));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(1)).fitHeightProperty()
                    .bind(height.multiply(80).divide(720));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(1)).layoutXProperty()
                    .bind(width.multiply(6).divide(1280));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(1)).layoutYProperty()
                    .bind(height.multiply(6).divide(720));

            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(2)).fitWidthProperty()
                    .bind(width.multiply(92.4).divide(1280));
            ((ImageView) ((Pane) gridPane.getChildren().get(i)).getChildren().get(2)).fitHeightProperty()
                    .bind(height.multiply(92.4).divide(720));

        }

    }

    /**
     * Set Up the board
     */
    private void setUp() {
        textField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                sendMessage();
            }
        });

        listView.setCellFactory(lv -> new ListCell<String>() {
            private final Text text;

            {
                text = new Text();
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(text);
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

    /**
     * Initialize the board
     */
    @FXML
    public void initialize() {
        turn.setDisable(true);
        turn.setVisible(false);
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
                if (e.getUsername().equals(controller.getPlayer())) {
                    player0.setVisible(true);
                    ((Label) player0.getChildren().get(3)).setText(e.getUsername());
                    ((ImageView) player0.getChildren().get(1))
                            .setImage(new Image(ImageEnum.getUrl(e.getGod().toUpperCase() + "_PLAYER")));
                    ((ImageView) player0.getChildren().get(1)).setUserData(e.getGod());
                    ((ImageView) player0.getChildren().get(2)).setVisible(false);
                } else if (!player1.isVisible()) {
                    player1.setVisible(true);
                    ((Label) player1.getChildren().get(3)).setText(e.getUsername());
                    ((ImageView) player1.getChildren().get(1))
                            .setImage(new Image(ImageEnum.getUrl(e.getGod().toUpperCase() + "_PLAYER")));
                    ((ImageView) player1.getChildren().get(1)).setUserData(e.getGod());
                    ((ImageView) player1.getChildren().get(2)).setVisible(false);
                } else {
                    player2.setVisible(true);
                    ((Label) player2.getChildren().get(3)).setText(e.getUsername());
                    ((ImageView) player2.getChildren().get(1))
                            .setImage(new Image(ImageEnum.getUrl(e.getGod().toUpperCase() + "_PLAYER")));
                    ((ImageView) player2.getChildren().get(1)).setUserData(e.getGod());
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
        setUpDimension();
    }

    /**
     * To create animation during the game
     *
     * @param imageView Background image of animation
     * @param state     visibility
     * @param fromValue initial opacity value
     * @param toValue   finale opacity value
     */
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
            fade.setOnFinished(e -> imageView.setVisible(false));
        }
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(imageView);
        fade.play();
    }

    private void changeTurn(ImageView imageView) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1200));
        imageView.setVisible(true);
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(imageView);
        fade.play();
        fade.setOnFinished(e -> {
            FadeTransition fade1 = new FadeTransition();
            fade1.setFromValue(10);
            fade1.setToValue(0);
            fade1.setCycleCount(1);
            fade1.setAutoReverse(false);
            fade1.setNode(imageView);
            fade1.play();
        });
    }

    /**
     * Show worker's preview on the board
     *
     * @param e mouse event
     */
    public void showWorker(MouseEvent e) {
        ImageView node = (ImageView) e.getSource();
        int i = Integer.parseInt(node.getUserData().toString());
        boardImages[i / 5][i % 5][1].setImage(new Image(ImageEnum.getUrl(color.toUpperCase())));
        boardImages[i / 5][i % 5][1].setVisible(true);
        boardImages[i / 5][i % 5][1].setOpacity(0.8);
    }

    /**
     * Close the showWorker() preview
     *
     * @param e mouse event
     */
    public void closeWorker(MouseEvent e) {
        ImageView node = (ImageView) e.getSource();
        int i = Integer.parseInt(node.getUserData().toString());
        boardImages[i / 5][i % 5][1].setVisible(false);
        boardImages[i / 5][i % 5][1].setOpacity(1);
    }

    /**
     * After choosing the block, it shows the preview of actions
     *
     * @param event mouse event
     */
    public void showConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int[] i = new Gson().fromJson(node.getUserData().toString(), int[].class);
        switch (i[1]) {
            case 0:
                List<Integer> x1 = swaps[i[0] / 5][i[0] % 5].getX1();
                List<Integer> x2 = swaps[i[0] / 5][i[0] % 5].getX2();
                List<Integer> y1 = swaps[i[0] / 5][i[0] % 5].getY1();
                List<Integer> y2 = swaps[i[0] / 5][i[0] % 5].getY2();
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
                List<Integer> position = builds[i[0] / 5][i[0] % 5][0].getPosition();
                boardImages[position.get(0)][position.get(1)][0]
                        .setImage(new Image(ImageEnum.getUrl(builds[i[0] / 5][i[0] % 5][0].getBlock().toUpperCase())));
                boardImages[position.get(0)][position.get(1)][0].setOpacity(0.7);
                boardImages[position.get(0)][position.get(1)][0].setVisible(true);
                break;
            case 2:
                List<Integer> position1 = builds[i[0] / 5][i[0] % 5][1].getPosition();
                boardImages[position1.get(0)][position1.get(1)][1]
                        .setImage(new Image(ImageEnum.getUrl(builds[i[0] / 5][i[0] % 5][1].getBlock().toUpperCase())));
                boardImages[position1.get(0)][position1.get(1)][1].setOpacity(0.7);
                boardImages[position1.get(0)][position1.get(1)][1].setVisible(true);
                break;
        }
    }

    /**
     * Close the preview of showConsequence()
     *
     * @param event mouse event
     */
    public void closeConsequence(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        int[] i = new Gson().fromJson(node.getUserData().toString(), int[].class);
        switch (i[1]) {
            case 0:
                List<Integer> x1 = swaps[i[0] / 5][i[0] % 5].getX1();
                List<Integer> x2 = swaps[i[0] / 5][i[0] % 5].getX2();
                List<Integer> y1 = swaps[i[0] / 5][i[0] % 5].getY1();
                List<Integer> y2 = swaps[i[0] / 5][i[0] % 5].getY2();
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
                List<Integer> position = builds[i[0] / 5][i[0] % 5][0].getPosition();
                boardImages[position.get(0)][position.get(1)][0].setOpacity(1);
                int size = board[position.get(0)][position.get(1)].getBlocks().size();
                if (size > 0 && board[position.get(0)][position.get(1)].getBlocks().get(size - 1).getTypeBlock()
                        .equals("WORKER")) {
                    size--;
                }
                if (size <= 0) {
                    boardImages[position.get(0)][position.get(1)][0].setVisible(false);
                } else {
                    boardImages[position.get(0)][position.get(1)][0]
                            .setImage(new Image(ImageEnum.getUrl(board[position.get(0)][position.get(1)].getBlocks()
                                    .get(size - 1).getTypeBlock().toUpperCase())));
                    boardImages[position.get(0)][position.get(1)][0].setVisible(true);
                }
                break;
            case 2:
                List<Integer> position1 = builds[i[0] / 5][i[0] % 5][1].getPosition();
                boardImages[position1.get(0)][position1.get(1)][1].setVisible(false);
                boardImages[position1.get(0)][position1.get(1)][1].setOpacity(1);
                break;
        }
    }

    /**
     * Show god power's image description
     *
     * @param event mouse event
     */
    @FXML
    public void showGod(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        god.setImage(new Image(ImageEnum.getUrl(node.getUserData() + "_DEF")));
        animation(god, true, 0, 10);
    }

    /**
     * Close showGod()
     *
     * @param event
     */
    @FXML
    public void closeGod(MouseEvent event) {
        animation(god, true, 10, 0);
    }

    /**
     * Set worker's color and send the choice to server
     *
     * @param event mouse event
     */
    @FXML
    public void chooseColor(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        color = node.getUserData().toString();
        controller.send(color);
    }

    /**
     * Send player's actions to server
     *
     * @param event mouse event
     */
    @FXML
    public void chooseAction(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        node.setDisable(true);
        String string = node.getUserData().toString();
        controller.send(string);
    }

    /**
     * After choosing the grid on board, shows all available actions for the chosen grid
     *
     * @param event mouse event
     */
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

    /**
     * Receive player's information from server (Current player, player's turn, ecc) and shows to players
     */
    private void setPlayerInfo() {
        if (!controller.getCurrentPlayer().equals(currentPlayer)) {
            currentPlayer = controller.getCurrentPlayer();
            Platform.runLater(() -> {
                if (currentPlayer.equals(controller.getPlayer())) {
                    changeTurn(turn);
                }
                Arrays.stream(players).forEach(e -> {
                    if (e.isVisible()) {
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
                    }
                });
            });
        }
        Platform.runLater(() -> {
            List<Player> listPlayer = controller.getUserInfo();
            listPlayer.stream().forEach(e -> {
                if (e.getStatus().equals("LOSE") || e.getStatus().equals("WIN")) {
                    String state = e.getStatus();
                    ((ImageView) Arrays.stream(players)
                            .filter(e1 -> e1.isVisible() && ((Label) e1.getChildren().get(3)).getText().equals(e.getUsername()))
                            .collect(Collectors.toList()).get(0).getChildren().get(2))
                            .setImage(new Image(Objects.requireNonNull(ImageEnum.getUrl(state))));
                }
            });
        });
    }

    /**
     * Receive board's information from server and shows to all players
     */
    private void setBoard() {
        try {
            Platform.runLater(() -> {
                Cell[][] map = controller.getBoard();
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (!map[i][j].equals(board[i][j])) {
                            if (map[i][j].getBlocks().size() == 0) {
                                if (boardImages[i][j][1].isVisible()) {
                                    animation(boardImages[i][j][1], false, 10, 0);
                                }
                                if (boardImages[i][j][0].isVisible()) {
                                    animation(boardImages[i][j][0], false, 10, 0);
                                }
                            } else {
                                int size = map[i][j].getBlocks().size();
                                if (map[i][j].getBlocks().get(size - 1).getTypeBlock().toUpperCase().equals("WORKER")) {
                                    String url = ImageEnum
                                            .getUrl(map[i][j].getBlocks().get(size - 1).getColor().toUpperCase());
                                    if (!boardImages[i][j][1].isVisible()
                                            || !board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 1).getColor()
                                            .equals(map[i][j].getBlocks().get(size - 1).getColor())) {
                                        if (boardImages[i][j][1].isVisible()) {
                                            animation(boardImages[i][j][1], true, 10, 0);
                                        }
                                        boardImages[i][j][1].setImage(new Image(url));
                                        animation(boardImages[i][j][1], true, 0, 10);
                                    }
                                    size--;
                                } else if (map[i][j].getBlocks().get(size - 1).getTypeBlock().toUpperCase()
                                        .equals("DOME")) {
                                    String url = ImageEnum
                                            .getUrl(map[i][j].getBlocks().get(size - 1).getTypeBlock().toUpperCase());
                                    if (!boardImages[i][j][1].isVisible() || !board[i][j].getBlocks()
                                            .get(board[i][j].getBlocks().size() - 1).getTypeBlock()
                                            .equals(map[i][j].getBlocks().get(size - 1).getTypeBlock())) {
                                        if (boardImages[i][j][1].isVisible()) {
                                            animation(boardImages[i][j][1], true, 10, 0);
                                        }
                                        boardImages[i][j][1].setImage(new Image(url));
                                        animation(boardImages[i][j][1], true, 0, 10);
                                    }
                                    size--;
                                } else {
                                    if (boardImages[i][j][1].isVisible()) {
                                        animation(boardImages[i][j][1], false, 10, 0);
                                    }
                                }
                                if (size > 0) {
                                    String url = ImageEnum
                                            .getUrl(map[i][j].getBlocks().get(size - 1).getTypeBlock().toUpperCase());
                                    String typeBlock;
                                    try {
                                        typeBlock = board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 1)
                                                .getTypeBlock();
                                        if (typeBlock.toUpperCase().equals("WORKER")
                                                || typeBlock.toUpperCase().equals("DOME")) {
                                            typeBlock = board[i][j].getBlocks().get(board[i][j].getBlocks().size() - 2)
                                                    .getTypeBlock();
                                        }
                                    } catch (IndexOutOfBoundsException e) {
                                        typeBlock = "LEVEL0";
                                    }

                                    if (!boardImages[i][j][0].isVisible()
                                            || !typeBlock.equals(map[i][j].getBlocks().get(size - 1).getTypeBlock())) {
                                        if (boardImages[i][j][0].isVisible()) {
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
        } catch (Exception e) {
        }
    }

    /**
     * Receive all available actions from server and shows to all players
     */
    private void setAction() {
        try {
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
                    if (e.getFuncData() == null) {
                        actionBox.getChildren().get(3).setDisable(false);
                        actionBox.getChildren().get(3).setEffect(null);
                        actionBox.getChildren().get(3).setOnMouseClicked(e1 -> controller.send(null));
                    } else {
                        if (e.getFuncName().equals("chooseWorker")) {
                            int i = Integer.parseInt(e.getFuncData());
                            count[i] = 1;
                            boardImages[i / 5][i % 5][2].setDisable(false);
                            boardImages[i / 5][i % 5][2].setUserData(i);
                            boardImages[i / 5][i % 5][2].setOnMouseClicked(e1 -> chooseAction(e1));
                        } else if (e.getFuncName().equals("chooseAction")) {
                            int[] i = new Gson().fromJson(e.getFuncData(), int[].class);
                            count[i[0]] = 1;
                            boardImages[i[0] / 5][i[0] % 5][2].setDisable(false);
                            boardImages[i[0] / 5][i[0] % 5][2].setUserData(i[0]);
                            boardImages[i[0] / 5][i[0] % 5][2].setOnMouseClicked(e1 -> chooseCell(e1));
                            switch (i[1]) {
                                case 0:
                                    swaps1[i[0] / 5][i[0] % 5] = new Gson().fromJson(e.getInfo(), Swap.class);
                                    break;
                                case 1:
                                    builds1[i[0] / 5][i[0] % 5][0] = new Gson().fromJson(e.getInfo(), Build.class);
                                    break;
                                case 2:
                                    builds1[i[0] / 5][i[0] % 5][1] = new Gson().fromJson(e.getInfo(), Build.class);
                                    break;
                            }
                        }
                    }
                });
            }
            for (int i = 0; i < 25; i++) {
                int e = count[i];
                if (e == 1) {
                    if (boardImages[i / 5][i % 5][2].getOpacity() == 0.4) {
                        animation(boardImages[i / 5][i % 5][2], true, 0.4, 0);
                    }
                } else {
                    if (boardImages[i / 5][i % 5][2].getOpacity() == 0) {
                        animation(boardImages[i / 5][i % 5][2], true, 0, 0.4);
                    }
                }
                boardImages[i / 5][i % 5][2].setVisible(true);
            }
            swaps = swaps1;
            builds = builds1;
        } catch (Exception e) {
        }
    }

    /**
     * Receive available colors for players from server and shows to all players
     */
    private void setColor() {
        setUp = true;
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

            if (e.getFuncData().equals("BLUE")) {
                ((ImageView) actionBox.getChildren().get(0)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(0)).setVisible(true);
                ((ImageView) actionBox.getChildren().get(0)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(0)).setUserData("BLUE");
                ((ImageView) actionBox.getChildren().get(0)).setOnMouseClicked(e1 -> chooseColor(e1));
            } else if (e.getFuncData().equals("BROWN")) {
                ((ImageView) actionBox.getChildren().get(1)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(1)).setVisible(true);
                ((ImageView) actionBox.getChildren().get(1)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(1)).setUserData("BROWN");
                ((ImageView) actionBox.getChildren().get(1)).setOnMouseClicked(e1 -> chooseColor(e1));
            } else if (e.getFuncData().equals("WHITE")) {
                ((ImageView) actionBox.getChildren().get(2)).setEffect(null);
                ((ImageView) actionBox.getChildren().get(2)).setVisible(true);
                ((ImageView) actionBox.getChildren().get(2)).setDisable(false);
                ((ImageView) actionBox.getChildren().get(2)).setUserData("WHITE");
                ((ImageView) actionBox.getChildren().get(2)).setOnMouseClicked(e1 -> chooseColor(e1));
            }

        });
    }

    /**
     * Shows all available grids for player's chosen worker to place
     *
     * @param count board grid enumeration
     */
    private void setWorker(int[] count) {
        List<Command> listCommand = controller.getCommand();
        listCommand.stream().forEach(e -> {
            int i = Integer.parseInt(e.getFuncData());
            count[i] = 1;
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

    /**
     * Set player's constant information (those that won't change until the end of game)
     */
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
                    if (((Label) e1.getChildren().get(3)).getText().equals(e.getUsername())) {
                        ((ImageView) e1.getChildren().get(2))
                                .setImage(new Image(ImageEnum.getUrl(e.getColor().toUpperCase())));
                        animation(((ImageView) e1.getChildren().get(2)), true, 0, 10);
                    }
                });
            });
        });
        setUp = false;
    }

    /**
     * Reload the board for all players
     */
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
                boardImages[i / 5][i % 5][2].setOnMouseExited(null);
                boardImages[i / 5][i % 5][2].setOnMouseEntered(null);
                boardImages[i / 5][i % 5][2].setOnMouseClicked(null);
            }
        }

    }

    /**
     * set view's dimension
     *
     * @param width  width
     * @param height height
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
     * @param status if it's allowed to change view
     */
    @Override
    public void changePage(Boolean status) {
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

    /**
     * Send quit message to server and end the game
     */
    @FXML
    private void quit() {
        controller.quit();
    }

    /**
     * Receive message from server
     *
     * @param message message
     */
    @Override
    public void update(ChatMessage message) {
        try {
            listView.setVisible(true);
            listView.getItems().add("< " + message.getUsername() + " > " + message.getMessage());
            Platform.runLater(() -> {
                listView.scrollTo(listView.getItems().size() - 1);
                Background background = Background.EMPTY;
                listView.setBackground(background);
            });
        } catch (Exception e) {
        }

    }

}
