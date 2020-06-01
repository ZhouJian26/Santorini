package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block implements RawObj {
    public final String typeBlock;
    public final String owner;
    public final String color;

    public Block(String block, String owner, String color) {
        this.owner = owner;
        this.typeBlock = block;
        this.color = color;
    }

    @Override
    public List<String> getRawData() {
        String toRes = typeBlock;
        if (owner != null)
            toRes += color;
        return new ArrayList<>(Arrays.asList(toRes));
    }
}