package it.polimi.ingsw.model;

public class Build extends Action {
    /*type of block to build and position */
    private TypeBlock block;
    private int[] position=new int[2];

    public Build() {
        super("Build");
    }


    /*initialization*/
    public void set(boolean status,TypeBlock block, int[] position){
        this.block=block;
        this.position[0]=position[0];
        this.position[1]=position[1];
        super.set(status);
    }

    public void execute(Cell[][] map){
        Block newBlock=new Block(block);
        map[position[0]][position[1]].addBlock(newBlock);
    }

    @Override
    public Action clone()  {
        Build build=new Build();
        build.set(this.getStatus(),block,position);
        return build;
    }
}
