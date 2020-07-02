package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.model.Command;
import it.polimi.ingsw.utils.model.Notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller for Game
 */
public class Controller extends Observable<String> implements Observer<Notification> {
    /**
     * Reference to Game for this Controller
     */
    private final Game game;

    /**
     * Current Client State, this is used to calculate the diff information to send
     */
    private ArrayList<String> prevReport = new ArrayList<>();

    /**
     * Create an instance of Controller that manage the Game
     * 
     * @param game the reference to game
     */
    public Controller(Game game) {
        if (game == null)
            throw new NullPointerException();
        this.game = game;
    }

    /**
     * On Notification parse the message and verify if it is a valid data
     * 
     */
    @Override
    public void update(Notification notification) {
        try {
            Command command = new Gson().fromJson(notification.getMessage(), Command.class);
            if (command == null)
                return;
            filter(notification.getUsername(), command.getFuncName(), command.getFuncData());
        } catch (Exception e) {
            // Just fail to parse
        }
    }

    /**
     * Function to notify all client about current Game State and to start Game
     */
    public void startGame() {
        createReport(new ArrayList<>());
    }

    /**
     * This function is used to filter requests, if the Game State is changed all
     * client will be notified with the new State through a createReport() function
     * 
     * @param username     player's username
     * @param functionName function's name to use
     * @param data         used for the function
     */
    private synchronized void filter(String username, String functionName, String data) {

        ArrayList<Command> report = new ArrayList<>();
        FuncCommand targetFunction = FuncCommand.getFromValue(functionName);
        // If is a Quit Command
        if (targetFunction == FuncCommand.QUIT_PLAYER && game.getPlayerList().stream()
                .anyMatch(e -> e.getUsername().equals(username) && e.getStatusPlayer() != StatusPlayer.LOSE)) {

            game.quitPlayer();
            // Update Client Game End, a non lose player quit the game
            createReport(report);
            return;
        }
        // Filter
        if (!game.getCurrentPlayer().equals(username))
            return;
        // Parse and Run Command
        splitter(targetFunction, data);

        // Add Option to End Turn
        if (game.getPhase() == GamePhase.CHOOSE_ACTION && game.canEndTurn())
            report.add(new Command(TypeCommand.ACTION.getValue(), FuncCommand.CHOOSE_ACTION.getValue(), null, null));

        // Update Client with new game state
        createReport(report);
    }

