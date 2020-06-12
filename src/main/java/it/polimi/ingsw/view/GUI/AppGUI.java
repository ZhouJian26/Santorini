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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AppGUI extends Application implements Runnable, Observer<ArrayList<Command>>, AppInterface {
    private Stage window;
    private Parser parser = new Parser();
    private MainController controller = new MainController();
    private Scene scene;
    private String gamePhase = null;
    private Controller viewController;

    public void main(String[] args) {

        launch(args);
    }

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        InitialPageController.setController(controller);
        BoardController.setController(controller);
        ChooseGodController.setController(controller);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InitialPage.fxml"));
        window = primaryStage;
        parser.addObservers(this);
        controller.set(parser, this);
        window = primaryStage;
        window.setTitle("Santorini");
        scene = new Scene(fxmlLoader.load());
        viewController = fxmlLoader.getController();
        window.setScene(scene);
        window.minHeightProperty().bind(window.widthProperty().multiply(0.5625).subtract(10));
        window.minWidthProperty().bind(window.heightProperty().divide(720).multiply(1280).subtract(15));
        window.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                viewController.setWidth(newValue.doubleValue());
            }
        });
        window.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                viewController.setHeight(newValue.doubleValue());
            }
        });
        window.setOnCloseRequest(e -> {
            controller.quit();
            window.close();
        });
        window.setHeight(720);
        viewController.setHeight(720);
        viewController.setWidth(1280);
        window.setWidth(1280);
        window.setOnCloseRequest(e->{
            controller.quit();
        });
        window.show();

    }

    public void changeScene() {
        //System.out.println("changeScene" + parser.getGamePhase());
        if (parser.getGamePhase().equals("END")) {
            Platform.runLater(() -> {
                Stage stage = new Stage();
                VBox vBox = new VBox();
                vBox.setPadding(new Insets(10,0,0,20));
                vBox.setSpacing(10);
                vBox.setPrefHeight(100);
                vBox.setPrefWidth(200);
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(10,0,0,0));
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
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InitialPage.fxml"));
                        scene.setRoot(fxmlLoader.load());
                        viewController = fxmlLoader.getController();
                    } catch (Exception e1) {
                    }
                });
                hBox.getChildren().addAll(again, quit);
                Label label;
                try{
                    Player player=(Player) parser.getPlayers().stream().filter(e->e.username.equals(controller.getPlayer()));
                    if(player.status.equals("WIN")){
                        label = new Label("Game Ended, You WIN!");
                    }else if(player.status.equals("LOSE")){
                        label = new Label("Game Ended, You LOSE!");
                    }else {
                        label = new Label("Game Ended.");
                    }
                }catch (Exception e1){
                    label = new Label("Game Ended.");
                }
                vBox.getChildren().addAll(label, hBox);
                stage.setScene(new Scene(vBox, 250, 100));
                stage.show();
            });
        } else if (parser.getGamePhase().equals("SET_GOD_LIST") || parser.getGamePhase().equals("CHOOSE_GOD")
                || parser.getGamePhase().equals("START_PLAYER")) {
            Platform.runLater(() -> {
                try {
                    // System.out.println("3" + parser.getGamePhase());
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChooseGodView.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    viewController = fxmlLoader.getController();
                } catch (Exception e) {

                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    // System.out.println("3" + parser.getGamePhase());
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BoardView.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    viewController = fxmlLoader.getController();
                } catch (Exception e) {

                }
            });
        }
        viewController.setHeight(720);
        viewController.setWidth(1280);
        window.setHeight(720);
        window.setWidth(1280);
    }

    @Override
    public void update(ArrayList<Command> message) {
        // based on setted view, print it

        if (message == null || message.equals(""))
            return;
       System.out.println("viewPrinter: " + message);
        if (gamePhase == null || (!gamePhase.equals(parser.getGamePhase()) && gamePhase.equals("START_PLAYER")) || parser.getGamePhase().equals("END")) {
            //System.out.println("changeScene");
            changeScene();
        } else {
            viewController.reSet();
        }
        gamePhase = parser.getGamePhase();
    }

    @Override
    public void onDisconnection() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            Button quit = new Button("Quit");
            quit.setOnAction(e -> {
                window.close();
                stage.close();
            });
            Button again = new Button("Play Again");
            again.setOnAction(e -> {
                window.close();
                stage.close();
            });
            hBox.getChildren().addAll(again, quit);
            hBox.setPadding(new Insets(15, 12, 15, 12));
            hBox.setSpacing(10);
            Label label = new Label("Game Ended");
            vBox.getChildren().addAll(label, hBox);
            stage.setScene(new Scene(vBox, 200, 100));
            stage.show();
        });
    }
}
