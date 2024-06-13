package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {
    String filePath;
    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private HUD hud;
    private boolean isGameOver = false;
    private boolean isGameWon = false;
    private int level;
    /**
     * Variables for earning points according to the time
     */
    int completedIn = 0;
    private double gameStartTime;
    boolean timePrinted = false;
    int maxPoint = 1000;
    int minPoint = 100;
    int earnedPoints;

    /**
     * Variables for enemy movement
     */
    double directionTimer = 0;
    Random random = new Random();

    /**
     * Variables for sound effects
     */
    private Sound lifeLostSound, keyCollectedSound, victorySound, gameOverSound;
    private boolean soundPlayed = false;

    /**
     * Maze Objects
     */
    private ArrayList<MazeObject> mazeLayout;
    private Array<Enemy> enemyArray;
    private Array<Trap> trapArray;
    private Array<Wall> wallArray;
    private Array<Exit> exitArray;
    private Entry entry;
    private Key key;


    /**
     * Variables for character
     */
    float Speed = 200.0f;
    float playerX;
    float playerY;
    int playerWidth = 30;
    int playerHeight = 44;
    float prevX;
    float prevY;
    Texture player,playerU, playerL, playerR, playerB;
    Rectangle rectanglePlayer;
    private final Character character;

    float cameraZoom = 0.75f;

    /**
     * Attributes for resuming game
     */
    private final HashMap<String, Object> statusGame = new HashMap<>();
    private HashMap<String, Object> returnGameStatus = new HashMap<>();


    /**
     * Constructor for GameScreen. Initializes the game elements and sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     * @param selectedLevel  The selected level for the game. Should be an integer between 1 and 5.
     */
    public GameScreen(MazeRunnerGame game, int selectedLevel) {
        this.game = game;

        if (selectedLevel == 1){
            this.level= 1;
        }
        if (selectedLevel == 2){
            this.level = 2;
        }
        if (selectedLevel == 3){
            this.level = 3;
        }
        if (selectedLevel == 4){
            this.level = 4;
        }
        if (selectedLevel == 5){
            this.level = 5;
        }
        if (selectedLevel == 6) {
            this.level = 6;
            if (game.isResumeGame()) {
                returnGameStatus = game.getReturnGameStatus();
                if (returnGameStatus != null && returnGameStatus.containsKey("filePath")) {
                    setFilePath((String) returnGameStatus.get("filePath"));
                } else {
                    setFilePath("maps/level-1.properties");
                }
            } else {
                setFilePath(game.chooseFile());
            }
        }

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = cameraZoom;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        //The character controlled by the player within the game.
        character = new Character();

        //The Heads-Up Display (HUD) displaying essential information during gameplay.
        hud = new HUD(game.getSpriteBatch(), character);

        // Call the create method to initialize additional game elements
        create();
    }

    /**
     * Initializes various game elements during the setup phase, including parsing maze layout,
     * loading sounds, and setting up necessary resources.
     */
    public void create(){
        // Initialize a ReadPropertiesFile instance to read maze configuration from properties file.
        ReadPropertiesFile readPropertiesFile = new ReadPropertiesFile();

        // Parse maze layout based on the selected level.
        mazeLayout = readPropertiesFile.parseMaze(level, getFilePath());

        // Initialize sound effects for different game events.
        lifeLostSound = Gdx.audio.newSound(Gdx.files.internal("lifeLost.wav"));
        keyCollectedSound = Gdx.audio.newSound(Gdx.files.internal("keyCollect.mp3"));
        victorySound = Gdx.audio.newSound(Gdx.files.internal("victory.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("GameOver.wav"));
    }


    /**
     * Renders the gameplay screen, updating and drawing game elements.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Update the camera
        camera.update();

        // Begin drawing with the sprite batch
        game.getSpriteBatch().begin();

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        // Check for escape key press to go back to the menu
        if(!game.isEscape()){
            if ((isGameWon || isGameOver) && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                game.goToMenu();
            }
            if ((!isGameWon && !isGameOver) && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                game.goToPauseScreen(updateStatusGame());
            }
        }

        //DRAW TRAPS
        if (trapArray != null){
            for (Trap trap : trapArray){
                game.getSpriteBatch().draw(trap.getTexture(), trap.getX(), trap.getY(), trap.getWidth(), trap.getHeight());
            }
        }

        //DRAW ENEMIES
        if (enemyArray != null){
            for (Enemy enemy : enemyArray){
                game.getSpriteBatch().draw(enemy.getTexture(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                //System.out.println("DRAWING ENEMY " + enemy.getX() + " " + enemy.getY());
            }
        }

        //DRAW WALLS
        if (wallArray != null){
            for (Wall wall : wallArray){
                game.getSpriteBatch().draw(wall.getTexture(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            }
        }

        //DRAW EXIT
        if (exitArray != null){
            for (Exit exit : exitArray){
                game.getSpriteBatch().draw(exit.getTexture(), exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight());
            }
        }

        //DRAW ENTRY
        game.getSpriteBatch().draw(entry.getTexture(), entry.getX(), entry.getY(), entry.getWidth(), entry.getHeight());


        //DRAW KEY if it is not collected
        if (!character.isKeyCollected()){
            game.getSpriteBatch().draw(key.getTexture(), key.getX(), key.getY(), key.getWidth(), key.getHeight());
        }

        //DRAW CHARACTER based on its direction
        if (character.getDirection().equals("DOWN")){
            game.getSpriteBatch().draw(player, playerX, playerY, playerWidth, playerHeight);
        }
        if (character.getDirection().equals("UP")){
            game.getSpriteBatch().draw(playerU, playerX, playerY, playerWidth, playerHeight);
        }
        if (character.getDirection().equals("RIGHT")){
            game.getSpriteBatch().draw(playerR, playerX, playerY, playerWidth, playerHeight);
        }
        if (character.getDirection().equals("LEFT")){
            game.getSpriteBatch().draw(playerL, playerX, playerY, playerWidth, playerHeight);
        }
        if (character.getDirection().equals("BACK")){
            game.getSpriteBatch().draw(playerB, playerX, playerY, playerWidth, playerHeight);
        }

        //COLLISION DETECTION BETWEEN ENEMY AND CHARACTER
        if (enemyArray != null){
            for (Enemy enemy : enemyArray){
                if (enemy.getBounds().overlaps(rectanglePlayer)) {
                    // Increment the timer if overlapping
                    enemy.setOverlapTimer(enemy.getOverlapTimer() + delta);
                    playerY = prevY;
                    playerX = prevX;
                    enemy.setY(enemy.getPrevY());
                    enemy.setX(enemy.getPrevX());

                    // Check if the timer has reached the delay duration
                    if (enemy.getOverlapTimer() >= 0.2 && character.getNumOfLives() > 0) {
                        character.loseLife();
                        lifeLostSound.play();

                        // Reset the timer
                        enemy.setOverlapTimer(0);
                    }
                }
            }
        }

        //COLLISION DETECTION BETWEEN TRAP AND CHARACTER
        if (trapArray != null){
            for (Trap trap : trapArray){
                if (trap.getBounds().overlaps(rectanglePlayer)) {
                    // Increment the timer if overlapping
                    trap.setOverlapTimer(trap.getOverlapTimer() + delta);
                    playerY = prevY;
                    playerX = prevX;

                    // Check if the timer has reached the delay duration
                    if (trap.getOverlapTimer() >= 0.2 && character.getNumOfLives() > 0) {
                        character.loseLife();
                        lifeLostSound.play();

                        // Reset the timer
                        trap.setOverlapTimer(0);
                    }
                }
            }
        }

        //COLLISION DETECTION BETWEEN WALL AND CHARACTER
        if (wallArray != null) {
            for (Wall wall : wallArray) {
                if (wall.getBounds().overlaps(rectanglePlayer)) {
                    playerY = prevY;
                    playerX = prevX;
                }
            }
        }

        //COLLISION DETECTION BETWEEN WALL AND ENEMY
        if (wallArray != null && enemyArray != null) {
            for (Wall wall : wallArray) {
                for (Enemy enemy : enemyArray) {
                    if (wall.getBounds().overlaps(enemy.getBounds())) {
                        enemy.setY(enemy.getPrevY());
                        enemy.setX(enemy.getPrevX());
                    }
                }
            }
        }

        //COLLISION DETECTION BETWEEN ENTRY AND ENEMY
        if (enemyArray != null){
            for (Enemy enemy : enemyArray){
                if (enemy.getBounds().overlaps(entry.getBounds())){
                    enemy.setY(enemy.getPrevY());
                    enemy.setX(enemy.getPrevX());
                }
            }
        }

        //COLLISION DETECTION BETWEEN EXIT AND ENEMY
        if (exitArray != null && enemyArray != null) {
            for (Exit exit : exitArray) {
                for (Enemy enemy : enemyArray) {
                    if (exit.getBounds().overlaps(enemy.getBounds())) {
                        enemy.setY(enemy.getPrevY());
                        enemy.setX(enemy.getPrevX());
                    }
                }
            }
        }

        //COLLISION DETECTION BETWEEN EXIT AND CHARACTER IF KEY IS COLLECTED
        if (exitArray != null) {
            for (Exit exit : exitArray) {
                if (exit.getBounds().overlaps(rectanglePlayer) && character.isKeyCollected()){
                    character.setDirection("BACK");
                    exit.setOverlapTimer(exit.getOverlapTimer() + delta);
                    if (exit.getOverlapTimer() > 0.1){
                        playerX = exit.getX() + 15;  // Adjust this based on your needs
                        playerY = exit.getY() + 5;
                        if (!timePrinted){
                            completedIn= (int) Math.round( (TimeUtils.nanoTime() - gameStartTime) / 1000000000);
                            timePrinted = true;
                        }
                        isGameWon = true;
                        exit.setOverlapTimer(0);
                    }
                }
            }
        }

        //COLLISION DETECTION BETWEEN EXIT AND CHARACTER IF KEY IS NOT COLLECTED
        if (exitArray !=null){
            for (Exit exit : exitArray){
                if (exit.getBounds().overlaps(rectanglePlayer) && !character.isKeyCollected()){
                    playerY = prevY;
                    playerX = prevX;
                }
            }
        }

        //COLLISION DETECTION BETWEEN KEY AND CHARACTER
        if (key.getBounds().overlaps(rectanglePlayer)){
            if (!character.isKeyCollected()){
                keyCollectedSound.play();
            }
            character.setKeyCollected(true);
        }

        //COLLISION DETECTION BETWEEN ENTRY AND CHARACTER
        if (rectanglePlayer.getX() < entry.getBounds().x){
            playerY = prevY;
            playerX = prevX;
        }

        //ENEMY MOVEMENTS
        if (enemyArray != null){
            for (Enemy enemy : enemyArray){
                enemy.setChangeDirectionTimer(enemy.getChangeDirectionTimer() + delta);
                if (enemy.getChangeDirectionTimer() >= directionTimer){
                    enemy.setChosenDirection(enemy.getPossibleDirections()[random.nextInt(enemy.getPossibleDirections().length)]);
                    enemy.setChangeDirectionTimer(0);
                }
                enemy.updatePosition(enemy.getChosenDirection());
                directionTimer = random.nextDouble(1,5);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)){
            cameraZoom += 0.05F;
            camera.zoom = cameraZoom;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            cameraZoom -= 0.05F;
            camera.zoom = cameraZoom;
        }

        if (!character.isPaused()){
            handleArrowKeyInput();
        }

        if (character.getNumOfLives() <= 0) {
            isGameOver = true;
        }

        //UPDATE RECTANGLES
        rectanglePlayer = new Rectangle(playerX, playerY, playerWidth, playerHeight);

        if (enemyArray != null){
            for (Enemy enemy : enemyArray){
                enemy.setBounds(new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight()));
            }
        }
        key.setBounds(new Rectangle(key.getX(), key.getY(), key.getWidth(), key.getHeight()));

        //Set camera position
        camera.position.set(playerX, playerY, 0);

        //Shifts viewport when character moves
        if (playerX > camera.position.x){
            camera.translate(1, 0,0);
        }
        if (playerY > camera.position.y){
            camera.translate(0, 1,0);
        }
        if (playerX < camera.position.x){
            camera.translate(-1, 0,0);
        }
        if (playerY < camera.position.y){
            camera.translate(0, -1,0);
        }


        // End the drawing with the sprite batch
        game.getSpriteBatch().end();


        // Handle game over and victory conditions
        if (isGameOver){
            if (!soundPlayed){
                gameOverSound.play();
                soundPlayed= true;
            }
            displayGameOverMessage();
            stopGamePlay();
        }

        if (isGameWon){
            if (!soundPlayed){
                victorySound.play();
                soundPlayed= true;
            }
            displayWinMessage();
            stopGamePlay();
        }

        //DRAW HUD
        hud = new HUD(game.getSpriteBatch(), character);
        hud.stage.draw();
    }


    /**
     * Handles arrow key input to move the player character and updates its direction.
     * The method checks for pressed arrow keys (UP, DOWN, LEFT, RIGHT) and updates
     * the player's position and direction accordingly.
     */
    private void handleArrowKeyInput(){

        // Handle UP arrow key
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            prevY = playerY; // Store the previous Y position
            playerY += Gdx.graphics.getDeltaTime() * Speed; // Move the player up
            character.setDirection("UP"); // Set the character's direction
        }

        // Handle DOWN arrow key
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            prevY = playerY; // Store the previous Y position
            playerY -= Gdx.graphics.getDeltaTime() * Speed; // Move the player down
            character.setDirection("DOWN"); // Set the character's direction
        }

        // Handle LEFT arrow key
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            prevX = playerX; // Store the previous X position
            playerX -= Gdx.graphics.getDeltaTime() * Speed; // Move the player left
            character.setDirection("LEFT"); // Set the character's direction
        }

        // Handle RIGHT arrow key
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            prevX = playerX; // Store the previous X position
            playerX += Gdx.graphics.getDeltaTime() * Speed; // Move the player right
            character.setDirection("RIGHT"); // Set the character's direction
        }
    }


    /**
     * Displays the victory message on the screen with relevant information.
     * The method calculates earned points based on completion time and displays
     * the victory message along with the completed time, earned points, and an
     * instruction to go back to the menu.
     */
    private void displayWinMessage(){
        // Set up the SpriteBatch for rendering
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin();

        // Calculate earned points based on completion time
        if (completedIn <= 30){
            earnedPoints = maxPoint;
        } else if (completedIn >= 120) {
            earnedPoints = minPoint;
        } else {
            earnedPoints = maxPoint - (completedIn - 30)*10;
        }

        // Draw the "VICTORY" message
        font.draw(game.getSpriteBatch(), "VICTORY", camera.position.x - 200, camera.position.y);
        // Draw the completed time
        font.draw(game.getSpriteBatch(), "Finished in " + completedIn + " seconds!", camera.position.x - 200, camera.position.y - 30);
        // Draw the earned points
        font.draw(game.getSpriteBatch(), "Earned Points: " + earnedPoints, camera.position.x - 200, camera.position.y - 60);
        // Draw instruction to go back to the menu
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", camera.position.x - 200, camera.position.y - 90);

        game.getSpriteBatch().end();
    }

    /**
     * Displays the game over message on the screen with relevant information.
     * The method displays the "GAME OVER" message along with earned points
     * (which is always 0 in case of game over) and an instruction to go back to the menu.
     */
    private void displayGameOverMessage(){

        // Set up the SpriteBatch for rendering
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin();

        // Draw the "GAME OVER" message
        font.draw(game.getSpriteBatch(), "GAME OVER", camera.position.x - 200, camera.position.y);
        // Draw earned points (always 0 in case of game over)
        font.draw(game.getSpriteBatch(), "Earned Points: " + 0, camera.position.x - 200, camera.position.y - 60);
        // Draw instruction to go back to the menu
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", camera.position.x - 200, camera.position.y - 30);

        game.getSpriteBatch().end();
    }


    /**
     * Pauses the gameplay by setting the character and all enemies to a paused state.
     * This method is called when the game is either won or lost to freeze the gameplay.
     * The character and enemy movement is halted, and the game enters a paused state.
     */
    private void stopGamePlay(){
        // Pause the main character
        character.setPaused(true);

        // Pause all enemies (if any)
        if (enemyArray != null){
            for (Enemy enemy : enemyArray){
                enemy.setPaused(true);
            }
        }
    }

    /**
     * Stores the map level, last x and y coordinates, number of lives and if key is collected in a HashMap when player enters PauseScreen
     * @return the HashMap statusGame passed into the goToPauseScreen() method from MazeRunnerGame
     */
    public HashMap<String, Object> updateStatusGame(){

        statusGame.put("level", level);
        statusGame.put("playerX", playerX);
        statusGame.put("playerY", playerY);
        statusGame.put("isKeyCollected", character.isKeyCollected());
        statusGame.put("livesLeft", character.getNumOfLives());
        if (level == 6){
            statusGame.put("filePath", filePath);
        }
        return statusGame;
    }

    /**
     * Called when the window is resized. Adjusts the camera to maintain an orthographic projection.
     *
     * @param width  The new width of the window.
     * @param height The new height of the window.
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    /**
     * Called when the game is paused. This method does not have specific functionality in this implementation.
     * It is included as part of the Screen interface implementation.
     */
    @Override
    public void pause() {
    }

    /**
     * Called when the game resumes from a paused state. This method does not have specific functionality in this implementation.
     * It is included as part of the Screen interface implementation.
     */
    @Override
    public void resume() {
    }


    /**
     * Called when the screen becomes the current screen of the game. Initializes game elements, such as enemies,
     * traps, walls, exits, and the player character. Also, sets up textures and rectangles for rendering.
     */
    @Override
    public void show() {

        if(game.isResumeGame()){
            returnGameStatus = game.getReturnGameStatus();
            character.setKeyCollected((Boolean) returnGameStatus.get("isKeyCollected"));
            character.setNumOfLives((Integer) returnGameStatus.get("livesLeft"));
        }

        // Record the starting time of the game for calculating completion time
        gameStartTime = TimeUtils.nanoTime();


        // Initialize arrays for game objects
        enemyArray = new Array<>();
        trapArray = new Array<>();
        wallArray = new Array<>();
        exitArray = new Array<>();

        // Populate game object arrays based on the provided maze layout
        if (mazeLayout != null) {
            for (MazeObject mazeObject : mazeLayout) {
                if (mazeObject instanceof Enemy) {
                    // Record initial positions for enemies
                    ((Enemy) mazeObject).setPrevX(mazeObject.getX());
                    ((Enemy) mazeObject).setPrevY(mazeObject.getY());
                    enemyArray.add((Enemy) mazeObject);
                }
                if (mazeObject instanceof Exit) {
                    exitArray.add((Exit) mazeObject);
                }
                if (mazeObject instanceof Entry) {
                    entry = (Entry) mazeObject;
                    // Set initial position for the player character
                    if (!game.isResumeGame()){
                        playerX = entry.getX() + 15;
                        playerY = entry.getY() + 5;
                    } else {
                        returnGameStatus = game.getReturnGameStatus();
                        playerX = (Float) returnGameStatus.get("playerX");
                        playerY = (Float) returnGameStatus.get("playerY");
                        game.setResumeGame(false);
                    }
                }
                if (mazeObject instanceof Key) {
                    key = (Key) mazeObject;
                }
                if (mazeObject instanceof Trap) {
                    trapArray.add((Trap) mazeObject);
                }
                if (mazeObject instanceof Wall) {
                    wallArray.add((Wall) mazeObject);
                }
            }
        }

        // Load textures for different player character directions
        player = new Texture(Gdx.files.internal("characterF.png"));
        playerU = new Texture(Gdx.files.internal("characterU.png"));
        playerL = new Texture(Gdx.files.internal("characterL.png"));
        playerR = new Texture(Gdx.files.internal("characterR.png"));
        playerB = new Texture(Gdx.files.internal("characterB.png"));

        // Initialize the player character's rectangle for collision detection
        rectanglePlayer = new Rectangle(playerX, playerY, playerWidth, playerHeight);

        // Record the initial position of the player character
        prevX = playerX;
        prevY = playerY;

    }

    /**
     * Called when the screen is no longer the current screen of the game. This method is typically used
     * for cleanup and resource disposal when switching to another screen or exiting the application.
     */
    @Override
    public void hide() {
    }

    /**
     * Disposes of resources associated with the GameScreen when it is no longer needed.
     * This method is called when the game is closing or when switching to another screen.
     */
    @Override
    public void dispose() {

    }

    /**
     * Returns the current file path stored in this object.
     *
     * @return The current file path as a String.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path to the specified value.
     *
     * @param filePath The new file path to set as a String.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}