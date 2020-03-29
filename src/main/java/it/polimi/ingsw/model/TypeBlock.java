package it.polimi.ingsw.model;

public enum TypeBlock {
    LEVEL1, LEVEL2, LEVEL3, DOME, WORKER;

    // check if upblock can to built on downblock
    public static boolean allowedLiftUp (TypeBlock downblock, TypeBlock upblock) {
        switch (upblock) {
            case LEVEL1:
                return false;
            case LEVEL2:
                return (downblock == TypeBlock.LEVEL1);
            case LEVEL3:
                return (downblock == TypeBlock.LEVEL2);
            case DOME:
                return (downblock != TypeBlock.DOME ) && ( downblock != TypeBlock.WORKER);
            case WORKER:
                return (downblock != TypeBlock.WORKER ) && ( downblock != TypeBlock.DOME);
            default:
                throw new RuntimeException();
        }

    }
}
