package it.polimi.ingsw.view.model;

public class God {
    public final String god;
    private String toSend;

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

    public void setToSend(String toSend) {
        if (this.toSend == null)
            this.toSend = toSend;
    }

    public String getToSend() {
        return toSend;
    }
}