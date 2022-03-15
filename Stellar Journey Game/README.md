# Stellar Journey
Implement the Stellar Journey Game, where the player controls the **USS Endeavour** spaceship and has the goal of eliminating all Blingon ships from a sector of the galaxy. The game is played in a number of turns, as described by the following rules:
1. When the game is started, display the sector of the galaxy according to the following rules:
 a. The game takes place on an **8x8** grid, having marked rows(**1-8**) and columns (**A-H**).
 b. Exactly **ten** stars are randomly placed in the sector, so that no 2 stars overlap, and no 2 stars are adjacent to each other on row, column or diagonal.
 c. The player's ship, the **USS Endeavour** starts in a random square of the grid that has no stars. The ship is represented as **E**.
 d. Three Blingon cruises are place randomly on empty squares of the grid. They must not overlap each other, the player's ship, or a star. The player can see only the ships directly adjacent to the **Endeavour**.
2. The game is played in a number of turns. Each turn, the player can give one of the following commands:
 a. __warp <coordinate**>**__ (e.g. warp G5). Moves the ship to the new coordinate. The new coordinate must be on the same rank, file, or diagonal as the starting position (e.g. from **A1** you can warp to **C3**), but not to **C4**. In case a star is in the way, the program display an error message and the ship is not moved. In case **Endeavour** would land on an enemy ship, the **Endeavour** is destroyed and the game is over. In case the command format or destination are invalid, an error message is displayed.
 b. __fire <coordinate**>**__. Destroy the Blingon ship at the given coordinates (e.g fire B4). The **fire** command only works for ships adjacent to the player's ship. If the wrong coordinates are provided, an error message is displayed.
 c. **cheat**. This displays the playing grid, with remaining Blingon ships revealed.
3. Every time a Blingon ship is destroyed, the remaining ones reposition randomly, making sure that constraints given at **1.d** are observed.
4. The player wins by destroying all enemy ships.

### Non-functional requirements:
  * Implements a layerd architecture solution.
  * The program must not crash, regardless of user input.
       
     
       
