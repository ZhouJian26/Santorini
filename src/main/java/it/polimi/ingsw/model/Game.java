package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This is the Game Class, it is used to manage the data of the game.
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
     * Players List
     */
    private List<Player> playerList;
    /**
     * Current Player index
     */
    private int player;
    /**
     * Gods List used in this game
     */
    private List<God> godList;
    /**
     * Instance of the Game Board
     */
    private final IslandBoard islandBoard;

    /**
     * Create a new game with the specified mode and players
     *
     * @param mode    the game mode
     * @param players all players'username
     * @exception IllegalArgumentException if repeated username or wrong number of
     *                                     players
     */
    public Game(GameMode mode, List<String> players) {
        godList = new ArrayList<>();
        if (players.stream().distinct().collect(Collectors.toList()).size() == players.size()
                && players.size() == mode.getPlayersNum())
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
     * Get Current Player's username
     * 
     * @return current player's username
     */
    public String getCurrentPlayer() {
        return playerList.get(player).getUsername();
    }

    /**
     * 
     * Get a copy of Game PlayerList
     * 
     * @return a copy of current players list
     */
    public ArrayList<Player> getPlayerList() {
        return (ArrayList<Player>) playerList.stream().map(Player::new).collect(Collectors.toList());
    }

    /**
     * Get a copy of Gods used in this game
     * 
     * @return a copy of current gods list for the game
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
     * Get a copy of available actions for the player in this turn
     * 
     * @return a copy of available actions for the player in this turn
     */
    public Action[][][] getActions() {
        return islandBoard.getActions();
    }

    /**
     * End the current game. It sets all players in IDLE mode if not WIN or LOSE
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
     * Shift to the next player, if only one player remains, his status will be set
     * as 'WIN'. The player also 'WIN' if all other players has status as 'LOSE'
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
        if (godList.size() == mode.getPlayersNum()) {
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
     * @param position worker position in ( row * 5 + col ) format, 0 {@literal <}=
     *                 position {@literal <} 25
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
     * @param position worker position in (row * 5 + col) format, 0 {@literal <}=
     *                 position {@literal <} 25
     */
    public void chooseWorker(int position) {
        islandBoard.chooseWorker(getCurrentPlayer(), new int[] { position / 5, position % 5 });
        if (phase == GamePhase.CHOOSE_WORKER)
            phase = phase.next();
    }

    /**
     * Use an action for current player
     *
     * @param position action position in [(row * 5 + col), dim] format, 0
     *                 {@literal <}= (row * 5 + col) {@literal <} 25 and 0
     *                 {@literal <}= dim {@literal <} 3
     */
    public void chooseAction(int[] position) {
        if (phase == GamePhase.PENDING && position != null)
            phase = phase.next();

        ReportAction reportAction = islandBoard.executeAction(playerList.get(player).getUsername(),
                position == null ? null : new int[] { position[0] / 5, position[0] % 5, position[1] });
        playerList.get(player).setStatusPlayer(reportAction.getStatusPlayer());
        autoEnd();
    }

    /**
     * Try to perform an auto-end action until a new player can play
     */
    private void autoEnd() {
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING)
            return;

        phase = GamePhase.CHOOSE_WORKER;
        nextPlayer();

        if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING)
            playerList.get(player).setStatusPlayer(
                    islandBoard.executeAction(playerList.get(player).getUsername(), null).getStatusPlayer());

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
        player = playerList.indexOf(playerList.stream().filter(e -> e.getUsername().equals(targetUsername))
                .collect(Collectors.toList()).get(0));
        playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
        phase = phase.next();
    }

}