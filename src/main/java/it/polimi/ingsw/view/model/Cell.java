package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Cell extends Action implements RawObj {
    private ArrayList<Block> blocks;

    public Cell(List<Block> blocks) {
        this.blocks = (ArrayList<Block>) blocks.stream().map(e -> new Block(e.typeBlock, e.owner, e.color))
                .collect(Collectors.toList());
    }

    public List<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

    @Override
    public List<String> getRawData() {
        List<String> toSend = (ArrayList<String>) blocks.stream().map(e -> e.getRawData().get(0))
                .collect(Collectors.toList());
        if (blocks.size() > 0 && blocks.get(blocks.size() - 1).typeBlock.equals("WORKER"))
            toSend.add("- W -");
        if (!toSend.isEmpty()) {
            int offset = toSend.get(toSend.size() - 1).equals("DOME") ? 1 : 0;
            offset += (toSend.get(toSend.size() - 1).equals("- W -") ? 2 : 0);
            toSend = new ArrayList<>(toSend.subList(Math.max(0, toSend.size() - 1 - offset), toSend.size()));
        }
        Collections.reverse(toSend);
        return toSend;
    }

}