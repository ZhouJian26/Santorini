package it.polimi.ingsw.view.model;

public class Color extends Action {
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
}