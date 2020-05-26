package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.Observable;

import java.util.stream.Collectors;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game extends Observable<String> {
    public final GameMode mode;
    private GamePhase phase;
    private List<Player> playerList;
    private int player;
    private List<God> godList;
    private final transient IslandBoard islandBoard;

    /**
     * Create a new game with the mode and players specified
     * 
     * @param mode    the game mode
     * @param players each player username
     */
    public Game(GameMode mode, ArrayList<String> players) throws IllegalArgumentException {
        godList = new ArrayList<God>();
        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size()
                && players.size() == mode.playersNum)
            playerList = players.stream().map(username -> new Player(username)).collect(Collectors.toList());
        else
            throw new IllegalArgumentException();

        this.mode = mode;
        islandBoard = new IslandBoard();
        phase = GamePhase.start();
        player = new Random().nextInt(playerList.size());
    }

    /**
     * Shift to next player
     */
    private void nextPlayer() {

        if (playerList.get(player).getStatusPlayer() != StatusPlayer.LOSE
                & playerList.get(player).getStatusPlayer() != StatusPlayer.WIN)
            playerList.get(player).setStatusPlayer(StatusPlayer.END);

        player = (player + 1) % playerList.size();

        /*
         * if (playerList.get(player).getStatusPlayer() == StatusPlayer.LOSE)
         * nextPlayer(); else
         */ if (playerList.get(player).getStatusPlayer() == StatusPlayer.END)
            playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);

    }

    /**
     * Check if the username is the current player
     * 
     * @param username username to check
     * @return the result of the check
     */
    private boolean isCurrentPlayer(String username) {
        return playerList.get(player).username.equals(username);
    }

    /**
     *
     * @return current free color
     */
    private List<Color> getColors() {
        List<Color> chosenColor = playerList.stream().map(e -> e.getColor()).filter(e -> e != null)
                .collect(Collectors.toList());
        List<Color> freeColor = Arrays.stream(Color.values()).filter(c -> !chosenColor.contains(c))
                .collect(Collectors.toList());
        return freeColor;
    }

    /**
     * Function to notify all player about game start with all instructions
     */
    public void start() {
        notify(createReport(new ArrayList<Command>()));
    }

    /**
     * Set a god for the current player
     * 
     * @param username player
     * @param god      to set
     */
    public void setGod(String username, God god) {
        if (phase == GamePhase.CHOOSE_GOD && isCurrentPlayer(username) && godList.contains(god)) {
            islandBoard.addGod(username, god);
            playerList.get(player).setGod(god);
            godList = godList.stream().filter(e -> e != god).collect(Collectors.toList());
            nextPlayer();
            if (godList.size() == 1) {
                setGod(playerList.get(player).username, godList.get(0));
                phase = phase.next();
            }
            notify(createReport(new ArrayList<Command>()));
        }
    }

    /**
     * Set gods to use in this game (one god at the time)
     * 
     * @param username player "god-like"
     * @param god      to set
     */
    public void setGodList(String username, God god) {
        if (phase == GamePhase.SET_GOD_LIST && isCurrentPlayer(username) && !godList.contains(god)
                && godList.size() < mode.playersNum) {
            godList.add(god);
            if (godList.size() == mode.playersNum) {
                phase = phase.next();
                nextPlayer();
            }
            notify(createReport(new ArrayList<Command>()));
        }
    }

    /**
     * 
     * @return A report in Json format converted to string, it contains all the
     *         information needed (ArrayList<Command>)
     */
    public String createReport(ArrayList<Command> report) {
        report.add(new Command("currentPlayer", playerList.get(player).username));
        report.add(new Command("gamePhase", phase.toString()));
        report.add(new Command("gameMode", mode.toString()));

        report.addAll(
                playerList.stream().map(e -> new Command("player", new Gson().toJson(e))).collect(Collectors.toList()));

        if (phase == GamePhase.SET_GOD_LIST)
            report.addAll(Arrays.stream(God.values()).filter(e -> e != God.STANDARD && !godList.contains(e))
                    .map(e -> new Command("god", "setGodList", e.toString(), e.toString()))
                    .collect(Collectors.toList()));

        if (phase == GamePhase.CHOOSE_GOD || phase == GamePhase.SET_GOD_LIST)
            report.addAll(godList.stream()
                    .map(e -> phase == GamePhase.CHOOSE_GOD
                            ? new Command("godList", "setGod", e.toString(), e.toString())
                            : new Command("godList", e.toString()))
                    .collect(Collectors.toList()));

        if (phase == GamePhase.SET_COLOR)
            report.addAll(getColors().stream().map(e -> new Command("color", "setColor", e.toString(), e.toString()))
                    .collect(Collectors.toList()));
        // todo convert to functional
        if (phase == GamePhase.CHOOSE_ACTION || phase == GamePhase.PENDING) {
            Action[][][] actions = islandBoard.getActions();

            for (int i = 0; i < actions.length; i++)
                for (int j = 0; j < actions[i].length; j++)
                    for (int k = 0; k < actions[i][j].length; k++)
                        if (actions[i][j][k].getStatus())
                            report.add(new Command("action", "chooseAction", new Gson().toJson(actions[i][j][k]),
                                    new Gson().toJson(new int[] { i * 5 + j, k })));
                        else
                            report.add(new Command("action", null, new Gson().toJson(actions[i][j][k]),
                                    new Gson().toJson(new int[] { i * 5 + j, k })));
        }
        // todo convert to functional
        Cell[][] board = islandBoard.getBoard();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                report.add(new Command("board",
                        ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING)
                                && board[i][j].getBlock().getTypeBlock() == TypeBlock.WORKER
                                && board[i][j].getBlock().getOwner().equals(playerList.get(player).username))
                                        ? "chooseWorker"
                                        : (phase == GamePhase.SET_WORKERS
                                                && board[i][j].getBlock().getTypeBlock() == TypeBlock.LEVEL0)
                                                        ? "setWorkers"
                                                        : null,
                        new Gson().toJson(board[i][j]), Integer.toString(i * 5 + j)));

        return new Gson().toJson(report);
    }

    /**
     * Set color for a player
     * 
     * @param username player
     * @param color    chosen
     */
    public void setColor(String username, Color color) {
        if (phase == GamePhase.SET_COLOR && isCurrentPlayer(username)) {
            playerList.get(player).setColor(color);
            phase = phase.next();
            notify(createReport(new ArrayList<Command>()));
        }
    }

    /**
     * Set/Place a worker for a player
     * 
     * @param username player
     * @param position worker position in (row * 5 + col) format
     */
    public void setWorkers(String username, int position) {
        if (phase == GamePhase.SET_WORKERS && isCurrentPlayer(username) && position < 25 && position >= 0) {
            int remainWorker = playerList.get(player).placeWoker();
            islandBoard.addWorker(username, playerList.get(player).getColor(),
                    new int[] { position / 5, position % 5 });
            if (remainWorker == 0) {
                nextPlayer();
                if (playerList.get(player).getColor() == null)
                    phase = phase.prev();
                else
                    phase = phase.next();
            }

            notify(createReport(new ArrayList<Command>()));
        }
    }

    /**
     * Choose a worker for a player
     * 
     * @param username player
     * @param position worker position in (row * 5 + col) format
     */
    public void chooseWorker(String username, int position) {
        if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING) && isCurrentPlayer(username)
                && position >= 0 && position < 25) {
            islandBoard.chooseWorker(username, new int[] { position / 5, position % 5 });
            if (phase == GamePhase.CHOOSE_WORKER)
                phase = phase.next();
            notify(createReport(new ArrayList<>(Arrays.asList(new Command("action", "chooseAction", null, null)))));
        }
    }

    /**
     * Use an action for a player
     * 
     * @param username player
     * @param position action position in [(row * 5 + col), dim] format
     */
    public void chooseAction(String username, int[] position) {
        if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING || phase == GamePhase.CHOOSE_ACTION)
                && isCurrentPlayer(username)
                && (position == null || (position[0] >= 0 && position[0] < 25 && position[1] >= 0))) {

            if (phase == GamePhase.PENDING && position != null)
                phase = phase.next();

            ReportAction reportAction = islandBoard.executeAction(playerList.get(player).username,
                    position == null ? null : new int[] { position[0] / 5, position[0] % 5, position[1] });

            playerList.get(player).setStatusPlayer(reportAction.statusPlayer);

            if (reportAction.statusPlayer == StatusPlayer.END || reportAction.statusPlayer == StatusPlayer.LOSE) {
                nextPlayer();
                phase = GamePhase.CHOOSE_WORKER;
                playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
            }
            notify(createReport(new ArrayList<>(Arrays.asList(new Command("playerStatus", reportAction.god.toString()),
                    new Command("action", "chooseAction", null, null)))));
        }
    }

    public void choosePlayer(String username, String targetUsername) {
        if (isCurrentPlayer(username) && !username.equals(targetUsername) && playerList.stream()
                .filter(e -> e.username.equals(targetUsername)).collect(Collectors.toList()).size() == 1) {

        }
    }
}