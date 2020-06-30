package it.polimi.ingsw.model;

public enum GameMode {

    /**
     * Type of game modes
     */
    TWO(2), THREE(3);

    /**
     * Players'number
     */
    private final int playersNum;

    /**
     * Set players'number
     * @param playersNum number of players
     */
    private GameMode(int playersNum) {
        this.playersNum = playersNum;
    }

    /**
     * Get number of players
     * @return Number of players
     */
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
