package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

/**
 * The PauseScreen class represents the screen that is displayed when the game is paused.
 * It provides options for resuming the game, choosing a new map, and quitting the game.
 */
public class PauseScreen implements Screen {

    private final Stage stage;
    private final HashMap<String, Object> statusGame = new HashMap<>();

    /**
     * Constructor for PauseScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game      The main game class, used to access global resources and methods.
     * @param statusGame A HashMap containing key-value pairs representing the current game status.
     *                   Expected keys: "level", "playerX", "playerY", "isKeyCollected", "livesLeft".
     */
    public PauseScreen(MazeRunnerGame game, HashMap<String, Object> statusGame) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        //statusGame = game.getStatusGame();

        table.add(new Label("Game Paused", game.getSkin(), "title")).padBottom(80).row();


        TextButton goToGameButton = new TextButton("Resume", game.getSkin());
        table.add(goToGameButton).width(600).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (statusGame !=null){
                    int level = (int) statusGame.get("level");
                    game.setResumeGame(true);
                    game.gameResumed(statusGame);
                    game.goToGame(level);
                } else {
                    game.goToMenu();
                }
            }
        });

        TextButton goToGameButton2 = new TextButton("Choose a New Map", game.getSkin());
        table.add(goToGameButton2).width(600).row();
        goToGameButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMapScreen(); // Change to the map screen when button is pressed
            }
        });

        TextButton goToGameButton3 = new TextButton("Quit Game", game.getSkin());
        table.add(goToGameButton3).width(600).row();
        goToGameButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Exit out of the game
            }
        });
    }

    /**
     * Sets the input processor so the stage can receive input events.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the stage by clearing the screen, updating the stage, and drawing the stage.
     *
     * @param delta The time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * Resizes the stage viewport when the window is resized.
     *
     * @param width  The new width of the window.
     * @param height The new height of the window.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    /**
     * Unused method from the Screen interface.
     */
    @Override
    public void pause() {

    }

    /**
     * Unused method from the Screen interface.
     */
    @Override
    public void resume() {

    }

    /**
     * Unused method from the Screen interface.
     */
    @Override
    public void hide() {

    }

    /**
     * Disposes of the stage when the screen is disposed.
     */
    @Override
    public void dispose() {
        stage.dispose();

    }
}