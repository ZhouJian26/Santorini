package it.polimi.ingsw.model;

public enum TypeBlock {
    WORKER, LEVEL1, LEVEL2, LEVEL3, DOME;

    // check if downblock is allowed to go up upblock
    public static boolean allowedLiftUp(TypeBlock downblock, TypeBlock upblock) {
        switch (upblock) {
            case DOME:
                return (downblock!=DOME)&&(downblock!=WORKER);
            case LEVEL1:
                return false;
            case LEVEL2:
                return (downblock == LEVEL1);
            case LEVEL3:
                return (downblock == LEVEL2);
            case WORKER:
                return (downblock!=WORKER)&&(downblock!=DOME);
            default:
                throw new RuntimeException();
        }

    }
}
