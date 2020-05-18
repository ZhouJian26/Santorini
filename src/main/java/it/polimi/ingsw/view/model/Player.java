package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Player implements RawObj {
    public final String username;
    public final String status;
    public final String color;
    public final String god;
    public final int workers;

    public Player(String username, String status, String color, String god, int workers) {
        if (username == null || username.length() == 0)
            throw new NullPointerException();
        this.username = username;
        this.status = status;
        this.color = color;
        this.god = god;
        this.workers = workers;
    }

    public ArrayList<String> getValues() {
        return new ArrayList<String>(Arrays.asList(username, status, color, god));
    }

    @Override
    public ArrayList<String> getRawData() {
        return new ArrayList<>(
                Arrays.asList("Username: " + username, "God: " + god, "Color: " + color, "Status: " + status));
    }
}