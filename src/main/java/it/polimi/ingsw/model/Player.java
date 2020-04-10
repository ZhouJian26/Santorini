package it.polimi.ingsw.model;

class Player implements Cloneable {
    private String username;
    private StatusPlayer status;
    private Color color;

    /**
     * 
     * @param username player username
     */
    public Player(String username) {
        this.username = username;
        status = StatusPlayer.END;
    }

    @Override
    public Player clone() {
        Player player = new Player(username);
        player.setStatusPlayer(status);
        return player;
    }

    /**
     * 
     * @return player username
     */
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
        if (this.color == null) {
            this.color = color;
        }
    }

    public Color getColor() {
        return color;
    }
}