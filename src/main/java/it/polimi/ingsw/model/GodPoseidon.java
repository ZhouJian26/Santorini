package it.polimi.ingsw.model;

class GodPoseidon extends GodDecorator {
    int count = 0;
    int[] position = new int[2];

    public GodPoseidon(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (godPower.getCurrentPlayer().equals(godPower.getName())) {
            if (events[0].equals(Event.BUILD) && count > 0) {
                setAction(map, actions, position);
                count--;
            } else if (events[0] == Event.ZERO) {
                count = 3;
                for (int i = 0; i < 25; i++) {
                    if ((map[i / 5][i % 5].getBlock().getTypeBlock().equals(TypeBlock.WORKER)
                            && map[i / 5][i % 5].getBlock().getOwner().equals(godPower.getName()))
                            && (i / 5 != godPower.getPositionWorker()[0] || i % 5 != godPower.getPositionWorker()[1])) {
                        position[0] = i / 5;
                        position[1] = i % 5;
                        break;
                    }
                }
            }
        }

    }

    private void setAction(Cell[][] map, Action[][][] actions, int[] positionWorker) {
        build(map, actions, positionWorker);
    }
}
