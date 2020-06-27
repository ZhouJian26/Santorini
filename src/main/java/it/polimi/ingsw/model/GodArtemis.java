package it.polimi.ingsw.model;

class GodArtemis extends GodDecorator {
    private int count = 0;
    private int[] startPosition = new int[2];

    public GodArtemis(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getName().equals(godPower.getCurrentPlayer())) {
            if (events[0] == Event.ZERO) {
                count = 1;
                startPosition = godPower.getPositionWorker();
            } else if (events[0].equals(Event.MOVE) && count == 1) {
                count = 0;
                setAction(map, actions);
            }
        }
    }

    private void setAction(Cell[][] map, Action[][][] actions) {
        move(map,actions,godPower.getPositionWorker());
        actions[startPosition[0]][startPosition[1]][0].set(false);
    }
}
