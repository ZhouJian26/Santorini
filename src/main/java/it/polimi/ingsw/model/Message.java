package it.polimi.ingsw.model;

public class Message {
    private Cell[][] board;
    private Action[][][] actions;

    public Message(Cell[][] board, Action[][][] actions) {
        this.board = board;
        this.actions = actions;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Action[][][] getActions() {
        return actions;
    }
}