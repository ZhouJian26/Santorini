package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Action implements RawObj {
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

    public List<String> getValues() {
        return new ArrayList<>(Arrays.asList(username, status, color, god));
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
}