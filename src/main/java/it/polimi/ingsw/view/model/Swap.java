package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Swap extends Action implements RawObj {
    public final ArrayList<Integer> x1;
    public final ArrayList<Integer> x2;
    public final ArrayList<Integer> y1;
    public final ArrayList<Integer> y2;
    public final Boolean status;
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

    @Override
    public ArrayList<String> getRawData() {
        if (Arrays.equals(y1.toArray(), y2.toArray()))
            return new ArrayList<>(Arrays.asList("Move", "From: " + x1.toString(), "To: " + x2.toString()));
        return new ArrayList<>(Arrays.asList("Swap", "From: " + x1.toString(), "To: " + x2.toString(),
                "From: " + y1.toString(), "To: " + y2.toString()));
    }
}