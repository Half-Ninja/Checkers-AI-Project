package fr.uparis.checkers_ai;

/**
 * base checkers classes containing the base functions of the class
 */
public class Checkerboard {
    /**
     * inner representation of the board
     * -1 = white pawn
     * -2 = white queen
     * 1 = black pawn
     * 2 = black queen
     *
     */
    private int[][] board;


    //TODO implement functions

    public Checkerboard(){
        this.board = new int[8][8];
    }

    /**
     * checks if the game is finished
     * @return true if the game is finished (one side has no more pieces left)
     */
    public boolean isFinished() {
        return false;
    }

    /**
     * returns the winner of the game
     * @return 0 is the game is not finished, >0 if white won, <0 if black won
     */
    public int wonBy() {
        return 0;
    }

}
