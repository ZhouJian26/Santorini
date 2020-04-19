package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.view.Observable;

import java.util.stream.Collectors;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends Observable<String> {
    private GameMode mode;
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
    public Game(GameMode mode, ArrayList<String> players) throws IllegalArgumentException {
        godList = new ArrayList<God>();
        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size()
                && players.size() == GameMode.playersNum(mode))
            playerList = players.stream().map(username -> new Player(username)).collect(Collectors.toList());
        else
            throw new IllegalArgumentException();

        this.mode = mode;
        islandBoard = new IslandBoard();
        phase = GamePhase.start();
    }

    private void nextPlayer() {
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.WIN)
            return;
        player++;
        if (playerList.get(player % playerList.size()).getStatusPlayer() == StatusPlayer.LOSE)
            nextPlayer();
        else
            notify(createReport());
    }

    private boolean isCurrentPlayer(String username) {
        return playerList.get(player).getUsername().equals(username);
    }

    private List<Color> getColors() {
        List<Color> choosenColor = playerList.stream().map(e -> e.getColor()).filter(e -> e != null)
                .collect(Collectors.toList());
        List<Color> freeColor = Arrays.stream(Color.values()).filter(c -> !choosenColor.contains(c))
                .collect(Collectors.toList());
        return freeColor;
    }

    public void setGod(String username, God god) {
        if (phase == GamePhase.CHOOSE_GOD && isCurrentPlayer(username) && godList.contains(god)) {
            islandBoard.addGod(username, god);
            playerList.get(player).setGod(god);
            godList = godList.stream().filter(e -> e != god).collect(Collectors.toList());
            nextPlayer();
            if (godList.size() == 1) {
                setGod(playerList.get(player).getUsername(), godList.get(0));
                phase = phase.next();
            }
        }
    }

    public void setGodList(String username, God god) {
        if (phase == GamePhase.SET_GOD_LIST && isCurrentPlayer(username) && !godList.contains(god)
                && godList.size() < GameMode.playersNum(mode)) {
            godList.add(god);
            if (godList.size() == GameMode.playersNum(mode)) {
                phase = phase.next();
                nextPlayer();
            }
        }
    }

    public String createReport() {

        ArrayList<Command> report = new ArrayList<>();
        report.add(new Command("currentPlayer", playerList.get(player).getUsername()));
        report.add(new Command("gamePhase", phase.toString()));
        report.add(new Command("gameMode", mode.toString()));
        // todo info complete di god??
        if (phase == GamePhase.SET_GOD_LIST)
            report.addAll(Arrays.stream(God.values()).filter(e -> e != God.STANDARD)
                    .map(e -> new Command("god", e.toString())).collect(Collectors.toList()));

        if (phase == GamePhase.CHOOSE_GOD || phase == GamePhase.SET_GOD_LIST)
            report.addAll(godList.stream().map(e -> new Command("godList",
                    phase == GamePhase.CHOOSE_GOD ? "setGod" : "setGodList", e.toString(), e.toString()))
                    .collect(Collectors.toList()));

        if (phase == GamePhase.SET_COLOR)
            report.addAll(getColors().stream().map(e -> new Command("color", "setColor", e.toString(), e.toString()))
                    .collect(Collectors.toList()));

        report.addAll(
                playerList.stream().map(e -> new Command("player", new Gson().toJson(e))).collect(Collectors.toList()));

        try {
            Cell[][] board = islandBoard.getBoard();
            for (int i = 0; i < board.length; i++)
                for (int j = 0; j < board[i].length; j++)
                    report.add(new Command("board",
                            phase == GamePhase.CHOOSE_WORKER ? "chooseWorker"
                                    : (phase == GamePhase.SET_WORKERS) ? "setWorkers" : null,
                            new Gson().toJson(board[i][i]), Integer.toString(i * 5 + j)));

            if (phase == GamePhase.CHOOSE_ACTION || phase == GamePhase.PENDING) {
                Action[][][] actions = islandBoard.getActions();
                for (int i = 0; i < actions.length; i++)
                    for (int j = 0; j < actions[i].length; j++)
                        for (int k = 0; k < actions[i][j].length; k++)
                            if (actions[i][j][k].getStatus())
                                report.add(new Command("action", "chooseAction", new Gson().toJson(actions[i][j][k]),
                                        new Gson().toJson(new int[] { i * 5 + j, k })));
            }
        } catch (Exception e) {
        }
        return new Gson().toJson(report);
    }

    public void setColor(String username, Color color) {
        if (phase == GamePhase.SET_COLOR && isCurrentPlayer(username) && playerList.get(player).getColor() == null) {
            playerList.get(player).setColor(color);
            phase = phase.next();
            notify(createReport());
        }
    }

    public void setWorkers(String username, int position) {
        if (phase == GamePhase.SET_WORKERS && isCurrentPlayer(username) && position < 25 && position >= 0) {
            int remainWorker = playerList.get(player).placeWoker();
            islandBoard.addWorker(username, playerList.get(player).getColor(),
                    new int[] { position / 5, position % 5 });
            if (remainWorker == 0)
                nextPlayer();

            if (playerList.get(player).getColor() == null)
                phase = phase.prev();
            else
                phase = phase.next();

            notify(createReport());
        }
    }

    public void chooseWorker(String username, int position) {
        if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING) && isCurrentPlayer(username)
                && position >= 0 && position < 25) {
            islandBoard.chooseWorker(username, new int[] { position / 5, position - position / 5 });
            phase = phase.next();
            notify(createReport());
        }
    }

    public void chooseAction(String username, int[] position) {
        if (phase == GamePhase.PENDING)
            phase = phase.next();

        if (phase == GamePhase.CHOOSE_ACTION && isCurrentPlayer(username) && position[0] >= 0 && position[0] < 25
                && position[1] >= 0) {
            StatusPlayer playerStatus = islandBoard
                    .executeAction(new int[] { position[0] / 5, position[0] - position[0] / 5, position[1] });
            playerList.get(player).setStatusPlayer(playerStatus);
            if (playerStatus == StatusPlayer.END) {
                nextPlayer();
                phase = GamePhase.CHOOSE_WORKER;
            }
            notify(createReport());
        }
    }
}