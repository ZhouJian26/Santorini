package it.polimi.ingsw.model;

public enum God {
    /**
     * List of available gods
     */
    STANDARD, APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, HERA, MEDUSA, MINOTAUR, PAN, POSEIDON,
    PROMETHEUS, ZEUS, TRITON;

    /**
     * @param input the string that needs to be converted
     * @return converted enum element
     */

    public static God strConverter(String input) {
        try {
            return Enum.valueOf(God.class, input.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
