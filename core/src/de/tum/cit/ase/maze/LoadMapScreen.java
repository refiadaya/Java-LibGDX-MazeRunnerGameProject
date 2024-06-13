package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Represents the screen for loading and selecting maze maps.
 * Implements the LibGDX Screen interface for managing the screen lifecycle.
 */
public class LoadMapScreen implements Screen {

    // Stage for UI elements
    private final Stage stage;

    // Selected maze map level
    private int selectedLevel;


    /**
     * Constructs a LoadMapScreen with the specified game instance.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public LoadMapScreen(MazeRunnerGame game) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a title label to the table
        table.add(new Label("Select a Mansion", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        TextButton goToGameButton = new TextButton("Map 1: Ravenwood Manor", game.getSkin());
        table.add(goToGameButton).width(600).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedLevel(1);
                game.goToGame(selectedLevel); // Change to the game screen when button is pressed
            }
        });

        TextButton goToGameButton2 = new TextButton("Map 2: Phantom Hallows House", game.getSkin());
        table.add(goToGameButton2).width(600).row();
        goToGameButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedLevel(2);
                game.goToGame(selectedLevel); // Change to the game screen when button is pressed
            }
        });

        TextButton goToGameButton3 = new TextButton("Map 3: Eerievale Mansion", game.getSkin());
        table.add(goToGameButton3).width(600).row();
        goToGameButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedLevel(3);
                game.goToGame(selectedLevel); // Change to the game screen when button is pressed
            }
        });

        TextButton goToGameButton4 = new TextButton("Map 4: Ghostly Gables Mansion", game.getSkin());
        table.add(goToGameButton4).width(600).row();
        goToGameButton4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedLevel(4);
                game.goToGame(selectedLevel); // Change to the game screen when button is pressed
            }
        });

        TextButton goToGameButton5 = new TextButton("Map 5: Wraithstone Manor", game.getSkin());
        table.add(goToGameButton5).width(600).row();
        goToGameButton5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedLevel(5);
                game.goToGame(selectedLevel); // Change to the game screen when button is pressed
            }
        });

        TextButton goToGameButton6 = new TextButton("Upload a Custom Map", game.getSkin());
        table.add(goToGameButton6).width(600).row();
        goToGameButton6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedLevel(6);
                game.goToGame(selectedLevel); // Change to the game screen when button is pressed
            }
        });
    }

    /**
     * Shows the LoadMapScreen and sets the input processor to the stage.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the LoadMapScreen, clearing the screen and drawing the stage.
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
     * Resizes the stage viewport on screen resize.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    /**
     * Called when the application is paused. No additional logic is implemented in this method.
     * This method is part of the LibGDX Screen interface.
     */
    @Override
    public void pause() {

    }

    /**
     * Called when the application is resumed. No additional logic is implemented in this method.
     * This method is part of the LibGDX Screen interface.
     */
    @Override
    public void resume() {

    }

    /**
     * Called when the screen is hidden. No additional logic is implemented in this method.
     * This method is part of the LibGDX Screen interface.
     */
    @Override
    public void hide() {

    }

    /**
     * Disposes of the stage when the LoadMapScreen is no longer needed.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Gets the stage associated with the LoadMapScreen.
     *
     * @return The stage for UI elements.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets the selected maze map level.
     *
     * @return The selected maze map level.
     */
    public int getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * Sets the selected maze map level.
     *
     * @param selectedLevel The selected maze map level.
     */
    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}

