package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A Stack of a cell in the Board
 */
public class Cell implements RawObj {
    /**
     * Stack of Block in the Board Cell
     */
    private ArrayList<Block> blocks;

    /**
     * Cell Constructor
     * 
     * @param blocks Block Stack on the Cell
     */
    public Cell(List<Block> blocks) {
        this.blocks = (ArrayList<Block>) blocks.stream().map(Block::new).collect(Collectors.toList());
    }

    /**
     * Get the Block Stack
     * 
     * @return block stack
     */
    public List<Block> getBlocks() {
        return new Gson().fromJson(new Gson().toJson(blocks), new TypeToken<ArrayList<Block>>() {
        }.getType());
    }

    /**
     * verify if this cell is equals to b
     * 
     * @param b confront cell
     * @return true if this equals b else false
     */
    public boolean equals(Cell b) {
        try {
            if (blocks.size() != b.getBlocks().size()) {
                return false;
            }
            int size = blocks.size();
            for (int i = 0; i < size; i++) {
                if (!blocks.get(i).getTypeBlock().equals(b.getBlocks().get(i).getTypeBlock())) {
                    return false;
                } else if (blocks.get(i).getTypeBlock().toUpperCase().equals("WORKER")
                        && !blocks.get(i).getColor().equals(b.getBlocks().get(i).getColor())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> getRawData() {
        List<String> toSend = (ArrayList<String>) blocks.stream().map(e -> e.getRawData().get(0))
                .collect(Collectors.toList());
        if (blocks.size() > 0 && blocks.get(blocks.size() - 1).getTypeBlock().equals("WORKER"))
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