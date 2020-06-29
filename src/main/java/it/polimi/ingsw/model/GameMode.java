package it.polimi.ingsw.model;

public enum GameMode {
    TWO(2), THREE(3);

    private final int playersNum;

    private GameMode(int playersNum) {
        this.playersNum = playersNum;
    }

    public int getPlayersNum() {
        return playersNum;
    }

    /**
     * @param input the str that needed to be converted
     * @return converted enum element, null if fail
     */
    public static GameMode strConverter(String input) {
        try {
            return Enum.valueOf(GameMode.class, input.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
