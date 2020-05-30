package it.polimi.ingsw.model;

public class Build implements Action {
    /*type of block to build and position */
    private TypeBlock block;
    private int[] position = new int[2];//useless
    private boolean status;
    private boolean blocked;
    private String TypeAction;
    private God god;

    public Build() {
        TypeAction = "Build";
    }

    @Override
    public void set(boolean status, TypeBlock block, int[] position) {
        this.block = block;
        this.position[0] = position[0];
        this.position[1] = position[1];
        if (!blocked) {
            this.status = status;
        }
    }

    @Override
    public boolean getStatus() {
        return status;
    }
    @Override
    public Event[] execute(Cell[][] map) {
        if (getStatus()) {
            Block newBlock = new Block(block);
            map[position[0]][position[1]].addBlock(newBlock);
        }
        Event[] events=new Event[1];
        events[0]=Event.BUILD;
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
        Build build = new Build();
        build.set(this.getStatus(), block, position);
        return build;
    }

    @Override
    public God getGod() {
        return god;
    }

    @Override
    public void set(int[] x1, int[] x2, int[] y1, int[] y2, boolean status) {

    }
}
