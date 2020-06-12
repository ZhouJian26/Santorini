package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Cell extends Action implements RawObj {
    private ArrayList<Block> blocks;

    public List<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

    @Override
    public List<String> getRawData() {
        ArrayList<String> toSend = (ArrayList<String>) blocks.stream().map(e -> e.getRawData().get(0))
                .collect(Collectors.toList());
        if (blocks.size() > 0 && blocks.get(blocks.size() - 1).typeBlock.equals("WORKER"))
            toSend.add("WORKER");

        // if (!toSend.isEmpty() && Arrays.asList("LEVEL1", "LEVEL2",
        // "LEVEL3").contains(toSend.get(toSend.size() - 1)))
        Collections.reverse(toSend);
        return toSend;
    }

}