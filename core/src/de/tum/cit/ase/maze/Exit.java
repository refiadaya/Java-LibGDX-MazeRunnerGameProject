package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the exit point in the maze game.
 * Extends the MazeObject class and provides functionality specific to the exit point.
 */

public class Exit extends MazeObject{
    private float overlapTimer = 0f;

    /**
     * Constructor for the Exit class.
     *
     * @param x The initial x-coordinate of the exit point.
     * @param y The initial y-coordinate of the exit point.
     */
    public Exit(float x, float y) {
        super(new Texture(Gdx.files.internal("exit.png")), x, y);
    }
    /**
     * Gets the overlap timer for the exit point.
     *
     * @return The current value of the overlap timer.
     */
    public float getOverlapTimer() {
        return overlapTimer;
    }

    /**
     * Sets the overlap timer for the exit point.
     *
     * @param overlapTimer The new value for the overlap timer.
     */
    public void setOverlapTimer(float overlapTimer) {
        this.overlapTimer = overlapTimer;
    }
}