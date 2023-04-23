package fr.uparis.checkers_ai;

import java.util.ArrayList;

/**
 * base checkers classes containing the base functions of the class
 */
public class Checkerboard {
    /**
     * inner representation of the board
     * -1 = black pawn
     * -2 = black queen
     * 1 = white pawn
     * 2 = white queen
     * white begins on top, black on the bottom
     */
    private int[][] board;

    /**
     * creates a new board in its base state.
     */
    public Checkerboard() {
        this.board = new int[8][8];

        // generate the board in its initial state
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1)
                    this.board[i][j] = i > 2 ? (i > 4 ? 1 : 0) : -1;
            }
    }

    /**
     * creates a new board from an existing array.
     */
    public Checkerboard(int[][] board) {
        if (board.length != 8 || board[0].length != 8)
            throw new IllegalArgumentException("arrays must be of size [8][8]");
        this.board = new int[8][8];

        // generate the board
        for (int i = 0; i < 8; i++)
            System.arraycopy(board[i], 0, this.board[i], 0, 8);
    }

    /**
     * checks if the game is finished
     * 
     * @return true if the game is finished (one side has no more pieces left)
     */
    public boolean isFinished() {
        boolean foundWhite = false, foundBlack = false;
        for (int[] line : board)
            for (int square : line) {
                if (square == 1 && !foundWhite) {
                    if (foundBlack)
                        return false;
                    foundWhite = true;
                }
                if (square == -1 && !foundBlack) {
                    if (foundWhite)
                        return false;
                    foundBlack = true;
                }
            }
        return true;
    }

    /**
     * returns the winner of the game
     * 
     * @return 0 is the game is not finished, >0 if white won, <0 if black won
     */
    public int wonBy() {
        boolean foundWhite = false, foundBlack = false;
        for (int[] line : board)
            for (int square : line) {
                if (square == 1 && !foundWhite) {
                    if (foundBlack)
                        return 0;
                    foundWhite = true;
                }
                if (square == -1 && !foundBlack) {
                    if (foundWhite)
                        return 0;
                    foundBlack = true;
                }
            }
        return foundBlack ? -1 : 1;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            res.append(i);
            for (int place : board[i])
                switch (place) {
                    case 0 -> res.append(' ');
                    case 1 -> res.append('w');
                    case 2 -> res.append('W');
                    case -1 -> res.append('b');
                    case -2 -> res.append('B');
                    default -> res.append('#');
                }
            res.append('\n');
        }
        return res.append(" 01234567").toString();
    }

    /**
     * use prime products to create a unique value corresponding to each case
     * 
     * @param x - x coordinate
     * @param y - y coordinate
     * @return the value of the given coordinates
     */
    private int caseValue(int x, int y) {
        return (int) (Math.pow(2, x) * Math.pow(3, y));
    }

    /**
     * check if the following singular move is possible, assuming a certain piece is
     * in the start, accounts for captures
     * 
     * @param fromX      x coordinate of the start
     * @param fromY      y coordinate of the start
     * @param toX        x coordinate of the end
     * @param toY        y coordinate of the end
     * @param piece      the piece to assume is in position
     * @param ignoreList the list of places to count as empty
     * @return true if possible
     */
    public boolean canMoveAssumingPiece(int fromX, int fromY, int toX, int toY, int piece,
            ArrayList<Integer> ignoreList) {
        // if no piece or not a diagonal or the destination full return false
        if (piece == 0 || Math.abs(fromX - toX) != Math.abs(fromY - toY) ||
                (board[toX][toY] != 0 && !ignoreList.contains(caseValue(toX, toY))))
            return false;

        int yDirection = (fromY < toY ? 1 : -1);

        // if a pawn
        if (Math.abs(piece) == 1) {
            // check if it is a simple move
            // fromX-toX will equal -1 if the piece goes 1 line down (3-4 = -1) and 1 if it
            // goes up,
            // and since black pawns can only go down (-1) and white up (1) simply do the
            // check thusly
            if (fromX - toX == piece)
                return true;

            // check if it is a valid capture
            // fromX-toX == 2 * piece -- make sure the piece moves two ranks/columns
            // board[toX + piece][toY - yDirection] * piece > 0 -- since -n * -n = n^2 and
            // n*0 = 0,
            // will only return a negative if one of the piece is negative(black) and the
            // other positive (white)
            //
            // also checks if the piece is not ignored
            if (fromX - toX == 2 * piece && board[toX + piece][toY - yDirection] * piece < 0
                    && !ignoreList.contains(caseValue(toX + piece, toY - yDirection)))
                return true;
        }

        // if a queen
        if (Math.abs(piece) == 2) {
            // if moving only one space
            if (Math.abs(fromX - toX) == 1)
                return true;

            int xDirection = (fromX < toX ? 1 : -1);

            // checks if the path is empty
            int prevCaseValue = caseValue(toX - xDirection, toY - yDirection);
            for (int i = 0; i < toX - fromX; i++)
                for (int j = fromY + yDirection; j != toY - yDirection; j += yDirection) {
                    if (board[i * xDirection][j * yDirection] != 0 // checks if the current piece is empty
                            && !ignoreList.contains(prevCaseValue)) // OR ignored
                        return false;
                }

            // checks is the case before the end is empty (path free) OR enemy (capture)
            // board[toX - piece][toY - yDirection] * piece > 0 -- since -n * n = -n^2 and
            // n*0 = 0,
            // will only return a positive if both pieces is negative(black) and the other
            // positive (white)
            // therefore by reversing the result, we will get true if board[toX - piece][toY
            // - yDirection]
            // is either opposite to piece OR 0
            return !(board[toX - xDirection][toY - yDirection] * piece > 0)
                    || ignoreList.contains(prevCaseValue);
        }
        return false;
    }

    /**
     * check if the following singular move is possible, assuming a certain piece is
     * in the start, accounts for captures
     * 
     * @param fromX x coordinate of the start
     * @param fromY y coordinate of the start
     * @param toX   x coordinate of the end
     * @param toY   y coordinate of the end
     * @param piece the piece to assume is in position
     * @return true if possible
     */
    public boolean canMoveAssumingPiece(int fromX, int fromY, int toX, int toY, int piece) {
        return canMoveAssumingPiece(fromX, fromY, toX, toY, piece, new ArrayList<>());
    }

    /**
     * check if the following singular move is a possible capture assuming a certain
     * piece is in the start
     * 
     * @param fromX      x coordinate of the start
     * @param fromY      y coordinate of the start
     * @param toX        x coordinate of the end
     * @param toY        y coordinate of the end
     * @param piece      the piece to assume is in position
     * @param ignoreList the list of places to count as empty
     * @return true if possible
     */
    public boolean canCaptureAssumingPiece(int fromX, int fromY, int toX, int toY, int piece,
            ArrayList<Integer> ignoreList) {
        if (!canMoveAssumingPiece(fromX, fromY, toX, toY, piece, ignoreList))
            return false; // can't capture if can't move
        int color = piece / Math.abs(piece);
        int xDirection = (fromX < toX ? 1 : -1);
        int yDirection = (fromY < toY ? 1 : -1);

        // check if the case right before the end is occupied by an enemy
        // board[toX - (fromX<toX?-1:1)][toY - (fromY<toY?-1:1)] * piece > 0 -- since -n
        // * -n = n^2 and n*0 = 0,
        // will only return a negative if one of the piece is negative(black) and the
        // other positive (white)
        return board[toX - xDirection][toY - yDirection] * color < 0
                && !ignoreList.contains(caseValue(toX - xDirection, toY - yDirection));
    }

    /**
     * check if the following singular move is a possible capture assuming a certain
     * piece is in the start
     * 
     * @param fromX x coordinate of the start
     * @param fromY y coordinate of the start
     * @param toX   x coordinate of the end
     * @param toY   y coordinate of the end
     * @param piece the piece to assume is in position
     * @return true if possible
     */
    public boolean canCaptureAssumingPiece(int fromX, int fromY, int toX, int toY, int piece) {
        return canCaptureAssumingPiece(fromX, fromY, toX, toY, piece, new ArrayList<>());
    }

    /**
     * check if the piece can do the following move or chain of captures
     * 
     * @param moves move[0] = (x,y) corresponding to the piece's current placement,
     *              move[1] and onwards corresponding to the following move (or
     *              moves in case of consecutive captures)
     * @return true if the chain is possible
     */
    public boolean canMove(int[][] moves) {
        if (moves.length < 2 || moves[0].length != 2)
            throw new IllegalArgumentException("moves must be an array of length > 2 of arrays of length 2");
        int piece = this.board[moves[0][0]][moves[0][1]];

        // if only doing one move, check if it is legal
        if (moves.length == 2)
            return canMoveAssumingPiece(moves[0][0], moves[0][1], moves[1][0], moves[1][1], piece);

        // if doing a chain of captures
        // due to the way java equals arrays (it doesn't) we'll be storing a sum of
        // prime factors under the form (2^x + 3^y)
        ArrayList<Integer> ignoreList = new ArrayList<>();
        for (int i = 0; i < moves.length - 1; i++) {
            // check if you can CAPTURE, return false if can't
            if (!canCaptureAssumingPiece(moves[i][0], moves[i][1], moves[i + 1][0], moves[i + 1][1], piece, ignoreList))
                return false;
            // add capture to the ignorelist
            ignoreList.add(caseValue(moves[i + 1][0] - (moves[i][0] < moves[i + 1][0] ? 1 : -1),
                    moves[i + 1][1] - (moves[i][1] < moves[i + 1][1] ? 1 : -1)));
        }
        return true;
    }

    /**
     *
     * @param moves move[0] = (x,y) corresponding to the piece's current placement,
     *              move[1] and onwards corresponding to the following move (or
     *              moves in case of consecutive captures)
     * @return false if the move is invalid
     */
    public boolean move(int[][] moves) {
        if (!canMove(moves))
            return false;
        board[moves[moves.length - 1][0]][moves[moves.length - 1][1]] = board[moves[0][0]][moves[0][1]]; // set the
                                                                                                         // final case
                                                                                                         // at the value
                                                                                                         // of the first
                                                                                                         // one
        board[moves[0][0]][moves[0][1]] = 0; // set the initial case at 0 (empty)

        // for each move
        for (int i = 0; i < moves.length - 1; i++) {
            // sets the case prior to the finish at 0, capturing any piece there (A1 -> F6,
            // capture E5)
            board[moves[i + 1][0] - (moves[i][0] < moves[i + 1][0] ? 1 : -1)][moves[i + 1][1]
                    - (moves[i][1] < moves[i + 1][1] ? 1 : -1)] = 0;
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public int pieceAt(int x, int y) {
        return board[x][y];
    }

    // TODO return all the legals moves of the player
    public ArrayList<int[][]> getAllMoves(boolean player) {
        ArrayList<int[][]> possibleMoves = new ArrayList<int[][]>();
        return possibleMoves;
    }
}
