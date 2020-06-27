package it.polimi.ingsw.model;

class GodDecorator implements GodInterface {
    protected GodInterface godPower;

    public GodDecorator(GodInterface godPower) {
        this.godPower = godPower;
    }

    @Override
    public void setCurrentPlayer(String name) {
        godPower.setCurrentPlayer(name);
    }

    @Override
    public void setWorker(int[] positionWorker) {
        godPower.setWorker(positionWorker);
    }

    @Override
    public void activate(boolean status) {
        godPower.activate(status);
    }

    @Override
    public StatusPlayer getPlayerStatus() {
        return godPower.getPlayerStatus();
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        godPower.getEvent(events, map, actions);
    }

    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        godPower.setStatusPlayer(statusPlayer);
    }

    @Override
    public String getName() {
        return godPower.getName();
    }

    @Override
    public boolean getStatus() {
        return godPower.getStatus();
    }

    @Override
    public int[] getPositionWorker() {
        return godPower.getPositionWorker();
    }

    @Override
    public God getLastGod() {
        return godPower.getLastGod();
    }

    @Override
    public void setLastGod(God lastGod) {
        godPower.setLastGod(lastGod);
    }

    @Override
    public void addInfo(CurrentPlayer currentPlayer) {
        godPower.addInfo(currentPlayer);
    }

    @Override
    public String getCurrentPlayer() {
        return godPower.getCurrentPlayer();
    }

    public void move(Cell[][] map, Action[][][] actions, int[] position) {
        int[] destination = new int[2];
        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {
            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {
                if ((map[i][j].getSize() <= map[position[0]][position[1]].getSize())
                        && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER)
                        && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                    destination[0] = i;
                    destination[1] = j;
                    ((Swap) actions[i][j][0]).set(position, destination, destination, destination, true);
                }
            }
        }
    }

    public void build(Cell[][] map, Action[][][] actions, int[] position ) {
        int[] destination=new int[2];
        TypeBlock typeBlock=null;
        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {

            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {

                if (!map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.WORKER)
                        && !map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock().equals(TypeBlock.DOME)) {
                    switch (map[i][j].getBlock(map[i][j].getSize() - 1).getTypeBlock()) {
                        case LEVEL1:
                            typeBlock = TypeBlock.LEVEL2;
                            destination[0] = i;
                            destination[1] = j;
                            ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                            break;
                        case LEVEL2:
                            typeBlock = TypeBlock.LEVEL3;
                            destination[0] = i;
                            destination[1] = j;
                            ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                            break;
                        case LEVEL3:
                            typeBlock = TypeBlock.DOME;
                            destination[0] = i;
                            destination[1] = j;
                            ((Build) actions[i][j][2]).set(true, typeBlock, destination);
                            break;
                        default:
                            typeBlock = TypeBlock.LEVEL1;
                            destination[0] = i;
                            destination[1] = j;
                            ((Build) actions[i][j][1]).set(true, typeBlock, destination);
                    }
                }
            }

        }
    }
}
