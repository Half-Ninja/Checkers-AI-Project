package fr.uparis.checkers_ai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CheckerboardTest {

    @Test
    void checkersInit(){
        //board right after initialisation
        int[][] test_board = {{0,-1,0,-1,0,-1,0,-1}, {-1,0,-1,0,-1,0,-1,0}, {0,-1,0,-1,0,-1,0,-1}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {1,0,1,0,1,0,1,0}, {0,1,0,1,0,1,0,1}, {1,0,1,0,1,0,1,0}};
        int[][] board_calculated = new Checkerboard().getBoard();

        Assertions.assertAll(() -> Assertions.assertArrayEquals(test_board[0], board_calculated[0]),
                () -> Assertions.assertArrayEquals(test_board[1], board_calculated[1]),
                () -> Assertions.assertArrayEquals(test_board[2], board_calculated[2]),
                () -> Assertions.assertArrayEquals(test_board[3], board_calculated[3]),
                () -> Assertions.assertArrayEquals(test_board[4], board_calculated[4]),
                () -> Assertions.assertArrayEquals(test_board[5], board_calculated[5]),
                () -> Assertions.assertArrayEquals(test_board[6], board_calculated[6]),
                () -> Assertions.assertArrayEquals(test_board[7], board_calculated[7])
        );
    }

    @Test
    void checkersFromBoard(){
        int[][] test_board = {{0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,-1,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}};
        int[][] board_calculated = new Checkerboard(test_board).getBoard();

        Assertions.assertAll(() -> Assertions.assertArrayEquals(test_board[0], board_calculated[0]),
                () -> Assertions.assertArrayEquals(test_board[1], board_calculated[1]),
                () -> Assertions.assertArrayEquals(test_board[2], board_calculated[2]),
                () -> Assertions.assertArrayEquals(test_board[3], board_calculated[3]),
                () -> Assertions.assertArrayEquals(test_board[4], board_calculated[4]),
                () -> Assertions.assertArrayEquals(test_board[5], board_calculated[5]),
                () -> Assertions.assertArrayEquals(test_board[6], board_calculated[6]),
                () -> Assertions.assertArrayEquals(test_board[7], board_calculated[7]));
    }

    @Test
    void isFinished() {
        int[][] board_array = {{0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}};
        Checkerboard board_finished = new Checkerboard(board_array);
        Checkerboard board_unfinished = new Checkerboard();

        Assertions.assertTrue(board_finished.isFinished());
        Assertions.assertFalse(board_unfinished.isFinished());
    }

    @Test
    void wonBy() {
        int[][] board_array = {{0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}};
        Checkerboard board_finished_white = new Checkerboard(board_array);
        board_array = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, -1, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
        Checkerboard board_finished_black = new Checkerboard(board_array);
        Checkerboard board_unfinished = new Checkerboard();

        Assertions.assertEquals(board_finished_white.wonBy(), 1);
        Assertions.assertEquals(board_finished_black.wonBy(), -1);
        Assertions.assertEquals(board_unfinished.wonBy(), 0);
    }

    @Test
    void canMoveAssumingPiece() {
        int[][] board_array = {{0,0,0,0,0,0,0,0}, {0,0,0,-1,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,-1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0}};
//      0+ + + +
//      1 + b + +
//      2+ + + +
//      3 + b + +
//      4+ + w +
//      5 + + + +
//      6+ + + +
//      7 + + + +
//       01234567
        Checkerboard board = new Checkerboard(board_array);
        Assertions.assertTrue(board.canMoveAssumingPiece(4,4,3,5,1)); //w to up-right (doable)
        Assertions.assertFalse(board.canMoveAssumingPiece(4,4,3,3,1)); //w to up-left (failed)
        Assertions.assertFalse(board.canMoveAssumingPiece(4,4,3,5,0)); //nothing to somewhere (failed)
        Assertions.assertFalse(board.canMoveAssumingPiece(4,4,2,1,1)); // non-diagonal (failed)
        Assertions.assertTrue(board.canMoveAssumingPiece(4,4,2,2,1)); // w capture up-left (doable)
        Assertions.assertFalse(board.canMoveAssumingPiece(4,4,2,6,1)); // w to up-right twice (failed)
        Assertions.assertTrue(board.canMoveAssumingPiece(4,4,2,6,2)); // w to up-right twice as queen (doable)
        Assertions.assertFalse(board.canMoveAssumingPiece(4,4,5,5,1)); // w down right (failed)
        Assertions.assertTrue(board.canMoveAssumingPiece(4,4,5,5,2)); // w down right as queen (doable)
        Assertions.assertTrue(board.canMoveAssumingPiece(5,1,2,4,2)); // imaginary 51 queen to 24 (doable)
        ArrayList<Integer> a = new ArrayList<>();
        a.add(216);
        Assertions.assertTrue(board.canMoveAssumingPiece(4,4,3,3,1, a)); //w to up-left w/ ignore 3 3 (doable)
    }

    @Test
    void canCaptureAssumingPiece() {
        //TODO JUNIT tests
    }

    @Test
    void canMove() {
        //TODO JUNIT tests
    }
}
