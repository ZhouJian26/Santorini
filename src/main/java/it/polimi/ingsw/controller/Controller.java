package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.TypeBlock;
import it.polimi.ingsw.utils.model.Notification;
import it.polimi.ingsw.utils.model.TypeCommand;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.FuncCommand;

public class Controller extends Observable<String> implements Observer<Notification> {
    private final Game game;

    /**
     * @param game the reference to game
     */
    public Controller(Game game) {
        if (game == null)
            throw new NullPointerException();
        this.game = game;
    }

    @Override
    public void update(Notification notification) {
        try {
            Command command = new Gson().fromJson(notification.message, Command.class);
            splitter(notification.username, command.funcName, command.funcData);
        } catch (Exception e) {
            // Just fail to parse
        }
    }

    public void startGame() {
        notify(createReport(new ArrayList<>()));
    }

    /**
     * 
     * @param username     player username
     * @param functionName function name to use
     * @param data         data to use for the function
     */
    private synchronized void splitter(String username, String functionName, String data) {
        try {
            boolean needUpdate = false;
            FuncCommand targetFunction = FuncCommand.getFromValue(functionName);
            ArrayList<Command> report = new ArrayList<>();

            if (targetFunction == FuncCommand.QUIT_PLAYER && game.quitPlayer(username)) {
                notify(createReport(report));
                return;
            }

            if (!game.getCurrentPlayer().equals(username))
                return;

            switch (targetFunction) {
                case CHOOSE_ACTION:
                    needUpdate = game.chooseAction(data == null ? null : new Gson().fromJson(data, int[].class));
                    GamePhase phase = game.getPhase();
                    if (phase != GamePhase.END && phase != GamePhase.CHOOSE_WORKER && phase != GamePhase.PENDING)
                        report.add(new Command(TypeCommand.ACTION.value, FuncCommand.CHOOSE_ACTION.value, null, null));
                    break;
                case SET_GOD:
                    needUpdate = game.setGod(God.strConverter(data));
                    break;
                case SET_WORKERS:
                    needUpdate = game.setWorkers(Integer.parseInt(data));
                    break;
                case CHOOSE_WORKER:
                    needUpdate = game.chooseWorker(Integer.parseInt(data));
                    break;
                case SET_COLOR:
                    needUpdate = game.setColor(Color.strConverter(data));
                    break;
                case SET_GOD_LIST:
                    needUpdate = game.setGodList(God.strConverter(data));
                    break;
                case SET_START_PLAYER:
                    needUpdate = game.choosePlayer(data);
                    break;
                default:
                    break;
            }
            if (needUpdate)
                notify(createReport(report));
        } catch (Exception e) {
            // Invalid Data
        }
    }

    private String createReport(ArrayList<Command> report) {
        final GamePhase phase = game.getPhase();

        report.add(new Command(TypeCommand.CURRENT_PLAYER.value, game.getCurrentPlayer()));
        report.add(new Command(TypeCommand.GAME_PHASE.value, phase.toString()));
        report.add(new Command(TypeCommand.GAME_MODE.value, game.getMode().toString()));
        report.addAll(reportBoard(phase));
        report.addAll(reportAction(phase));
        report.addAll(reportPlayer(phase));

        switch (phase) {
            case SET_COLOR: {
                report.addAll(game.getColors().stream().map(e -> new Command(TypeCommand.COLOR.value,
                        FuncCommand.SET_COLOR.value, e.toString(), e.toString())).collect(Collectors.toList()));
                break;
            }
            case SET_GOD_LIST: {
                ArrayList<God> godList = game.getGodList();
                report.addAll(Arrays.stream(God.values()).filter(e -> e != God.STANDARD && !godList.contains(e))
                        .map(e -> new Command(TypeCommand.GOD.value, FuncCommand.SET_GOD_LIST.value, e.toString(),
                                e.toString()))
                        .collect(Collectors.toList()));
                report.addAll(godList.stream().map(e -> new Command(TypeCommand.GOD_LIST.value, e.toString()))
                        .collect(Collectors.toList()));
                break;
            }
            case CHOOSE_GOD: {
                ArrayList<God> godList = game.getGodList();
                report.addAll(godList.stream().map(e -> new Command(TypeCommand.GOD_LIST.value,
                        FuncCommand.SET_GOD.value, e.toString(), e.toString())).collect(Collectors.toList()));
                break;
            }
            default:
                break;
        }
        return new Gson().toJson(report);
    }

    private ArrayList<Command> reportPlayer(GamePhase phase) {
        switch (phase) {
            case START_PLAYER:
                return (ArrayList<Command>) game
                        .getPlayerList().stream().map(e -> new Command(TypeCommand.PLAYER.value,
                                FuncCommand.SET_START_PLAYER.value, new Gson().toJson(e), e.username))
                        .collect(Collectors.toList());
            default:
                return (ArrayList<Command>) game.getPlayerList().stream()
                        .map(e -> new Command(TypeCommand.PLAYER.value, new Gson().toJson(e)))
                        .collect(Collectors.toList());
        }
    }

    private ArrayList<Command> reportBoard(GamePhase phase) {
        ArrayList<Command> report = new ArrayList<>();
        Cell[][] board = game.getBoard();

        switch (phase) {
            case PENDING:
            case CHOOSE_WORKER: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        String funcName = null;
                        if (board[i][j].getBlock().getTypeBlock() == TypeBlock.WORKER
                                && board[i][j].getBlock().getOwner().equals(game.getCurrentPlayer()))
                            funcName = FuncCommand.CHOOSE_WORKER.value;
                        report.add(new Command(TypeCommand.BOARD.value, funcName, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
                break;
            }
            case SET_WORKERS: {
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board[i].length; j++) {
                        String funcName = null;
                        if (board[i][j].getBlock().getTypeBlock() == TypeBlock.LEVEL0)
                            funcName = FuncCommand.SET_WORKERS.value;
                        report.add(new Command(TypeCommand.BOARD.value, funcName, new Gson().toJson(board[i][j]),
                                Integer.toString(i * 5 + j)));
                    }
                break;
            }
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

    private ArrayList<Command> reportAction(GamePhase phase) {
        ArrayList<Command> report = new ArrayList<>();

        if (phase != GamePhase.CHOOSE_ACTION && phase != GamePhase.PENDING)
            return report;

        Action[][][] actions = game.getActions();
        for (int i = 0; i < actions.length; i++)
            for (int j = 0; j < actions[i].length; j++)
                for (int k = 0; k < actions[i][j].length; k++)
                    if (actions[i][j][k].getStatus())
                        report.add(new Command(TypeCommand.ACTION.value, FuncCommand.CHOOSE_ACTION.value,
                                new Gson().toJson(actions[i][j][k]), new Gson().toJson(new int[] { i * 5 + j, k })));
                    else
                        report.add(new Command(TypeCommand.ACTION.value, null, new Gson().toJson(actions[i][j][k]),
                                new Gson().toJson(new int[] { i * 5 + j, k })));
        return report;
    }
}
