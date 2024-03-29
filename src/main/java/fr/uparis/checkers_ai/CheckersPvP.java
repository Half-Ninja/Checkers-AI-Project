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
        if(super.play(move)){
            switchPlayer();
            return true;
        }
        else return false;
    }
}
