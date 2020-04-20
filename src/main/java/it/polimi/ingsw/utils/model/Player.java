package it.polimi.ingsw.utils.model;

public class Player {
    public final String username;
    public final String status;
    public final String color;
    public final String god;
    public final int workers;

    public Player(String username, String status, String color, String god, int workers) {
        this.username = username;
        this.status = status;
        this.color = color;
        this.god = god;
        this.workers = workers;
    }
}