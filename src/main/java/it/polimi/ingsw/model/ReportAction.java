package it.polimi.ingsw.model;

class ReportAction {
    public final StatusPlayer statusPlayer;
    public final God god;

    public ReportAction(God god) {
        this.god = god;
        statusPlayer=null;
    }
    public ReportAction(StatusPlayer statusPlayer){
        this.statusPlayer=statusPlayer;
        god=null;
    }
    public ReportAction(StatusPlayer statusPlayer,God god) {
        this.god=god;
        this.statusPlayer=statusPlayer;
    }
}
