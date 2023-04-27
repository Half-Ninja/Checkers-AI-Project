package fr.uparis.checkers_ai;

import fr.uparis.checkers_ai.ai.AI;
import fr.uparis.checkers_ai.ai.AlphaBetaAI;
import fr.uparis.checkers_ai.ai.HardAi;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Console front-end for checkers
 */
public class ConsoleUI {

    private ConsoleUI(){} // static class

    //define the difficulty levels here!
    public static final AI easyAI = new AlphaBetaAI(2);
    public static final AI mediumAI = new HardAi(2);
    public static final AI hardAI = new HardAi(4);

    // scanner for inputs
    public static final Scanner in = new Scanner(System.in);

    public static String titleCard = "   _____ _               _                 _ \n  / ____| |             | |               | |\n | |    | |__   ___  ___| | _____ _ __ ___| |\n | |    | '_ \\ / _ \\/ __| |/ / _ \\ '__/ __| |\n | |____| | | |  __/ (__|   <  __/ |  \\__ \\_|\n  \\_____|_| |_|\\___|\\___|_|\\_\\___|_|  |___(_)";

    /**
     * sanitize an int input and records an input error as Integer.MIN_VALUE
     * @return the inputed int, or Integer.MIN_VALUE if an invalid value was inputed
     */
    private static int intInput(){
        try{
            int res = in.nextInt();
            in.nextLine();
            return res;
        } catch (InputMismatchException e){
            in.nextLine();
            return Integer.MIN_VALUE;
        }
    }

    public static void main(String[] args){
        boolean keepPlaying = true;
        while (keepPlaying) { // until the player chooses to exit
            System.out.println(titleCard); // cool logo
            System.out.println("select mode : \n 1 - Player Vs Player\n 2 - Player Vs Computer\n 3 - Exit");  //menu
            switch(intInput()) {
                case 1 ->  // PvP game
                    play(new CheckersPvP()); //go straight into the loop

                case 2 ->  // PvCPU game
                    AImenu(); // VsAI submenu

                case 3 -> { //exit
                    System.out.println("exiting");
                    keepPlaying = false;
                }
                default ->
                    System.out.println("input not recognised");

            }
            if (keepPlaying) { // if no choice to exit was made
                System.out.print("exit program (Y/n):");
                String input = in.next().toLowerCase().trim();

                while (!input.equals("y") && !input.equals("n")){ //until you have a valid option
                    System.out.print("exit program (Y/n):");
                    input = in.next().toLowerCase().trim();
                }
                keepPlaying = input.equals("n");
            }
        }
    }

    /**
     * submenu for when the user wants to play vs an AI
     */
    private static void AImenu() {
        System.out.println("AI");
    }

    /**
     * main game loop
     * @param game the game to play
     */
    private static void play(Checkers game) {
        while (!game.isFinished()){
            System.out.println(game);
            System.out.println((game.currentPlayer()?"white":"black") + "'s turn");
            int[][] move = null;
            do {
                if(move != null) System.out.println("Invalid move, try again");
                move = getMoveInput();
                while(move == null) {
                    System.out.println("Error, please input your moves under the format [A1 B2] or [A1 C3 A6] (without brackets)");
                    move = getMoveInput();
                }
            } while(!game.play(move));
        }
        System.out.println(game.wonBy()>0?"White won!":"Black won!");
    }

    /**
     * calculates the move from an input
     * @return the move inputed by the user
     */
    private static int[][] getMoveInput() {
        String input = in.nextLine();
        if (!input.toLowerCase().matches("(?:[abcdefg][12345678][ -])+[abcdefg][12345678]"))
            return null;
        int[][] res = new int[(input.length()+1)/3][2];
        Scanner sc = new Scanner(input);

        int i = 0;
        while (sc.hasNext()){
            String place = sc.next();
            switch(place.charAt(1)){
                case '1'-> res[i][0] = 7;
                case '2'-> res[i][0] = 6;
                case '3'-> res[i][0] = 5;
                case '4'-> res[i][0] = 4;
                case '5'-> res[i][0] = 3;
                case '6'-> res[i][0] = 2;
                case '7'-> res[i][0] = 1;
                case '8'-> res[i][0] = 0;
            }
            switch(Character.toLowerCase(place.charAt(0))){
                case 'a'-> res[i][1] = 0;
                case 'b'-> res[i][1] = 1;
                case 'c'-> res[i][1] = 2;
                case 'd'-> res[i][1] = 3;
                case 'e'-> res[i][1] = 4;
                case 'f'-> res[i][1] = 5;
                case 'g'-> res[i][1] = 6;
                case 'h'-> res[i][1] = 7;
            }
            i++;
        }
        return res;
    }

}
