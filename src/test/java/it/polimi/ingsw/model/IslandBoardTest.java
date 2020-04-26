package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class IslandBoardTest {

    @Test
    public void getBoardTest() {
        IslandBoard board = new IslandBoard();
        Cell[][] boardCopy = board.getBoard();
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                assertEquals(0, boardCopy[i][j].getSize());
            }
        }
        boardCopy[3][3].addBlock(new Block(TypeBlock.WORKER));
        Cell[][] boardCopyTwo = board.getBoard();
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                assertEquals(0, boardCopyTwo[i][j].getSize());
            }
        }
    }

    @Test
    public void getActionsTest() {
        IslandBoard board = new IslandBoard();
        Action[][][] actionsCopy = board.getActions();
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                assertEquals(false, actionsCopy[i][j][0].getStatus());
                assertEquals(false, actionsCopy[i][j][1].getStatus());
                assertEquals(false, actionsCopy[i][j][2].getStatus());
            }
        }
        actionsCopy[3][3][1].set(true);
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                assertEquals(false, board.getActions()[i][j][0].getStatus());
                assertEquals(false, board.getActions()[i][j][1].getStatus());
                assertEquals(false, board.getActions()[i][j][2].getStatus());
            }
        }
    }

    @Test
    public void resetActionTest() {
        IslandBoard board = new IslandBoard();
        board.addWorker("aaa", Color.WHITE, new int[]{3, 3});
        board.chooseWorker("aaa", new int[]{3, 3});
        Action[][][] actionsCopy = board.getActions();
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                if (i == 3 && j == 3) {

                } else
                    assertEquals(true, actionsCopy[i][j][0].getStatus());
            }
        }
        board.resetAction(true);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(false, board.getActions()[i][j][0].getStatus());
                assertEquals(false, board.getActions()[i][j][1].getStatus());
                assertEquals(false, board.getActions()[i][j][2].getStatus());
            }
        }
    }

    @Test
    public void addGodTest() {
        assertTrue(true);
    }

    @Test
    public void chooseWorkerTest() {
        IslandBoard board = new IslandBoard();
        GodStandard godStandard = new GodStandard(new GodPower(God.STANDARD, null));
        godStandard.setCurrentPlayer(null);
        board.addWorker("aaa", Color.WHITE, new int[]{3, 3});
        board.chooseWorker("abc", new int[]{3, 3});

        assertEquals(godStandard.getCurrentPlayer(), null);
        board.chooseWorker("aaa", new int[]{3, 3});
        assertEquals(godStandard.getCurrentPlayer(), "aaa");
        assertEquals(godStandard.getPositionWorker()[0], 3);
        assertEquals(godStandard.getPositionWorker()[1], 3);

    }


    @Test
    public void addWalkerTest() {

        IslandBoard board = new IslandBoard();
        int[] position1 = {2, 3};
        int[] position2 = {3, 3};
        board.addWorker("name", Color.BLUE, position1);
        board.addWorker("name", Color.BLUE, position2);
        assertEquals(TypeBlock.WORKER, board.getBoard()[2][3].getBlock(0).getTypeBlock());
        assertEquals(TypeBlock.WORKER, board.getBoard()[3][3].getBlock(0).getTypeBlock());
    }

    @Test
    public void setActionsTest() {
        IslandBoard board = new IslandBoard();
        GodStandard godStandard = new GodStandard(new GodPower(God.STANDARD, null));
        Event[] events = new Event[3];
        board.addWorker("aaa", Color.WHITE, new int[]{3, 3});
        board.chooseWorker("aaa", new int[]{3, 3});
        board.resetAction(true);
        events[0] = Event.ZERO;
        board.setActions(events);
        Action[][][] actionsCopy = board.getActions();
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                if (i == 3 && j == 3) {

                } else
                    assertEquals(true, actionsCopy[i][j][0].getStatus());
            }
        }

    }

    @Test
    public void executeActionTest() {
        IslandBoard board = new IslandBoard();
        board.addWorker("aaa", Color.WHITE, new int[]{3, 3});
        board.chooseWorker("aaa", new int[]{3, 3});
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                if (i == 3 && j == 3) {

                } else
                    assertEquals(true,  board.getActions()[i][j][0].getStatus());
            }
        }
        board.executeAction(new int[]{2, 2, 0});

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(false,  board.getActions()[i][j][0].getStatus());
            }
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (i == 2 && j == 2) {

                } else
                    assertEquals(true,  board.getActions()[i][j][1].getStatus());
            }
        }

        board.executeAction(new int[]{1,1,1});


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(false,  board.getActions()[i][j][0].getStatus());
                assertEquals(false,  board.getActions()[i][j][1].getStatus());
                assertEquals(false,  board.getActions()[i][j][2].getStatus());
            }
        }

        ReportAction reportAction=board.executeAction(null);
        assertEquals(reportAction.statusPlayer,StatusPlayer.END);

        board.chooseWorker("aaa", new int[]{2, 2});
        board.executeAction(new int[]{1, 1, 0});
        board.executeAction(new int[]{2, 2, 1});
        board.executeAction(null);
        board.chooseWorker("aaa", new int[]{1, 1});
        board.executeAction(new int[]{2, 2, 0});
        board.executeAction(new int[]{1, 1, 1});
        board.executeAction(null);
        board.chooseWorker("aaa", new int[]{2, 2});
        board.executeAction(new int[]{1, 1, 0});
        board.executeAction(new int[]{2, 2, 1});
        board.executeAction(null);
        board.chooseWorker("aaa", new int[]{1, 1});
        board.executeAction(new int[]{2, 2, 0});
        board.executeAction(new int[]{1, 1, 1});
        board.executeAction(null);
        assertEquals(TypeBlock.LEVEL3,board.getBoard()[1][1].getBlock().getTypeBlock());
        assertEquals(TypeBlock.WORKER,board.getBoard()[2][2].getBlock(2).getTypeBlock());

        board.chooseWorker("aaa", new int[]{2, 2});
        reportAction=board.executeAction(new int[]{1, 1, 0});
        assertEquals(StatusPlayer.WIN,reportAction.statusPlayer);
        assertEquals(God.STANDARD,reportAction.god);
        board.chooseWorker("aaa", new int[]{2, 2});
    }

}
