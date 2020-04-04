package it.polimi.ingsw.model;

class Player implements Cloneable {
    private String username;

    /**
     * 
     * @param username player username
     */
    public Player(String username) {
        this.username = username;
    }

    @Override
    public Player clone() {
        Player player = new Player(username);
        return player;
    }

    /**
     * 
     * @return player username
     */
    public String getUsername() {
        return username;
    }
}