import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RedBlueNim {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Check if minimum required arguments are provided
        if (args.length < 2) {
            System.out.println("Usage: java RedBlueNim <num-red> <num-blue> [<version>] [<first-player>] [<depth>]");
            return;
        }

        int numRed = Integer.parseInt(args[0]);
        int numBlue = Integer.parseInt(args[1]);

        // Set default values if optional arguments are not provided
        String version = args.length > 2 ? args[2] : "standard";
        String firstPlayer = args.length > 3 ? args[3] : "computer";
        int depth = args.length > 4 ? Integer.parseInt(args[4]) : 5;

        // Validate version argument
        if (!version.equals("standard") && !version.equals("misere")) {
            System.out.println("Invalid version. Please use 'standard' or 'misere'.");
            return;
        }

        // Validate first player argument
        if (!firstPlayer.equals("computer") && !firstPlayer.equals("human")) {
            System.out.println("Invalid first player. Please use 'computer' or 'human'.");
            return;
        }

        // Start the game with the parsed or default parameters
        playGame(numRed, numBlue, version, firstPlayer, depth);
    }

    private static void playGame(int numRed, int numBlue, String version, String firstPlayer, int depth) {
        int[] piles = {numRed, numBlue};
        String currentPlayer = firstPlayer;

        // Main game loop
        while (!gameOver(piles)) {
            System.out.println("Current piles: Red - " + piles[0] + ", Blue - " + piles[1]);
            
            // Check if it's the computer's turn
            if ("computer".equals(currentPlayer)) {
                int[] move = chooseBestMove(piles, depth, version);
                applyMove(piles, move);
                System.out.println("Computer removed " + move[1] + " from " + (move[0] == 0 ? "red" : "blue") + " pile.");
            } else {
                // Human's turn
                getHumanMove(piles);
            }
            
            // Switch turns
            currentPlayer = "human".equals(currentPlayer) ? "computer" : "human";
        }

        // Calculate the final score after the game ends
        int score = calculateScore(piles, version);

        // Determine and display the winner based on who made the last move and game version
        if ("standard".equals(version)) {
            if ("computer".equals(currentPlayer)) {
                System.out.println("Game over! Computer loses with a final score of " + score + " points. Human wins!");
            } else {
                System.out.println("Game over! Human loses with a final score of " + score + " points. Computer wins!");
            }
        } else { // Misère version
            if ("computer".equals(currentPlayer)) {
                System.out.println("Game over! Computer wins with a final score of " + score + " points. Human loses!");
            } else {
                System.out.println("Game over! Human wins with a final score of " + score + " points. Computer loses!");
            }
        }
    }

    private static int[] chooseBestMove(int[] piles, int depth, String version) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        for (int[] move : generatePossibleMoves(piles)) {
            int[] futurePiles = piles.clone();
            applyMove(futurePiles, move);
            int score = minimaxWithAlphaBetaPruning(futurePiles, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, version);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static int minimaxWithAlphaBetaPruning(int[] piles, int depth, int alpha, int beta, boolean maximizingPlayer, String version) {
        if (depth == 0 || gameOver(piles)) {
            return evaluateGameState(piles, version);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] childPiles : generatePossibleMoves(piles)) {
                applyMove(piles, childPiles);
                int eval = minimaxWithAlphaBetaPruning(piles, depth - 1, alpha, beta, false, version);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break; // Alpha cut-off
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] childPiles : generatePossibleMoves(piles)) {
                applyMove(piles, childPiles);
                int eval = minimaxWithAlphaBetaPruning(piles, depth - 1, alpha, beta, true, version);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break; // Beta cut-off
            }
            return minEval;
        }
    }

    private static List<int[]> generatePossibleMoves(int[] piles) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // For each pile
            for (int j = 1; j <= 2; j++) { // Remove 1 or 2 marbles
                if (piles[i] >= j) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    private static boolean gameOver(int[] piles) {
        return piles[0] == 0 || piles[1] == 0;
    }

    private static int calculateScore(int[] piles, String version) {
        int score = 2 * piles[0] + 3 * piles[1];
        return "misere".equals(version) ? -score : score;
    }

    private static int evaluateGameState(int[] piles, String gameVersion) {
        // Simplistic evaluation: More marbles left is better
        return 2 * piles[0] + 3 * piles[1];
    }

    private static void applyMove(int[] piles, int[] move) {
        piles[move[0]] -= move[1];
    }

    private static void getHumanMove(int[] piles) {
        System.out.print("Choose a pile (0 for red, 1 for blue): ");
        int pile = scanner.nextInt();
        System.out.print("Choose the number of marbles to remove (1 or 2): ");
        int amount = scanner.nextInt();
        applyMove(piles, new int[]{pile, amount});
    }
}