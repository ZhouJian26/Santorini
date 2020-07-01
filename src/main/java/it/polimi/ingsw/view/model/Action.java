package it.polimi.ingsw.view.model;

import com.google.gson.Gson;

import it.polimi.ingsw.utils.model.Command;

/**
 * Abstract Class used to manage data to send back to server
 */
abstract class Action {
    /**
     * Data to send to server
     */
    private String toSend;

    /**
     * Set data to send to server
     * 
     * @param toSend data to send
     */
    public void setToSend(String toSend) {
        if (this.toSend == null)
            this.toSend = toSend;
    }

    /**
     * Set data to send to server
     * 
     * @param toSend data to send
     */
    public void setToSend(Command toSend) {
        if (this.toSend == null && toSend.getFuncName() != null)
            this.toSend = new Gson().toJson(toSend);
    }

    /**
     * Get data to send to server
     * 
     * @return data to send
     */
    public String getToSend() {
        return toSend;
    }
}