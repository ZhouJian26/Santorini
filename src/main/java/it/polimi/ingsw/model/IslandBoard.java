package it.polimi.ingsw.model;

public class IslandBoard {
    private Cell[][] board = new Cell[5][5];
    private Action[][][] actions = new Action[5][5][2];

    public IslandBoard() {
        int i, j, k;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                board[i][j] = new Cell();
                for (k = 0; k < 2; k++) {
                    actions[i][j][k] = new Action();
                }
            }
        }
    }

    /* return a copy of board */
    public Cell[][] getBoard() throws CloneNotSupportedException {
        Cell[][] boardCopy = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardCopy[i][j] = board[i][j].clone();
            }
        }
        return boardCopy;
    }

    /* return a copy of actions */
    public Action[][][] getActions() throws CloneNotSupportedException {
        Action[][][] actionsCopy = new Action[5][5][2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; i < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    actionsCopy[i][j][k] = actions[i][j][k].clone();
                }
            }
        }
        return actionsCopy;
    }

    /* initialization of Worker */
    public void addWorker(String playerId, Color color, int[] position) {
        board[position[0]][position[1]].addBlock(new Worker(TypeBlock.WORKER, playerId, color));/* two addWorker */
    }

    public void executeAction(int[] position) {
        actions[position[0]][position[1]][position[2]].esecute(board);
    }

}
