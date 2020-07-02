package it.polimi.ingsw.model;

/**
 * Class that is used to store general player's information
 */
public class Player {
    /**
     * Player's username
     */
    private final String username;
    /**
     * Player's status
     */
    private StatusPlayer status;
    /**
     * Player's color
     */
    private Color color;
    /**
     * Player's god
     */
    private God god;
    /**
     * Player's number of worker to place
     */
    private int workers;

    /**
     * Create a Player instance with a username
     * 
     * @param username player's username
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
     * Create a Player's instance from a Player instance
     * 
     * @param player Player's instance
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
     * Set player's status
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
     * Get player's status
     * 
     * @return player's status
     */
    public StatusPlayer getStatusPlayer() {
        return status;
    }

    /**
     * Set player color
     * 
     * @param color color to be set
     * @exception NullPointerException if color is null
     */
    public void setColor(Color color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Get Player's color
     * 
     * @return player's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set player's god
     * 
     * @param god god to be set
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
     * @return remaining number of workers to place
     */
    public int placeWoker() {
        if (workers == 0)
            throw new IllegalAccessError();
        workers--;
        return workers;
    }

    /**
     * Get player's god
     * 
     * @return player's god
     */
    public God getGod() {
        return god;
    }

    /**
     * Get player's username
     * 
     * @return player's username
     */
    public String getUsername() {
        return username;
    }
}