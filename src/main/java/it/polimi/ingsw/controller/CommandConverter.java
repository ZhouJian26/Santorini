package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.model.Command;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class with static functions to Convert Data into Command ArrayList
 */
class CommandConverter {
    /**
     * Create an Arraylist of Command with Players Info
     * 
     * @param phase      GamePhase
     * @param playerList list of Players to be converted
     * @return Player converted into Command
     */
    public ArrayList<Command> reportPlayer(GamePhase phase, ArrayList<Player> playerList) {
        switch (phase) {
            case START_PLAYER:
                return (ArrayList<Command>) playerList
                        .stream().map(e -> new Command(TypeCommand.PLAYER.getValue(),
                                FuncCommand.SET_START_PLAYER.getValue(), new Gson().toJson(e), e.getUsername()))
                        .collect(Collectors.toList());
            default:
                return (ArrayList<Command>) playerList.stream()
                        .map(e -> new Command(TypeCommand.PLAYER.getValue(), new Gson().toJson(e)))
                        .collect(Collectors.toList());
        }
    }

    /**
     * Create an Arraylist of Command with Board Info
     * 
     * @param phase         GamePhase
     * @param board         Game Board to be converted
     * @param currentPlayer Current Player's username
     * @return Board State as ArrayList<Command>
     */
    public ArrayList<Command> reportBoard(GamePhase phase, Cell[][] board, String currentPlayer) {
        ArrayList<Command> report = new ArrayList<>();

        switch (phase) {
            case PENDING:
            case CHOOSE_WORKER: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        String funcName = null;
                        if (board[i][j].getBlock().getTypeBlock() == TypeBlock.WORKER
                                && board[i][j].getBlock().getOwner().equals(currentPlayer))
                            funcName = FuncCommand.CHOOSE_WORKER.getValue();
                        report.add(new Command(TypeCommand.BOARD.getValue(), funcName, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
            }
                break;
            case SET_WORKERS: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        String funcName = null;
                        if (board[i][j].getBlock().getTypeBlock() == TypeBlock.LEVEL0)
                            funcName = FuncCommand.SET_WORKERS.getValue();
                        report.add(new Command(TypeCommand.BOARD.getValue(), funcName, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
            }
                break;
            default: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        report.add(new Command(TypeCommand.BOARD.getValue(), null, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
            }
        }
        return report;
    }

    /**
     * Create an Arraylist of Command with Player Action Info
     * 
     * @param phase   GamePhase
     * @param actions Action State to be converted
     * @return Action State as ArrayList<Command>
     */
    public ArrayList<Command> reportAction(GamePhase phase, Action[][][] actions) {
        ArrayList<Command> report = new ArrayList<>();

        if (phase != GamePhase.CHOOSE_ACTION && phase != GamePhase.PENDING)
            return report;
        for (int i = 0; i < actions.length; i++)
            for (int j = 0; j < actions[i].length; j++)
                for (int k = 0; k < actions[i][j].length; k++)
                    if (actions[i][j][k].getStatus())
                        report.add(new Command(TypeCommand.ACTION.getValue(), FuncCommand.CHOOSE_ACTION.getValue(),
                                new Gson().toJson(actions[i][j][k]), new Gson().toJson(new int[] { i * 5 + j, k })));
        return report;
    }
}