package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.TypeCommand;
import it.polimi.ingsw.utils.Observable;

import java.util.stream.Collectors;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game extends Observable<String> {
    public final GameMode mode;
    private GamePhase phase;
    private List<Player> playerList;
    private int player;
    private List<God> godList;
    private IslandBoard islandBoard;

    /**
     * Create a new game with the mode and players specified
     *
     * @param mode    the game mode
     * @param players each player username
     */
    public Game(GameMode mode, List<String> players) throws IllegalArgumentException {
        godList = new ArrayList<>();
        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size()
                && players.size() == mode.playersNum)
            playerList = players.stream().map(Player::new).collect(Collectors.toList());
        else
            throw new IllegalArgumentException();

        this.mode = mode;
        islandBoard = new IslandBoard();
        phase = GamePhase.start();
        player = new Random().nextInt(playerList.size());
        playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
    }

    public void quitPlayer(String username) {
        if (playerList.stream()
                .anyMatch(e -> e.username.equals(username) && e.getStatusPlayer() != StatusPlayer.LOSE)) {
            phase = GamePhase.END;
            notify(createReport(new ArrayList<Command>()));
        }
    }

    /**
     * Shift to next player
     */
    private void nextPlayer() {
        if (playerList.get(player).getStatusPlayer() != StatusPlayer.LOSE
                && playerList.get(player).getStatusPlayer() != StatusPlayer.WIN)
            playerList.get(player).setStatusPlayer(StatusPlayer.IDLE);
        if (playerList.stream().filter(e -> e.getStatusPlayer() == StatusPlayer.WIN).findAny().isEmpty()
                && playerList.stream().filter(e -> e.getStatusPlayer() == StatusPlayer.IDLE)
                        .collect(Collectors.toList()).size() > 1) {
            // at least 2 player
            while ((player = (player + 1) % playerList.size()) >= 0
                    && playerList.get(player).getStatusPlayer() != StatusPlayer.IDLE)
                ;
            playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
        } else {
            playerList = playerList.stream().map(e -> {
                if (e.getStatusPlayer() == StatusPlayer.IDLE)
                    e.setStatusPlayer(StatusPlayer.WIN);
                return e;
            }).collect(Collectors.toList());
        }
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
     * @return current free color
     */
    private List<Color> getColors() {
        List<Color> chosenColor = playerList.stream().map(Player::getColor).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return Arrays.stream(Color.values()).filter(c -> !chosenColor.contains(c)).collect(Collectors.toList());
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

            if (!godList.isEmpty())
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
     * @return A report in Json format converted to string, it contains all the
     *         information needed (ArrayList<Command>)
     */
    private String createReport(ArrayList<Command> report) {
        report.add(new Command(TypeCommand.currentPlayer.toString(), playerList.get(player).username));
        report.add(new Command(TypeCommand.gamePhase.toString(), phase.toString()));
        report.add(new Command(TypeCommand.gameMode.toString(), mode.toString()));

        report.addAll(playerList.stream()
                .map(e -> (phase == GamePhase.START_PLAYER)
                        ? new Command(TypeCommand.player.toString(), "setStartPlayer", new Gson().toJson(e), e.username)
                        : new Command(TypeCommand.player.toString(), new Gson().toJson(e)))
                .collect(Collectors.toList()));

        if (phase == GamePhase.SET_GOD_LIST)
            report.addAll(Arrays.stream(God.values()).filter(e -> e != God.STANDARD && !godList.contains(e))
                    .map(e -> new Command(TypeCommand.god.toString(), "setGodList", e.toString(), e.toString()))
                    .collect(Collectors.toList()));

        if (phase == GamePhase.CHOOSE_GOD || phase == GamePhase.SET_GOD_LIST)
            report.addAll(godList.stream()
                    .map(e -> phase == GamePhase.CHOOSE_GOD
                            ? new Command(TypeCommand.godList.toString(), "setGod", e.toString(), e.toString())
                            : new Command(TypeCommand.godList.toString(), e.toString()))
                    .collect(Collectors.toList()));

        if (phase == GamePhase.SET_COLOR)
            report.addAll(getColors().stream()
                    .map(e -> new Command(TypeCommand.color.toString(), "setColor", e.toString(), e.toString()))
                    .collect(Collectors.toList()));

        if (phase == GamePhase.CHOOSE_ACTION || phase == GamePhase.PENDING) {
            Action[][][] actions = islandBoard.getActions();

            for (int i = 0; i < actions.length; i++)
                for (int j = 0; j < actions[i].length; j++)
                    for (int k = 0; k < actions[i][j].length; k++)
                        if (actions[i][j][k].getStatus())
                            report.add(new Command(TypeCommand.action.toString(), "chooseAction",
                                    new Gson().toJson(actions[i][j][k]),
                                    new Gson().toJson(new int[] { i * 5 + j, k })));
                        else
                            report.add(new Command(TypeCommand.action.toString(), null,
                                    new Gson().toJson(actions[i][j][k]),
                                    new Gson().toJson(new int[] { i * 5 + j, k })));
        }

        Cell[][] board = islandBoard.getBoard();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                String funcName = null;

                if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING)
                        && board[i][j].getBlock().getTypeBlock() == TypeBlock.WORKER
                        && board[i][j].getBlock().getOwner().equals(playerList.get(player).username))
                    funcName = "chooseWorker";
                else if ((phase == GamePhase.SET_WORKERS && board[i][j].getBlock().getTypeBlock() == TypeBlock.LEVEL0))
                    funcName = "setWorkers";

                report.add(new Command(TypeCommand.board.toString(), funcName, new Gson().toJson(board[i][j]),
                        Integer.toString(i * 5 + j)));
            }

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
            notify(createReport(new ArrayList<>(
                    Arrays.asList(new Command(TypeCommand.action.toString(), "chooseAction", null, null)))));
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
            notify(createReport(calculateActionResult(reportAction)));
        }
    }

    private ArrayList<Command> calculateActionResult(ReportAction reportAction) {
        ArrayList<Command> toRes = new ArrayList<>(
                Arrays.asList(new Command(TypeCommand.playerStatus.toString(), reportAction.god.toString())));
        if (reportAction.statusPlayer == StatusPlayer.IDLE || reportAction.statusPlayer == StatusPlayer.LOSE) {
            phase = GamePhase.CHOOSE_WORKER;
            nextPlayer();
            if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING) {
                reportAction = islandBoard.executeAction(playerList.get(player).username, null);
                playerList.get(player).setStatusPlayer(reportAction.statusPlayer);
                toRes = new ArrayList<>(
                        Arrays.asList(new Command(TypeCommand.playerStatus.toString(), reportAction.god.toString())));
            }
        } else if (reportAction.statusPlayer == StatusPlayer.WIN)
            phase = GamePhase.END;
        else {
            toRes.add(new Command(TypeCommand.action.toString(), "chooseAction", null, null));
        }
        return toRes;
    }

    public void choosePlayer(String username, String targetUsername) {
        if (phase == GamePhase.START_PLAYER && isCurrentPlayer(username) && playerList.stream()
                .filter(e -> e.username.equals(targetUsername)).collect(Collectors.toList()).size() == 1) {
            playerList.get(player).setStatusPlayer(StatusPlayer.IDLE);
            player = playerList.indexOf(playerList.stream().filter(e -> e.username.equals(targetUsername))
                    .collect(Collectors.toList()).get(0));
            playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
            phase = phase.next();
            notify(createReport(new ArrayList<Command>()));
        }
    }

}