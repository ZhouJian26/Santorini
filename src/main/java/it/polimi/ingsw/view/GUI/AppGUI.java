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
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AppGUI extends Application implements Runnable, Observer<ArrayList<Command>>, AppInterface {
    /**
     * Container
     */
    private Stage window;

    /**
     * parser for messages
     */
    private Parser parser;

    /**
     * First page controller
     */
    private MainController controller;

    /**
     * Scene
     */
    private Scene scene;

    /**
     * Game Phase
     */
    private String gamePhase = null;

    /**
     * View Controller
     */
    private Controller viewController;

    /**
     * Mouse
     */
    private ImageCursor Mouse = new ImageCursor(new Image("GraphicSrc/mouse.png"), 0, 0);


    public void main(String[] args) {
        launch(args);
    }

    /**
     * Run client's GUI
     */
    @Override
    public void run() {
        launch();
    }

    /**
     * Start GUI
     * @param primaryStage Client's window
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new MainController();
        parser = new Parser();
        parser.addObservers(this);
        controller.set(parser, this);

        InitialPageController.setController(controller);
        Board.setController(controller);
        ChooseGod.setController(controller);

        window = primaryStage;
        window.setTitle("Santorini");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InitialPage.fxml"));

        if (scene == null) {
            scene = new Scene(fxmlLoader.load());
        } else {
            scene.setRoot(fxmlLoader.load());
        }

        scene.setOnMouseEntered(e -> {
            scene.setCursor(Mouse);
        });
        scene.setOnMouseExited(e -> {
            scene.setCursor(Cursor.DEFAULT);
        });

        viewController = fxmlLoader.getController();
        

        window.setScene(scene);

        window.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                viewController.setDimension(window.getWidth(),newValue.doubleValue()-20);

            }
        });
        window.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                viewController.setDimension(newValue.doubleValue(),window.getHeight());

            }
        });
        window.setOnCloseRequest(e -> {
            controller.closeConnection();
        });
        // window.setResizable(false);
        window.setMinHeight(380);
        window.setMinWidth(640);
        window.setHeight(740);
        window.setWidth(1280);
        viewController.setDimension(1280,720);
        viewController.changePage(true);
        window.show();

    }

    /**
     * Change the scene
     */
    public void changeScene() {
        if (parser.getGamePhase().equals("END")) {
            // reStart();
            viewController.reSet();
            controller.closeConnection();
        } else if (parser.getGamePhase().equals("SET_GOD_LIST") || parser.getGamePhase().equals("CHOOSE_GOD")
                || parser.getGamePhase().equals("START_PLAYER")) {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChooseGod.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    viewController = fxmlLoader.getController();
                    viewController.changePage(true);
                    viewController.setDimension(window.getWidth(),window.getHeight() - 20);
                } catch (Exception e) {

                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Board.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    scene.getStylesheets().add("board.css");
                    viewController = fxmlLoader.getController();
                    viewController.changePage(true);
                    viewController.setDimension(window.getWidth(),window.getHeight() - 20);
                } catch (Exception e) {

                }
            });
        }


    }

    /**
     * Restart
     */
    public void reStart() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
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
                controller.closeConnection();
                window.close();
                stage.close();
            });
            Button again = new Button("Play Again");
            again.setOnAction(e -> {
                controller.closeConnection();
                stage.close();
                gamePhase = null;
                viewController.changePage(false);
                try {
                    start(window);
                } catch (Exception e1) {
                }
            });
            hBox.getChildren().addAll(again, quit);
            Label label;
            try {
                Player player = (Player) parser.getPlayers().stream()
                        .filter(e -> e.getUsername().equals(controller.getPlayer())).collect(Collectors.toList())
                        .get(0);
                if (player.getStatus().equals("WIN")) {
                    label = new Label("Game Ended, You WIN!");
                } else if (player.getStatus().equals("LOSE")) {
                    label = new Label("Game Ended, You LOSE!");
                } else {
                    label = new Label("Game Ended.");
                }
            } catch (Exception e1) {
                label = new Label("Game Ended.");
            }
            vBox.getChildren().addAll(label, hBox);
            stage.setScene(new Scene(vBox, 250, 100));
            stage.setAlwaysOnTop(true);
            stage.show();
        });
    }

    /**
     * Update messages and/or commands
     * @param message message to be updated
     */
    @Override
    public void update(ArrayList<Command> message) {
        if (message == null || message.equals(""))
            return;
        if (gamePhase == null || (!gamePhase.equals(parser.getGamePhase()) && gamePhase.equals("START_PLAYER"))) {
            viewController.changePage(false);
        } else if (parser.getGamePhase().equals("END")) {
            viewController.reSet();
            reStart();
        }
        viewController.reSet();
        gamePhase = parser.getGamePhase();
    }

    /**
     * Used for disconnection
     */
    @Override
    public void onDisconnection() {
        System.out.println("disco");
        reStart();
    }
}
