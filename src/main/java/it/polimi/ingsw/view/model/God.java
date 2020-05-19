package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

public class God extends Action implements RawObj {
    public final String god;

    public God(String god) {
        if (god == null)
            throw new NullPointerException();
        this.god = god;
    }

    public God(String god, String toSend) {
        if (god == null)
            throw new NullPointerException();
        this.god = god;
        setToSend(toSend);
    }

    @Override
    public ArrayList<String> getRawData() {
        return new ArrayList<>(Arrays.asList("God: " + god));
    }
}