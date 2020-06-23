package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.StatusPlayer;
import it.polimi.ingsw.utils.model.Notification;
import it.polimi.ingsw.utils.model.TypeCommand;
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
            filter(notification.username, command.funcName, command.funcData);
        } catch (Exception e) {
            // Just fail to parse
        }
    }

    /**
     * Function to notify all client about current Game State or to start the game
     */
    public void startGame() {
        notify(createReport(new ArrayList<>()));
    }

    /**
     * This function is used to filter requests, if the Game State is changed all
     * client will be notified with the new State through a notify()
     * 
     * @param username     player username
     * @param functionName function name to use
     * @param data         to use for the function
     */
    private synchronized void filter(String username, String functionName, String data) {
        ArrayList<Command> report = new ArrayList<>();
        FuncCommand targetFunction = FuncCommand.getFromValue(functionName);

        // If is a Quit Command
        if (targetFunction == FuncCommand.QUIT_PLAYER && game.getPlayerList().stream()
                .anyMatch(e -> e.username.equals(username) && e.getStatusPlayer() != StatusPlayer.LOSE)) {
            game.quitPlayer();

            // Update Client Game End, a non lose player quit the game
            notify(createReport(report));
            return;
        }
        // Filter
        if (!game.getCurrentPlayer().equals(username))
            return;
        // Parse and Run Command
        splitter(targetFunction, data);

        // Add Option to End Turn
        if (game.getPhase() == GamePhase.CHOOSE_ACTION)
            report.add(new Command(TypeCommand.ACTION.value, FuncCommand.CHOOSE_ACTION.value, null, null));

        // Update Client with new game state
        notify(createReport(report));

    }

    /**
     * This function is used to parse and check input data from user, and then run
     * the command
     * 
     * @param command
     * @param data
     */
    private void splitter(FuncCommand command, String data) {
        try {
            GamePhase phase = game.getPhase();
            switch (command) {
                case CHOOSE_ACTION: {
                    int[] position = null;
                    if (data != null)
                        position = new Gson().fromJson(data, int[].class);

                    if (position != null && (position[0] < 0 || position[0] >= 25 || position[1] < 0))
                        break;
                    if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING
                            || phase == GamePhase.CHOOSE_ACTION)) {
                        game.chooseAction(position);
                    }
                    break;
                }
                case SET_GOD:
                    if (phase == GamePhase.CHOOSE_GOD && game.getGodList().contains(God.strConverter(data)))
                        game.setGod(God.strConverter(data));
                    break;
                case SET_WORKERS: {
                    int position = Integer.parseInt(data);
                    if (phase == GamePhase.SET_WORKERS && position < 25 && position >= 0)
                        game.setWorkers(position);
                    break;
                }
                case CHOOSE_WORKER: {
                    int position = Integer.parseInt(data);
                    if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING) && position >= 0
                            && position < 25)
                        game.chooseWorker(Integer.parseInt(data));
                    break;
                }
                case SET_COLOR:
                    if (phase == GamePhase.SET_COLOR)
                        game.setColor(Color.strConverter(data));
                    break;
                case SET_GOD_LIST: {
                    God god = God.strConverter(data);
                    System.out.println(phase);
                    System.out.println(game.getGodList().size());
                    System.out.println(game.getGodList().contains(god));
                    if (god != null && phase == GamePhase.SET_GOD_LIST
                            && game.getGodList().size() < game.mode.playersNum && !game.getGodList().contains(god)) {
                        game.setGodList(god);
                    }
                    break;
                }
                case SET_START_PLAYER:
                    if (phase == GamePhase.START_PLAYER
                            && game.getPlayerList().stream().anyMatch(e -> e.username.equals(data)))
                        game.choosePlayer(data);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            // Invalid Data
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param report initial report state
     * @return Game State as ArrayList<Command> converted into a Json via Gson
     */
    private String createReport(ArrayList<Command> report) {
        GamePhase phase = game.getPhase();

        report.add(new Command(TypeCommand.CURRENT_PLAYER.value, game.getCurrentPlayer()));
        report.add(new Command(TypeCommand.GAME_PHASE.value, phase.toString()));
        report.add(new Command(TypeCommand.GAME_MODE.value, game.mode.toString()));
        report.addAll(infoOnPhase(phase));
        report.addAll(CommandConverter.reportBoard(phase, game.getBoard(), game.getCurrentPlayer()));
        report.addAll(CommandConverter.reportAction(phase, game.getActions()));
        report.addAll(CommandConverter.reportPlayer(phase, game.getPlayerList()));

        return new Gson().toJson(report);
    }

    /**
     * 
     * @param phase Current Game Phase
     * @return if there is any data to be added based on Current Phase will be
     *         returned, otherwise an empty arraylist will be returned
     */
    private ArrayList<Command> infoOnPhase(GamePhase phase) {
        ArrayList<Command> report = new ArrayList<>();
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
        return report;
    }
}
