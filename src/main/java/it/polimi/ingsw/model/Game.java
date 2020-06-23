package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    public final GameMode mode;
    private GamePhase phase;
    private List<Player> playerList;
    private int player;
    private List<God> godList;
    private IslandBoard islandBoard;

    /**
     * Create a new game with the mode and players specified
     *
     * @param mode    the game mode
     * @param players each player username
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

    public GamePhase getPhase() {
        return phase;
    }

    public GameMode getMode() {
        return mode;
    }

    public String getCurrentPlayer() {
        return playerList.get(player).username;
    }

    public ArrayList<Player> getPlayerList() {
        return (ArrayList<Player>) playerList.stream().map(Player::new).collect(Collectors.toList());
    }

    public ArrayList<God> getGodList() {
        return (ArrayList<God>) godList.stream().map(e -> e).collect(Collectors.toList());
    }

    public Cell[][] getBoard() {
        return islandBoard.getBoard();
    }

    public Action[][][] getActions() {
        return islandBoard.getActions();
    }

    public boolean quitPlayer(String username) {
        if (playerList.stream()
                .anyMatch(e -> e.username.equals(username) && e.getStatusPlayer() != StatusPlayer.LOSE)) {
            phase = GamePhase.END;
            return true;
        }
        return false;
    }

    /**
     * Shift to next player
     */
    private void nextPlayer() {
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.WIN) {
            playerList = playerList.stream().map(e -> {
                if (e.getStatusPlayer() != StatusPlayer.WIN)
                    e.setStatusPlayer(StatusPlayer.LOSE);
                return e;
            }).collect(Collectors.toList());
            phase = GamePhase.END;
            return;
        }

        if (playerList.get(player).getStatusPlayer() != StatusPlayer.LOSE)
            playerList.get(player).setStatusPlayer(StatusPlayer.IDLE);

        if (playerList.stream().filter(e -> e.getStatusPlayer() == StatusPlayer.IDLE).collect(Collectors.toList())
                .size() > 1) {
            // at least 2 player idle
            while ((player = (player + 1) % playerList.size()) >= 0
                    && playerList.get(player).getStatusPlayer() != StatusPlayer.IDLE) {
            }
            ;
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
     * @return current free color
     */
    public List<Color> getColors() {
        List<Color> chosenColor = playerList.stream().map(Player::getColor).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return Arrays.stream(Color.values()).filter(c -> !chosenColor.contains(c)).collect(Collectors.toList());
    }

    /**
     * Set a god for the current player
     *
     * @param username player
     * @param god      to set
     */
    public boolean setGod(God god) {
        if (phase == GamePhase.CHOOSE_GOD && godList.contains(god)) {
            islandBoard.addGod(getCurrentPlayer(), god);
            playerList.get(player).setGod(god);
            godList = godList.stream().filter(e -> e != god).collect(Collectors.toList());

            if (!godList.isEmpty())
                nextPlayer();

            if (godList.size() == 1) {
                setGod(godList.get(0));
                phase = phase.next();
            }
            return true;
        }
        return false;
    }

    /**
     * Set gods to use in this game (one god at the time)
     *
     * @param username player "god-like"
     * @param god      to set
     */
    public boolean setGodList(God god) {
        if (phase == GamePhase.SET_GOD_LIST && !godList.contains(god) && godList.size() < mode.playersNum) {
            godList.add(god);
            if (godList.size() == mode.playersNum) {
                phase = phase.next();
                nextPlayer();
            }
            return true;
        }
        return false;
    }

    /**
     * Set color for a player
     *
     * @param username player
     * @param color    chosen
     */
    public boolean setColor(Color color) {
        if (phase == GamePhase.SET_COLOR) {
            playerList.get(player).setColor(color);
            phase = phase.next();
            return true;
        }
        return false;
    }

    /**
     * Set/Place a worker for a player
     *
     * @param username player
     * @param position worker position in (row * 5 + col) format
     */
    public boolean setWorkers(int position) {
        if (phase == GamePhase.SET_WORKERS && position < 25 && position >= 0) {
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
            return true;
        }
        return false;
    }

    /**
     * Choose a worker for a player
     *
     * @param username player
     * @param position worker position in (row * 5 + col) format
     */
    public boolean chooseWorker(int position) {
        if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING) && position >= 0 && position < 25) {
            islandBoard.chooseWorker(getCurrentPlayer(), new int[] { position / 5, position % 5 });
            if (phase == GamePhase.CHOOSE_WORKER)
                phase = phase.next();
            return true;
        }
        return false;
    }

    /**
     * Use an action for a player
     *
     * @param username player
     * @param position action position in [(row * 5 + col), dim] format
     */
    public boolean chooseAction(int[] position) {
        if ((phase == GamePhase.CHOOSE_WORKER || phase == GamePhase.PENDING || phase == GamePhase.CHOOSE_ACTION)

                && (position == null || (position[0] >= 0 && position[0] < 25 && position[1] >= 0))) {

            if (phase == GamePhase.PENDING && position != null)
                phase = phase.next();

            ReportAction reportAction = islandBoard.executeAction(playerList.get(player).username,
                    position == null ? null : new int[] { position[0] / 5, position[0] % 5, position[1] });
            playerList.get(player).setStatusPlayer(reportAction.statusPlayer);
            autoEnd();
            return true;
        }
        return false;
    }

    private void autoEnd() {
        if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING)
            return;

        phase = GamePhase.CHOOSE_WORKER;
        nextPlayer();

        if (playerList.get(player).getStatusPlayer() == StatusPlayer.GAMING)
            playerList.get(player)
                    .setStatusPlayer(islandBoard.executeAction(playerList.get(player).username, null).statusPlayer);
        // todo sposta sopra per facilitare il test
        if (playerList.get(player).getStatusPlayer() != StatusPlayer.WIN)
            autoEnd();
    }

    public boolean choosePlayer(String targetUsername) {
        if (phase == GamePhase.START_PLAYER && playerList.stream().anyMatch(e -> e.username.equals(targetUsername))) {
            playerList.get(player).setStatusPlayer(StatusPlayer.IDLE);
            player = playerList.indexOf(playerList.stream().filter(e -> e.username.equals(targetUsername))
                    .collect(Collectors.toList()).get(0));
            playerList.get(player).setStatusPlayer(StatusPlayer.GAMING);
            phase = phase.next();
            return true;
        }
        return false;
    }

}