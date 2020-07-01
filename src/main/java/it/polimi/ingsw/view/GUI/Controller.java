package it.polimi.ingsw.view.GUI;

public interface Controller {

    /**
     * Reload the board for all players
     */
    void reSet();


    /**
     * Set Height
     * @param height height
     */
    void setDimension(double width,double height);

    /**
     * Change view
     * @param status if it's allowed to change view
     */
    void changePage( Boolean status);
}
