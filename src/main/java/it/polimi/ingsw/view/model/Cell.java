package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Cell extends Action implements RawObj {
    private ArrayList<Block> blocks;

    public ArrayList<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

    @Override
    public ArrayList<String> getRawData() {
        ArrayList<String> toSend = (ArrayList<String>) blocks.stream().map(e -> e.getRawData().get(0))
                .collect(Collectors.toList());

        if (toSend.size() > 0 && Arrays.asList("LEVEL1", "LEVEL2", "LEVEL3").contains(toSend.get(toSend.size() - 1)))

            Collections.reverse(toSend);
        return toSend;
    }

}