package it.polimi.ingsw.model;

import org.junit.Test;

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

    /*
    test addGod function
     */
    @Test
    public void addGodTest() {
        IslandBoard board = new IslandBoard();
        assertTrue(board.addGod("aaa", God.APOLLO));
        assertTrue(board.addGod("aaa", God.ATHENA));
        assertTrue(board.addGod("aaa", God.ARTEMIS));
        assertTrue(board.addGod("aaa", God.ATLAS));
        assertTrue(board.addGod("aaa", God.DEMETER));
        assertTrue(board.addGod("aaa", God.HEPHAESTUS));
        assertTrue(board.addGod("aaa", God.MINOTAUR));
        assertTrue(board.addGod("aaa", God.PROMETHEUS));
        assertTrue( board.addGod("aaa", God.PAN));
        assertTrue(board.addGod("aaa", God.TRITON));
        assertTrue(board.addGod("aaa", God.HERA));
        assertTrue(board.addGod("aaa", God.MEDUSA));
        assertTrue(board.addGod("aaa", God.POSEIDON));
        assertTrue(board.addGod("aaa", God.ZEUS));
        assertTrue(!board.addGod("aaa", null));
    }

    /*
    verify chooseWorker function
    after chooseWorker there's already actions that this worker can do
     */
    @Test
    public void chooseWorkerTest() {
        IslandBoard board = new IslandBoard();
        board.addWorker("aaa", Color.WHITE, new int[]{3, 3});
        board.chooseWorker("aaa", new int[]{3, 3});
        Action[][][] actionsCopy = board.getActions();
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                if (i == 3 && j == 3) {
                    assertEquals(false, actionsCopy[i][j][0].getStatus());
                } else
                    assertEquals(true, actionsCopy[i][j][0].getStatus());
                assertEquals(false, actionsCopy[i][j][1].getStatus());
                assertEquals(false, actionsCopy[i][j][2].getStatus());
            }
        }

    }


    /*
    verify canEndTurn function
    a player can end turn only if player complete basic actions(a move and a build),
    but if player doesn't have any other actions, the changes of the turn will be automatic.
    So a player can call this function only if player has extra actions
     */
    @Test
    public void canEndTurnTest(){
        IslandBoard islandBoard=new IslandBoard();
        islandBoard.addGod("aaa",God.POSEIDON);//allows extra actions
        islandBoard.addWorker("aaa",Color.WHITE,new int[]{1,1});
        islandBoard.chooseWorker("aaa",new int[]{1,1});
        assertFalse(islandBoard.canEndTurn());
        islandBoard.executeAction("aaa", new int[]{0,0,0});
        assertFalse(islandBoard.canEndTurn());
        islandBoard.executeAction("aaa",new int[]{1,1,1});
        assertTrue(islandBoard.canEndTurn());
    }

    /*
    verify that after addWorker there if a worker on the board, with same owner and color of worker added
     */
    @Test
    public void addWorkerTest() {

        IslandBoard board = new IslandBoard();
        int[] position1 = {2, 3};
        int[] position2 = {3, 3};
        board.addWorker("name", Color.BLUE, position1);
        board.addWorker("name", Color.BLUE, position2);
        assertEquals(TypeBlock.WORKER, board.getBoard()[2][3].getBlock(0).getTypeBlock());
        int a = 0;

        assertEquals(TypeBlock.WORKER, board.getBoard()[3][3].getBlock(0).getTypeBlock());
        a++;
        assertEquals(a, 1);

        a++;
        assertEquals(a, 2);
    }

    /*
    simulation of a match to verify the execute function
    after execute it calculate automatically next possible action
    and return a report of players status and lastGod that modifies players status
    return playerStatus idle when the player has no more action
    when player has status lose it delete all his worker from board
     */
    @Test
    public void executeActionTest() {
        IslandBoard board = new IslandBoard();

        board.addGod("aaa", God.APOLLO);
        board.addGod("bbb", God.ATLAS);
        board.addGod("ccc", God.PAN);
        ReportAction report;
        report=board.executeAction("aaa",null);
        assertEquals(StatusPlayer.LOSE,report.getStatusPlayer());
        assertEquals(God.STANDARD,report.getGod());
        board.addWorker("aaa", Color.WHITE, new int[]{1, 1});
        board.addWorker("aaa", Color.WHITE, new int[]{1, 2});
        board.addWorker("bbb", Color.BLUE, new int[]{2, 3});
        board.addWorker("bbb", Color.BLUE, new int[]{3, 2});
        board.addWorker("ccc", Color.BROWN, new int[]{1, 3});
        board.addWorker("ccc", Color.BROWN, new int[]{2, 1});

        report = board.executeAction("ccc", null);
        assertEquals(report.getStatusPlayer(), StatusPlayer.GAMING);

        assertEquals(TypeBlock.WORKER, board.getBoard()[1][1].getBlock().getTypeBlock());
        assertEquals(Color.WHITE, board.getBoard()[1][1].getBlock().getColor());
        assertEquals("aaa", board.getBoard()[1][1].getBlock().getOwner());
        assertEquals(TypeBlock.WORKER, board.getBoard()[1][2].getBlock().getTypeBlock());
        assertEquals(Color.WHITE, board.getBoard()[1][2].getBlock().getColor());
        assertEquals("aaa", board.getBoard()[1][2].getBlock().getOwner());

        assertEquals(TypeBlock.WORKER, board.getBoard()[2][3].getBlock().getTypeBlock());
        assertEquals(Color.BLUE, board.getBoard()[2][3].getBlock().getColor());
        assertEquals("bbb", board.getBoard()[2][3].getBlock().getOwner());
        assertEquals(TypeBlock.WORKER, board.getBoard()[3][2].getBlock().getTypeBlock());
        assertEquals(Color.BLUE, board.getBoard()[3][2].getBlock().getColor());
        assertEquals("bbb", board.getBoard()[3][2].getBlock().getOwner());

        assertEquals(TypeBlock.WORKER, board.getBoard()[1][3].getBlock().getTypeBlock());
        assertEquals(Color.BROWN, board.getBoard()[1][3].getBlock().getColor());
        assertEquals("ccc", board.getBoard()[1][3].getBlock().getOwner());
        assertEquals(TypeBlock.WORKER, board.getBoard()[2][1].getBlock().getTypeBlock());
        assertEquals(Color.BROWN, board.getBoard()[2][1].getBlock().getColor());
        assertEquals("ccc", board.getBoard()[2][1].getBlock().getOwner());

        board.chooseWorker("aaa", new int[]{1, 1});
        assertTrue(board.getActions()[2][1][0].getStatus());
        board.executeAction("aaa", new int[]{2, 1, 0});

        assertEquals(TypeBlock.WORKER, board.getBoard()[2][1].getBlock().getTypeBlock());
        assertEquals(Color.WHITE, board.getBoard()[2][1].getBlock().getColor());
        assertEquals("aaa", board.getBoard()[2][1].getBlock().getOwner());

        assertEquals(TypeBlock.WORKER, board.getBoard()[1][1].getBlock().getTypeBlock());
        assertEquals(Color.BROWN, board.getBoard()[1][1].getBlock().getColor());
        assertEquals("ccc", board.getBoard()[1][1].getBlock().getOwner());

        report = board.executeAction("aaa", new int[]{3, 0, 1});

        assertEquals(StatusPlayer.IDLE, report.getStatusPlayer());

        board.chooseWorker("bbb", new int[]{2, 3});
        board.executeAction("bbb", new int[]{2, 2, 0});
        report = board.executeAction("bbb", null);
        assertEquals(StatusPlayer.GAMING, report.getStatusPlayer());
        report = board.executeAction("bbb", new int[]{3, 1, 1});

        assertEquals(StatusPlayer.IDLE, report.getStatusPlayer());

        board.chooseWorker("ccc", new int[]{1, 1});
        report = board.executeAction("ccc", null);
        assertEquals(StatusPlayer.GAMING, report.getStatusPlayer());
        board.executeAction("ccc", new int[]{2, 0, 0});
        board.executeAction("ccc", new int[]{3, 0, 1});

        board.chooseWorker("aaa", new int[]{2, 1});
        board.executeAction("aaa", new int[]{3, 1, 0});
        board.executeAction("aaa", new int[]{2, 1, 1});

        board.chooseWorker("bbb", new int[]{2, 2});
        board.executeAction("bbb", new int[]{2, 1, 0});
        board.executeAction("bbb", new int[]{2, 2, 1});

        board.chooseWorker("ccc", new int[]{2, 0});
        board.executeAction("ccc", new int[]{1, 0, 0});
        board.executeAction("ccc", new int[]{2, 0, 1});

        board.chooseWorker("aaa", new int[]{3, 1});
        board.executeAction("aaa", new int[]{3, 0, 0});
        board.executeAction("aaa", new int[]{2, 0, 1});

        board.chooseWorker("bbb", new int[]{2, 1});
        board.executeAction("bbb", new int[]{1, 1, 0});
        board.executeAction("bbb", new int[]{2, 0, 1});

        board.chooseWorker("ccc", new int[]{1, 3});
        board.executeAction("ccc", new int[]{1, 4, 0});
        board.executeAction("ccc", new int[]{1, 3, 1});

        board.chooseWorker("aaa", new int[]{3, 0});
        board.executeAction("aaa", new int[]{4, 0, 0});
        board.executeAction("aaa", new int[]{3, 0, 1});

        assertEquals(board.getBoard()[2][0].getSize(), 3);
        assertEquals(board.getBoard()[3][0].getSize(), 3);
        assertEquals(board.getBoard()[3][1].getSize(), 1);
        assertEquals(board.getBoard()[2][1].getSize(), 1);

        board.chooseWorker("bbb", new int[]{1, 1});
        board.executeAction("bbb", new int[]{0, 2, 0});
        board.executeAction("bbb", new int[]{1, 1, 1});
        assertEquals(TypeBlock.WORKER, board.getBoard()[1][0].getBlock().getTypeBlock());
        board.chooseWorker("ccc", new int[]{1, 0});
        board.executeAction("ccc", new int[]{1, 1, 0});
        board.executeAction("ccc", new int[]{2, 1, 1});

        board.chooseWorker("aaa", new int[]{4, 0});
        board.executeAction("aaa", new int[]{3, 1, 0});
        board.executeAction("aaa", new int[]{4, 1, 1});

        board.chooseWorker("bbb", new int[]{0, 2});

        board.executeAction("bbb", new int[]{0, 1, 0});
        board.executeAction("bbb", new int[]{0, 2, 1});

        assertEquals(TypeBlock.WORKER, board.getBoard()[1][1].getBlock().getTypeBlock());
        board.chooseWorker("ccc", new int[]{1, 1});
        board.executeAction("ccc", new int[]{2, 1, 0});
        board.executeAction("ccc", new int[]{1, 1, 1});

        board.chooseWorker("aaa", new int[]{3, 1});
        board.executeAction("aaa", new int[]{4, 0, 0});
        board.executeAction("aaa", new int[]{4, 1, 1});

        assertEquals(board.getBoard()[4][1].getSize(), 2);
        assertEquals(board.getBoard()[3][0].getSize(), 3);
        assertEquals(board.getBoard()[3][1].getSize(), 1);

        board.chooseWorker("bbb", new int[]{0, 1});
        board.executeAction("bbb", new int[]{0, 0, 0});
        board.executeAction("bbb", new int[]{0, 1, 1});

        assertEquals(TypeBlock.WORKER, board.getBoard()[2][1].getBlock().getTypeBlock());
        board.chooseWorker("ccc", new int[]{2, 1});
        board.executeAction("ccc", new int[]{2, 2, 0});
        board.executeAction("ccc", new int[]{3, 1, 1});

        board.chooseWorker("aaa", new int[]{4, 0});
        report = board.executeAction("aaa", null);
        assertEquals(StatusPlayer.LOSE, report.getStatusPlayer());
        assertEquals(board.getBoard()[4][0].getSize(), 0);

        board.chooseWorker("bbb", new int[]{0, 0});
        board.executeAction("bbb", new int[]{0, 1, 0});
        board.executeAction("bbb", new int[]{0, 0, 1});

        assertEquals(TypeBlock.WORKER, board.getBoard()[2][2].getBlock().getTypeBlock());
        board.chooseWorker("ccc", new int[]{2, 2});
        board.executeAction("ccc", new int[]{2, 1, 0});
        board.executeAction("ccc", new int[]{1, 0, 1});

        board.chooseWorker("bbb", new int[]{0, 1});
        board.executeAction("bbb", new int[]{0, 0, 0});
        board.executeAction("bbb", new int[]{0, 1, 1});

        assertEquals(TypeBlock.WORKER, board.getBoard()[2][1].getBlock().getTypeBlock());
        board.chooseWorker("ccc", new int[]{2, 1});
        board.executeAction("ccc", new int[]{3, 1, 0});
        board.executeAction("ccc", new int[]{2, 1, 1});

        board.chooseWorker("bbb", new int[]{0, 0});
        board.executeAction("bbb", new int[]{0, 1, 0});
        board.executeAction("bbb", new int[]{0, 0, 1});

        assertEquals(TypeBlock.WORKER, board.getBoard()[3][1].getBlock().getTypeBlock());
        assertEquals(Color.BROWN, board.getBoard()[3][1].getBlock().getColor());
        board.chooseWorker("ccc", new int[]{3, 1});
        report = board.executeAction("ccc", new int[]{3, 0, 0});

        assertEquals(StatusPlayer.WIN, report.getStatusPlayer());

        board.chooseWorker("bbb", new int[]{0, 1});
        board.executeAction("bbb", new int[]{0, 0, 0});
        board.executeAction("bbb", new int[]{0, 1, 1});

        board.chooseWorker("ccc", new int[]{3, 0});
        report = board.executeAction("ccc", new int[]{4, 0, 0});
        assertEquals(StatusPlayer.WIN, report.getStatusPlayer());

    }

}
