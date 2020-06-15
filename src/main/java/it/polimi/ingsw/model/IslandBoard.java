package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IslandBoard {
    private List<GodInterface> god = new ArrayList<>();
    private Cell[][] board = new Cell[5][5];
    private Action[][][] actions = new Action[5][5][3];

    public IslandBoard() {
        int i;
        int j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                board[i][j] = new Cell();
                actions[i][j][0] = new Swap();
                actions[i][j][1] = new Build();
                actions[i][j][2] = new Build();
                actions[i][j][0].setGod(God.STANDARD);
                actions[i][j][1].setGod(God.STANDARD);
                actions[i][j][2].setGod(God.STANDARD);
            }
        }
        god.add(new GodStandard(new GodPower(God.STANDARD, null)));
        god.get(0).addInfo(new CurrentPlayer());

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
        try {
            this.god.add((GodInterface) Class
                    .forName("it.polimi.ingsw.model.God" + god.toString().charAt(0)
                            + god.toString().toLowerCase().substring(1))
                    .getConstructor(GodInterface.class).newInstance(new GodPower(god, name)));
        } catch (Exception e) {
        }
    }

    public void chooseWorker(String name, int[] position) {
        resetAction(true);

        if (board[position[0]][position[1]].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                && board[position[0]][position[1]].getBlock().getOwner().equals(name)) {
            CurrentPlayer currentPlayer = new CurrentPlayer(position, name, StatusPlayer.GAMING, God.STANDARD);
            for (GodInterface godInterface : god) {

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
        for (GodInterface godInterface : god) {
            godInterface.getEvent(events, board, actions);
        }
    }

    /**
     * @param positionAction xyz [0][1][2]
     */
    public ReportAction executeAction(String player, int[] positionAction) {
        Event[] event = new Event[3];
        if (positionAction != null) {
            event = actions[positionAction[0]][positionAction[1]][positionAction[2]].execute(board);
            resetAction(true);
            if (event[0] == Event.MOVE) {
                god.get(0).setWorker(positionAction);
            }
            setActions(event);
            event[0] = Event.TWO; // End turn automatic
        } else {
            event[0] = Event.ONE;

            if (god.get(0).getCurrentPlayer() == null || !god.get(0).getCurrentPlayer().equals(player)) {
                int count = 0;
                System.out.println("ib");
                for (int i = 0; i < 25; i++) {
                    if (board[i / 5][i % 5].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                            && board[i / 5][i % 5].getBlock().getOwner().equals(player)) {
                        chooseWorker(board[i / 5][i % 5].getBlock().getOwner(), new int[] { i / 5, i % 5 });
                        god.get(0).getEvent(event, board, actions);
                        if (god.get(0).getPlayerStatus() == StatusPlayer.GAMING) {
                            count++;
                            break;
                        }

                    }
                }
                ReportAction reportAction;
                if (count == 0) {
                    System.out.println("0");
                    reportAction = new ReportAction(StatusPlayer.LOSE, God.STANDARD);
                } else {
                    System.out.println("1");
                    reportAction = new ReportAction(StatusPlayer.GAMING, God.STANDARD);
                }
                resetAction(true);
                return reportAction;
            }
        }

        god.get(0).getEvent(event, board, actions);
        if (god.get(0).getPlayerStatus().equals(StatusPlayer.IDLE)) {
            resetAction(true);
        }

        if (god.get(0).getPlayerStatus().equals(StatusPlayer.LOSE)) {
            god = god.stream().filter(e -> e.equals(god.get(0)) || !e.getName().equals(player))
                    .collect(Collectors.toList());
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[i][j].getBlock().getOwner().equals(god.get(0).getCurrentPlayer())) {
                        board[i][j].popBlock();
                    }
                }
            }
        }
        return new ReportAction(god.get(0).getPlayerStatus(), god.get(0).getLastGod());
    }
}
