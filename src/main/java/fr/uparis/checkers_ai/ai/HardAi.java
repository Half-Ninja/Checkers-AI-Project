package fr.uparis.checkers_ai.ai;

import fr.uparis.checkers_ai.Checkerboard;

public class HardAi extends AlphaBetaAI {

    public HardAi(int maxDepth) {
        super(maxDepth);
    }

    /**
     * Evaluates the given board state.
     * Negative values are better for black, positive for white
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    @Override
    public int evaluate(Checkerboard board) {
        int score = 0;
        score += pieceEvaluation(board.getBoard());
        score += centerControl(board.getBoard());
        score += promotionLineBlocked(board.getBoard());
        score += distanceToPromotion(board.getBoard());
        return score;
    }

    /**
     * Evaluates the center control of a given board state.
     * 1 for each piece in the large center
     * 2 for each piece in the small center
     * Negative values are better for black, positive for white
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    private int centerControl(int[][] board) {
        int score = 0;
        // large center
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                if (board[i][j] > 0)
                    score++;
                else if (board[i][j] < 0)
                    score--;
            }
        }

        // small center
        if (board[4][3] > 0)
            score++;
        else
            score--;

        if (board[3][4] > 0)
            score++;
        else
            score--;

        return score;
    }

    /**
     * Evaluates the distance of each pieces to the promotion line of a given board
     * state.
     * Distance from the first line of the player
     * Negative values are better for black, positive for white
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    private int distanceToPromotion(int[][] board) {
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != -2 && board[i][j] != 2 && board[i][j] != 0) {
                    if (board[i][j] > 0)
                        score += board.length - j - 1;
                    else
                        score += j;
                }
            }
        }
        return score;
    }

    /**
     * Evaluates the pieces of a given board state.
     * 4 if the opponent promotion line is blocked
     * Negative values are better for black, positive for white
     *
     * @param board the checkerboard to evaluate
     * @return The evaluation score for the given board state
     */
    private int promotionLineBlocked(int[][] board) {
        int score = 0;
        int nbPieceOnPromotionLine = 0;
        // white promotion line blocked with black piece
        for (int i = 1, j = 0; i < board.length; i += 2) {
            if (board[i][j] == -1) {
                nbPieceOnPromotionLine++;
            }
        }
        if (nbPieceOnPromotionLine == board.length / 2) {
            score -= 4;
        }

        nbPieceOnPromotionLine = 0;
        // black promotion line blocked with white piece
        for (int i = 0, j = 7; i < board.length; i += 2) {
            if (board[i][j] == 1) {
                nbPieceOnPromotionLine++;
            }
        }
        if (nbPieceOnPromotionLine == board.length / 2) {
            score += 4;
        }
        return score;
    }

}
