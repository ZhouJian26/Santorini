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
import it.polimi.ingsw.view.model.TypeAction;

/**
 * View Printer for CLI
 */
class ViewPrinter extends Observable<String> implements Observer<ArrayList<Command>> {
    /**
     * Player username
     */
    private String username;
    /**
     * Status if a update is needed due to server update or some user interaction
     */
    private boolean needUpdate;
    /**
     * Data Parser
     */
    private final Parser parser;
    /**
     * View Printer status
     */
    private boolean status;
    /**
     * Last command that user has selected
     */
    private Command lastCommand;
    /**
     * Set mode that the printer have to manage interaction with user
     */
    private boolean confirmMode = false;

    /**
     * ViewPrinter Constructor
     * 
     * @param parser data parser
     */
    public ViewPrinter(Parser parser) {
        this.parser = parser;
        status = false;
        needUpdate = false;
    }

    /**
     * Set Status
     * 
     * @param status status
     */
    public void setStatus(boolean status) {
        this.status = status;
        printView();
    }

    /**
     * Set username
     * 
     * @param username player username
     */
    public void setUsername(String username) {
        if (username == null || username.length() == 0)
            throw new NullPointerException();
        this.username = username;
    }

    /**
     * Clear console
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Create a string with the word centered
     * 
     * @param space string length
     * @param word  word to be centered
     * @return string of length space with the word centered
     */
    private String centerFill(int space, String word) {
        String out = String.format("%" + space + "s%s%" + space + "s", "", word, "");
        return out.substring((out.length() - space) / 2, (out.length() + space) / 2);
    }

    /**
     * Print in console a list of string
     * 
     * @param toPrint list of string to be printed
     */
    private void printRow(ArrayList<String> toPrint) {
        for (String x : toPrint)
            System.out.format("%3s%s\n", "", x);
    }

    /**
     * Make a collage between 2 arraylist of string cutting the first column of the
     * second arraylist. Simply cat row per row, centering the content in the middle
     * 
     * @param container_1 first arraylist of string
     * @param container_2 second arraylist of string
     * @return arraylist of previous 2 arraylist with a row per row cat and centered
     *         vertically
     */
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

    /**
     * Create a string
     * 
     * @param space   length of string
     * @param start   start char of the string
     * @param end     end char of the string
     * @param fill    fill of the string
     * @param padding white space betweeb start to fill and fill to end.
     * @return string created
     */
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

    /**
     * Create a string
     * 
     * @param space length of string
     * @param start start char of the string
     * @param end   end char of the string
     * @param fill  fill of the string
     * @return string created
     */
    private String breakRow(int space, String start, String end, String fill) {
        return breakRow(space, start, end, fill, 0);
    }

