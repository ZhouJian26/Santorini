package it.polimi.ingsw.view.model;

import java.util.List;

/**
 * Interface manage data and user readability
 */
public interface RawObj {
    /**
     * Convert data to a list of string that contains a user readable information
     * about the class
     * 
     * @return data converted into an array of string user readable information
     *         about the class
     */
    public List<String> getRawData();
}