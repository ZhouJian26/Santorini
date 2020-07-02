package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Data Structure
 */
public class Player implements RawObj {
    /**
     * username
     */
    private final String username;
    /**
     * player status
     */
    private final String status;
    /**
     * worker color
     */
    private final String color;
    /**
     * player god
     */
    private final String god;
    /**
     * player remaining workers
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
     * Get player username
     * 
     * @return player username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get player status
     * 
     * @return player status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get player color
     * 
     * @return player color
     */
    public String getColor() {
        return color;
    }

    /**
     * Get player god
     * 
     * @return player god
     */
    public String getGod() {
        return god;
    }

    /**
     * Get player remaining number of workers
     * 
     * @return numeber of remaining workers
     */
    public int getWorkers() {
        return workers;
    }

}