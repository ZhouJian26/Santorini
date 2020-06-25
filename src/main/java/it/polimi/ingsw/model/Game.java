package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This is the Game Class, it is used to manage the data for a game.
 */
public class Game {
    /**
     * GameMode of this game
     */
    public final GameMode mode;
    /**
     * Current GamePhase
     */
    private GamePhase phase;
    /**
     * Player List
     */
    private List<Player> playerList;
    /**
     * Current Player index
     */
    private int player;
    /**
     * God List used in this game
     */
    private List<God> godList;
    /**
     * Istance of the Game Board
     */
    private final IslandBoard islandBoard;

    /**
     * Create a new game with the mode and players specified
     *
     * @param mode    the game mode
     * @param players each player username
     * @exception IllegalArgumentException if repeated username or wrong number of
     *                                     players
     */
    public Game(GameMode mode, List<String> players) {
        godList = new ArrayList<>();
        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size()
                && players.size() == mode.playersNum)
            playerList = players.stream().map(Player::new).collect(Collectors.toList());
        else
            throw new IllegalArgumentException();

        this.mode = mode;
        islandBoard = new IslandBoard();
        phase = GamePhase.start();
        player = new Random().nextInt(playerList.size());
        playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
    }

    /**
     * Check if the current player can end the turn
     * 
     * @return True if can end turn, otherwise False
     */
    public boolean canEndTurn() {
        return islandBoard.canEndTurn();
    }

    /**
     * Get current Game Phase
     * 
     * @return current GamePhase
     */
    public GamePhase getPhase() {
        return phase;
    }

    /**
     * Get Current Player username
     * 
     * @return current player username
     */
    public String getCurrentPlayer() {
        return playerList.get(player).username;
    }

    /**
     * 
     * Get a copy of Game PlayerList
     * 
     * @return a copy of current player list
     */
    public ArrayList<Player> getPlayerList() {
        return (ArrayList<Player>) playerList.stream().map(Player::new).collect(Collectors.toList());
    }

    /**
     * Get a copy of God used in this game
     * 
     * @return a copy of current god list for the game
     */
    public ArrayList<God> getGodList() {
        return (ArrayList<God>) godList.stream().map(e -> e).collect(Collectors.toList());
    }

    /**
     * Get a copy of the Game Board
     * 
     * @return a copy of current game board
     */
    public Cell[][] getBoard() {
        return islandBoard.getBoard();
    }

    /**
     * Get a copy of the current usable action for the player
     * 
     * @return a copy of current usable actions for the player
     */
    public Action[][][] getActions() {
        return islandBoard.getActions();
    }

    /**
     * End the current game. It sets all player in IDLE mode if not WIN or LOSE
     */
    public void quitPlayer() {
        playerList = playerList.stream().map(e -> {
            if (e.getStatusPlayer() == StatusPlayer.GAMING)
                e.setStatusPlayer(StatusPlayer.IDLE);
            return e;
        }).collect(Collectors.toList());
        phase = GamePhase.END;
    }

    /**
     * Shift to next player, if remains only one player of current player status is
     * WIN then all player status is set to LOSE and the one to WIN
     */
    private void nextPlayer() {

        // Set all player except the Winner to Lose StaturPlayer and set the Winner to
        // IDLE state
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.WIN) {
            playerList = playerList.stream().map(e -> {
                if (e.getStatusPlayer() != StatusPlayer.WIN)
                    e.setStatusPlayer(StatusPlayer.LOSE);
                else
                    e.setStatusPlayer(StatusPlayer.IDLE);
                return e;
            }).collect(Collectors.toList());
        }

        // If the current player is not LOSE then it is changed to IDLE
        if (playerList.get(player).getStatusPlayer() != StatusPlayer.LOSE)
            playerList.get(player).setStatusPlayer(StatusPlayer.IDLE);

        // If there are at least 2 player on IDLE State, then search for the first
        // player on IDLE state, otherwise set the only player to WINNER
        if (playerList.stream().filter(e -> e.getStatusPlayer() == StatusPlayer.IDLE).collect(Collectors.toList())
                .size() > 1) {
            // at least 2 player IDLE
            while ((player = (player + 1) % playerList.size()) >= 0
                    && playerList.get(player).getStatusPlayer() != StatusPlayer.IDLE) {
            }
            playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
        } else {
            playerList = playerList.stream().map(e -> {
                if (e.getStatusPlayer() == StatusPlayer.IDLE)
                    e.setStatusPlayer(StatusPlayer.WIN);
                return e;
            }).collect(Collectors.toList());
            player = playerList.indexOf(playerList.stream().filter(e -> e.getStatusPlayer() == StatusPlayer.WIN)
                    .collect(Collectors.toList()).get(0));
            phase = GamePhase.END;
        }
    }

    /**
     * Get available colors to pick
     * 
     * @return current free color
     */
    public List<Color> getColors() {
        List<Color> chosenColor = playerList.stream().map(Player::getColor).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return Arrays.stream(Color.values()).filter(c -> !chosenColor.contains(c)).collect(Collectors.toList());
    }

    /**
     * Set the god for the current player
     *
     * @param god god to set
     */
    public void setGod(God god) {
        islandBoard.addGod(getCurrentPlayer(), god);
        playerList.get(player).setGod(god);
        godList = godList.stream().filter(e -> e != god).collect(Collectors.toList());

        if (!godList.isEmpty())
            nextPlayer();

        if (godList.size() == 1) {
            setGod(godList.get(0));
            phase = phase.next();
        }
    }

    /**
     * Set gods to use in this game (one god at the time)
     *
     * @param god god to set
     */
    public void setGodList(God god) {
        godList.add(god);
        if (godList.size() == mode.playersNum) {
            phase = phase.next();
            nextPlayer();
        }
    }

    /**
     * Set color for current player
     *
     * @param color chosen color
     */
    public void setColor(Color color) {
        playerList.get(player).setColor(color);
        phase = phase.next();
    }

    /**
     * Set/Place a worker for current player
     *
     * @param position worker position in ( row * 5 + col ) format, 0 <= position <
     *                 25
     */
    public void setWorkers(int position) {
        int remainWorker = playerList.get(player).placeWoker();
        islandBoard.addWorker(getCurrentPlayer(), playerList.get(player).getColor(),
                new int[] { position / 5, position % 5 });
        if (remainWorker == 0) {
            nextPlayer();
            if (playerList.get(player).getColor() == null)
                phase = phase.prev();
            else
                phase = phase.next();
        }
    }

    /**
     * Choose a worker for current player
     *
     * @param position worker position in (row * 5 + col) format, 0 <= position < 25
     */
    public void chooseWorker(int position) {
        islandBoard.chooseWorker(getCurrentPlayer(), new int[] { position / 5, position % 5 });
        if (phase == GamePhase.CHOOSE_WORKER)
            phase = phase.next();
    }

    /**
     * Use an action for current player
     *
     * @param position action position in [(row * 5 + col), dim] format, 0 <= (row *
     *                 5 + col) < 25 and 0 <= dim < 3
     */
    public void chooseAction(int[] position) {
        if (phase == GamePhase.PENDING && position != null)
            phase = phase.next();

        ReportAction reportAction = islandBoard.executeAction(playerList.get(player).username,
                position == null ? null : new int[] { position[0] / 5, position[0] % 5, position[1] });
        playerList.get(player).setStatusPlayer(reportAction.statusPlayer);
        autoEnd();
    }

    /**
     * Try to perform an autoend action until a new player can play
     */
    private void autoEnd() {
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING)
            return;

        phase = GamePhase.CHOOSE_WORKER;
        nextPlayer();

        if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING)
            playerList.get(player)
                    .setStatusPlayer(islandBoard.executeAction(playerList.get(player).username, null).statusPlayer);

        if (playerList.get(player).getStatusPlayer() != StatusPlayer.WIN)
            autoEnd();
    }

    /**
     * Set start player
     * 
     * @param targetUsername start player username
     * @exception IndexOutOfBoundsException if username not exists
     */
    public void choosePlayer(String targetUsername) {
        playerList.get(player).setStatusPlayer(StatusPlayer.IDLE);
        player = playerList.indexOf(
                playerList.stream().filter(e -> e.username.equals(targetUsername)).collect(Collectors.toList()).get(0));
        playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
        phase = phase.next();
    }

}