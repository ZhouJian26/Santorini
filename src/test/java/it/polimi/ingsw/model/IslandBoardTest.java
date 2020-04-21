package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class IslandBoardTest {
    IslandBoard board = new IslandBoard();

    @Test
    public void getBoardTest() {
    }

    @Test
    public void getActionsTest() {
        board.addWorker("ciao", Color.BLUE, new int[] { 2, 3 });
        God god = God.APOLLO;
        board.addGod("ciao", god);
        board.addGod("a", God.ATLAS);
        board.chooseWorker("ciao", new int[] { 2, 3 });
    }

    @Test
    public void addWalkerTest() throws CloneNotSupportedException {

        int[] position1 = { 2, 3 };
        int[] position2 = { 3, 3 };
        board.addWorker("name", Color.BLUE, position1);
        board.addWorker("name", Color.BLUE, position2);
        assertEquals(TypeBlock.WORKER, board.getBoard()[2][3].getBlock(0).getTypeBlock());
        assertEquals(TypeBlock.WORKER, board.getBoard()[3][3].getBlock(0).getTypeBlock());
    }

    @Test
    public void spiegazione() {
        ArrayList<String> playerList = new ArrayList<>(Arrays.asList("marco", "pino"));
        Game game = new Game(GameMode.TWO, playerList);
        Report report = new Report(game);
        String currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setGodList(currentPlayer, God.APOLLO);
        game.setGodList(currentPlayer, God.ATHENA);
        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setGod(currentPlayer, God.ATHENA);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setColor(currentPlayer, Color.BLUE);
        game.setWorkers(currentPlayer, 0);
        game.setWorkers(currentPlayer, 1);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.setColor(currentPlayer, Color.BROWN);
        game.setWorkers(currentPlayer, 2);
        game.setWorkers(currentPlayer, 3);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.chooseWorker(currentPlayer, 0);
        game.chooseWorker(currentPlayer, 1);
        game.chooseWorker(currentPlayer, 0);
        game.chooseAction(currentPlayer, new int[] { 5, 0 });

        game.chooseAction(currentPlayer, new int[] { 11, 1 });
        game.chooseAction(currentPlayer, null);

        currentPlayer = report.getDataFiltered("currentPlayer").get(0).info;
        game.chooseWorker(currentPlayer, 2);
        game.chooseWorker(currentPlayer, 3);
        game.chooseAction(currentPlayer, new int[] { 4, 0 });
        report.printInfo();
        report.printCommand();
    }

}
