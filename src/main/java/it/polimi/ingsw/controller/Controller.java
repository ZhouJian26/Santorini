package it.polimi.ingsw.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.God;

/*
    Todo List
    1) setGodList() -> [DONE]
    2) setGod() -> [DONE]
    3) setWokers() -> [DONE]
    4) chooseWoker() -> [DONE]
    5) chooseAction() -> [DONE]

    6) handle failed convertion data
    7) handle paralell data access
 */
public class Controller {
    Game game;

    /**
     * @param game the reference to game
     */
    public Controller(Game game) {
        this.game = game;
    }

    /**
     * 
     * @param username player username
     * @param gods     array of gods that the "godlike" choose
     */
    public void setGodList(String username, String[] gods) {
        // convert string to god
        God[] godList = (God[]) Arrays.stream(gods).map(god -> God.strConverter(god)).collect(Collectors.toList())
                .toArray();
        game.setGodList(username, godList);
    }

    /**
     * 
     * @param username player username
     * @param god      god that this player choose
     */
    public void setGod(String username, String god) {
        game.setGod(username, God.strConverter(god));
    }

    /**
     * 
     * @param username  player username
     * @param color     color that this plauer choose
     * @param positions array of position that this player has choose to place his
     *                  wokers in format 0 to 24, so position [2,3] -> (2 * 5) + 3 =
     *                  13
     */
    public void setWokers(String username, String color, List<Integer> positions) {
        // convert string to color
        // check positions value
        if (positions.stream().filter(wokerPosition -> wokerPosition < 0 || wokerPosition >= 25).findAny().isEmpty()) {
            game.setWokers(Color.strConverter(color), username, positions);
        }
    }

    /**
     * 
     * @param username player username
     * @param position position of the woker that the player want use
     */
    public void chooseWoker(String username, int position) {
        if (position >= 0 && position < 25)
            game.chooseWoker(username, position);
    }

    /**
     * 
     * @param username player username
     * @param position position of the action that the player want use in format
     *                 [t1,t2] that means t1 = y*5+x and t2 = z
     */
    public void chooseAction(String username, int[] position) {
        if (position[0] >= 0 && position[0] < 25)
            game.chooseAction(username, position);
    }
}