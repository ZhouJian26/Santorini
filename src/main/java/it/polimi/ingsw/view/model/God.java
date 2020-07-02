package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * God Data Structure
 */
public class God implements RawObj {
    /**
     * God value
     */
    private final String value;

    /**
     * God Constructor
     * 
     * @param god god value
     */
    public God(String god) {
        if (god == null)
            throw new NullPointerException();
        this.value = god;
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList("God: " + value));
    }

    /**
     * Get god value
     * 
     * @return god value
     */
    public String getValue() {
        return value;
    }
}