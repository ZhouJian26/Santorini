package it.polimi.ingsw.model;

/**
 * Class that is used to store general player information
 */
public class Player {
    /**
     * Player username
     */
    public final String username;
    /**
     * Player status
     */
    private StatusPlayer status;
    /**
     * Player color
     */
    private Color color;
    /**
     * Player god
     */
    private God god;
    /**
     * Player number of woker to place
     */
    private int workers;

    /**
     * Create a Player istance with a username
     * 
     * @param username player username
     * @exception NullPointerException if username is null
     */
    public Player(String username) {
        if (username == null)
            throw new NullPointerException();
        this.username = username;
        this.status = StatusPlayer.IDLE;
        workers = 2;
    }

    /**
     * Create a Player istance from a Player istance
     * 
     * @param player Player istance
     * @exception NullPointerException if player is null
     */
    public Player(Player player) {
        if (player == null)
            throw new NullPointerException();
        this.username = player.username;
        this.color = player.getColor();
        this.god = player.god;
        this.workers = player.workers;
        this.status = player.getStatusPlayer();
    }

    /**
     * Set player status
     * 
     * @param status status to set
     * @exception NullPointerException if status is null
     */
    public void setStatusPlayer(StatusPlayer status) {
        if (status == null)
            throw new NullPointerException();
        this.status = status;
    }

    /**
     * Get player status
     * 
     * @return player status
     */
    public StatusPlayer getStatusPlayer() {
        return status;
    }

    /**
     * Set player color
     * 
     * @param color color to set
     * @exception NullPointerException if color is null
     */
    public void setColor(Color color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Get Player color
     * 
     * @return player color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set player god
     * 
     * @param god god to set
     * @exception NullPointerException if god is null
     */
    public void setGod(God god) {
        if (god == null)
            throw new NullPointerException();
        this.god = god;
    }

    /**
     * Delete a worker to place
     * 
     * @return remaining number of worker to place
     */
    public int placeWoker() {
        if (workers == 0)
            throw new IllegalAccessError();
        workers--;
        return workers;
    }

    /**
     * Get player god
     * 
     * @return player god
     */
    public God getGod() {
        return god;
    }
}