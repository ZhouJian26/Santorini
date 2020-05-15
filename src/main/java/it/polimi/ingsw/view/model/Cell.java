package it.polimi.ingsw.view.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Cell extends Action {
    private ArrayList<Block> blocks;

    public ArrayList<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

}