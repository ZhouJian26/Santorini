
package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AppGUI extends Application implements Runnable {
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Santorini");
        URL url = getClass().getResource("/InitialPage.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 845, 470);
        window.setScene(scene);
        window.show();

    }

}