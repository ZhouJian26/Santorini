package it.polimi.ingsw.view.model;

/**
 * Class used to preparse a worker action to get the type
 */
public class TypeAction {
    /**
     * Worker action type
     */
    private final String typeAction;

    /**
     * TypeAction Constructor
     * 
     * @param type worker action type
     */
    public TypeAction(String type) {
        this.typeAction = type;
    }

    /**
     * Get Worker Action type
     * 
     * @return action type
     */
    public String getTypeAction() {
        return typeAction;
    }
}