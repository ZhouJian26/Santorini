package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ChooseGod implements Controller {
    private static MainController controller = new MainController();
    private String[] players = new String[3];
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);
    private ImageView[] gods = new ImageView[14];
    private Glow glow = new Glow();
    private Lighting lighting = new Lighting();

    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView port, card, god0, god1, god2, god3, god4, god5, god6, god7, god8, god9, god10, god11, god12, god13, podium0, podium1, podium2, cloud, background;
    @FXML
    private Pane camp0, camp1, camp2;
    @FXML
    private Label player0, player1, player2;

    private void setUpDimension(){
        background.fitWidthProperty().bind(width);
        background.fitHeightProperty().bind(height);
        cloud.fitWidthProperty().bind(width);
        cloud.fitHeightProperty().bind(height);
        camp0.prefWidthProperty().bind(width.multiply(200).divide(1280));
        camp1.prefWidthProperty().bind(width.multiply(200).divide(1280));
        camp2.prefWidthProperty().bind(width.multiply(200).divide(1280));
        camp0.prefHeightProperty().bind(height.multiply(220).divide(720));
        camp1.prefHeightProperty().bind(height.multiply(220).divide(720));
        camp2.prefHeightProperty().bind(height.multiply(220).divide(720));
        camp1.layoutYProperty().bind(height.multiply(220).divide(720));
        camp2.layoutYProperty().bind(height.multiply(440).divide(720));
        podium0.fitHeightProperty().bind(height.multiply(84).divide(720));
        podium1.fitHeightProperty().bind(height.multiply(84).divide(720));
        podium2.fitHeightProperty().bind(height.multiply(84).divide(720));
        podium0.fitWidthProperty().bind(width.multiply(150).divide(1280));
        podium1.fitWidthProperty().bind(width.multiply(150).divide(1280));
        podium2.fitWidthProperty().bind(width.multiply(150).divide(1280));
        podium0.layoutYProperty().bind(height.multiply(120).divide(720));
        podium1.layoutYProperty().bind(height.multiply(120).divide(720));
        podium2.layoutYProperty().bind(height.multiply(120).divide(720));
        ((ImageView)camp0.getChildren().get(1)).fitWidthProperty().bind(width.multiply(100).divide(1280));
        ((ImageView)camp0.getChildren().get(1)).fitHeightProperty().bind(height.multiply(130).divide(720));
        ((ImageView)camp0.getChildren().get(1)).layoutYProperty().bind(height.multiply(20).divide(720));
        ((ImageView)camp0.getChildren().get(1)).layoutXProperty().bind(width.multiply(25).divide(1280));

        ((ImageView)camp1.getChildren().get(1)).fitWidthProperty().bind(width.multiply(100).divide(1280));
        ((ImageView)camp1.getChildren().get(1)).fitHeightProperty().bind(height.multiply(130).divide(720));
        ((ImageView)camp1.getChildren().get(1)).layoutYProperty().bind(height.multiply(20).divide(720));
        ((ImageView)camp1.getChildren().get(1)).layoutXProperty().bind(width.multiply(25).divide(1280));

        ((ImageView)camp2.getChildren().get(1)).fitWidthProperty().bind(width.multiply(100).divide(1280));
        ((ImageView)camp2.getChildren().get(1)).fitHeightProperty().bind(height.multiply(130).divide(720));
        ((ImageView)camp2.getChildren().get(1)).layoutYProperty().bind(height.multiply(20).divide(720));
        ((ImageView)camp2.getChildren().get(1)).layoutXProperty().bind(width.multiply(25).divide(1280));

        port.fitHeightProperty().bind(height.multiply(72).divide(720));
        port.fitWidthProperty().bind(width.multiply(80).divide(1280));
        port.layoutYProperty().bind(height.multiply(650).divide(720));

        card.fitHeightProperty().bind(height.multiply(450).divide(720));
        card.fitWidthProperty().bind(width.multiply(262).divide(1280));
        card.layoutYProperty().bind(height.multiply(40).divide(720));
        card.layoutXProperty().bind(width.multiply(1000).divide(1280));

        gridPane.prefWidthProperty().bind(width.multiply(730).divide(1280));
        gridPane.prefHeightProperty().bind(height.multiply(700).divide(720));
        gridPane.layoutXProperty().bind(width.multiply(240).divide(1280));

        for(int i=0;i<14;i++){
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(0)).fitWidthProperty().bind(width.multiply(145).divide(1280));
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(0)).fitHeightProperty().bind(height.multiply(82).divide(720));
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(1)).fitWidthProperty().bind(width.multiply(116).divide(1280));
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(1)).fitHeightProperty().bind(height.multiply(150).divide(720));
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(1)).layoutXProperty().bind(width.multiply(10).divide(1280));
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(1)).layoutYProperty().bind(height.multiply(30).divide(720));
            ((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(0)).layoutYProperty().bind(height.multiply(150).divide(720));

        }
    }

    public static void setController(MainController controller) {
        ChooseGod.controller = controller;
    }

    @FXML
    public void choose(MouseEvent event) {
        if (!controller.getPlayer().equals(players[0])) {
            return;
        }
        ImageView node = (ImageView) event.getSource();
        String string = (String) node.getUserData();
        controller.send(string);
    }

    @FXML
    public void choosePlayer(MouseEvent event) {
        if (!controller.getPlayer().equals(players[0])) {
            return;
        }
        Pane node = (Pane) event.getSource();
        String string = (String) node.getUserData();
        int i = Integer.parseInt(string);
        controller.send(players[i]);
    }

    @FXML
    public void quit() {
        animation(cloud,true);
        controller.quit();
    }

    private void animation(ImageView imageView, boolean state) {
        FadeTransition fade = new FadeTransition();
        if (state) {
            imageView.setVisible(true);
            fade.setFromValue(0);
            fade.setToValue(10);
        } else {
            imageView.setVisible(false);
            fade.setFromValue(10);
            fade.setToValue(0);
        }
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(imageView);
        fade.play();
    }

    private void translation(Pane pane, double x, double y) {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setToX(x);
        translate.setToY(y);
        translate.setAutoReverse(false);
        translate.setNode(pane);
        translate.play();
    }

    @FXML
    public void show(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        String string = (String) node.getUserData();
        String url = ImageEnum.getUrl(string + "_DEF");
        card.setImage(new Image(url));
        card.setVisible(true);
        animation(card, true);
    }

    @FXML
    public void close(MouseEvent event) {
        animation(card, false);
    }


    @Override
    public void reSet() {
        resetPlayerInfo();
        resetAction();
    }

    public void resetPlayerInfo() {
        podium0.setEffect(lighting);
        podium1.setEffect(lighting);
        podium2.setEffect(lighting);
        String currPlayer = controller.getCurrentPlayer();
        if (currPlayer.equals(players[0])) {
            podium0.setImage(new Image(ImageEnum.getUrl("PODIUM_GOLD")));
            podium0.setEffect(glow);
            podium1.setImage(new Image(ImageEnum.getUrl("PODIUM")));
            podium2.setImage(new Image(ImageEnum.getUrl("PODIUM")));
        } else if (currPlayer.equals(players[1])) {
            podium1.setImage(new Image(ImageEnum.getUrl("PODIUM_GOLD")));
            podium1.setEffect(glow);
            podium0.setImage(new Image(ImageEnum.getUrl("PODIUM")));
            podium2.setImage(new Image(ImageEnum.getUrl("PODIUM")));
        } else {
            podium2.setImage(new Image(ImageEnum.getUrl("PODIUM_GOLD")));
            podium2.setEffect(glow);
            podium1.setImage(new Image(ImageEnum.getUrl("PODIUM")));
            podium0.setImage(new Image(ImageEnum.getUrl("PODIUM")));
        }
    }

    private void chooseStartPlayer() {
        gridPane.setVisible(false);
        if (controller.getCurrentPlayer().equals(controller.getPlayer())) {
            Platform.runLater(() -> {
                List<Player> playersList = controller.getUserInfo();
                playersList.stream().forEach(e -> {
                    if (e.username.equals(players[0])) {
                        translation(camp0, 250 * width.doubleValue() / 1280, 220 * height.doubleValue() / 720);
                        camp0.setDisable(false);
                    } else if (e.username.equals(players[1])) {
                        camp1.layoutYProperty().unbind();
                        translation(camp1, 550 * width.doubleValue() / 1280,  height.doubleValue() / 720);
                        camp1.setDisable(false);
                    } else {
                        camp2.layoutYProperty().unbind();
                        translation(camp2, 850 * width.doubleValue() / 1280, -220 * height.doubleValue() / 720);
                        camp2.setDisable(false);
                    }
                });
            });
        }
        //System.out.println("in");
        Platform.runLater(() -> {
            List<Player> playersList = controller.getUserInfo();
            playersList.stream().forEach(e -> {
                if (e.username.equals(players[0])) {
                    ((ImageView) camp0.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(e.god + "_PLAYER")));
                    ((ImageView) camp0.getChildren().get(1)).setVisible(true);
                    animation(((ImageView) camp0.getChildren().get(1)), true);
                } else if (e.username.equals(players[1])) {
                    ((ImageView) camp1.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(e.god + "_PLAYER")));
                    ((ImageView) camp1.getChildren().get(1)).setVisible(true);
                    animation(((ImageView) camp1.getChildren().get(1)), true);
                } else {
                    ((ImageView) camp2.getChildren().get(1)).setImage(new Image(ImageEnum.getUrl(e.god + "_PLAYER")));
                    ((ImageView) camp2.getChildren().get(1)).setVisible(true);
                    animation(((ImageView) camp2.getChildren().get(1)), true);
                }
            });
        });
        background.setEffect(lighting);
    }

    public void resetAction() {
        gridPane.setVisible(true);
        int[] activeGods = new int[14];
        for (int i = 0; i < 14; i++) {
            if (gods[i].isVisible()) {
                activeGods[i] = 1;
            } else {
                activeGods[i] = 0;
            }
        }
        if (controller.getGamePhase().equals("SET_GOD_LIST") || controller.getGamePhase().equals("CHOOSE_GOD")) {
            List<Command> listCommand = controller.getCommand();
            Arrays.stream(gods).forEach(e -> {
                if (listCommand.stream().anyMatch(e1 -> e1.funcData.equals((String) e.getUserData()))) {
                    if (!e.isVisible()) {
                        animation(e, true);
                    }
                } else {
                    if (e.isVisible()) {
                        animation(e, false);
                    }
                }
            });
        } else if(controller.getGamePhase().equals("START_PLAYER")) {
            chooseStartPlayer();
        }
    }

    @Override
    public void setWidth(double width) {
        this.width.set(width * 1.01);
        this.height.set(width * 720 / 1280);
    }

    @Override
    public void setHeight(double height) {
        this.height.set(height * 1.01);
        this.width.set(height * 1280 / 720);
    }

    @Override
    public void changePage(Boolean state) {
        System.out.println("2");
        cloud.setVisible(true);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        if (!state) {
            fade.setFromValue(0);
            fade.setToValue(10);
            fade.setOnFinished(e -> {
                controller.changeScene();
            });
        } else {
            System.out.println("aaaaaa");
            fade.setFromValue(10);
            fade.setToValue(0);
        }
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(cloud);
        fade.play();
    }

    @FXML
    private void initialize() {
        for(int i=0;i<14;i++){
            gods[i]=((ImageView)((Pane)gridPane.getChildren().get(i)).getChildren().get(1));
            gods[i].setVisible(false);
        }

        camp0.setVisible(false);
        camp0.setDisable(true);
        camp1.setVisible(false);
        camp1.setDisable(true);
        camp2.setVisible(false);
        camp2.setDisable(true);

        List<Player> listPlayer = controller.getUserInfo();
        listPlayer.stream().forEach(e -> {
            if (e.username.equals(controller.getPlayer())) {
                players[0] = e.username;
                camp0.setVisible(true);
                player0.setText(e.username);
            } else if (!camp1.isVisible()) {
                players[1] = e.username;
                camp1.setVisible(true);
                player1.setText(e.username);
            } else {
                players[2] = e.username;
                camp2.setVisible(true);
                player2.setText(e.username);
            }
        });

        cloud.setDisable(true);
        setUpDimension();
        reSet();
    }
}
