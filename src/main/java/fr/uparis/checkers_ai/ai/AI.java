package fr.uparis.checkers_ai.ai;

import fr.uparis.checkers_ai.Checkerboard;

/**
 * interface corresponding to an AI for use in CheckersAI
 */
public interface AI {
    /**
     * looks at the board and calculate the move
     * 
     * @param board  the checkerboard to evaluate
     * @param player The player to optimize the evaluation function for (true for
     *               white, false for black)
     * @return an array corresponding to the move, the first index corresponding to
     *         the piece's current placement, and each subsequennt index
     *         corresponding to the following move (or moves in case of consecutive
     *         captures)
     */
    int[][] chooseMove(Checkerboard board, boolean player);
}
