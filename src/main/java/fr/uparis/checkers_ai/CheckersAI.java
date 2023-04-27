package fr.uparis.checkers_ai;

import fr.uparis.checkers_ai.ai.AI;

/**
 * Checkers implementation player vs AI
 */
public class CheckersAI extends Checkers{
    private final AI ai;

    /**
     * initialise a new checkers game against a given AI
     * @param ai the AI to play against
     */
    public CheckersAI(AI ai){
        super();
        this.ai = ai;
    }

    /**
     * initialise a new checkers game against a given AI
     * @param ai the AI to play against
     * @param asBlack true if the player is to play second
     */
    public CheckersAI(AI ai, boolean asBlack){
        this(ai);
        if(asBlack){ // if the player plays second
            getBoard().move(ai.chooseMove(getBoard(), true));
        }
        switchPlayer();
    }

    @Override
    public boolean play(int[][] move) {
        if (!super.play(move)) return false;
        return getBoard().move(ai.chooseMove(getBoard(), true));
    }
}
