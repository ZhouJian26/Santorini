package it.polimi.ingsw.model;

class Player {
    private String username;
    private StatusPlayer status;
    private Color color;
    private God god;
    private int workers;

    /**
     * 
     * @param username player username
     */
    public Player(String username) {
        this.username = username;
        workers = 2;
        status = StatusPlayer.END;
    }

    public String getUsername() {
        return username;
    }

    public void setStatusPlayer(StatusPlayer status) {
        this.status = status;
    }

    public StatusPlayer getStatusPlayer() {
        return status;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setGod(God god) {
        this.god = god;
    }

    public int placeWoker() {
        workers--;
        return workers;
    }

    public God getGod() {
        return god;
    }
}