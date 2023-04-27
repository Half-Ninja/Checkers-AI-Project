package fr.uparis.checkers_ai.ai;

import java.util.ArrayList;

import fr.uparis.checkers_ai.Checkerboard;

/**
 * Checkers implementation with AI
 */
public class AlphaBetaAI implements AI {
    // maximum search depth for the AI algorithm
    private final int maxDepth;

    /**
     * Constructors
     *
     * @param maxDepth The maximum search depth for the alpha-beta algorithm
     */
    public AlphaBetaAI(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Calculate the best move
     * 
     * @param board  the checkerboard to evaluate
     * @param player The player to optimize the evaluation function for (true for
     *               white, false for black)
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

        // first level of the alpha-beta tree
        ArrayList<int[][]> moves = board.getAllMoves(player);

        if (player) {
            int max = Integer.MIN_VALUE;
            for (int[][] move : moves) {
                Checkerboard newBoard = new Checkerboard(board.getBoard());
                newBoard.move(move);
                int value = alphaBeta(newBoard, alpha, beta, depth + 1, false);
                if (value > max) {
                    max = value;
                    bestMove = move;
                    if (max >= beta)
                        return bestMove;
                    if (max > alpha)
                        alpha = max;
                }
            }
            return bestMove;
        } else {
            int min = Integer.MAX_VALUE;
            for (int[][] move : moves) {
                Checkerboard newBoard = new Checkerboard(board.getBoard());
                newBoard.move(move);
                int value = alphaBeta(newBoard, alpha, beta, depth + 1, true);
                if (value < min) {
                    min = value;
                    bestMove = move;
                    if (min <= alpha)
                        return bestMove;
                    if (min < beta)
                        beta = min;
                }
            }
            return bestMove;
        }
    }

    /**
     * The alpha-beta algorithm for finding the best move
     *
     * @param board  the checkerboard to evaluate
     * @param alpha  The alpha value for alpha-beta pruning
     * @param beta   The beta value for alpha-beta pruning
     * @param depth  The current depth in the search tree
     * @param player The player to optimize the evaluation function for (true for
     *               white, false for black)
     * @return The value of the best move found
     */
    private int alphaBeta(Checkerboard board, int alpha, int beta, int depth, boolean player) {
        if (depth >= maxDepth || board.isFinished()) {
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
     * Evaluates the given board state.
     * Negative values are better for black, positive for white
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    public int evaluate(Checkerboard board) {
        return pieceEvaluation(board.getBoard());
    }

    /**
     * Evaluates the pieces of a given board state.
     * 10 for a queen and 1 for a pawn
     * Negative values are better for black, positive for white
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    public int pieceEvaluation(int[][] board) {
        int score = 0;
        for (int[] ints : board) {
            for (int j = 0; j < board.length; j++) {
                if (ints[j] == -2 || ints[j] == 2) // if a queen
                    score += ints[j] * 5;
                else
                    score += ints[j];
            }
        }
        return score;
    }
}