    /**
     * Create an arraylist of string centering the content in the middle with a
     * specific end and start char for each row
     * 
     * @param toPrint arraylist of string to be centered
     * @param space   final length of each string
     * @param start   start char character
     * @param end     end char character
     * @return arraylist of string created
     */
    private ArrayList<String> composeRow(List<ArrayList<String>> toPrint, int space, String start, String end) {
        ArrayList<String> toRes = new ArrayList<>();
        int maxH = toPrint.stream().map(e -> e.size()).max(Integer::compare).orElse(0);
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

    /**
     * Standard arraylist centering in the middle, with "|" as start and end char
     * and a prefidex 90 string length
     * 
     * @param toPrint arraylist of string to be centered
     * @return arraylist of string created
     */
    private ArrayList<String> composeRow(List<ArrayList<String>> toPrint) {
        return composeRow(toPrint, 90, "|", "|");
    }

    /**
     * Get Player Info as a arraylist of string
     * 
     * @return Player Info as a arraylist of string
     */
    private ArrayList<String> getPlayerInfo() {
        ArrayList<String> toRes = new ArrayList<>();
        toRes.addAll(composeRow(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("PLAYERS"))))));
        List<ArrayList<String>> toPrint = parser.getPlayers().stream().map(e -> {
            ArrayList<String> toRet = (ArrayList<String>) e.getRawData();
            if (e.getUsername().equals(username))
                toRet.add(0, "(You)");
            if (e.getUsername().equals(parser.getCurrentPlayer()))
                toRet.add(0, "- Current Player -");
            return toRet;
        }).collect(Collectors.toList());
        toRes.addAll(composeRow(toPrint));
        toRes.add(breakRow(91, "|", "|", "-", 3));
        return toRes;
    }

    /**
     * Get Board Info as a arraylist of string
     * 
     * @return Board Info as a arraylist of string
     */
    private ArrayList<String> getBoardInfo() {

        ArrayList<String> toRes = new ArrayList<>();
        toRes.addAll(composeRow(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("BOARD"))))));
        Cell[][] toPrint = parser.getBoard();
        int position = 0;
        for (Cell[] row : toPrint) {
            ArrayList<ArrayList<String>> toPrintRow = new ArrayList<>();
            String braker = "|";
            for (Cell cell : row) {
                ArrayList<String> toPush = (ArrayList<String>) cell.getRawData();
                toPush.add("[" + position + "]");
                toPrintRow.add(toPush);
                braker += breakRow(19, "", " ", "-", 3);
                position++;
            }
            braker = braker.substring(0, braker.length() - 1) + "|";
            // toRes.add(breakRow(121, "|", "|", "-", 3));
            toRes.addAll(composeRow(toPrintRow));

            toRes.add(braker);
        }
        return toRes;
    }

    /**
     * Get Player Action Info as a arraylist of string
     * 
     * @return Player Action Info as a arraylist of string
     */
    private ArrayList<String> getActionInfo() {
        if (username == null)
            return null;
        int space = 12 * 7;
        ArrayList<String> toRes = new ArrayList<>();
        toRes.add(breakRow(space + 1, ".", ".", "-"));
        if (!parser.getGamePhase().equals("END") && username.equals(parser.getCurrentPlayer())) {
            toRes.addAll(composeRow(
                    new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("ACTIONS", "It's your turn!")))), space,
                    "|", "|"));
            ArrayList<ArrayList<String>> toPrint = (ArrayList<ArrayList<String>>) parser.getUsableCommandList().stream()
                    .map(e -> {
                        ArrayList<String> toRet;
                        switch (e.getType()) {
                            case "action":
                                if (e.getInfo() == null)
                                    toRet = new ArrayList<>(Arrays.asList("End Turn"));
                                else if (new Gson().fromJson(e.getInfo(), TypeAction.class).getTypeAction()
                                        .equals("Swap"))
                                    toRet = new Gson().fromJson(e.getInfo(), Swap.class).getRawData();
                                else
                                    toRet = (ArrayList<String>) new Gson().fromJson(e.getInfo(), Build.class)
                                            .getRawData();
                                break;
                            case "board":
                                toRet = (ArrayList<String>) new Gson().fromJson(e.getInfo(), Cell.class).getRawData();
                                toRet.add("[" + e.getFuncData() + "]");
                                break;
                            case "color":
                                toRet = (ArrayList<String>) new Color(e.getInfo()).getRawData();
                                break;
                            case "god":
                                toRet = (ArrayList<String>) new God(e.getInfo()).getRawData();
                                break;
                            case "godList":
                                toRet = (ArrayList<String>) new God(e.getInfo()).getRawData();
                                break;
                            case "player":
                                toRet = (ArrayList<String>) new Gson().fromJson(e.getInfo(), Player.class).getRawData();
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
                        toPrint.size() < 3 ? toPrint.size() : 3);
                int i = 0;
                for (ArrayList<String> x : rowToPrint)
                    x.add(0, "Action: " + (indexAction + i++));
                indexAction += rowToPrint.size();
                toRes.addAll(composeRow(rowToPrint, space, "|", "|"));
                toRes.add(breakRow(space + 1, "|", "|", "-", 3));
                toPrint.subList(0, toPrint.size() < 3 ? toPrint.size() : 3).clear();
            }
            toRes.remove(toRes.size() - 1);
        } else if (parser.getPlayers().stream()
                .anyMatch(e -> e.getUsername().equals(username) && e.getStatus().equals("WIN"))) {
            ArrayList<String> winAsci = new ArrayList<>(
                    Arrays.asList("__          ___       ", "\\ \\        / (_)      ", " \\ \\  /\\  / / _ _ __  ",
                            "  \\ \\/  \\/ / | | '_ \\ ", "   \\  /\\  /  | | | | |", "    \\/  \\/   |_|_| |_|"));
            toRes.addAll(composeRow(new ArrayList<>(Arrays.asList(winAsci)), space, "|", "|"));

        } else if (parser.getPlayers().stream()
                .anyMatch(e -> e.getUsername().equals(username) && e.getStatus().equals("LOSE"))) {
            ArrayList<String> loseAsci = new ArrayList<>(
                    Arrays.asList(" _                    ", "| |                   ", "| |     ___  ___  ___ ",
                            "| |    / _ \\/ __|/ _ \\", "| |___| (_) \\__ \\  __/", "|______\\___/|___/\\___|"));
            toRes.addAll(composeRow(new ArrayList<>(Arrays.asList(loseAsci)), space, "|", "|"));
        } else {
            toRes.addAll(composeRow(
                    new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList("ACTIONS", "It's not your turn!")))),
                    space, "|", "|"));
        }
        toRes.add(breakRow(space + 1, "'", "'", "-"));
        return toRes;
    }

    /**
     * Get a Player Action Command based on the index
     * 
     * @param index command index
     * @return command selected, otherwise null
     */
    private Command getActionString(int index) {
        try {
            return parser.getUsableCommandList().get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Use a Player Action Command based on the index
     * 
     * @param index command index
     */
    public void useAction(String index) {
        index = index.trim();
        if (index.toUpperCase().equals("QUIT")) {
            status = false;
            return;
        }
        needUpdate = true;
        Command toSend = null;
        try {
            toSend = getActionString(Integer.parseInt(index));
            if (toSend != null && (toSend.getFuncName().equals("setGodList") || toSend.getFuncName().equals("setGod"))
                    && (lastCommand == null || !new Gson().toJson(lastCommand).equals(new Gson().toJson(toSend)))) {
                confirmMode = true;
                printView();
                System.out.print("\n   God " + toSend.getFuncData() + " effect:\n\n      "
                        + GodEffect.strConverter(toSend.getFuncData()).getEffect()
                        + "\n\n   Retype action code to confirm or Type another God to change: ");
                lastCommand = toSend;
                return;
            }
            confirmMode = false;
        } catch (Exception e) {
            // fail parse
        }
        if (toSend != null)
            notify(Parser.toString(toSend));
        else
            printView();
    }

    /**
     * Print view
     */
    private synchronized void printView() {
        if (!needUpdate || !status)
            return;
        needUpdate = false;
        clearConsole();

        printLogo();
        ArrayList<String> gameInfo, Actions;
        gameInfo = new ArrayList<>(Arrays.asList(breakRow(91, ".", ".", "-")));
        // gameInfo.addAll(printGameInfo());
        gameInfo.addAll(getPlayerInfo());
        gameInfo.addAll(getBoardInfo());
        gameInfo.remove(gameInfo.size() - 1);
        gameInfo.add(breakRow(91, "'", "'", "-"));
        Actions = getActionInfo();

        if (Actions != null)
            printRow(composeRow(gameInfo, Actions));
        else
            printRow(gameInfo);

        if (parser.getGamePhase().equals("END"))
            System.out.println("   Game ended");

        System.out.print("   Type QUIT to exit from the game\n    ");

        if (!confirmMode && username.equals(parser.getCurrentPlayer()) && !parser.getGamePhase().equals("END"))
            System.out.print("   Type Action number: ");

        if (!username.equals(parser.getCurrentPlayer())
                && (parser.getGamePhase().equals("SET_GOD_LIST") || parser.getGamePhase().equals("CHOOSE_GOD"))) {
            List<Command> godList = parser.getCommandList("godList");
            if (godList.size() == 0)
                return;
            System.out.println("\n   God to use in this Game:");
            parser.getCommandList("godList").forEach(e -> {
                System.out.println(
                        "\n      God " + e.getInfo() + "\n\n      " + GodEffect.strConverter(e.getInfo()).getEffect());
            });
        }
    }

    @Override
    public void update(ArrayList<Command> message) {
        // System.out.println("view: "+ message);
        if (message == null || message.size() == 0)
            return;
        needUpdate = true;
        printView();
    }

    /**
     * Print Logo
     */
    public static void printLogo() {
        ArrayList<String> logoAsci = new ArrayList<>(Arrays.asList(
                "  .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------. ",
                " | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |",
                " | |    _______   | || |      __      | || | ____  _____  | || |  _________   | || |     ____     | || |  _______     | || |     _____    | || | ____  _____  | || |     _____    | |",
                " | |   /  ___  |  | || |     /  \\     | || ||_   \\|_   _| | || | |  _   _  |  | || |   .'    `.   | || | |_   __ \\    | || |    |_   _|   | || ||_   \\|_   _| | || |    |_   _|   | |",
                " | |  |  (__ \\_|  | || |    / /\\ \\    | || |  |   \\ | |   | || | |_/ | | \\_|  | || |  /  .--.  \\  | || |   | |__) |   | || |      | |     | || |  |   \\ | |   | || |      | |     | |",
                " | |   '.___`-.   | || |   / ____ \\   | || |  | |\\ \\| |   | || |     | |      | || |  | |    | |  | || |   |  __ /    | || |      | |     | || |  | |\\ \\| |   | || |      | |     | |",
                " | |  |`\\____) |  | || | _/ /    \\ \\_ | || | _| |_\\   |_  | || |    _| |_     | || |  \\  `--'  /  | || |  _| |  \\ \\_  | || |     _| |_    | || | _| |_\\   |_  | || |     _| |_    | |",
                " | |  |_______.'  | || ||____|  |____|| || ||_____|\\____| | || |   |_____|    | || |   `.____.'   | || | |____| |___| | || |    |_____|   | || ||_____|\\____| | || |    |_____|   | |",
                " | |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |",
                " | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |",
                "  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' "));
        System.out.print("\n\n");
        for (String x : logoAsci)
            System.out.println(x);
        System.out.print("\n\n");

    }
}