package it.polimi.ingsw.view.GUI;

import java.util.HashMap;
import java.util.Map;

public class ControllerMatching {

    private final Map<String, MainController> ctrlList = new HashMap<>();

    private static ControllerMatching instance;

    private ControllerMatching(){

    }
    public static synchronized ControllerMatching getInstance() {
        if (instance == null) {
            instance = new ControllerMatching();
        }
        return instance;
    }


    public void setUsername (String name, MainController controller){
        ctrlList.put(name, controller);
    }


    public MainController getController(String nameKey){
        return ctrlList.get(nameKey);
    }


}
