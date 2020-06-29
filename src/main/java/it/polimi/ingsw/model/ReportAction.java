package it.polimi.ingsw.model;

class ReportAction {
    private final StatusPlayer statusPlayer;
    private final God god;

    public ReportAction(StatusPlayer statusPlayer) {
        if (statusPlayer == null)
            throw new NullPointerException();
        this.statusPlayer = statusPlayer;
        god = God.STANDARD;
    }

    public ReportAction(StatusPlayer statusPlayer, God god) {
        if (statusPlayer == null || god == null)
            throw new NullPointerException();
        this.god = god;
        this.statusPlayer = statusPlayer;
    }

    public StatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    public God getGod() {
        return god;
    }
}
