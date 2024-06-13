package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Represents the entry point in the maze game.
 * Extends the MazeObject class.
 */
public class Entry extends MazeObject{
    /**
     * Constructor for the Entry class.
     *
     * @param x The initial x-coordinate of the entry point.
     * @param y The initial y-coordinate of the entry point.
     */
    public Entry(float x, float y) {
        super(new Texture(Gdx.files.internal("entry.png")), x, y);
    }
}