    /**
     * This function is used to parse and check input data from user, and then run
     * the command
     * 
     * @param command Function to Launch
     * @param data    Data used in the Function
     */
    private void splitter(FuncCommand command, String data) {

        GamePhase phase = game.getPhase();
        switch (command) {
            case CHOOSE_ACTION: {
                int[] position = null;
                if (data != null)
                    position = new Gson().fromJson(data, int[].class);

                if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING
                        || phase == GamePhase.CHOOSE_ACTION)) {
                    game.chooseAction(position);
                }
            }
                break;
            case SET_GOD:
                if (phase == GamePhase.CHOOSE_GOD && game.getGodList().contains(God.strConverter(data)))
                    game.setGod(God.strConverter(data));
                break;
            case SET_WORKERS: {
                int position = Integer.parseInt(data);
                if (phase == GamePhase.SET_WORKERS && position < 25 && position >= 0)
                    game.setWorkers(position);

            }
                break;
            case CHOOSE_WORKER: {
                int position = Integer.parseInt(data);
                if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING) && position >= 0 && position < 25)
                    game.chooseWorker(Integer.parseInt(data));

            }
                break;
            case SET_COLOR:
                if (phase == GamePhase.SET_COLOR)
                    game.setColor(Color.strConverter(data));
                break;
            case SET_GOD_LIST: {
                God god = God.strConverter(data);
                if (god != null && phase == GamePhase.SET_GOD_LIST
                        && game.getGodList().size() < game.mode.getPlayersNum() && !game.getGodList().contains(god)) {
                    game.setGodList(god);
                }
            }
                break;
            case SET_START_PLAYER:
                if (phase == GamePhase.START_PLAYER
                        && game.getPlayerList().stream().anyMatch(e -> e.getUsername().equals(data)))
                    game.choosePlayer(data);
                break;
            default:
                break;
        }
    }

    /**
     * Create and Send to Client the current Game State
     * 
     * @param report initial report state
     * @return Game State as ArrayList<Command> converted into a Json via Gson
     */
    private void createReport(ArrayList<Command> report) {
        GamePhase phase = game.getPhase();
        CommandConverter cc = new CommandConverter();
        // Prepare report to send
        report.add(new Command(TypeCommand.CURRENT_PLAYER.getValue(), game.getCurrentPlayer()));
        report.add(new Command(TypeCommand.GAME_PHASE.getValue(), phase.toString()));
        report.add(new Command(TypeCommand.GAME_MODE.getValue(), game.mode.toString()));
        report.addAll(infoOnPhase(phase));
        report.addAll(cc.reportBoard(phase, game.getBoard(), game.getCurrentPlayer()));
        report.addAll(cc.reportAction(phase, game.getActions()));
        report.addAll(cc.reportPlayer(phase, game.getPlayerList()));

        // Convert to array of string for diff state
        ArrayList<String> newReport = (ArrayList<String>) report.stream().map(e -> new Gson().toJson(e))
                .collect(Collectors.toList());

        // Diff from current client state with prev state
        ArrayList<Command> toRes = new ArrayList<>();
        toRes.addAll(prevReport.stream().filter(e -> !newReport.contains(e))
                .map(e -> new Gson().fromJson(e, Command.class)).map(e -> {
                    e.setStatus(false);
                    return e;
                }).collect(Collectors.toList()));
        toRes.addAll(newReport.stream().filter(e -> !prevReport.contains(e))
                .map(e -> new Gson().fromJson(e, Command.class)).map(e -> {
                    e.setStatus(true);
                    return e;
                }).collect(Collectors.toList()));

        // Update prev state
        prevReport = (ArrayList<String>) newReport.stream()
                .filter(e -> !(new Gson().fromJson(e, Command.class).getType().equals("action")))
                .collect(Collectors.toList());

        // Prepare data to send
        String toSendAll = new Gson()
                .toJson(toRes.stream().filter(e -> !e.getType().equals("action")).collect(Collectors.toList()));
        String toSendCurrentPlayer = new Gson().toJson(toRes);

        // Update all client not current player state
        notify((ArrayList<String>) game.getPlayerList().stream().map(e -> e.getUsername())
                .filter(e -> !e.equals(game.getCurrentPlayer())).collect(Collectors.toList()), toSendAll);
        // Update current player state
        notify(new ArrayList<>(Arrays.asList(game.getCurrentPlayer())), toSendCurrentPlayer);
    }

    /**
     * Create an ArrayList of setup Command from current GamePhase
     * 
     * @param phase Current Game Phase
     * @return if there is any data to be added based on Current Phase will be
     *         returned, otherwise an empty arraylist will be returned
     */
    private ArrayList<Command> infoOnPhase(GamePhase phase) {
        ArrayList<Command> report = new ArrayList<>();
        switch (phase) {
            case SET_COLOR: {
                report.addAll(game
                        .getColors().stream().map(e -> new Command(TypeCommand.COLOR.getValue(),
                                FuncCommand.SET_COLOR.getValue(), e.toString(), e.toString()))
                        .collect(Collectors.toList()));

            }
                break;
            case SET_GOD_LIST: {
                ArrayList<God> godList = game.getGodList();
                report.addAll(Arrays.stream(God.values()).filter(e -> e != God.STANDARD && !godList.contains(e))
                        .map(e -> new Command(TypeCommand.GOD.getValue(), FuncCommand.SET_GOD_LIST.getValue(),
                                e.toString(), e.toString()))
                        .collect(Collectors.toList()));
                report.addAll(godList.stream().map(e -> new Command(TypeCommand.GOD_LIST.getValue(), e.toString()))
                        .collect(Collectors.toList()));

            }
                break;
            case CHOOSE_GOD: {
                ArrayList<God> godList = game.getGodList();
                report.addAll(godList.stream().map(e -> new Command(TypeCommand.GOD_LIST.getValue(),
                        FuncCommand.SET_GOD.getValue(), e.toString(), e.toString())).collect(Collectors.toList()));

            }
                break;
            default:
                break;
        }
        return report;
    }
}
