package it.polimi.ingsw.view.model;

public class Color {
    public final String color;
    private String toSend;

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

    public void setToSend(String toSend) {
        if (this.toSend == null)
            this.toSend = toSend;
    }

    public String getToSend() {
        return toSend;
    }
}