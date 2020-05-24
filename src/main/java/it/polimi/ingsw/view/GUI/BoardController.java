package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class BoardController {

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox vBox0,vBox1,vBox2;

    @FXML
    private Pane pane00,pane01,pane02,pane03,pane04,pane10,pane11,pane12,pane13,pane14,pane21,pane22,pane23,pane24,pane20,pane30,pane31,pane32,pane33,pane34,pane40,pane41,pane42,pane43,pane44;

    @FXML
    private ImageView light00,light01,light02,light03,light04,light10,light11,light12,light13,light14,light21,light22,light23,light24,light20,light30,light31,light32,light33,light34,light40,light41,light42,light43,light44;


    @FXML
    private ImageView image01,image02,image03,image04,image10,image11,image12,image13,image14,image21,image22,image23,image24,image20,image30,image31,image32,image33,image34,image40,image41,image42,image43,image44;


    @FXML
    private Label player0, player1, player2, godName0, godName1, godName2, worker0, worker1, worker2, turn0, turn1, turn2, status0, status1, status2, lab0;

    @FXML
    private ImageView god0, god1, god2;

    private static MainController controller = new MainController();

    @FXML
    public void clicked(){

    }

    public static void setController(MainController controller){
        BoardController.controller =controller;
    }

}
