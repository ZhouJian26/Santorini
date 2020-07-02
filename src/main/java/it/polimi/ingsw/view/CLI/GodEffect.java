package it.polimi.ingsw.view.CLI;

/**
 * God effects map god name to gods'effects
 */
enum GodEffect {
    APOLLO("Your Move: Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated."),
    ARTEMIS("Your Move: Your Worker may move one additional time, but not back to its initial space."),
    ATHENA("Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn."),
    ATLAS("Your Build: Your Worker may build a dome at any level."),
    DEMETER("Your Build: Your Worker may build one additional time, but not on the same space."),
    HEPHAESTUS("Your Build: Your Worker may build one additional block (not dome) on top of your first block."),
    HERA("Opponent’s Turn: An opponent cannot win by moving into a perimeter space."),
    MEDUSA("End of Your Turn: If possible, your Workers build in lower neighboring spaces that are occupied by opponent Workers, removing the opponent Workers from the game."),
    MINOTAUR(
            "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level."),
    PAN("Win Condition: You also win if your Worker moves down two or more levels."),
    POSEIDON("End of Your Turn: If your unmoved Worker is on the ground level, it may build up to three times."),
    PROMETHEUS("Your Turn: If your Worker does not move up, it may build both before and after moving."),
    ZEUS("Your Build: Your Worker may build a block under itself."),
    TRITON("Your Move: Each time your Worker moves into a perimeter space, it may immediately move again.");

    /**
     * God Effect
     */
    private final String effect;

    /**
     * GetEffect Constructor
     * 
     * @param effect God Effect
     */
    private GodEffect(String effect) {
        this.effect = effect;
    }

    /**
     * Get god effect
     * 
     * @return god effect
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Convert a god name into a GodEffect, otherwise null is returned
     * 
     * @param input the str that needed to be converted
     * @return converted enum element
     */

    public static GodEffect strConverter(String input) {
        try {
            return Enum.valueOf(GodEffect.class, input.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}