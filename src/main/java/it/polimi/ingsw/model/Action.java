package it.polimi.ingsw.model;

public class Action implements Cloneable {
    /* status this action will be executed or not */
    private boolean status;
    private boolean blocked;
    private String TypeAction;
    private God god;

    public Action(String typeAction) {
        TypeAction = typeAction;
    }

    public void execute(Cell[][] map) {
    }

    public void set(boolean status) {
        if (!blocked) {
            this.status = status;
        }
    }

    public void setGod(God god) {
        if (!blocked)
            this.god = god;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void set(int[] x1, int[] x2, int[] y1, int[] y2, boolean status) {
    }

    public void set(boolean status, TypeBlock block, int[] position) {
    }

    public boolean getStatus() {
        return status;
    }

    public Action clone() {
        return new Action(TypeAction);
    }

    public God getGod() {
        return god;
    }
}
