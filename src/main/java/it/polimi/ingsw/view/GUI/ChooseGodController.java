package it.polimi.ingsw.view.GUI;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChooseGodController {

    @FXML
    private Button button0,button1,button2,button3,button4,button5,button6,button7;
    @FXML
    private ImageView god0,god1,god2,god3,god4,god5,god6,god7;

    @FXML
    public void clicked(){
        if(god0.isPressed()){
            System.out.println("1");
        }
        else {
            System.out.println("2");
        }
    }
}
