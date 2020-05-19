package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Block implements RawObj {
    public final String block;
    public final String owner;
    public final String color;

    public Block(String block, String owner, String color) {
        this.owner = owner;
        this.block = block;
        this.color = color;
    }

    @Override
    public ArrayList<String> getRawData() {
        String toRes = block;
        if (owner != null)
            toRes += " (" + owner + ")";
        return new ArrayList<>(Arrays.asList(toRes));
    }
}