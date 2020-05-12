package it.polimi.ingsw.view.model;

import java.util.ArrayList;

public class Swap {
    public final ArrayList<Integer> x1;
    public final ArrayList<Integer> x2;
    public final ArrayList<Integer> y1;
    public final ArrayList<Integer> y2;
    public final Boolean status;
    private String toSend;
    // public final Boolean isBlocked;

    public Swap(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> y1, ArrayList<Integer> y2,
            Boolean status, Boolean isBlocked) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.status = status;
        // this.isBlocked = isBlocked;
    }

    public void setToSend(String toSend) {
        if (this.toSend == null)
            this.toSend = toSend;
    }

    public String getToSend() {
        return toSend;
    }
}