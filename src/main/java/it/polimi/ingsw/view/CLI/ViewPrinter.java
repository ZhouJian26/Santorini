package it.polimi.ingsw.view.CLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.Gson;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.view.model.Block;
import it.polimi.ingsw.view.model.Build;
import it.polimi.ingsw.view.model.Cell;
import it.polimi.ingsw.view.model.Color;
import it.polimi.ingsw.view.model.God;
import it.polimi.ingsw.view.model.Player;
import it.polimi.ingsw.view.model.Swap;
import it.polimi.ingsw.view.socket.Parser;

class TypeAction {
    public final String typeAction;

    public TypeAction(String type) {
        this.typeAction = type;
    }
}

public class ViewPrinter extends Observable<String> implements Observer<ArrayList<Command>> {
    private String username;
    private boolean needUpdate;
    private final Parser parser;
    private boolean status;

    public ViewPrinter(Parser parser) {
        this.parser = parser;
        status = false;
        needUpdate = false;
    }

    public void setStatus(boolean status) {
        this.status = status;
        printView();
    }

    public void setUsername(String username) {
        if (username == null || username.length() == 0)
            throw new NullPointerException();
        this.username = username;
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printGameInfo() {
        ArrayList<String> toPrint = new ArrayList<>(Arrays.asList("Game Mode: " + parser.getGameMode(),
                "Game Phase: " + parser.getGamePhase(), "Current Player: " + parser.getCurrentPlayer()));
        for (String x : toPrint)
            System.out.println(x);
    }

    private void printPlayerInfo() {
        ArrayList<Player> toPrint = parser.getPlayers();
        for (Player x : toPrint)
            System.out.println("Username: " + x.username + x.god != null ? "\nGod: " + x.god
                    : "" + x.color != null ? "\nColor: " + x.color : "");
    }

    private void printBoardInfo() {
        Cell[][] toPrint = parser.getBoard();
        // todo da fare
        for (Cell[] row : toPrint) {
            for (Cell cell : row)
                for (Block block : cell.getBlocks())
                    System.out.print(block.block);
            System.out.flush();
        }
    }

    private void printActionInfo() {
        if (username == null || !username.equals(parser.getCurrentPlayer()))
            return;
        System.out.println("It's your moment! \nAction available\n");
        // todo convert to swith case
        ArrayList<ArrayList<String>> toPrint = (ArrayList<ArrayList<String>>) parser.getUsableCommandList().stream()
                .map(e -> {
                    ArrayList<String> toRes;
                    switch (e.type) {
                        case "action":
                            if (new Gson().fromJson(e.info, TypeAction.class).typeAction.equals("Swap"))
                                toRes = new Gson().fromJson(e.info, Swap.class).getRawData();
                            else
                                toRes = new Gson().fromJson(e.info, Build.class).getRawData();
                        case "board":
                            toRes = new Gson().fromJson(e.info, Cell.class).getRawData();
                            toRes.add(Integer.parseInt(e.funcData) / 5 + "," + Integer.parseInt(e.funcData) % 5);
                            break;
                        case "color":
                            toRes = new Color(e.info).getRawData();
                            break;
                        case "god":
                            toRes = new God(e.info).getRawData();
                            break;
                        case "godList":
                            toRes = new God(e.info).getRawData();
                            break;
                        default:
                            toRes = null;
                            break;
                    }
                    return toRes;
                }).filter(e -> e != null && e.size() > 0).collect(Collectors.toList());
        int indexAction = 0;
        while (toPrint.size() > 0) {
            List<ArrayList<String>> rowToPrint = (List<ArrayList<String>>) toPrint.subList(0,
                    toPrint.size() < 5 ? toPrint.size() : 5);

            int maxH = rowToPrint.stream().map(e -> e.size()).max(Integer::compare).get();
            for (int i = 0; i < rowToPrint.size(); i++)
                System.out.format("%19s: %d", "Action", indexAction + i);
            indexAction += rowToPrint.size();
            System.out.println();
            IntStream.range(0, maxH).forEachOrdered(index -> {
                for (ArrayList<String> x : rowToPrint) {
                    if (index - (maxH - x.size()) / 2 >= 0 && index < (maxH + x.size()) / 2)
                        System.out.format("%25s", x.get(index - (maxH - x.size()) / 2));
                }
                System.out.println();
            });
            System.out.println();
            toPrint.subList(0, toPrint.size() < 5 ? toPrint.size() : 5).clear();
        }
    }

    private String getActionString(int index) {
        try {
            System.out.println(parser.getUsableCommandList().toString());
            return new Gson().toJson(parser.getUsableCommandList().get(index));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean useAction(int index) {
        String toSend = getActionString(index);
        if (toSend == null)
            return false;
        System.out.println(toSend);
        notify(toSend);
        return true;
    }

    private void printGeneralInfo() {
        printGameInfo();
        // printPlayerInfo();
        printActionInfo();
    }

    private void printView() {
        if (!needUpdate || !status)
            return;
        needUpdate = false;
        clearConsole();
        printGeneralInfo();

        // myAction();
    }

    @Override
    public void update(ArrayList<Command> message) {
        // based on setted view, print it
        if (message == null)
            return;
        needUpdate = true;
        printView();
    }

}