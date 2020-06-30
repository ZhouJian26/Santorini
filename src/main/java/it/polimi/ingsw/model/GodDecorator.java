package it.polimi.ingsw.model;

class GodDecorator implements GodInterface {
    /**
     * God Power
     */
    protected GodInterface godPower;

    /**
     * Set God Power
     * @param godPower God Power to be set
     */
    public GodDecorator(GodInterface godPower) {
        this.godPower = godPower;
    }

    /**
     * Set player's username
     * @param name player's username
     */
    @Override
    public void setCurrentPlayer(String name) {
        godPower.setCurrentPlayer(name);
    }

    /**
     *Set the worker's position
     * @param positionWorker worker's position
     */
    @Override
    public void setWorker(int[] positionWorker) {
        godPower.setWorker(positionWorker);
    }
    /**
     * Activate God Power's status
     * @param status God's status that needs to be activated
     */
    @Override
    public void activate(boolean status) {
        godPower.activate(status);
    }

    /**
     * Get player's status
     * @return player's status
     */
    @Override
    public StatusPlayer getPlayerStatus() {
        return godPower.getPlayerStatus();
    }

    /**
     * Get event
     * @param events event to be updated
     * @param map board situation at the moment
     * @param actions action of the event
     */
    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        godPower.getEvent(events, map, actions);
    }

    /**
     * Set player's status
     * @param statusPlayer player's status to be set
     */
    @Override
    public void setStatusPlayer(StatusPlayer statusPlayer) {
        godPower.setStatusPlayer(statusPlayer);
    }

    /**
     * Get player's username
     * @return player's username
     */
    @Override
    public String getName() {
        return godPower.getName();
    }

    /**
     * Get god's status
     * @return God's stauts
     */
    @Override
    public boolean getStatus() {
        return godPower.getStatus();
    }

    /**
     * Get the worker's position
     * @return worker's position
     */
    @Override
    public int[] getPositionWorker() {
        return godPower.getPositionWorker();
    }

    /**
     * Get the last god that changed his own status
     * @return last god that changed his own status
     */
    @Override
    public God getLastGod() {
        return godPower.getLastGod();
    }

    /**
     * Set the last god that changed his own status
     * @param lastGod last god that changed his own status
     */
    @Override
    public void setLastGod(God lastGod) {
        godPower.setLastGod(lastGod);
    }

    /**
     * Add information to the current player
     * @param currentPlayer Player that information needs to be updated
     */
    @Override
    public void addInfo(CurrentPlayer currentPlayer) {
        godPower.addInfo(currentPlayer);
    }

    /**
     * Get the current player
     * @return current player
     */
    @Override
    public String getCurrentPlayer() {
        return godPower.getCurrentPlayer();
    }

    /**
     * The god changed possible move actions of the board at the current turn
     * @param map Current board
     * @param actions Possible move actions on the board
     * @param position Chosen worker's position
     */
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

    /**
     * The god changed possible build actions of the board at the current turn
     * @param map Current board
     * @param actions Possible build actions on the board
     * @param position Chosen worker's position
     */
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
