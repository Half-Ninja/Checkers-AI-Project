package fr.uparis.checkers_ai;

/**
 * class representing a checkerboard
 */
public abstract class Checkers {
    /**
     * current player (true for white, false for black)
     */
    private boolean current_player;

    /**
     * Checkerboard class
     */
    private final Checkerboard board;

    /**
     * initialise a new checkers game
     */
    public Checkers(){
        this.board = new Checkerboard();
        this.current_player = true;
    }

    /**
     * if possible, move the piece selected to the selected places
     * @param move move[0] = (x,y) corresponding to the piece's current placement, move[1] and onwards corresponding to the following move (or moves in case of consecutive captures)
     * @return true if the move is correctly executed
     */
    public boolean play(int[][] move){
        if(isFinished() ||      //      //      //      //      //      //      //      //      //on a finished game OR
                (this.getBoard().pieceAt(move[0][0],move[0][1]) < 0 && !this.currentPlayer()) || //white piece selected on black's turn
                (this.getBoard().pieceAt(move[0][0],move[0][1]) > 0 && this.currentPlayer())   //black piece selected on white's turn
        ) return false; //returns false
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

    public boolean currentPlayer() {
        return current_player;
    }

    public void switchPlayer() {
        this.current_player = !this.current_player;
    }
}
