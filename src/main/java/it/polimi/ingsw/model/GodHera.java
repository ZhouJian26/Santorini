package it.polimi.ingsw.model;

public class GodHera extends GodDecorator {
    public GodHera(GodInterface godPower) {
        super(godPower);
    }

    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        System.out.println("0");
        if (events[0].equals(Event.MOVE) && !godPower.getName().equals(godPower.getCurrentPlayer())) {
            System.out.println("1");
            if (godPower.getPositionWorker()[0] == 0 || godPower.getPositionWorker()[0] == 4 || godPower.getPositionWorker()[1] == 0 || godPower.getPositionWorker()[1] == 4) {
                System.out.println("2");
                if (godPower.getPlayerStatus().equals(StatusPlayer.WIN)) {
                    godPower.setStatusPlayer(StatusPlayer.GAMING);
                    godPower.setLastGod(God.HERA);
                }
            }
        }
    }
}
