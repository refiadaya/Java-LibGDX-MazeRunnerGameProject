# Maze Runner Game

## Project Structure

The project is organized as follows:

- `.gradle`: Gradle build files and settings.
- `.idea`: IntelliJ IDEA project files.
- `assets`: Game assets such as images and sounds.
- `core`: Core game logic.
- `desktop`: Desktop-specific code.
- `gradle`: Additional Gradle files.
- `maps`: Maps for the game.

### Code Structure

The main codebase is organized within the `de.tum.cit.ase.maze` package.

### Class Hierarchy

- `MazeRunnerGame`: Main class extending `Game` from LibGDX, managing screens and global resources.
- `MazeObject`: Abstract class representing objects in the maze, with subclasses: `Wall`, `Entry`, `Exit`, `Trap`, `Enemy`, `Key`.
    - `Wall`: Represents an impassable wall in the maze.
    - `Entry`: Represents the entry point of the maze.
    - `Exit`: Represents the exit point of the maze.
    - `Trap`: Represents a trap in the maze.
    - `Enemy`: Represents an enemy in the maze.
    - `Key`: Represents a key object in the maze.
- `Character`: Class representing the player's character in the maze.
- `HUD`: Class handling the Heads-Up Display, providing information about character's lives and key collection.
- `GameScreen`: Class representing the game screen where the main gameplay occurs, implementing LibGDX `Screen` interface.
- `MenuScreen`, `LoadMapScreen`, `PauseScreen`: Screens for menu, level loading, and pause functionality, implementing LibGDX `Screen` interface.
- `ReadPropertiesFile`: Utility class for parsing maze configuration from properties files.

## How to Run and Use the Game

### Prerequisites
- Ensure you have Java Development Kit (JDK) installed on your system.
- Git should be installed to clone the repository.

### Steps to Run the Game

1. Clone the repository:

    ```bash
    git clone https://go72lux@bitbucket.ase.in.tum.de/scm/FOPHN2324INFUN2324PROJECTWORKX/fophn2324infun2324projectworkx-ctrlaltdefeatexams.git
    ```

2. Open the project in IntelliJ IDEA or your preferred IDE.

3. Set up the run configuration for the `DesktopLauncher` class:
  - In IntelliJ IDEA, right-click on `DesktopLauncher` in the `desktop` package.
  - Select "Run DesktopLauncher" to launch the game.

4. The game will open in a new window with the main menu screen. Click "Go To Game" to navigate to the Load Map Screen.

5. In the Load Map Screen, choose a specific level by clicking the corresponding button or upload your own properties file:
  - Map 1: Ravenwood Manor
  - Map 2: Phantom Hallows House
  - Map 3: Eerievale Mansion
  - Map 4: Ghostly Gables Mansion
  - Map 5: Wraithstone Manor
  - Upload a Custom Map

6. After selecting a level, the game screen will open, and you can start playing: 
  - Use the arrow keys to navigate through the maze, collect the key, and reach the exit.

7. Pause the game by pressing the Esc key. In the pause screen, you can choose to resume the game or return to the menu.

### Game Mechanics

- **Levels**:
  - The game has multiple maps represented by different levels.
  - Select a level from the Load Map Screen to start playing.

- **Objective**: 
  - Navigate through the maze, collect the key, and reach the exit to win.

- **Character Controls**:
  - Use the arrow keys to move the character through the maze.
  - Collect the key to unlock the exit.
  - Avoid enemies and traps to stay alive.

- **HUD (Heads-Up Display)**:
  - The HUD shows the number of lives, and whether the key is collected.
  - Lives decrease upon interaction with traps or enemies.

- **Entry and Exit**:
  - The entry represents the starting point of each level.
  - The exit is the goal of the game. Reach it to win the game.

- **Key**:
  - Collect the key in each level to unlock the exit door.

- **Walls**:
  - Walls form the maze structure and create barriers for the character.

- **Enemies**:
  - Enemies move in patterns and can decrease the character's lives upon contact.

- **Traps**:
  - Traps are stationary hazards that can decrease the character's lives upon contact.

- **Points and Scoring**:
  - The maximum achievable point in Maze Runner Game is set at 1000.
  - Finish the game in less than or equal to 30 seconds.
  - For each second beyond this limit, you will lose 10 points.
  - The minimum attainable point is set at 100.
  - If the game is not successfully completed, you will receive 0 point.

Enjoy playing Maze Runner Game!

