package it.polimi.ingsw.model;

public enum GameMode {
    TWO, THREE;

    /**
     * @param input the str that needed to be converted
     * @return converted enum element
     */
    public static GameMode strConverter(String input) {
        return Enum.valueOf(GameMode.class, input.toUpperCase());
    }
    /**
     * @param mode GameMode chosen by players
     * @return numbers of players
     */
    public static int playersNum (GameMode mode){
        if (mode == GameMode.TWO) return 2;
        else return 3;
    }
}
