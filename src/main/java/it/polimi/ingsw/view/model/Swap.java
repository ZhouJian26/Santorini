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
        String from = "[";
        String to = "] => [";
        if (Arrays.equals(y1.toArray(), y2.toArray()))
            return new ArrayList<>(
                    Arrays.asList("Move", from + (x1.get(0) * 5 + x1.get(1)) + to + (x2.get(0) * 5 + x2.get(1)) + "]"));
        return new ArrayList<>(
                Arrays.asList("Swap", from + (x1.get(0) * 5 + x1.get(1)) + to + (x2.get(0) * 5 + x2.get(1)) + "]",
                        from + (y1.get(0) * 5 + y1.get(1)) + to + (y2.get(0) * 5 + y2.get(1)) + "]"));
    }
}