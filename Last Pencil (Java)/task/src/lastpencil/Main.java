package lastpencil;

import com.sun.source.tree.WhileLoopTree;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int amountPencils = getPencilAmount(scanner);
        String[] player = getPlayers(scanner, random);
        playGame(scanner, amountPencils, player);
        scanner.close();
    }

    private static int getPencilAmount(Scanner scanner) {
        int amountPencils = 0;
        boolean validInput = false;
        System.out.println("How many pencils would you like to use:");
        while (!validInput) {
            String amountPencilsString = scanner.nextLine();
            if (amountPencilsString.contains("-")) {
                System.out.println("The number of pencils should be numeric");
                continue;
            }

            try {
                amountPencils = Integer.parseInt(amountPencilsString);
                if (amountPencils > 0) validInput = true;
                else System.out.println("The number of pencils should be positive");
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }
        return amountPencils;
    }

    private static String[] getPlayers(Scanner scanner, Random random) {
        String john = "John";
        String jack = "Jack";
        System.out.println("Who will be the firstPlayer (John, Jack):");
        String firstPlayer = scanner.nextLine();
        while (!(firstPlayer.equals(john)) && !(firstPlayer.equals(jack))) {
            System.out.println("Choose between 'John' and 'Jack'");
            firstPlayer = scanner.nextLine();
        }
        return new String[]{firstPlayer, firstPlayer.equals(jack) ? john : jack};
    }

    private static void playGame(Scanner scanner, int amountPencils, String[] players) {
        int turn = 0;
        String errorNumber = "Possible values: '1', '2' or '3'";
        while (amountPencils > 0) {
            boolean validInput = false;
            int takePencils = 0;
            for (int i = 0; amountPencils > i; i++) {
                System.out.print("|");
            }
            System.out.println();
            System.out.println(players[turn] + "'s turn!");
            String takePencilsString = players[turn].equals("John") ? scanner.nextLine() : getBotMove(amountPencils);
            while (!validInput) {
                try {
                    takePencils = Integer.parseInt(takePencilsString);
                    if (players[turn].equals("Jack")) System.out.println(takePencils);
                    if (takePencils > 3 || takePencils <= 0) {
                        System.out.println(errorNumber);
                        takePencilsString = scanner.nextLine();
                        continue;
                    }
                    if (takePencils > amountPencils) {
                        System.out.println("Too many pencils were taken");
                        takePencilsString = scanner.nextLine();
                        continue;
                    }
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println(errorNumber);
                    takePencilsString = scanner.nextLine();
                }
            }
            amountPencils -= takePencils;
            turn = (turn == 0) ? 1 : 0;
            if (amountPencils == 0) {
                System.out.println(players[turn] + " won!");
            }
        }
    }

    private static String getBotMove(int amountPencils) {
        Random random = new Random();
        String takenPencils = "";
        if (amountPencils == 1) {
            takenPencils = "1";
        } else if ((amountPencils - 1) % 4 == 0) {
            takenPencils = String.valueOf(1 + random.nextInt(3));
        } else if (amountPencils % 4 == 0) {
            takenPencils = "3";
        } else if ((amountPencils - 3) % 4 == 0) {
            takenPencils = "2";
        } else if ((amountPencils - 2) % 4 == 0) {
            takenPencils = "1";
        }
        return takenPencils;
    }

}
