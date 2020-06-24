package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseGod implements Controller {
    private static MainController controller = new MainController();
    private String[] players = new String[3];
    private DoubleProperty height = new SimpleDoubleProperty(720);
    private DoubleProperty width = new SimpleDoubleProperty(1280);
    private Scene scene;
    private ImageView[] gods = new ImageView[14];
    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView card, god0, god1, god2, god3, god4, god5, god6, god7, god8, god9, god10, god11, god12, god13, podium0, podium1, podium2, cloud;

    @FXML
    private Pane camp0, camp1, camp2;

    @FXML
    private Label player0, player1, player2;

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
        System.out.println("quit");
        controller.quit(true);
    }

    @FXML
    public void show(MouseEvent event) {
        ImageView node = (ImageView) event.getSource();
        String string = (String) node.getUserData();
        System.out.println(string);
        String url = ImageEnum.getUrl(string + "_DEF");
        System.out.println(url);
        card.setImage(new Image(url));
        card.setVisible(true);

        FadeTransition fade = new FadeTransition();
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(card);
        fade.play();
    }

    @FXML
    public void close(MouseEvent event) {
        FadeTransition fade = new FadeTransition();
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(card);
        fade.play();
    }


    @Override
    public void reSet() {
        System.out.println("e");
        resetPlayerInfo();
        System.out.println("f");
        resetAction();
        System.out.println("g");
    }

    public void resetPlayerInfo() {
        System.out.println("h");
        String currPlayer = controller.getCurrentPlayer();
        if (currPlayer.equals(players[0])) {
            podium0.setImage(new Image(ImageEnum.getUrl("PODIUM_GOLD")));
            podium1.setImage(new Image(ImageEnum.getUrl("PODIUM")));
            podium2.setImage(new Image(ImageEnum.getUrl("PODIUM")));
        } else if (currPlayer.equals(players[1])) {
            podium1.setImage(new Image(ImageEnum.getUrl("PODIUM_GOLD")));
            podium0.setImage(new Image(ImageEnum.getUrl("PODIUM")));
            podium2.setImage(new Image(ImageEnum.getUrl("PODIUM")));
        } else {
            podium2.setImage(new Image(ImageEnum.getUrl("PODIUM_GOLD")));
            podium1.setImage(new Image(ImageEnum.getUrl("PODIUM")));
            podium0.setImage(new Image(ImageEnum.getUrl("PODIUM")));
        }
    }

    public void resetAction() {
        gridPane.setVisible(true);
        Arrays.stream(gods).forEach(e -> {
            e.setVisible(false);
        });
        List<Command> listCommand = controller.getCommand();
        listCommand.forEach(e -> {
            System.out.println(e.funcName);
            if (e.funcName.equals("setGodList") || e.funcName.equals("setGod")) {
                System.out.println(e.funcData);
                ImageView i = Arrays.stream(gods).filter(e1 -> ((String) e1.getUserData()).equals(e.funcData)).collect(Collectors.toList()).get(0);
                i.setVisible(true);
            } else {
                gridPane.setVisible(false);
                camp0.setDisable(false);
                camp1.setDisable(false);
                camp2.setDisable(false);
            }
        });
    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void changePage(Boolean state) {
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
        Arrays.stream(gods).forEach(e -> e.setVisible(false));

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
        reSet();
    }
}
