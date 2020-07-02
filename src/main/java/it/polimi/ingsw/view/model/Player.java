package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Data Structure
 */
public class Player implements RawObj {
    /**
     * Username
     */
    private final String username;
    /**
     * Player's status
     */
    private final String status;
    /**
     * Worker's color
     */
    private final String color;
    /**
     * Player's god
     */
    private final String god;
    /**
     * Player's remaining workers
     */
    private final int workers;

    /**
     * Player Constructor
     * 
     * @param username player username
     * @param status   player status
     * @param color    player color
     * @param god      player god
     * @param workers  player remaining workers
     */
    public Player(String username, String status, String color, String god, int workers) {
        if (username == null || username.length() == 0)
            throw new NullPointerException();
        this.username = username;
        this.status = status;
        this.color = color;
        this.god = god;
        this.workers = workers;
    }

    @Override
    public List<String> getRawData() {
        ArrayList<String> toRes = new ArrayList<>();
        toRes.add("Username: " + username);
        if (god != null)
            toRes.add("God: " + god);
        if (color != null)
            toRes.add("Color: " + color);
        if (status.equals("WIN") || status.equals("LOSE"))
            toRes.add("Status: " + status);
        return toRes;
    }

    /**
     * Get player's username
     * 
     * @return player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get player's status
     * 
     * @return player status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get player's color
     * 
     * @return player's color
     */
    public String getColor() {
        return color;
    }

    /**
     * Get player's god
     * 
     * @return player's god
     */
    public String getGod() {
        return god;
    }

    /**
     * Get player's remaining number of workers
     * 
     * @return number of remaining workers
     */
    public int getWorkers() {
        return workers;
    }

}