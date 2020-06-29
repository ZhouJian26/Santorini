package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

import java.util.*;
import java.util.stream.Collectors;

class Lobby {

    private Map<GameMode, List<Connection>> waitingList = new HashMap<>();
    private Chat chat = new Chat();
    /**
     * Singleton Pattern
     */

    private final static Lobby instance = new Lobby();

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
        // Check if it is a valid and unique username among active connection list
        if (username.length() == 0 || (waitingList.get(mode) != null
                && waitingList.get(mode).stream().anyMatch(e -> (e.getUsername().equals(username) && e.isActive()))))
            return false;

        List<Connection> targetList = new ArrayList<>();

        // Add connection to the current chat
        connection.addObservers(chat);
        chat.addObservers(connection);

        // Check if this is a new game
        if (waitingList.get(mode) != null)
            targetList = waitingList.get(mode).stream().filter(e -> e.isActive()).collect(Collectors.toList());

        // Add connection to the waiting list
        targetList.add(connection);

        // Check if enough players, based on type game
        if (targetList.size() == mode.getPlayersNum()) {
            Game game = new Game(mode, targetList.stream().map(e -> e.getUsername()).collect(Collectors.toList()));
            Controller controller = new Controller(game);
            System.out.print("- Start Game | Mode: " + mode.toString() + " | Players: ");
            for (Connection x : targetList) {
                System.out.print(x.getUsername() + " - ");
                controller.addObservers(x.getUsername(), x);
                x.addObservers(controller);
            }
            System.out.println();
            chat = new Chat();
            waitingList.remove(mode);
            controller.startGame();
        } else {
            waitingList.put(mode, targetList);
        }
        return true;
    }
}
