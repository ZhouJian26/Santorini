package it.polimi.ingsw.controller;

import java.util.List;
import java.util.stream.Collectors;

import it.polimi.ingsw.model.Game;

/*
    Todo List
    1) setGodList() -> need converter to god in enum
    2) setGod() -> need converter to god in enum
    3) setWokers() -> need converter to color in enum
    4) chooseWoker() -> [DONE]
    5) chooseAction() -> [DONE]
 */
public class Controller {
    Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void setGodList(String username, String[] gods) {
        // convert string to god
        // todo
    }

    public void setGod(String username, String god) {
        // convert string to god
        // todo
    }

    public void setWokers(String username, String color, List<Integer> positions) {
        // convert string to color
        // check positions value
        if (positions.stream().filter(wokerPosition -> wokerPosition >= 0 && wokerPosition < 25)
                .collect(Collectors.toList()).size() == positions.size() && color == "yellow"/* valid color */) {
            // todo
        }
    }

    public void chooseWoker(String username, int position) {
        // check positions value
        if (position >= 0 && position < 25)
            game.chooseWoker(username, position);
    }

    public void chooseAction(String username, int position) {
        // check positions value
        if (position >= 0 && position < 25)
            game.chooseAction(username, position);
    }
}