package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class Color extends Action implements RawObj {
    public final String value;

    public Color(String color) {
        if (color == null)
            throw new NullPointerException();
        this.value = color;
    }

    public Color(String color, String toSend) {
        if (color == null)
            throw new NullPointerException();
        this.value = color;
        setToSend(toSend);
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList("Color: " + value));
    }
}