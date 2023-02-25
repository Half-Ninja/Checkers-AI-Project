package fr.uparis.checkers_ai;

/**
 * base checkers classes containing the base functions of the class
 */
public class Checkerboard {
    /**
     * inner representation of the board
     * -1 = white pawn
     * -2 = white queen
     * 1 = black pawn
     * 2 = black queen
     *
     */
    private int[][] board;


    //TODO implement functions

    /**
     * creates a new board in its base state.
     */
    public Checkerboard(){
        this.board = new int[8][8];

        //generate the board in its initial state
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++){
                if ((i + j) % 2 == 1)
                    this.board[i][j] = i > 2 ? (i > 4 ? 1 : 0) : -1;
            }
    }

    /**
     * creates a new board from an existing array.
     */
    public Checkerboard(int[][] board){
        if(board.length != 8 || board[0].length != 8 ) throw new IllegalArgumentException("arrays must be of size [8][8]");
        this.board = new int[8][8];

        //generate the board
        for (int i = 0; i < 8; i++)
            System.arraycopy(board[i], 0, this.board[i], 0, 8);
    }

    /**
     * checks if the game is finished
     * @return true if the game is finished (one side has no more pieces left)
     */
    public boolean isFinished() {
        return false;
    }

    /**
     * returns the winner of the game
     * @return 0 is the game is not finished, >0 if white won, <0 if black won
     */
    public int wonBy() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int[] line : board){
            for(int place : line)
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
        return res.toString();
    }

    public int[][] getBoard(){
        return board;
    }
}
