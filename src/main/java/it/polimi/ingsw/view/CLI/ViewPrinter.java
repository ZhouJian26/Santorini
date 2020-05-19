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
    public final String TypeAction;

    public TypeAction(String type) {
        this.TypeAction = type;
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
        List<ArrayList<String>> title = new ArrayList<>();
        title.add(new ArrayList<>(Arrays.asList("GAME INFO")));
        printRow(title);
        List<ArrayList<String>> toPrint = Arrays
                .asList("Game Mode: " + parser.getGameMode(), "Game Phase: " + parser.getGamePhase(),
                        "Current Player: " + parser.getCurrentPlayer())
                .stream().map(e -> new ArrayList<>(Arrays.asList(e))).collect(Collectors.toList());
        printRow(toPrint);
    }

    private String centerFill(int space, String word) {
        String out = String.format("%" + space + "s%s%" + space + "s", "", word, "");
        return out.substring((out.length() - space) / 2, (out.length() + space) / 2 - 1);
    }

    private String centerFill(int space, String word, String fill) {
        String out = "";
        for (int i = 0; i < space; i++)
            out += fill;
        out = out + word + out;
        return out.substring((out.length() - space) / 2, (out.length() - space) / 2 + space);
    }

    private void printRow(List<ArrayList<String>> toPrint) {
        int spaceDefault = 120;
        int maxH = toPrint.stream().map(e -> e.size()).max(Integer::compare).get();
        System.out.print(centerFill(spaceDefault + 1, "", "-") + "\n|" + centerFill(spaceDefault, "") + "|\n|");
        IntStream.range(0, maxH).forEachOrdered(index -> {
            for (ArrayList<String> x : toPrint) {
                if (index - (maxH - x.size()) / 2 >= 0 && index < (maxH + x.size()) / 2)
                    System.out.print(
                            centerFill(spaceDefault / toPrint.size(), x.get(index - (maxH - x.size()) / 2)) + "|");
                else
                    System.out.print(centerFill(spaceDefault / toPrint.size(), "") + "|");
            }
            if (index + 1 < maxH)
                System.out.print("\n|");
            else
                System.out.print("\n|" + centerFill(spaceDefault, "") + "|");
        });
        System.out.println();
    }

    private void printPlayerInfo() {
        List<ArrayList<String>> title = new ArrayList<>();
        title.add(new ArrayList<>(Arrays.asList("PLAYERS")));
        printRow(title);
        List<ArrayList<String>> toPrint = parser.getPlayers().stream().map(e -> e.getRawData())
                .collect(Collectors.toList());
        printRow(toPrint);
    }

    private void printBoardInfo() {

        List<ArrayList<String>> title = new ArrayList<>();
        title.add(new ArrayList<>(Arrays.asList("BOARD")));
        printRow(title);
        Cell[][] toPrint = parser.getBoard();
        int position = 0;
        for (Cell[] row : toPrint) {
            ArrayList<ArrayList<String>> toPrintRow = new ArrayList<>();
            for (Cell cell : row) {
                ArrayList<String> toPush = cell.getRawData();
                toPush.add("[" + position / 5 + "," + position % 5 + "]");
                toPrintRow.add(toPush);
                position++;
            }
            printRow(toPrintRow);
        }
    }

    private void printActionInfo() {
        if (username == null || !username.equals(parser.getCurrentPlayer()))
            return;
        List<ArrayList<String>> title = new ArrayList<>();
        title.add(new ArrayList<>(Arrays.asList("ACTIONS", "It's your turn!")));
        printRow(title);
        ArrayList<ArrayList<String>> toPrint = (ArrayList<ArrayList<String>>) parser.getUsableCommandList().stream()
                .map(e -> {
                    ArrayList<String> toRes;
                    // System.out.println(new Gson().toJson(e));
                    switch (e.type) {
                        case "action":
                            if (e.info == null)
                                toRes = new ArrayList<>(Arrays.asList("End Turn"));
                            else if (new Gson().fromJson(e.info, TypeAction.class).TypeAction.equals("Swap"))
                                toRes = new Gson().fromJson(e.info, Swap.class).getRawData();
                            else
                                toRes = new Gson().fromJson(e.info, Build.class).getRawData();
                            break;
                        case "board":
                            toRes = new Gson().fromJson(e.info, Cell.class).getRawData();
                            toRes.add("[" + Integer.parseInt(e.funcData) / 5 + "," + Integer.parseInt(e.funcData) % 5
                                    + "]");
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
            int i = 0;
            for (ArrayList<String> x : rowToPrint)
                x.add(0, "Action: " + (indexAction + i++));
            indexAction += rowToPrint.size();
            printRow(rowToPrint);
            toPrint.subList(0, toPrint.size() < 5 ? toPrint.size() : 5).clear();
        }
    }

    private String getActionString(int index) {
        try {
            return new Gson().toJson(parser.getUsableCommandList().get(index));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean useAction(int index) {
        String toSend = getActionString(index);
        if (toSend == null)
            return false;
        notify(toSend);
        return true;
    }

    private void printGeneralInfo() {
        printGameInfo();
        printPlayerInfo();
    }

    private void printView() {
        if (!needUpdate || !status)
            return;
        needUpdate = false;
        clearConsole();
        printGeneralInfo();
        printBoardInfo();
        printActionInfo();
        System.out.println(centerFill(121, "", "-"));
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