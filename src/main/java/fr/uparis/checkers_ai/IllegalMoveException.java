package fr.uparis.checkers_ai;

public class IllegalMoveException extends Exception{
    public IllegalMoveException(String errorMessage) {
        super(errorMessage);
    }
}
