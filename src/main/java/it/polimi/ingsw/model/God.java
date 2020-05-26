package it.polimi.ingsw.model;

public enum God {
    STANDARD, APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, HERA, MEDUSA, MINOTAUR, PAN, PERSEPHONE, POSEIDON, PROMETHEUS, ZEUS;

    /**
     * @param input the str that needed to be converted
     * @return converted enum element
     */

    public static God strConverter(String input) {
        return Enum.valueOf(God.class, input.toUpperCase());
    }
}
