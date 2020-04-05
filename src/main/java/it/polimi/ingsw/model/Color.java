package it.polimi.ingsw.model;

public enum Color {
    WHITE, BROWN, BLUE;

    /**
     * @param input the str that needed to be converted
     * @return converted enum element
     */

    public static Color strConverter(String input) {
        return Enum.valueOf(Color.class, input.toUpperCase());
    }

}