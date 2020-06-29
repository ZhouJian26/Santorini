package it.polimi.ingsw.model;

class GodStandard extends GodDecorator {

    /**
     * Boolean value for moved or built
     */
    private boolean status;

    /**
     * Count player's actions
     * If count == 2, player can end his turn (unless gods'powers say otherwise)
     */
    private int count = 0;

    /**
     * *God* Standard's class
     * @param godPower God's power
     */
    public GodStandard(GodInterface godPower) {
        super(godPower);
    }

    /**
     * Get event
     * @param events event to be updated
     * @param map board situation at the moment
     * @param actions action of the event
     */
    @Override
    public void getEvent(Event[] events, Cell[][] map, Action[][][] actions) {
        if (events[0] == Event.ONE || events[0] == Event.TWO || events[0] == Event.THREE) {
            if (godPower.getPlayerStatus() == StatusPlayer.WIN) {
                return;
            }
            godPower.setStatusPlayer(StatusPlayer.LOSE);
            godPower.setLastGod(God.STANDARD);
            if (count == 0) {
                for (int i = 0; i < 25; i++) {
                    if (actions[i / 5][i % 5][0].getStatus()) {
                        godPower.setStatusPlayer(StatusPlayer.GAMING);
                        break;

                    }
                }
            } else {
                for (int i = 0; i < 25; i++) {
                    if (actions[i / 5][i % 5][1].getStatus() || (actions[i / 5][i % 5][2].getStatus())) {
                        godPower.setStatusPlayer(StatusPlayer.GAMING);
                        break;
                    }
                }
            }
            if (count == 2 && (events[0] == Event.ONE || events[0] == Event.THREE || godPower.getPlayerStatus().equals(StatusPlayer.LOSE))) {
                godPower.setStatusPlayer(StatusPlayer.IDLE);
                godPower.setLastGod(God.STANDARD);
                if (events[0] == Event.ONE) {
                    count = 0;
                }
            }
            return;
        }

        int[] position = godPower.getPositionWorker();
        if (events[0] == Event.ZERO) {
            status = false;
            count = 0;
            setAction(map, actions);
        } else if (events[0].equals(Event.MOVE)) {
            if (count == 0) {
                count = 1;
            }
            status = true;
            if (events[1].equals(Event.UP)) {
                if (map[position[0]][position[1]].getSize() == 4) {
                    if (godPower.getLastGod().equals(God.STANDARD)) {
                        godPower.setStatusPlayer(StatusPlayer.WIN);
                    }
                }
                setAction(map, actions);

            } else {
                setAction(map, actions);
            }
        } else {
            if (count == 1) {
                count = 2;
            } else if (count == 0) {
                status = false;
                setAction(map, actions);
            }
        }

    }

    /**
     * Set god's special move/build action (God Power) if possible
     * @param map Current board
     * @param actions List of possible actions
     */
    private void setAction(Cell[][] map, Action[][][] actions) {
        if (!status) {
            move(map, actions, godPower.getPositionWorker());
        } else {
            build(map, actions, godPower.getPositionWorker());
        }
    }
}


