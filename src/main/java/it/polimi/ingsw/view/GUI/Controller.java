package it.polimi.ingsw.view.GUI;

public interface Controller {

    /**
     * Reload the board for all players
     */
    void reSet();

    /**
     * Set width
     * @param width width
     */
    void setWidth(double width);

    /**
     * Set Height
     * @param height height
     */
    void setHeight(double height);

    /**
     * Change view
     * @param status if it's allowed to change view
     */
    void changePage( Boolean status);
}
