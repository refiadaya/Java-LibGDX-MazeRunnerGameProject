package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Represents the wall in the maze game.
 * Extends the MazeObject class.
 */
public class Wall extends MazeObject{

    /**
     * Constructor for the Wall class.
     *
     * @param x The initial x-coordinate of the wall.
     * @param y The initial y-coordinate of the wall.
     */
    public Wall(float x, float y) {
        super(new Texture(Gdx.files.internal("wall.png")), x, y);
    }

}
