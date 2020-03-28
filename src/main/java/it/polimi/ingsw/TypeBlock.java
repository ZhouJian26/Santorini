package it.polimi.ingsw;

public enum TypeBlock {
    WORKER, LEVEL1, LEVEL2, LEVEL3, DOME;

    //check if downblock is allowed to go up upblock
    public static boolean allowedLiftUp (TypeBlock downblock, TypeBlock upblock){
        switch (upblock){
            case DOME:
                return (downblock == LEVEL3);
            case LEVEL1:
                return (downblock == WORKER);
            case LEVEL2:
                return (downblock == WORKER || downblock == LEVEL1);
            case LEVEL3:
                return (downblock == WORKER || downblock == LEVEL2) ;
            case WORKER:
                return false;
            default:
                throw new RuntimeException();
        }

    }
}
