package it.polimi.ingsw.model;

public class GodPersephone extends GodDecorator {
    public GodPersephone(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if(godPower.getName().equals(godPower.getCurrentPlayer())){
            System.out.println("active");
            activate(true);
        }
        else if (godPower.getStatus()){
            System.out.println("1");
            if (events[0] == Event.ZERO) {
                System.out.println("2");
                if (!setEvent(map, actions, godPower.getPositionWorker(),false)) {
                    System.out.println("3");
                    for (int i = 0; i < 25; i++) {
                        System.out.println(i);
                        if (map[i / 5][i % 5].getBlock().getTypeBlock().equals(TypeBlock.WORKER) && map[i / 5][i % 5].getBlock().getOwner().equals(godPower.getCurrentPlayer())) {
                            System.out.println("2: "+ i);
                            if (i / 5 != godPower.getPositionWorker()[0] || i % 5 != godPower.getPositionWorker()[1]) {
                                System.out.println("5");
                                if (setEvent(map, actions, new int[]{i / 5, i % 5}, false)) {
                                    System.out.println("6");
                                    setEvent(map, actions, godPower.getPositionWorker(), true);
                                }
                                break;
                            }
                        }
                    }
                }
                else {
                    System.out.println("4");
                    setEvent(map,actions,godPower.getPositionWorker(),true);
                }
            }
        }
    }

    private boolean setEvent(Cell[][] map, Action[][][] actions, int[] position, boolean flag) {
        boolean stato = false;
        for (int i = Math.max(0, position[0] - 1); (i <= Math.min(4, position[0] + 1)); i++) {
            for (int j = Math.max(0, position[1] - 1); j <= Math.min(4, position[1] + 1); j++) {
                System.out.println(i);
                System.out.println(j);
                System.out.println(position);
                if (i != position[0] || j != position[1]) {
                    if (map[i][j].getSize() == map[position[0]][position[1]].getSize() && !map[i][j].getBlock().getTypeBlock().equals(TypeBlock.WORKER)) {
                        System.out.println("si");
                        if (flag) {
                            actions[i][j][0].set(true);
                            actions[i][j][0].setGod(God.PERSEPHONE);
                        }
                        stato = true;
                    } else {
                        System.out.println("no");
                        if (flag) {
                            actions[i][j][0].set(false);
                            actions[i][j][0].setGod(God.PERSEPHONE);
                            actions[i][j][0].setBlocked(true);
                        }
                    }
                }
            }
        }
        return stato;
    }

}
