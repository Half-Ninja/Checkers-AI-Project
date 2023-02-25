package fr.uparis.checkers_ai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}
