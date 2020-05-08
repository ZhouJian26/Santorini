package it.polimi.ingsw.view.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class Block {
    public final String block;
    public final String owner;
    public final String color;

    public Block(String block, String owner, String color) {
        this.owner = owner;
        this.block = block;
        this.color = color;
    }
}

public class Cell {
    private ArrayList<Block> blocks;

    public ArrayList<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

    public void printer() {
        for (Block x : blocks) {
            System.out.println(x.block + " - " + x.owner + " - " + x.color);
        }
    }
}