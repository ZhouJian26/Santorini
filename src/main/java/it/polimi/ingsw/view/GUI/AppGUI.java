package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.socket.Parser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AppGUI extends Application implements Runnable, Observer<ArrayList<Command>> {
    private Stage window;
    private String username;
    private Parser parser = new Parser();
    private MainController controller = new MainController();
    private Scene scene;


    public void main(String[] args) {

        //launch(args);
    }


    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InitialPageController.setController(controller);
        window = primaryStage;
        parser.addObservers(this);
        controller.set(parser, this);
        window = primaryStage;
        window.setTitle("Santorini");
        URL url = getClass().getResource("/InitialPage.fxml");
        Parent root = FXMLLoader.load(url);
        scene = new Scene(root, 845, 470);
        window.setScene(scene);
        window.show();

    }

    public void changeScene() {
        System.out.println("2" + parser.getGamePhase());
        if (parser.getGamePhase().equals("SET_GOD_LIST") || parser.getGamePhase().equals("CHOOSE_GOD")|| parser.getGamePhase().equals("SET_COLOR")) {
            Platform.runLater(() -> {
                try {
                    System.out.println("3" + parser.getGamePhase());
                    ChooseGodController.setController(controller);
                    URL url = getClass().getResource("/ChooseGodView.fxml");
                    Parent root = null;
                    root = FXMLLoader.load(url);
                    scene = new Scene(root);
                    window.setScene(scene);
                } catch (Exception e) {

                }
            });
        } else if (parser.getGamePhase().equals("SET_WORKERS")) {
            Platform.runLater(() -> {
                try {
                    System.out.println("3" + parser.getGamePhase());
                    BoardController.setController(controller);
                    URL url = getClass().getResource("/BoardView.fxml");
                    Parent root = null;
                    root = FXMLLoader.load(url);
                    scene = new Scene(root);
                    window.setScene(scene);
                } catch (Exception e) {

                }
            });
        }
    }

    @Override
    public void update(ArrayList<Command> message) {
        // based on setted view, print it
        System.out.println("viewPrinter: " + message);
        if (message == null)
            return;

        changeScene();
    }
}
