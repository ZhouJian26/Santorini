package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IslandBoard {
    private List<GodInterface> god = new ArrayList<>();
    private Cell[][] board = new Cell[5][5];
    private Action[][][] actions = new Action[5][5][3];

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
        Action[][][] actionsCopy = new Action[5][5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 3; k++) {
                    actionsCopy[i][j][k] = actions[i][j][k].clone();
                }
            }
        }
        return actionsCopy;
    }

    public void resetAction(boolean priority) {
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
                actions[i][j][0].setGod(God.STANDARD);
                actions[i][j][1].setGod(God.STANDARD);
                actions[i][j][2].setGod(God.STANDARD);

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
            case HERA:
                this.god.add(new GodHera(new GodPower(god, name)));
                break;
            case MEDUSA:
                this.god.add(new GodMedusa(new GodPower(god, name)));
                break;
            case PERSEPHONE:
                this.god.add(new GodPersephone(new GodPower(god, name)));
                break;
            case POSEIDON:
                this.god.add(new GodPoseidon(new GodPower(god, name)));
                break;
            case ZEUS:
                this.god.add(new GodZeus(new GodPower(god, name)));
                break;
        }
    }

    public void chooseWorker(String name, int[] position) {
        resetAction(true);
        if (board[position[0]][position[1]].getBlock().getTypeBlock().equals(TypeBlock.WORKER) && board[position[0]][position[1]].getBlock().getOwner().equals(name)) {
            CurrentPlayer currentPlayer = new CurrentPlayer();
            currentPlayer.positionWorker = position;
            currentPlayer.currentPlayer = name;
            currentPlayer.statusPlayer = StatusPlayer.GAMING;
            currentPlayer.lastGod = God.STANDARD;
            for (GodInterface godInterface : god
            ) {
                godInterface.addInfo(currentPlayer);
            }
            Event[] event = new Event[1];
            event[0] = Event.ZERO;
            setActions(event);
        }
    }


    /* initialization of Worker */
    public void addWorker(String playerId, Color color, int[] position) {
        board[position[0]][position[1]].addBlock(new Block(TypeBlock.WORKER, playerId, color));/* two addWorker */
    }

    public void setActions(Event[] events) {
        for (GodInterface godInterface : god
        ) {
            godInterface.getEvent(events, board, actions);
        }
    }

    /**
     * @param positionAction xyz [0][1][2]
     */
    public ReportAction executeAction(String player,int[] positionAction) {
        if (positionAction != null) {
            actions[positionAction[0]][positionAction[1]][positionAction[2]].execute(board);
            resetAction(false);
            Event[] event = new Event[3];
            if (positionAction[2] == 0) {
                event[0] = Event.MOVE;
                switch (board[positionAction[0]][positionAction[1]].getSize()
                        - board[god.get(0).getPositionWorker()[0]][god.get(0).getPositionWorker()[1]].getSize()) {

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
                    default:
                        event[1] = Event.ZERO;
                        break;
                }
                god.get(0).setWorker(positionAction);
            } else {
                event[0] = Event.BUILD;
                /*switch (board[positionAction[0]][positionAction[1]].getBlock().getTypeBlock()){
                    case LEVEL1:
                        event[1] = Event.ONE;
                        break;
                    case LEVEL2:
                        event[1] = Event.TWO;
                        break;
                    case LEVEL3:
                        event[1] = Event.THREE;
                        break;
                    case DOME:
                        event[1] = Event.FOUR;
                        break;
                }*/
            }
            setActions(event);
        } else {
            Event[] events = new Event[1];
            events[0] = Event.ONE;
            if (god.get(0).getCurrentPlayer() == null) {

            }
            else {
                god.get(0).getEvent(events, board, actions);
                if (god.get(0).getPlayerStatus().equals(StatusPlayer.END)) {
                    god.get(0).setCurrentPlayer(null);
                    resetAction(true);
                }
            }

        }
        if (god.get(0).getPlayerStatus().equals(StatusPlayer.LOSE)) {
            /*god = god.stream().filter(e -> !e.getName().equals(e.getCurrentPlayer())).collect(Collectors.toList());*/
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[i][j].getBlock().getOwner().equals(god.get(0).getCurrentPlayer())) {
                        board[i][j].popBlock();
                    }
                }
            }
        }
        ReportAction reportAction = new ReportAction(god.get(0).getPlayerStatus(), god.get(0).getLastGod());

        return reportAction;
    }

}
