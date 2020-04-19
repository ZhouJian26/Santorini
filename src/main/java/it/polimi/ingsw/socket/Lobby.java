package it.polimi.ingsw.socket;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

import java.util.*;

public class Lobby {


    private Map< GameMode, List<String> > allWaitingList = new HashMap<>();
    ArrayList<String> twoPlayers = new ArrayList<>();
    ArrayList<String> threePlayers = new ArrayList<>();
    private Map< String, Connection > matchingList = new HashMap<>();

    /**
     *
     * @param connection
     * @param username username of the player (connection)
     * @param mode  game mode chosen by player
     */

    public Lobby(Connection connection, String username, GameMode mode) {
    }

    private int listCheck (GameMode mode){
        if (mode == GameMode.TWO){
            if(allWaitingList.containsKey(mode)){
                return 2;
            }else allWaitingList.put(mode, this.twoPlayers);
            return 2;
        }
        if (mode == GameMode.THREE){
            if (allWaitingList.containsKey(mode)){
                return 3;
            }else allWaitingList.put(mode, this.threePlayers);
            return 3;
        }
        return 0;
    }

    /**
     * This method is used to set lists in order to start the game
     * @param connection
     * @param username username of the player (connection)
     * @param mode  game mode chosen by player
     *
     * Todo need to complete 3 players mode
     */

    public synchronized void Lobby(Connection connection, String username, GameMode mode) {
        matchingList.put(username, connection);
        if (listCheck(mode) == 2){
            this.twoPlayers.add(username);
        }
        else if (listCheck(mode) == 3){
            this.threePlayers.add(username);
        }
        if (twoPlayers.size() == 2){
            Connection cPlayer1 = matchingList.get(twoPlayers.get(0));
            Connection cPlayer2 = matchingList.get(twoPlayers.get(1));

            Game game = new Game(mode, twoPlayers);
            Controller controller = new Controller(game);
            game.addObservers(cPlayer1);
            game.addObservers(cPlayer2);
            cPlayer1.addObservers(controller);
            cPlayer2.addObservers(controller);

            twoPlayers.clear();
        }

        if (threePlayers.size()==3){
            Connection cPlayerA = matchingList.get(threePlayers.get(0));
            Connection cPlayerB = matchingList.get(threePlayers.get(1));
            Connection cPlayerC = matchingList.get(threePlayers.get(2));

            Game gameA = new Game(mode, threePlayers);
            Controller controllerA = new Controller(gameA);
            gameA.addObservers(cPlayerA);
            gameA.addObservers(cPlayerB);
            gameA.addObservers(cPlayerC);
            cPlayerA.addObservers(controllerA);
            cPlayerB.addObservers(controllerA);
            cPlayerC.addObservers(controllerA);

            threePlayers.clear();
        }


    }
}
