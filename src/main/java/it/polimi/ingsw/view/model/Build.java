package it.polimi.ingsw.view.model;

import java.util.ArrayList;

public class Build {
    public final String block;
    public final ArrayList<Integer> position;
    public final Boolean status;

    public Build(String block, ArrayList<Integer> position, Boolean status) {
        this.block = block;
        this.position = position;
        this.status = status;
    }

    public void printer() {
        System.out.println("Build: " + status + " - " + block + " - " + position.toString());
    }
}