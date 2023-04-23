package fr.uparis.checkers_ai;

import java.util.ArrayList;

import fr.uparis.checkers_ai.ai.AI;

/**
 * Checkers implementation with AI
 */
public class CheckersAI implements AI {
    // maximum search depth for the AI algorithm
    private int maxDepth;

    /**
     * Constructors
     *
     * @param maxDepth The maximum search depth for the alpha-beta algorithm
     */
    public CheckersAI(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Calculate the best move
     * 
     * @param board  the checkerboard to evaluate
     * @param player The player to optimize the evaluation function for
     * @return an array corresponding to the move, the first index corresponding to
     *         the piece's current placement, and each subsequennt index
     *         corresponding to the following move (or moves in case of consecutive
     *         captures)
     */
    @Override
    public int[][] chooseMove(Checkerboard board, boolean player) {
        int[][] bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int depth = 0;

        while (depth <= maxDepth) {
            int value = alphaBeta(board, alpha, beta, depth, player);
            if (value == Integer.MAX_VALUE || value == Integer.MIN_VALUE) {
                break;
            }
            bestMove = board.getLastMove();
            depth++;
        }
        return bestMove;
    }

    /**
     * The alpha-beta algorithm for finding the best move
     *
     * @param board  the checkerboard to evaluate
     * @param alpha  The alpha value for alpha-beta pruning
     * @param beta   The beta value for alpha-beta pruning
     * @param depth  The current depth in the search tree
     * @param player The player to optimize the evaluation function for
     * @return The value of the best move found
     */
    private int alphaBeta(Checkerboard board, int alpha, int beta, int depth, boolean player) {
        if (depth == maxDepth || board.isFinished()) {
            return evaluate(board);
        }

        ArrayList<int[][]> moves = board.getAllMoves(player);

        if (player) {
            int max = Integer.MIN_VALUE;

            for (int[][] move : moves) {
                Checkerboard newBoard = new Checkerboard(board.getBoard());
                newBoard.move(move);
                int value = alphaBeta(newBoard, alpha, beta, depth + 1, false);
                if (value > max) {
                    max = value;
                    if (max >= beta)
                        return max;
                    if (max > alpha)
                        alpha = max;
                }
            }
            return max;
        } else {
            int min = Integer.MAX_VALUE;
            for (int[][] move : moves) {
                Checkerboard newBoard = new Checkerboard(board.getBoard());
                newBoard.move(move);
                int value = alphaBeta(newBoard, alpha, beta, depth + 1, true);
                if (value < min) {
                    min = value;
                    if (min <= alpha)
                        return min;
                    if (min < beta)
                        beta = min;
                }
            }
            return min;
        }
    }

    /**
     * Evaluates the given board state for the given player
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    private int evaluate(Checkerboard board) {
        int score = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                score += board.getBoard()[i][j];
            }
        return score;
    }
}
