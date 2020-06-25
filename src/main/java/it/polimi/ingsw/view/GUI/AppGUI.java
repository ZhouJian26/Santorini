package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.socket.AppInterface;
import it.polimi.ingsw.view.socket.Parser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AppGUI extends Application implements Runnable, Observer<ArrayList<Command>>, AppInterface {
    private Stage window;
    private Parser parser;
    private MainController controller;
    private Scene scene;
    private String gamePhase = null;
    private Controller viewController;
    private ImageCursor Mouse = new ImageCursor(new Image("GraphicSrc/empty.png"), 30, 20);

    public void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new MainController();
        parser = new Parser();
        InitialPageController.setController(controller);
        Board.setController(controller);
        ChooseGod.setController(controller);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InitialPage.fxml"));
        window = primaryStage;
        parser.addObservers(this);
        controller.set(parser, this);
        window = primaryStage;
        window.setTitle("Santorini");
        if (scene == null) {
            scene = new Scene(fxmlLoader.load());
        } else {
            scene.setRoot(fxmlLoader.load());
        }
        viewController = fxmlLoader.getController();
        window.setScene(scene);
        window.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                viewController.setHeight(newValue.doubleValue());
                if ((window.getWidth() * 740) / (1280 * window.getHeight()) > 1.01
                        || (window.getWidth() * 740) / (1280 * window.getHeight()) < 0.99) {
                    window.setWidth(newValue.doubleValue() * 1280 / 740);
                }
            }
        });
        window.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                viewController.setWidth(newValue.doubleValue());
                if ((window.getWidth() * 740) / (1280 * window.getHeight()) > 1.01
                        || (window.getWidth() * 740) / (1280 * window.getHeight()) < 0.99) {
                    window.setHeight(newValue.doubleValue() * 740 / 1280);
                }
            }
        });
        window.setOnCloseRequest(e -> {
            controller.quit(false);
        });
        window.setHeight(740);
        window.setWidth(1280);
        viewController.setHeight(740);
        viewController.setWidth(1280);
        viewController.changePage(true);
        scene.setOnMouseEntered(e -> {
            scene.setCursor(Mouse);
        });
        // scene.setOnMouseExited(e -> {
        // scene.setCursor(Cursor.DEFAULT);
        // });
        window.show();
    }

    public void changeScene() {
        // System.out.println("changeScene" + parser.getGamePhase());
        if (parser.getGamePhase().equals("END")) {
            viewController.reSet();
            controller.quit(true);
        } else if (parser.getGamePhase().equals("SET_GOD_LIST") || parser.getGamePhase().equals("CHOOSE_GOD")
                || parser.getGamePhase().equals("START_PLAYER")) {
            Platform.runLater(() -> {
                try {
                    // System.out.println("3" + parser.getGamePhase());
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChooseGod.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    viewController = fxmlLoader.getController();
                    viewController.changePage(true);
                } catch (Exception e) {

                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    // System.out.println("3" + parser.getGamePhase());
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Board.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    scene.getStylesheets().add("board.css");
                    viewController = fxmlLoader.getController();
                    viewController.changePage(true);
                } catch (Exception e) {

                }
            });
        }
        viewController.setHeight(window.getHeight());
        viewController.setWidth(window.getWidth());
    }

    public void reStart() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(10, 0, 0, 20));
            vBox.setSpacing(10);
            vBox.setPrefHeight(100);
            vBox.setPrefWidth(200);
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(10, 0, 0, 0));
            hBox.setSpacing(30);
            hBox.setPrefHeight(100);
            hBox.setPrefWidth(200);
            Button quit = new Button("Quit");
            quit.setOnAction(e -> {
                window.close();
                stage.close();
            });
            Button again = new Button("Play Again");
            again.setOnAction(e -> {
                stage.close();
                gamePhase = null;
                try {
                    start(window);
                } catch (Exception e1) {
                }
            });
            hBox.getChildren().addAll(again, quit);
            Label label;
            try {
                Player player = (Player) parser.getPlayers().stream()
                        .filter(e -> e.username.equals(controller.getPlayer())).collect(Collectors.toList()).get(0);
                if (player.status.equals("WIN")) {
                    label = new Label("Game Ended, You WIN!");
                } else if (player.status.equals("LOSE")) {
                    label = new Label("Game Ended, You LOSE!");
                } else {
                    label = new Label("Game Ended.");
                }
            } catch (Exception e1) {
                label = new Label("Game Ended.");
            }
            vBox.getChildren().addAll(label, hBox);
            stage.setScene(new Scene(vBox, 250, 100));
            stage.show();
        });
    }

    @Override
    public void update(ArrayList<Command> message) {
        // based on setted view, print it

        if (message == null || message.equals(""))
            return;
        // System.out.println("viewPrinter: " + message);
        if (gamePhase == null || (!gamePhase.equals(parser.getGamePhase()) && gamePhase.equals("START_PLAYER"))
                || parser.getGamePhase().equals("END")) {
            System.out.println("changeScene");
            viewController.changePage(false);
            // changeScene();
        } else {
            viewController.reSet();
        }
        gamePhase = parser.getGamePhase();
    }

    @Override
    public void onDisconnection() {
        System.out.println("disco");
        reStart();
    }
}
