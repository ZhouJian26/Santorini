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

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String centerFill(int space, String word) {
        String out = String.format("%" + space + "s%s%" + space + "s", "", word, "");
        return out.substring((out.length() - space) / 2, (out.length() + space) / 2);
    }

    private void printRow(ArrayList<String> toPrint) {
        for (String x : toPrint)
            System.out.format("%5s%s\n", "", x);
    }

    private ArrayList<String> composeRow(ArrayList<String> container_1, ArrayList<String> container_2) {
        String patternTop = container_1.get(0) + container_2.get(0).substring(1, container_2.get(0).length());
        String patternBottom = container_1.get(container_1.size() - 1) + container_2.get(container_2.size() - 1)
                .substring(1, container_2.get(container_2.size() - 1).length());
        container_1.remove(0);
        container_2.remove(0);
        container_1.remove(container_1.size() - 1);
        container_2.remove(container_2.size() - 1);
        int maxH = Math.max(container_1.size(), container_2.size());
        ArrayList<String> toRes = new ArrayList<>();
        toRes.add(patternTop);
        for (int index = 0; index < maxH; index++) {
            String toAdd = "";
            if (index - (maxH - container_1.size()) / 2 >= 0 && index < (maxH + container_1.size()) / 2)
                toAdd += container_1.get(index - (maxH - container_1.size()) / 2);
            else
                toAdd += "|" + String.format("%" + (container_1.get(0).length() - 1) + "s", "|");

            if (index - (maxH - container_2.size()) / 2 >= 0 && index < (maxH + container_2.size()) / 2)
                toAdd += container_2.get(index - (maxH - container_2.size()) / 2).substring(1,
                        container_2.get(index - (maxH - container_2.size()) / 2).length());
            else
                toAdd += String.format("%" + (container_2.get(0).length() - 1) + "s", "|");
            toRes.add(toAdd);
        }
        toRes.add(patternBottom);
        return toRes;
    }

    private String breakRow(int space, String start, String end, String fill, int padding) {
        String toRes = "";
        space -= 2 * (1 + padding);
        for (int i = 0; i < space; i++)
            toRes += fill;
        toRes = toRes.substring(0, space);
        if (padding == 0)
            return start + toRes + end;
        else
            return String.format("%s%" + padding + "s%s%" + padding + "s%s", start, "", toRes, "", end);
    }

    private String breakRow(int space, String start, String end, String fill) {
        return breakRow(space, start, end, fill, 0);
    }

    private ArrayList<String> composeRow(List<ArrayList<String>> toPrint, int space, String start, String end) {
        ArrayList<String> toRes = new ArrayList<>();
        int maxH = toPrint.stream().map(e -> e.size()).max(Integer::compare).get();
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
            toAdd = toAdd.substring(0, toAdd.length() - 1) + end;
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
        toRes.addAll(composeRow(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("PLAYERS"))))));
        List<ArrayList<String>> toPrint = parser.getPlayers().stream().map(e -> {
            ArrayList<String> toRet = e.getRawData();
            if (e.username.equals(username))
                toRet.add(0, "(You)");
            if (e.username.equals(parser.getCurrentPlayer()))
                toRet.add(0, "- Current Player -");
            return toRet;
        }).collect(Collectors.toList());
        toRes.addAll(composeRow(toPrint));
        toRes.add(breakRow(121, "|", "|", "-", 3));
        return toRes;
    }

    private ArrayList<String> printBoardInfo() {

        ArrayList<String> toRes = new ArrayList<>();
        toRes.addAll(composeRow(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("BOARD"))))));
        Cell[][] toPrint = parser.getBoard();
        int position = 0;
        for (Cell[] row : toPrint) {
            ArrayList<ArrayList<String>> toPrintRow = new ArrayList<>();
            String braker = "|";
            for (Cell cell : row) {
                ArrayList<String> toPush = cell.getRawData();
                toPush.add("[" + position / 5 + "," + position % 5 + "]");
                toPrintRow.add(toPush);
                braker += breakRow(25, "", " ", "-", 3);
                position++;
            }
            braker = braker.substring(0, braker.length() - 1) + "|";
            // toRes.add(breakRow(121, "|", "|", "-", 3));
            toRes.addAll(composeRow(toPrintRow));

            toRes.add(braker);
        }
        return toRes;
    }

    private ArrayList<String> printActionInfo() {
        if (username == null)
            return null;
        int space = 12 * 8;
        ArrayList<String> toRes = new ArrayList<>();
        toRes.add(breakRow(space + 1, ".", ".", "-"));
        if (username.equals(parser.getCurrentPlayer())) {
            toRes.addAll(composeRow(
                    new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("ACTIONS", "It's your turn!")))), space,
                    "|", "|"));
            ArrayList<ArrayList<String>> toPrint = (ArrayList<ArrayList<String>>) parser.getUsableCommandList().stream()
                    .map(e -> {
                        ArrayList<String> toRet;
                        switch (e.type) {
                            case "action":
                                if (e.funcName.equals("chooseAction"))
                                    if (e.info == null)
                                        toRet = new ArrayList<>(Arrays.asList("End Turn"));
                                    else if (new Gson().fromJson(e.info, TypeAction.class).TypeAction.equals("Swap"))
                                        toRet = new Gson().fromJson(e.info, Swap.class).getRawData();
                                    else
                                        toRet = new Gson().fromJson(e.info, Build.class).getRawData();
                                else
                                    toRet = new ArrayList<>(Arrays.asList("Undo Action"));
                                break;
                            case "board":
                                toRet = new Gson().fromJson(e.info, Cell.class).getRawData();
                                toRet.add("[" + Integer.parseInt(e.funcData) / 5 + ","
                                        + Integer.parseInt(e.funcData) % 5 + "]");
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
                            case "player":
                                toRet = new Gson().fromJson(e.info, Player.class).getRawData();
                                break;
                            default:
                                toRet = null;
                                break;
                        }
                        return toRet;
                    }).filter(e -> e != null && e.size() > 0).collect(Collectors.toList());
            int indexAction = 0;
            while (toPrint.size() > 0) {
                List<ArrayList<String>> rowToPrint = (List<ArrayList<String>>) toPrint.subList(0,
                        toPrint.size() < 4 ? toPrint.size() : 4);
                int i = 0;
                for (ArrayList<String> x : rowToPrint)
                    x.add(0, "Action: " + (indexAction + i++));
                indexAction += rowToPrint.size();
                toRes.addAll(composeRow(rowToPrint, space, "|", "|"));
                toRes.add(breakRow(space + 1, "|", "|", "-", 3));
                toPrint.subList(0, toPrint.size() < 4 ? toPrint.size() : 4).clear();
            }
            toRes.remove(toRes.size() - 1);
        } else {
            toRes.addAll(composeRow(
                    new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("ACTIONS", "It's not your turn!")))),
                    space, "|", "|"));
        }
        toRes.add(breakRow(space + 1, "'", "'", "-"));
        return toRes;
    }

    private String getActionString(int index) {
        try {
            return new Gson().toJson(parser.getUsableCommandList().get(index));
        } catch (Exception e) {
            return null;
        }
    }

    public void useAction(int index) {
        try {
            String toSend = getActionString(index);
            needUpdate = true;
            printView();
            if (toSend != null)
                notify(toSend);
        } catch (Exception e) {
        }
    }

    private synchronized void printView() {
        if (!needUpdate || !status)
            return;
        needUpdate = false;
        clearConsole();

        printLogo();
        ArrayList<String> gameInfo, Actions;
        gameInfo = new ArrayList<>(Arrays.asList(breakRow(121, ".", ".", "-")));
        // gameInfo.addAll(printGameInfo());
        gameInfo.addAll(printPlayerInfo());
        gameInfo.addAll(printBoardInfo());
        gameInfo.remove(gameInfo.size() - 1);
        gameInfo.add(breakRow(121, "'", "'", "-"));
        Actions = printActionInfo();
        if (Actions != null)
            printRow(composeRow(gameInfo, Actions));
        else
            printRow(gameInfo);
    }

    @Override
    public void update(ArrayList<Command> message) {
        // based on setted view, print it
        // System.out.println("viewPrinter: " + message);
        if (message == null)
            return;
        needUpdate = true;
        printView();
    }

    public static void printLogo() {
        System.out.print("\n");
        System.out.format("%205s",
                "   .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------. \n");
        System.out.format("%205s",
                "  | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n");
        System.out.format("%205s",
                "  | |    _______   | || |      __      | || | ____  _____  | || |  _________   | || |     ____     | || |  _______     | || |     _____    | || | ____  _____  | || |     _____    | |\n");
        System.out.format("%205s",
                "  | |   /  ___  |  | || |     /  \\     | || ||_   \\|_   _| | || | |  _   _  |  | || |   .'    `.   | || | |_   __ \\    | || |    |_   _|   | || ||_   \\|_   _| | || |    |_   _|   | |\n");
        System.out.format("%205s",
                "  | |  |  (__ \\_|  | || |    / /\\ \\    | || |  |   \\ | |   | || | |_/ | | \\_|  | || |  /  .--.  \\  | || |   | |__) |   | || |      | |     | || |  |   \\ | |   | || |      | |     | |\n");
        System.out.format("%205s",
                "  | |   '.___`-.   | || |   / ____ \\   | || |  | |\\ \\| |   | || |     | |      | || |  | |    | |  | || |   |  __ /    | || |      | |     | || |  | |\\ \\| |   | || |      | |     | |\n");
        System.out.format("%205s",
                "  | |  |`\\____) |  | || | _/ /    \\ \\_ | || | _| |_\\   |_  | || |    _| |_     | || |  \\  `--'  /  | || |  _| |  \\ \\_  | || |     _| |_    | || | _| |_\\   |_  | || |     _| |_    | |\n");
        System.out.format("%205s",
                "  | |  |_______.'  | || ||____|  |____|| || ||_____|\\____| | || |   |_____|    | || |   `.____.'   | || | |____| |___| | || |    |_____|   | || ||_____|\\____| | || |    |_____|   | |\n");
        System.out.format("%205s",
                "  | |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n");
        System.out.format("%205s",
                "  | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n");
        System.out.format("%205s",
                "   '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' \n");
        System.out.print("\n");
    }
}