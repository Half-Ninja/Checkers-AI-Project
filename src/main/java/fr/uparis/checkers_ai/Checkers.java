package fr.uparis.checkers_ai;

/**
 * class representing a checkerboard
 */
public abstract class Checkers {
    /**
     * current player (false for white, true for black)
     */
    private boolean current_player;

    /**
     * Checkerboard class
     */
    private Checkerboard board;

    public Checkers(){
        this.board = new Checkerboard();
        this.current_player = false;
    }

    public Checkers(boolean blackFirst){
        this.board = new Checkerboard();
        this.current_player = blackFirst;
    }

    /**
     * if possible, move the piece selected to the selected places
     * @param move move[0] = (x,y) corresponding to the piece's current placement, move[1] and onwards corresponding to the following move (or moves in case of consecutive captures)
     * @return true if the move is correctly executed
     */
    public boolean play(int[][] move){
        return this.board.move(move);
    }

    /**
     * checks if the game is finished
     * @return true if the game is finished (one side has no more pieces left)
     */
    public boolean isFinished(){
        return board.isFinished();
    }

    /**
     * returns the winner of the game
     * @return 0 is the game is not finished, >0 if white won, <0 if black won
     */
    public int wonBy(){
        return board.wonBy();
    }

    @Override
    public String toString() {
        return board.toString();
    }

    public Checkerboard getBoard() {
        return board;
    }
}
