package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game {

    //To chose properties files
    private String chosenFilePath;
    NativeFileChooser fileChooser;

    // Screens
    private GameScreen gameScreen;
    private LoadMapScreen loadMapScreen;
    private PauseScreen pauseScreen;

    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;

    // UI Skin
    private Skin skin;

    //HUD
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;

    private Music menuBackgroundMusic, gameBackgroundMusic;

    private final HashMap<String, Object> returnGameStatus = new HashMap<>();
    private boolean resumeGame = false;
    private boolean escape = false;


    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin
        loadMapScreen = new LoadMapScreen(this);

        // Play some background music
        // Background sound
        menuBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("menuBackground.wav"));
        menuBackgroundMusic.setLooping(true);

        gameBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("gameBackground.ogg"));
        gameBackgroundMusic.setLooping(true);

        goToMenu(); // Navigate to the menu screen
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }

        // Switch to menu screen background music
        menuBackgroundMusic.play();
        if (gameBackgroundMusic.isPlaying()) {
            gameBackgroundMusic.stop();
        }
    }

    /**
     * Switches to the game screen.
     * @param selectedLevel The selected level to start the game.
     *
     */
    public void goToGame(int selectedLevel) {
        escape = false;
        this.setScreen(new GameScreen(this, selectedLevel)); // Set the current screen to GameScreen
        if (loadMapScreen != null) {
            loadMapScreen.dispose(); // Dispose the menu screen if it exists
            loadMapScreen = null;
        }
        if (pauseScreen != null) {
            pauseScreen.dispose(); // Dispose the menu screen if it exists
            pauseScreen = null;
        }

        // Switch to game screen background music
        gameBackgroundMusic.play();
        if (menuBackgroundMusic.isPlaying()) {
            menuBackgroundMusic.stop();
        }
    }

    /**
     * Switches to the map selection screen.
     */
    public void goToMapScreen(){
        this.setScreen(new LoadMapScreen(this));
        if (gameScreen != null) {
            setEscape(true);
            System.out.println(isEscape());
            gameScreen.dispose();
            gameScreen = null;
        }
        if (pauseScreen != null){
            System.out.println(isEscape());
            pauseScreen.dispose();
            pauseScreen = null;
        }

        menuBackgroundMusic.play();
        if (gameBackgroundMusic.isPlaying()) {
            gameBackgroundMusic.stop();
        }
    }

    /**
     * Switches to the pause screen, passing the current game status information.
     * Disposes of the game screen if it exists.
     *
     * @param statusGame A HashMap containing key-value pairs representing the current game status.
     *                   This information is used to resume the game if applicable.
     */
    public void goToPauseScreen(HashMap<String, Object> statusGame){
        escape = true;
        this.setScreen(new PauseScreen(this, statusGame));
        if (gameScreen != null){
            gameScreen.dispose();
            gameScreen = null;
        }
        Gdx.app.log("GameScreen", "Entering goToPauseScreen");

        menuBackgroundMusic.play();
        if (gameBackgroundMusic.isPlaying()) {
            gameBackgroundMusic.stop();
        }
    }

    /**
     * Resumes the game based on the provided game status information.
     * Updates the player's position, key collection status, and remaining lives.
     *
     * @param statusGame A HashMap containing key-value pairs representing the game status.
     *                   Expected keys: "level", "playerX", "playerY", "isKeyCollected", "livesLeft".
     */
    public void gameResumed(HashMap<String, Object> statusGame){
        gameScreen = new GameScreen(this, (int) statusGame.get("level"));
        int level = (Integer) statusGame.get("level");
        float playerX = (Float) statusGame.get("playerX");
        float playerY = (Float) statusGame.get("playerY");
        boolean isKeyCollected = (Boolean) statusGame.get("isKeyCollected");
        int livesLeft = (Integer) statusGame.get("livesLeft");
        returnGameStatus.put("playerX", playerX);
        returnGameStatus.put("playerY", playerY);
        returnGameStatus.put("level", level);
        returnGameStatus.put("isKeyCollected", isKeyCollected);
        returnGameStatus.put("livesLeft", livesLeft);
        if (level == 6){
            String filePath = (String) statusGame.get("filePath");
            returnGameStatus.put("filePath", filePath);
            System.out.println("PlayerX = " + playerX + ", PlayerY =  " + playerY + ", level =  " + level + ", keyCollected= " + isKeyCollected + ", livesLeft= " + livesLeft + ", Game Resumed = " + isResumeGame() + ", File Path = " + filePath);
        }
    }

    /**
     * Opens a file chooser dialog to allow the user to select a .properties file.
     * Only files with the ".properties" extension are shown in the dialog.
     *
     * @return The path of the chosen .properties file, or null if no file was chosen or an error occurred.
     */
    public String chooseFile() {
        // Configure
        NativeFileChooserConfiguration configuration = new NativeFileChooserConfiguration();

        // Starting from user's dir
        configuration.directory = Gdx.files.internal("maps");


        configuration.nameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        };

        // Add a nice title
        configuration.title = "Choose .properties file";

        fileChooser.chooseFile(configuration, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                // Do stuff with the chosen .properties file
                chosenFilePath = file.path();
                Gdx.app.log("File chosen", file.path());
            }

            @Override
            public void onCancellation() {
                // Warn user how rude it can be to cancel developer's effort
            }

            @Override
            public void onError(Exception exception) {
                // Handle error (hint: use exception type)
            }
        });
        return chosenFilePath;
    }


    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin

        menuBackgroundMusic.dispose();
        gameBackgroundMusic.dispose();
    }

    /**
     * Gets the UI Skin used in the game.
     *
     * @return The Skin used for UI elements.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Gets the SpriteBatch used for rendering graphics in the game.
     *
     * @return The SpriteBatch used for rendering graphics.
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /**
     * Gets the current game status to be returned, including relevant information.
     *
     * @return A HashMap containing key-value pairs representing the game status.
     */
    public HashMap<String, Object> getReturnGameStatus() {
        return returnGameStatus;
    }

    /**
     * Checks if the game should be resumed.
     *
     * @return True if the game should be resumed, false otherwise.
     */
    public boolean isResumeGame() {
        return resumeGame;
    }

    /**
     * Sets the flag indicating whether the game should be resumed.
     *
     * @param resumeGame True to resume the game, false to start a new game.
     */
    public void setResumeGame(boolean resumeGame) {
        this.resumeGame = resumeGame;
    }

    /**
     * Returns the current state of the 'escape' flag.
     *
     * @return {@code true} if the 'escape' flag is set to true, indicating an escape action; {@code false} otherwise.
     */
    public boolean isEscape() {
        return escape;
    }

    /**
     * Sets the 'escape' flag to the specified value.
     *
     * @param escape {@code true} to indicate an escape action; {@code false} to indicate no escape action.
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
    }

}
