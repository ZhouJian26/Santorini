package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Swap extends Action implements RawObj {
    public final List<Integer> x1;
    public final List<Integer> x2;
    public final List<Integer> y1;
    public final List<Integer> y2;
    public final Boolean status;

    public Swap(List<Integer> x1, List<Integer> x2, List<Integer> y1, List<Integer> y2, Boolean status) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.status = status;
    }

    @Override
    public ArrayList<String> getRawData() {
        String from = "From: ";
        String to = "To: ";
        if (Arrays.equals(y1.toArray(), y2.toArray()))
            return new ArrayList<>(Arrays.asList("Move", from + x1.toString(), to + x2.toString()));
        return new ArrayList<>(Arrays.asList("Swap", from + x1.toString(), to + x2.toString(), from + y1.toString(),
                to + y2.toString()));
    }
}