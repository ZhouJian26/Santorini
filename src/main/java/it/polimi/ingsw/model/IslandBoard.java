package it.polimi.ingsw.model;

public class IslandBoard {
    private GodInterface[] god = new GodInterface[4];
    private Cell[][] board = new Cell[5][5];
    private Action[][][] actions = new Action[5][5][2];
    private int[] positionWorker = new int[2];

    public IslandBoard() {
        int i, j, k;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                board[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
            }
        }
        /* god[0] = new GodStandard(new GodPower(God.STANDARD, "game")); */
    }

    /* return a copy of board */
    public Cell[][] getBoard() {
        Cell[][] boardCopy = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardCopy[i][j] = board[i][j].clone();
            }
        }
        return boardCopy;
    }

    /* return a copy of actions */
    public Action[][][] getActions() {
        Action[][][] actionsCopy = new Action[5][5][2];
        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    actionsCopy[i][j][k] = actions[i][j][k].clone();
                }
            }
        }
        return actionsCopy;
    }

    public void addGod(String name, God god) {
        int i = 0;
        for (i = 1; i < 4 && this.god[i] != null; i++) {
        }
        switch (god) {
            case APOLLO:
                this.god[i] = new GodApollo(new GodPower(god, name));
                break;
        }
    }

    public void chooseWorker(String name, int[] position) {
        god[0].setWorker(position);
        god[0].setCurrentPlayer(name);
        god[0].setStatusPlayer(StatusPlayer.GAMING);
        positionWorker[0] = position[0];
        positionWorker[1] = position[1];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
            }
        }
        setActions(null);
    }

    /* initialization of Worker */
    public void addWorker(String playerId, Color color, int[] position) {
        board[position[0]][position[1]].addBlock(new Worker(TypeBlock.WORKER, playerId, color));/* two addWorker */
    }

    public void setActions(Event[] events) {
        for (int i = 0; i < 4 && god[i] != null; i++) {
            god[i].getEvent(events, board, actions);
        }
    }

    /**
     * @param positionAction xyz
     */
    public StatusPlayer executeAction(int[] positionAction) {
        if (positionAction != null) {
            actions[positionAction[0]][positionAction[1]][positionAction[2]].esecute(board);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    actions[i][j][0].set(false);
                    actions[i][j][1].set(false);
                }
            }
            Event[] event = new Event[3];
            if (positionAction[2] == 0) {
                event[0] = Event.MOVE;
                god[0].setWorker(positionAction);
                if (board[positionWorker[0]][positionWorker[1]]
                        .getBlock(board[positionWorker[0]][positionWorker[1]].getSize()).getTypeBlock()
                        .equals(TypeBlock.WORKER)) {
                    event[1] = Event.FOUR;/* È un swap tra due worker */
                } else {
                    switch (board[positionAction[0]][positionAction[1]].getSize()
                            - board[positionWorker[0]][positionWorker[1]].getSize()) {
                        case 1:
                            event[1] = Event.ZERO;
                            break;
                        case 2:
                            event[1] = Event.UP;
                            break;
                        case 0:
                            event[1] = Event.DOWN;
                            event[2] = Event.ONE;
                            break;
                        case -1:
                            event[1] = Event.DOWN;
                            event[2] = Event.TWO;
                            break;
                        case -2:
                            event[1] = Event.DOWN;
                            event[2] = Event.THREE;
                            break;
                    }
                    positionWorker[0] = positionAction[0];
                    positionWorker[1] = positionAction[1];
                }
            } else {
                event[0] = Event.BUILD;
            }
            setActions(event);
        } else {
            god[0].run(actions);
        }

        return god[0].getPlayerStatus();
    }

}
