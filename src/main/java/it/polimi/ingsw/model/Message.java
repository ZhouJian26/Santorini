package it.polimi.ingsw.model;

public class Message {
    private Cell[][] board;
    private Action[][][] actions;
    private String currentPlayer;

    public Message(String currentPlayer, Cell[][] board, Action[][][] actions) {
        this.board = board;
        this.actions = actions;
        this.currentPlayer = currentPlayer;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Action[][][] getActions() {
        return actions;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}