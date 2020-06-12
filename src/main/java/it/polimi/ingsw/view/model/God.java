package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class God extends Action implements RawObj {
    public final String value;

    public God(String god) {
        if (god == null)
            throw new NullPointerException();
        this.value = god;
    }

    public God(String god, String toSend) {
        if (god == null)
            throw new NullPointerException();
        this.value = god;
        setToSend(toSend);
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList("God: " + value));
    }
}