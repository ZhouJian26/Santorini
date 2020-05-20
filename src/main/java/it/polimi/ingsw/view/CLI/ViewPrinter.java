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

    private ArrayList<String> printGameInfo() {
        ArrayList<String> toRes = new ArrayList<>();
        List<ArrayList<String>> title = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("GAME INFO"))));
        // title.add(new ArrayList<>(Arrays.asList("GAME INFO")));
        toRes.addAll(composeRow(title));
        toRes.add(breakRow(121, "|", "|", "-"));
        toRes.addAll(composeRow(Arrays
                .asList("Game Mode: " + parser.getGameMode(), "Game Phase: " + parser.getGamePhase(),
                        "Current Player: " + parser.getCurrentPlayer())
                .stream().map(e -> new ArrayList<>(Arrays.asList(e))).collect(Collectors.toList())));
        toRes.add(breakRow(121, "|", "|", "-"));
        return toRes;
    }

    private String centerFill(int space, String word) {
        String out = String.format("%" + space + "s%s%" + space + "s", "", word, "");
        return out.substring((out.length() - space) / 2, (out.length() + space) / 2);
    }

    private String centerFill(int space, String word, String fill) {
        String out = "";
        for (int i = 0; i < space; i++)
            out += fill;
        out = out + word + out;
        return out.substring((out.length() - space) / 2, (out.length() - space) / 2 + space);
    }

    private void printRow(ArrayList<String> toPrint) {
        for (String x : toPrint)
            System.out.println(x);
    }

    private ArrayList<String> composeRow(ArrayList<String> container_1, ArrayList<String> container_2) {
        int maxH = Math.max(container_1.size(), container_2.size());
        ArrayList<String> toRes = new ArrayList<>();
        for (int index = 0; index < maxH; index++) {
            String toAdd = "";
            if (index - (maxH - container_1.size()) / 2 >= 0 && index < (maxH + container_1.size()) / 2)
                toAdd += container_1.get(index - (maxH - container_1.size()) / 2);
            else
                toAdd += String.format("%" + container_1.get(0).length() + "s", "");

            if (index - (maxH - container_2.size()) / 2 >= 0 && index < (maxH + container_2.size()) / 2)
                toAdd += container_2.get(index - (maxH - container_2.size()) / 2).substring(1,
                        container_2.get(index - (maxH - container_2.size()) / 2).length());
            else
                toAdd += String.format("%" + container_2.get(0).length() + "s", "");
            toRes.add(toAdd);
        }
        return toRes;
    }

    private String breakRow(int space, String start, String end, String fill) {
        String toRes = "";
        for (int i = 0; i < space; i++)
            toRes += fill;
        toRes = start + toRes.substring(0, space - 2) + end;
        return toRes;
    }

    private ArrayList<String> composeRow(List<ArrayList<String>> toPrint, int space, String start, String end) {
        ArrayList<String> toRes = new ArrayList<>();
        int maxH = toPrint.stream().map(e -> e.size()).max(Integer::compare).get();
        // toRes.add(centerFill(space + 1, "", "-"));
        toRes.add(breakRow(space + 1, start, end, " "));
        IntStream.range(0, maxH).forEachOrdered(index -> {
            String toAdd = start;
            for (ArrayList<String> x : toPrint) {
                if (index - (maxH - x.size()) / 2 >= 0 && index < (maxH + x.size()) / 2)
                    toAdd += centerFill(space / toPrint.size() - 1, x.get(index - (maxH - x.size()) / 2));
                else
                    toAdd += centerFill(space / toPrint.size() - 1, "");
                toAdd += "|";
            }
            toRes.add(toAdd);
        });
        toRes.add(breakRow(space + 1, start, end, " "));
        return toRes;
    }

    private ArrayList<String> composeRow(List<ArrayList<String>> toPrint) {
        return composeRow(toPrint, 120, "|", "|");
    }

    private ArrayList<String> printPlayerInfo() {
        ArrayList<String> toRes = new ArrayList<>();
        List<ArrayList<String>> title = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("PLAYERS"))));
        toRes.addAll(composeRow(title));
        toRes.add(breakRow(121, "|", "|", "-"));
        List<ArrayList<String>> toPrint = parser.getPlayers().stream().map(e -> e.getRawData())
                .collect(Collectors.toList());
        toRes.addAll(composeRow(toPrint));
        toRes.add(breakRow(121, "|", "|", "-"));
        return toRes;
    }

    private ArrayList<String> printBoardInfo() {

        ArrayList<String> toRes = new ArrayList<>();
        List<ArrayList<String>> title = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("BOARD"))));
        toRes.addAll(composeRow(title));
        toRes.add(breakRow(121, "|", "|", "-"));
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
            toRes.addAll(composeRow(toPrintRow));
            toRes.add(breakRow(121, "|", "|", "-"));
        }
        return toRes;
    }

    private ArrayList<String> printActionInfo() {
        if (username == null || !username.equals(parser.getCurrentPlayer()))
            return null;
        int space = 12 * 8;
        ArrayList<String> toRes = new ArrayList<>();
        toRes.add(breakRow(space + 1, "-", "-", "-"));
        List<ArrayList<String>> title = new ArrayList<>(
                Arrays.asList(new ArrayList<>(Arrays.asList("ACTIONS", "It's your turn!"))));
        toRes.addAll(composeRow(title, space, " ", "|"));
        ArrayList<ArrayList<String>> toPrint = (ArrayList<ArrayList<String>>) parser.getUsableCommandList().stream()
                .map(e -> {
                    ArrayList<String> toRet;
                    // System.out.println(new Gson().toJson(e));
                    switch (e.type) {
                        case "action":
                            if (e.info == null)
                                toRet = new ArrayList<>(Arrays.asList("End Turn"));
                            else if (new Gson().fromJson(e.info, TypeAction.class).TypeAction.equals("Swap"))
                                toRet = new Gson().fromJson(e.info, Swap.class).getRawData();
                            else
                                toRet = new Gson().fromJson(e.info, Build.class).getRawData();
                            break;
                        case "board":
                            toRet = new Gson().fromJson(e.info, Cell.class).getRawData();
                            toRet.add("[" + Integer.parseInt(e.funcData) / 5 + "," + Integer.parseInt(e.funcData) % 5
                                    + "]");
                            break;
                        case "color":
                            toRet = new Color(e.info).getRawData();
                            break;
                        case "god":
                            toRet = new God(e.info).getRawData();
                            break;
                        case "godList":
                            toRet = new God(e.info).getRawData();
                            break;
                        default:
                            toRet = null;
                            break;
                    }
                    return toRet;
                }).filter(e -> e != null && e.size() > 0).collect(Collectors.toList());
        int indexAction = 0;
        while (toPrint.size() > 0) {
            toRes.add(breakRow(space + 1, "|", "|", "-"));
            List<ArrayList<String>> rowToPrint = (List<ArrayList<String>>) toPrint.subList(0,
                    toPrint.size() < 4 ? toPrint.size() : 4);
            int i = 0;
            for (ArrayList<String> x : rowToPrint)
                x.add(0, "Action: " + (indexAction + i++));
            indexAction += rowToPrint.size();
            toRes.addAll(composeRow(rowToPrint, space, "|", "|"));
            toPrint.subList(0, toPrint.size() < 4 ? toPrint.size() : 4).clear();
        }
        toRes.add(breakRow(space + 1, "|", "-", "-"));
        return toRes;
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

    private void printView() {
        if (!needUpdate || !status)
            return;
        needUpdate = false;
        clearConsole();
        ArrayList<String> gameInfo, Actions;
        gameInfo = new ArrayList<>(Arrays.asList(breakRow(121, "-", "-", "-")));
        gameInfo.addAll(printGameInfo());
        gameInfo.addAll(printPlayerInfo());
        gameInfo.addAll(printBoardInfo());
        gameInfo.remove(gameInfo.size() - 1);
        gameInfo.add(breakRow(121, "-", "-", "-"));
        Actions = printActionInfo();
        if (Actions != null)
            printRow(composeRow(gameInfo, Actions));
        else
            printRow(gameInfo);
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