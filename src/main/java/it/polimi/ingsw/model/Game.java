package it.polimi.ingsw.model;

import it.polimi.ingsw.view.Observable;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    1) setGodList() -> [DONE]
    2) setGod() -> [DONE]
    3) setWorkers() -> [DONE]
    4) chooseWorker() -> [DONE]
    5) execureAction() -> [DONE]
    6) Game() -> [DONE]
    7) getActions() -> [DONE] ni
    8) getBoard() -> [DONE] ni
    9) getGods() -> [DONE]
    10) getPlayers() [DONE]
    11) getMode() [DONE]
    12) getPlayer() [DONE]

    13) Implement custom Observable for Game
*/
public class Game extends Observable<Message> {
    private GameMode mode;
    private GamePhase phase;
    private List<Player> playerList;
    private int player;
    private List<God> godList;
    private IslandBoard islandBoard;
    private boolean changeWorker;

    /**
     * Create a new game with the mode and players specified
     * 
     * @param mode    the game mode
     * @param players each player username
     */
    public Game(GameMode mode, ArrayList<String> players) throws IllegalArgumentException {

        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size())
            playerList = players.stream().map(username -> new Player(username)).collect(Collectors.toList());
        else
            throw new IllegalArgumentException();

        this.mode = mode;
        islandBoard = new IslandBoard();
        phase = GamePhase.start();
    }

    private List<Color> choosenColor() {
        List<Color> colorList = playerList.stream().map(e -> e.getColor()).filter(e -> e != null)
                .collect(Collectors.toList());
        return colorList;
    }

    private void nextPlayer() {
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.WIN)
            return;
        player++;
        if (player == playerList.size())
            player = 0;
        changeWorker = true;
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.LOSE)
            nextPlayer();
    }

    private boolean isCurrentPlayer(String username) {
        return playerList.get(player).getUsername().equals(username);
    }

    public List<Color> getColors() {
        List<Color> choosenColor = choosenColor();
        List<Color> freeColor = Arrays.stream(Color.values()).filter(c -> !choosenColor.contains(c))
                .collect(Collectors.toList());
        return freeColor;
    }

    /**
     * @return a god list of the current mode or current god to be choose, based on
     *         gamephase
     */
    public God[] getGod() {
        if (godList.size() > 0)
            return (God[]) godList.toArray();
        return God.values();
    }

    /**
     * 
     * @return game mode
     */
    public GameMode getGameMode() {
        return mode;
    }

    public void setGod(String username, God god) {
        if (phase == GamePhase.CHOOSE_GOD && isCurrentPlayer(username)) {
            islandBoard.addGod(username, god);
            godList = godList.stream().filter(e -> e != god).collect(Collectors.toList());
            nextPlayer();
            if (godList.size() == 1) {
                setGod(playerList.get(player).getUsername(), godList.get(0));
                phase = phase.next();
            }
        }
    }

    /**
     * 
     * @return a clone of current player
     */
    public Player getPlayer() {
        return playerList.get(player).clone();
    }

    /**
     * 
     * @return a clone of players
     */
    public List<Player> getPlayers() {
        List<Player> players = playerList.stream().map(player -> player.clone()).collect(Collectors.toList());
        return players;
    }

    public void setGodList(String username, God[] godList) {
        if (phase == GamePhase.SET_GOD_LIST && isCurrentPlayer(username)
                && Arrays.stream(godList).distinct().collect(Collectors.toList()).size() == GameMode.playersNum(mode)) {
            this.godList = Arrays.stream(godList).distinct().collect(Collectors.toList());
            phase = phase.next();
            nextPlayer();
        }
    }

    private Cell[][] getBoard() {
        try {
            return islandBoard.getBoard();
        } catch (Exception e) {
            return null;
        }
    }

    private Action[][][] getActions() {
        try {
            return islandBoard.getActions();
        } catch (Exception e) {
            return null;
        }
    }

    public Message createUpdate() {
        if (phase == GamePhase.ACTIVE || phase == GamePhase.END)
            return new Message(playerList.get(player).getUsername(), getBoard(), getActions());
        return null;
    }

    public void setWorkers(Color color, String username, List<Integer> positions) {
        if (phase == GamePhase.SET_WORKERS && isCurrentPlayer(username) && playerList.get(player).getColor() == null
                && positions.stream().distinct().filter(workerPosition -> (workerPosition >= 25 || workerPosition < 0))
                        .collect(Collectors.toList()).size() == positions.size()) {
            for (int i : positions)
                islandBoard.addWorker(username, color, new int[] { i / 5, i - i / 5 });
            playerList.get(player).setColor(color);
            nextPlayer();
            if (playerList.get(player).getColor() != null) {
                phase = phase.next();
            }
        }
    }

    public void chooseWorker(String username, int position) {
        if (phase == GamePhase.ACTIVE && changeWorker && isCurrentPlayer(username) && position >= 0 && position < 25) {
            islandBoard.chooseWorker(username, new int[] { position / 5, position - position / 5 });
            changeWorker = false;
            notify(createUpdate());
        }
    }

    public void chooseAction(String username, int[] position) {
        if (phase == GamePhase.ACTIVE && isCurrentPlayer(username) && position[0] >= 0 && position[0] < 25
                && position[1] >= 0) {
            StatusPlayer playerStatus = islandBoard
                    .executeAction(new int[] { position[0] / 5, position[0] - position[0] / 5, position[1] });
            playerList.get(player).setStatusPlayer(playerStatus);
            if (playerStatus == StatusPlayer.END)
                nextPlayer();
            notify(createUpdate());
        }
    }
}