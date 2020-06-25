package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TypeBlock;
import it.polimi.ingsw.utils.model.Command;

import it.polimi.ingsw.utils.model.TypeCommand;
import it.polimi.ingsw.utils.model.FuncCommand;

class CommandConverter {
    /**
     * 
     * @param phase      GamePhase
     * @param playerList list of Player to be converted
     * @return Player converted into Command
     */
    public static ArrayList<Command> reportPlayer(GamePhase phase, ArrayList<Player> playerList) {
        switch (phase) {
            case START_PLAYER:
                return (ArrayList<Command>) playerList.stream().map(e -> new Command(TypeCommand.PLAYER.value,
                        FuncCommand.SET_START_PLAYER.value, new Gson().toJson(e), e.username))
                        .collect(Collectors.toList());
            default:
                return (ArrayList<Command>) playerList.stream()
                        .map(e -> new Command(TypeCommand.PLAYER.value, new Gson().toJson(e)))
                        .collect(Collectors.toList());
        }
    }

    /**
     * 
     * @param phase         GamePhase
     * @param board         Game Board to be converted
     * @param currentPlayer Current Player Username
     * @return Board State as ArrayList<Command>
     */
    public static ArrayList<Command> reportBoard(GamePhase phase, Cell[][] board, String currentPlayer) {
        ArrayList<Command> report = new ArrayList<>();

        switch (phase) {
            case PENDING:
            case CHOOSE_WORKER: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        String funcName = null;
                        if (board[i][j].getBlock().getTypeBlock() == TypeBlock.WORKER
                                && board[i][j].getBlock().getOwner().equals(currentPlayer))
                            funcName = FuncCommand.CHOOSE_WORKER.value;
                        report.add(new Command(TypeCommand.BOARD.value, funcName, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
            }
                break;
            case SET_WORKERS: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        String funcName = null;
                        if (board[i][j].getBlock().getTypeBlock() == TypeBlock.LEVEL0)
                            funcName = FuncCommand.SET_WORKERS.value;
                        report.add(new Command(TypeCommand.BOARD.value, funcName, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
            }
                break;
            default: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        report.add(new Command(TypeCommand.BOARD.value, null, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
            }
        }
        return report;
    }

    /**
     * 
     * @param phase   GamePhase
     * @param actions Action State to be converted
     * @return Action State as ArrayList<Command>
     */
    public static ArrayList<Command> reportAction(GamePhase phase, Action[][][] actions) {
        ArrayList<Command> report = new ArrayList<>();

        if (phase != GamePhase.CHOOSE_ACTION && phase != GamePhase.PENDING)
            return report;
        for (int i = 0; i < actions.length; i++)
            for (int j = 0; j < actions[i].length; j++)
                for (int k = 0; k < actions[i][j].length; k++)
                    if (actions[i][j][k].getStatus())
                        report.add(new Command(TypeCommand.ACTION.value, FuncCommand.CHOOSE_ACTION.value,
                                new Gson().toJson(actions[i][j][k]), new Gson().toJson(new int[] { i * 5 + j, k })));
        return report;
    }
}