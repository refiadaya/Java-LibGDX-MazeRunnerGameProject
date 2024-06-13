package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the entry point in the maze game.
 * Extends the MazeObject class.
 */
public class Key extends MazeObject{

    /**
     * Constructs a Key object with the specified coordinates.
     *
     * @param x The x-coordinate of the key.
     * @param y The y-coordinate of the key.
     */
    public Key(float x, float y) {
        super(new Texture(Gdx.files.internal("key.png")), x, y);
    }

}
