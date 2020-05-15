package it.polimi.ingsw.view.model;

public class God extends Action {
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
}