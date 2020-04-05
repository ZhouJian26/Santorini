package it.polimi.ingsw.model;

public enum TypeBlock {
    LEVEL1, LEVEL2, LEVEL3, DOME, WORKER;

    // check if upblock can be built on downblock in STANDARD CONDITION
    // need to consider exceptions with GodPowers
    /**
     * @param upblock the block that player wants to put above
     * @param downblock the block below
     * @return if the move is allowed
     */

    public static boolean allowedLiftUp (TypeBlock downblock, TypeBlock upblock) {
        switch (upblock) {
            case LEVEL1:
                return false;
            case LEVEL2:
                return (downblock == TypeBlock.LEVEL1);
            case LEVEL3:
                return (downblock == TypeBlock.LEVEL2);
            case DOME:
                return (downblock == TypeBlock.LEVEL3);
            case WORKER:
                return (downblock != TypeBlock.WORKER ) && ( downblock != TypeBlock.DOME);
            default:
                throw new RuntimeException();
        }

    }
}
