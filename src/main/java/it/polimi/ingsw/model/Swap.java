package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Cell;

public class Swap implements Action {
    private int[] x1 = new int[2];
    private int[] x2 = new int[2];
    private int[] y1 = new int[2];
    private int[] y2 = new int[2];
    private boolean status;
    private boolean blocked;
    private String TypeAction;
    private God god;

    public Swap() {
        this.TypeAction = "Swap";
    }

    /*initialization*/
    public void set(int[] x1, int[] x2, int[] y1, int[] y2, boolean status) {
        for (int i = 0; i < 2; i++) {
            this.x1[i] = x1[i];
            this.x2[i] = x2[i];
            this.y1[i] = y1[i];
            this.y2[i] = y2[i];
        }
        if (!blocked) {
            this.status = status;
        }
    }

    @Override
    public void set(boolean status, TypeBlock block, int[] position) {

    }

    @Override
    public boolean getStatus() {
        return status;
    }

    public void execute(Cell[][] map) {
        if (getStatus()) {
            Block block1 = map[x1[0]][x1[1]].popBlock();
            Block block2 = map[y1[0]][y1[1]].popBlock();
            map[y2[0]][y2[1]].addBlock(block2);
            map[x2[0]][x2[1]].addBlock(block1);
        }
    }

    @Override
    public void setGod(God god) {
        this.god = god;
    }

    @Override
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public void set(boolean status) {
        this.status=status;
    }

    @Override
    public Action clone() {
        Swap swapCopy = new Swap();
        swapCopy.set(x1, x2, y1, y2, this.getStatus());
        return swapCopy;

    }

    @Override
    public God getGod() {
        return god;
    }
}
