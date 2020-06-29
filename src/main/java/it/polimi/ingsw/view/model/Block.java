package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block implements RawObj {
    private final String typeBlock;
    private final String owner;
    private final String color;

    public Block(String block, String owner, String color) {
        this.owner = owner;
        this.typeBlock = block;
        this.color = color;
    }

    public Block(Block toClone) {
        this.owner = toClone.owner;
        this.typeBlock = toClone.typeBlock;
        this.color = toClone.color;
    }

    @Override
    public List<String> getRawData() {
        String toRes = typeBlock;
        if (owner != null)
            toRes = color;
        return new ArrayList<>(Arrays.asList(toRes));
    }

    public String getTypeBlock() {
        return typeBlock;
    }

    public String getOwner() {
        return owner;
    }

    public String getColor() {
        return color;
    }
}