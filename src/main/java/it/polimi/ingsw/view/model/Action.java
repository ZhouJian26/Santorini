package it.polimi.ingsw.view.model;

import com.google.gson.Gson;

import it.polimi.ingsw.utils.model.Command;

public class Action {

    private String toSend;

    public void setToSend(String toSend) {
        if (this.toSend == null)
            this.toSend = toSend;
    }

    public void setToSend(Command toSend) {
        if (this.toSend == null && toSend.getFuncName() != null)
            this.toSend = new Gson().toJson(toSend);
    }

    public String getToSend() {
        return toSend;
    }
}