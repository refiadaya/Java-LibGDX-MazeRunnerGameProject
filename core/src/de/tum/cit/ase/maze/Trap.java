package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Represents a trap object in the game maze.
 * Extends the MazeObject class and provides functionality specific to traps.
 */
public class Trap extends MazeObject{
    private float overlapTimer = 0f; // Timer to track the duration of overlap

    /**
     * Constructor for the Trap class.
     *
     * @param x The x-coordinate of the trap.
     * @param y The y-coordinate of the trap.
     */
    public Trap(float x, float y) {
        super(new Texture(Gdx.files.internal("trap.png")), x, y);
    }

    /**
     * Gets the overlap timer value.
     *
     * @return The current value of the overlap timer.
     */
    public float getOverlapTimer() {
        return overlapTimer;
    }

    /**
     * Sets the overlap timer value.
     *
     * @param overlapTimer The value to set for the overlap timer.
     */
    public void setOverlapTimer(float overlapTimer) {
        this.overlapTimer = overlapTimer;
    }
}