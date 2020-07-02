package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

/**
 * Worker Color Data Structure
 */
public class Color implements RawObj {
    /**
     * Color Value
     */
    private final String value;

    /**
     * Color Constructor
     * 
     * @param color color value
     */
    public Color(String color) {
        if (color == null)
            throw new NullPointerException();
        this.value = color;
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList("Color: " + value));
    }

    /**
     * Get color value
     * 
     * @return color value
     */
    public String getValue() {
        return value;
    }
}