package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents the Heads-Up Display (HUD) in the maze game.
 * Manages and displays essential information such as the number of lives and whether the key is collected.
 */
public class HUD {

    //The stage for rendering the HUD components.
    public Stage stage;

    //The viewport for the HUD.
    private Viewport viewport;

    //The current number of lives.
    private int numOfLives;

    //Indicates whether the key is collected or not.
    private boolean isKeyCollected;

    //Labels for displaying HUD information.
    Label numOfLivesLabel;
    Label isKeyCollectedLabel;
    Label numOfLivesL;
    Label isKeyCollectedL;


    /**
     * Constructor for the HUD class.
     *
     * @param sb The SpriteBatch used for rendering.
     * @param character The character object for which the HUD is displayed.
     */
    public HUD(SpriteBatch sb, Character character){
        numOfLives = character.getNumOfLives();
        isKeyCollected = character.isKeyCollected();
        viewport = new FitViewport(MazeRunnerGame.V_WIDTH, MazeRunnerGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table. top();
        table.setFillParent(true);

        String isKeyCollectedString = isKeyCollected ? "YES" : "NO";

        numOfLivesL = new Label(String.format("%01d", numOfLives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        isKeyCollectedL = new Label(isKeyCollectedString, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numOfLivesLabel = new Label("Number Of lives:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        isKeyCollectedLabel = new Label("Is Key Collected:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(numOfLivesLabel).expandX().padTop(0).padBottom(0);
        table.add(isKeyCollectedLabel).expandX().padTop(0).padBottom(0);
        table.row();
        table.add(numOfLivesL).expandX().padBottom(300);
        table.add(isKeyCollectedL).expand().padBottom(300);

        stage.addActor(table);


    }

}
