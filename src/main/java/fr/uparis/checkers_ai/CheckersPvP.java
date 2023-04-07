package fr.uparis.checkers_ai;

/**
 * Checkers implementation player vs player
 */
public class CheckersPvP extends Checkers {

    /**
     * if possible, move the piece selected to the selected places
     * @param move move[0] = (x,y) corresponding to the piece's current placement, move[1] and onwards corresponding to the following move (or moves in case of consecutive captures)
     * @return true if the move is correctly executed
     */
    @Override
    public boolean play(int[][] move){
        if(isFinished() ||  //on a finished game OR
                (this.getBoard().pieceAt(move[0][0],move[0][1]) < 0 && this.currentPlayer()) //white piece selected on black's turn
            ) return false; //returns false
        if(super.play(move)){
            switchPlayer();
            return true;
        }
        else return false;
    }
}
