package it.polimi.ingsw.model;

public class Player {
    public final String username;
    private StatusPlayer status;
    private Color color;
    private God god;
    private int workers;

    /**
     * 
     * @param username player username
     */
    public Player(String username) {
        if (username == null)
            throw new NullPointerException();
        this.username = username;
        this.status = StatusPlayer.IDLE;
        workers = 2;
    }

    public Player(Player player) {
        if (player == null)
            throw new NullPointerException();
        this.username = player.username;
        this.color = player.getColor();
        this.god = player.god;
        this.workers = player.workers;
        this.status = player.getStatusPlayer();
    }

    public void setStatusPlayer(StatusPlayer status) {
        if (status == null)
            throw new NullPointerException();
        this.status = status;
    }

    public StatusPlayer getStatusPlayer() {
        return status;
    }

    public void setColor(Color color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setGod(God god) {
        if (god == null)
            throw new NullPointerException();
        this.god = god;
    }

    public int placeWoker() {
        if (workers == 0)
            throw new IllegalAccessError();
        workers--;
        return workers;
    }

    public God getGod() {
        return god;
    }
}