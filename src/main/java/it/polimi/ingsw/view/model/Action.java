package it.polimi.ingsw.view.model;

public class Action {

    private String toSend;

    public void setToSend(String toSend) {
        if (this.toSend == null)
            this.toSend = toSend;
    }

    public String getToSend() {
        return toSend;
    }
}