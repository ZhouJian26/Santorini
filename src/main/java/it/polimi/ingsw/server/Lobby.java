package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to Manage Pools of Users waiting for a Game
 */
class Lobby {
    /**
     * A Hashmap to store for each GameMode a List of Connection
     */
    private Map<GameMode, List<Connection>> waitingList = new HashMap<>();
    /**
     * Singleton Pattern
     */
    private final static Lobby instance = new Lobby();

    /**
     * Get the Lobby Instance
     * 
     * @return the instance of Lobby
     */
    public static Lobby getInstance() {
        return instance;
    }

    /**
     * This method is used to set lists in order to start the game
     *
     * @param connection connection to add in the lobby
     * @param username   username of the player (connection)
     * @param mode       game mode chosen by player
     */

    public synchronized boolean putOnWaiting(Connection connection, String username, GameMode mode) {
        if (username.length() == 0 || (waitingList.get(mode) != null
                && waitingList.get(mode).stream().anyMatch(e -> (e.getUsername().equals(username) && e.isActive()))))
            return false;

        List<Connection> targetList = new ArrayList<>();

        if (waitingList.get(mode) != null)
            targetList = waitingList.get(mode).stream().filter(e -> e.isActive()).collect(Collectors.toList());

        targetList.add(connection);

        if (targetList.size() == mode.getPlayersNum()) {

            Chat chat = new Chat();
            Game game = new Game(mode, targetList.stream().map(e -> e.getUsername()).collect(Collectors.toList()));
            Controller controller = new Controller(game);
            System.out.print("- Start Game | Mode: " + mode.toString() + " | Players: ");
            for (Connection x : targetList) {
                System.out.print(x.getUsername() + " - ");
                controller.addObservers(x.getUsername(), x);
                x.addObservers(controller);

                x.addObservers(chat);
                chat.addObservers(x);
            }
            System.out.println();
            waitingList.remove(mode);
            controller.startGame();
        } else {
            waitingList.put(mode, targetList);
        }
        return true;
    }
}
