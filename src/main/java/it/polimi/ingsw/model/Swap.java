package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Action;

public class Swap implements Action {
    private int[] x1 = new int[2];
    private int[] x2 = new int[2];
    private int[] y1 = new int[2];
    private int[] y2 = new int[2];
    private boolean status;
    private boolean blocked;
    private final String typeAction;
    private God god;

    public Swap() {
        this.typeAction = "Swap";
    }

    public String getTypeAction() {
        return typeAction;
    }

    @Override
    public void set(int[] x1, int[] x2, int[] y1, int[] y2, boolean status) {
        for (int i = 0; i < 2; i++) {
            this.x1[i] = x1[i];
            this.x2[i] = x2[i];
            this.y1[i] = y1[i];
            this.y2[i] = y2[i];
        }
        if (!blocked) {
            set(status);
        }
    }

    @Override
    public void set(boolean status, TypeBlock block, int[] position) {

    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public Event[] execute(Cell[][] map) {
        if (getStatus()) {
            Block block1 = map[x1[0]][x1[1]].popBlock();
            Block block2 = map[y1[0]][y1[1]].popBlock();
            map[y2[0]][y2[1]].addBlock(block2);
            map[x2[0]][x2[1]].addBlock(block1);
        }
        Event[] events = new Event[3];
        events[0] = Event.MOVE;
        if (y2[0] == y1[0] && y2[1] == y1[1]) {
            switch (map[x2[0]][x2[1]].getSize() - map[x1[0]][x1[1]].getSize()) {
                case 1:
                    events[1] = Event.ZERO;
                    break;
                case 2:
                    events[1] = Event.UP;
                    break;
                case 0:
                    events[1] = Event.DOWN;
                    events[2] = Event.ONE;
                    break;
                case -1:
                    events[1] = Event.DOWN;
                    events[2] = Event.TWO;
                    break;
                case -2:
                    events[1] = Event.DOWN;
                    events[2] = Event.THREE;
                    break;
                default:
                    break;
            }
        } else {
            if (map[x2[0]][x2[1]].getSize() - map[x1[0]][x1[1]].getSize() == 1)
                events[1] = Event.UP;
            else
                events[1] = Event.ZERO;
        }
        return events;
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
        if (!blocked) {
            this.status = status;
        }
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
