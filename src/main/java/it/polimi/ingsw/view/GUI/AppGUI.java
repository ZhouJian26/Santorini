
package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AppGUI extends Application implements Runnable {
    Stage window;
    private String username;
    private MainController controller = new MainController();
    private InitialPageController initialController = new InitialPageController();


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialController.set(controller);
        window = primaryStage;
        window.setTitle("Santorini");
        URL url = getClass().getResource("/InitialPage.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 845, 470);
        window.setScene(scene);
        window.show();

    }

}