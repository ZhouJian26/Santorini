package it.polimi.ingsw.model;

import it.polimi.ingsw.view.Observable;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Todo List:
    1) setGodList() -> need filter function on the enum
    2) setGod() -> manca funzione add god ---
    3) setWokers() -> [DONE]
    4) chooseWoker() -> manca funzione in IslandBoard ---
    5) execureAction() -> manca funzione in IslandBoard ---
    6) Game() -> [DONE]
    7) getActions() -> [DONE] cazzata
    8) getBoard() -> [DONE] cazzata 
    9) getGods() -> need filter function on the enum
    10) getPlayers() [DONE]
    11) getMode() [DONE]
    12) getPlayer() [DONE]

    13) Implement custom Observable for Game
*/
public class Game extends Observable<Game> implements Cloneable {
    private GameMode mode;
    private GamePhase phase; // ? Move to IslandBoard
    private List<Player> playerList;
    private int player;
    private List<God> godList;
    private IslandBoard islandBoard;
    private boolean isActive;

    /**
     * Create a new game with the mode and players specified
     * 
     * @param mode    the game mode
     * @param players each player username
     */
    public Game(GameMode mode, ArrayList<String> players) {
        isActive = false;

        if (Arrays.stream(GameMode.values()).filter(gameMode -> gameMode != mode).findAny().isPresent())
            this.mode = mode;
        else
            return;

        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size())
            playerList = players.stream().map(username -> new Player(username)).collect(Collectors.toList());
        else
            return;

        islandBoard = new IslandBoard();
        isActive = true;
    }

    /**
     * @return a god list of the current mode or current god to be choose, based on
     *         gamephase
     */
    public God[] getGod() {
        return God.values(); // todo need to be filtered with a lambda
    }

    /**
     * 
     * @return game mode
     */
    public GameMode getGameMode() {
        return mode;
    }

    public void setGod(String username, God god) {
        // todo if username is the current one, and choose the god
        if (playerList.get(player).getUsername().equals(username)) {
            // set god to that player
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
        if (playerList.get(player).getUsername().equals(username)
                && Arrays.stream(godList).distinct().collect(Collectors.toList()).size() == GameMode.playersNum(mode)) {
            this.godList = Arrays.stream(godList).collect(Collectors.toList());
        }
    }

    public Cell[][] getBoard() throws CloneNotSupportedException {
        // todo è una cazzata ^_^
        return islandBoard.getBoard();
    }

    public Action[][][] getActions() throws CloneNotSupportedException {
        // todo è una cazzata ^_^
        return islandBoard.getActions();
    }

    public void setWokers(Color color, String username, List<Integer> positions) {
        // verify if is authorized users and if positions are valid
        if (playerList.get(player).getUsername().equals(username)
                && positions.stream().distinct().filter(wokerPosition -> (wokerPosition < 25 && wokerPosition >= 0))
                        .collect(Collectors.toList()).size() == positions.size())
            for (int i : positions)
                islandBoard.addWorker(username, color, new int[] { i / 5, i - i / 5 });
    }

    public void chooseWoker(String username, int position) {
        if (playerList.get(player).getUsername().equals(username) && position >= 0 && position < 25) {
            // todo miss the function
        }
    }

    public void chooseAction(String username, int position) {
        if (playerList.get(player).getUsername().equals(username) && position >= 0 && position < 25) {
            // todo miss the function
        }
    }
}