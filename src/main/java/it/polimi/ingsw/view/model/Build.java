package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Build Action Data Structure
 */
public class Build implements RawObj {
    /**
     * Block that can be built
     */
    private final String block;
    /**
     * Position on the board
     */
    private final List<Integer> position;

    /**
     * Build Contructor
     * 
     * @param block    block to be built
     * @param position position on the board
     */
    public Build(String block, List<Integer> position) {
        this.block = block;
        this.position = position;
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList("Build", "Type Block: " + block,
                "Position: [" + (position.get(0) * 5 + position.get(1)) + "]"));
    }

    /**
     * Get Block to be built
     * 
     * @return block type
     */
    public String getBlock() {
        return block;
    }

    /**
     * Get Position on the board
     * 
     * @return board position
     */
    public List<Integer> getPosition() {
        return position.stream().map(e -> e).collect(Collectors.toList());
    }

}