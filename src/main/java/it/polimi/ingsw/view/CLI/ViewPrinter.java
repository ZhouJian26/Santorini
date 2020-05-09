package it.polimi.ingsw.view.CLI;

import java.util.ArrayList;
import java.util.Arrays;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.socket.Parser;

public class ViewPrinter implements Observer<ArrayList<Command>> {
    public String username;
    private final Parser parser;
    private boolean needUpdate;

    public ViewPrinter(Parser parser) {
        this.parser = parser;
        needUpdate = false;
    }

    public void setUsername(String username) {
        if (username == null || username.length() == 0)
            throw new NullPointerException();
        this.username = username;
    }

    private void printGeneralInfo() {
        ArrayList<String> toPrint = new ArrayList<>(Arrays.asList(
                parser.getCommandList("gameMode").stream().map(e -> e.info).reduce("Game Mode: ", (p, e) -> p + e),
                (parser.getCommandList("gamePhase").stream().map(e -> e.info).reduce("Game Phase: ", (p, e) -> p + e)),
                parser.getCommandList("currentPlayer").stream().map(e -> e.info).reduce("Current Player: ",
                        (p, e) -> p + e)));
        for (String x : toPrint)
            System.out.println(x);
    }

    private void myAction() {
        if (username.equals(parser.getCommandList("currentPlayer").stream().map(e -> e.info).reduce("Current Player: ",
                (p, e) -> p + e))) {

        }
    }

    @Override
    public void update(ArrayList<Command> message) {
        // TODO Auto-generated method stub
        // based on setted view, print it
        needUpdate = true;
        printGeneralInfo();
        myAction();
    }

}