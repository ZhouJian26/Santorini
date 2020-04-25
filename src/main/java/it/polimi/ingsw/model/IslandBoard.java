package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IslandBoard {
    private List<GodInterface> god = new ArrayList<>();
    private Cell[][] board = new Cell[5][5];
    private Action[][][] actions = new Action[5][5][3];
    private int[] positionWorker = new int[2];

    public IslandBoard() {
        int i, j, k;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                board[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
                actions[i][j][2] = new Build();
            }
        }

        god.add(new GodStandard(new GodPower(God.STANDARD, null)));

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

    public void resetAction(Action[][][] actions, boolean priority) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (priority) {
                    actions[i][j][0].setBlocked(false);
                    actions[i][j][1].setBlocked(false);
                    actions[i][j][2].setBlocked(false);
                }
                actions[i][j][0].set(false);
                actions[i][j][1].set(false);
                actions[i][j][2].set(false);
                actions[i][j][0].setGod(null);
                actions[i][j][1].setGod(null);
                actions[i][j][2].setGod(null);

            }
        }

    }

    public void addGod(String name, God god) {

        switch (god) {
            case APOLLO:
                this.god.add(new GodApollo(new GodPower(god, name)));
                break;

            case ARTEMIS:
                this.god.add(new GodArtemis(new GodPower(god, name)));
                break;
            case ATHENA:
                this.god.add(new GodAthena(new GodPower(god, name)));
                break;
            case ATLAS:
                this.god.add(new GodAtlas(new GodPower(god, name)));
                break;
            case DEMETER:
                this.god.add(new GodDemeter(new GodPower(god, name)));
                break;
            case HEPHAESTUS:
                this.god.add(new GodHephaestus(new GodPower(god, name)));
                break;
            case MINOTAUR:
                this.god.add(new GodMinotaur(new GodPower(god, name)));
                break;
            case PAN:
                this.god.add(new GodPan(new GodPower(god, name)));
                break;
            case PROMETHEUS:
                this.god.add(new GodPrometheus(new GodPower(god, name)));
                break;
        }
    }

    public void chooseWorker(String name, int[] position) {

        god.get(0).setWorker(position);
        god.get(0).setCurrentPlayer(name);
        god.get(0).setStatusPlayer(StatusPlayer.GAMING);
        god.get(0).setLastGod(God.STANDARD);
        positionWorker[0] = position[0];
        positionWorker[1] = position[1];
        resetAction(actions, true);
        Event[] event = new Event[1];
        event[0] = Event.ZERO;
        setActions(event);
    }

    /* initialization of Worker */
    public void addWorker(String playerId, Color color, int[] position) {
        board[position[0]][position[1]].addBlock(new Block(TypeBlock.WORKER, playerId, color));/* two addWorker */
    }

    public void setActions(Event[] events) {
        for (int i = 0; i < god.size(); i++) {
            god.get(i).getEvent(events, board, actions);
        }
    }

    /**
     * @param positionAction xyz [0][1][2]
     */
    public ReportAction executeAction(int[] positionAction) {
        if (positionAction != null) {
            actions[positionAction[0]][positionAction[1]][positionAction[2]].execute(board);
            resetAction(actions, false);
            Event[] event = new Event[3];
            if (positionAction[2] == 0) {
                event[0] = Event.MOVE;
                god.get(0).setWorker(positionAction);
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
            god.get(0).run(actions);
            if (god.get(0).getPlayerStatus().equals(StatusPlayer.END)) {
                resetAction(actions, true);
            }
        }
        if (god.get(0).getPlayerStatus().equals(StatusPlayer.LOSE)) {
            god=god.stream().filter(e->!e.getName().equals(e.getCurrentPlayer())).collect(Collectors.toList());
        }
        ReportAction reportAction=new ReportAction(god.get(0).getPlayerStatus(),god.get(0).getLastGod());

        return reportAction;
    }

}
