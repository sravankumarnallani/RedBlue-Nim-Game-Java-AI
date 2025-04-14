Red-Blue Nim Game
=================

Name: Sravan Kumar Nallani
UTA ID:1002147954

Programming Language: Java
Version: Java SE 17

Description
-----------
The Red-Blue Nim game is a two-player game involving two piles of marbles: red and blue. Players take turns removing 1 or 2 marbles from either pile. The game can be played in two versions: standard and misère. In the standard version, the player who cannot make a move (because one of the piles is empty) loses, while in the misère version, this player wins. The score is calculated based on the marbles left (2 points per red marble, 3 points per blue marble), and in the misère version, the score is negative.

Code Structure:
---------------
- main: Parses command-line arguments and initializes the game.
- playGame: Manages the game flow, alternating turns between the human player and the AI.
- chooseBestMove: AI logic that selects the optimal move using the MinMax algorithm with Alpha-Beta pruning.
- minimaxWithAlphaBetaPruning: Recursive function implementing the MinMax algorithm with Alpha-Beta pruning.
- generatePossibleMoves: Generates all possible moves from the current game state.
- gameOver: Checks if the game has ended based on the marbles left.
- calculateScore: Calculates and returns the game score.
- evaluateGameState: Evaluates and scores the current game state for the AI.
- applyMove: Applies a move to the game state.
- getHumanMove: Handles input for the human player's moves.

How to Compile
--------------
Ensure Java JDK 17 or higher is installed on your system. Navigate to the directory containing RedBlueNim.java and compile the code using the following command:

go to the src folder directory: cd src,
    javac RedBlueNim.java

How to Run
----------
After compilation, run the program with the java command followed by the class name and command line arguments specifying the game's initial state and rules. The arguments are as follows:

1. <num-red>: Initial number of red marbles (required).
2. <num-blue>: Initial number of blue marbles (required).
3. <version>: Game version, either "standard" or "misere". Defaults to "standard" if not specified.
4. <first-player>: Who starts the game, either "computer" or "human". Defaults to "computer" if not specified.
5. <depth>: (Optional) Depth limit for the depth-limited MinMax search with alpha-beta pruning. Not used in the basic implementation.

Example command to run the game:

    java RedBlueNim 5 5 standard computer

Game Logic Overview
-------------------
- The game starts with the specified number of red and blue marbles.
- Players alternate turns, each choosing a pile and removing 1 or 2 marbles.
- The game checks for the end condition (one of the piles is empty) after each move.
- The score is calculated, and the game outcome is announced at the end.
- The minimaxWithAlphaBetaPruning and evaluateGameState methods are placeholders for implementing AI logic using the MinMax algorithm with Alpha-Beta pruning. These methods would enable the computer to make strategic decisions based on the game state, though they require further implementation.
