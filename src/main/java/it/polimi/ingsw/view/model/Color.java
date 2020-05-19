package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Color extends Action implements RawObj {
    public final String color;

    public Color(String color) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
    }

    public Color(String color, String toSend) {
        if (color == null)
            throw new NullPointerException();
        this.color = color;
        setToSend(toSend);
    }

    @Override
    public ArrayList<String> getRawData() {
        return new ArrayList<>(Arrays.asList("Color: " + color));
    }
}