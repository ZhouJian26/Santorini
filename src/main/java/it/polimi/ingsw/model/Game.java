package it.polimi.ingsw.model;

import it.polimi.ingsw.view.Observable;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

/*
    Todo List:
    1) setGodList()
    2) setGod()
    3) setWokers()
    4) chooseWoker()
    5) chooseAction()
    6) Game() [DONE]
    7) getActions()
    8) getBoard()
    9) getGods()
    10) getPlayers()
    11) getMode() [DONE]
    12) getPlayer() [DONE]
*/
public class Game extends Observable<Game> implements Cloneable {
    private GameMode mode;
    private GamePhase phase; // ? Move to IslandBoard
    private List<Player> playerList;
    private int player;
    private List<God> godList;

    // ? God List only on construction phase
    /**
     * Create a new game with the mode and players specified
     * 
     * @param mode    the game mode
     * @param players each player username
     */
    public Game(GameMode mode, ArrayList<String> players) {
        this.mode = mode;
        playerList = players.stream().map(username -> new Player(username)).collect(Collectors.toList());
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

    public List<Player> getPlayers() {
        List<Player> players = playerList.stream().map(player -> player.clone()).collect(Collectors.toList());
        return players;
    }
}