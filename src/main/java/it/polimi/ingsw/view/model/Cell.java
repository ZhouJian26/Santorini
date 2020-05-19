package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.utils.model.Command;

public class Cell extends Action implements RawObj {
    private ArrayList<Block> blocks;

    public ArrayList<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

    @Override
    public ArrayList<String> getRawData() {
        // int position = Integer.parseInt(new Gson().fromJson(getToSend(),
        // Command.class).funcData);
        ArrayList<String> toSend = (ArrayList<String>) blocks.stream().map(e -> e.getRawData().get(0))
                .collect(Collectors.toList());
        toSend.add("Cell");
        Collections.reverse(toSend);
        return toSend;
    }

}