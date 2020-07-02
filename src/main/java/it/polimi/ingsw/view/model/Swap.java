package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Worker Swap Data Structure
 */
public class Swap implements RawObj {
    /**
     * First Element start position to move
     */
    private final List<Integer> x1;
    /**
     * First Element end position to move
     */
    private final List<Integer> x2;
    /**
     * Second Element start position to move
     */
    private final List<Integer> y1;
    /**
     * Second Element end position to move
     */
    private final List<Integer> y2;

    /**
     * Swap Constructor
     * 
     * @param x1 First Element start position to move
     * @param x2 First Element end position to move
     * @param y1 Second Element start position to move
     * @param y2 Second Element end position to move
     */
    public Swap(List<Integer> x1, List<Integer> x2, List<Integer> y1, List<Integer> y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public ArrayList<String> getRawData() {
        String from = "[";
        String to = "] => [";
        if (Arrays.equals(y1.toArray(), y2.toArray()))
            return new ArrayList<>(
                    Arrays.asList("Move", from + (x1.get(0) * 5 + x1.get(1)) + to + (x2.get(0) * 5 + x2.get(1)) + "]"));
        return new ArrayList<>(
                Arrays.asList("Swap", from + (x1.get(0) * 5 + x1.get(1)) + to + (x2.get(0) * 5 + x2.get(1)) + "]",
                        from + (y1.get(0) * 5 + y1.get(1)) + to + (y2.get(0) * 5 + y2.get(1)) + "]"));
    }

    /**
     * Get First Element start position to move
     * 
     * @return First Element start position to move
     */
    public List<Integer> getX1() {
        return x1.stream().map(e -> e).collect(Collectors.toList());
    }

    /**
     * Get First Element end position to move
     * 
     * @return First Element end position to move
     */
    public List<Integer> getX2() {
        return x2.stream().map(e -> e).collect(Collectors.toList());
    }

    /**
     * Get Second Element start position to move
     * 
     * @return Second Element start position to move
     */
    public List<Integer> getY1() {
        return y1.stream().map(e -> e).collect(Collectors.toList());
    }

    /**
     * Get Second Element end position to move
     * 
     * @return Second Element end position to move
     */
    public List<Integer> getY2() {
        return y2.stream().map(e -> e).collect(Collectors.toList());
    }
}