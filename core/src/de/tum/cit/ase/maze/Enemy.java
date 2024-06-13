package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an enemy character in the maze game.
 * Extends the MazeObject class and includes additional properties and behaviors specific to enemies.
 */
public class Enemy extends MazeObject {
    private String[] possibleDirections = {"UP", "DOWN", "RIGHT", "LEFT"};
    private String chosenDirection;
    private boolean isPaused = false;
    float Speed = 125.0f;
    private float prevX = 0f;
    private float prevY = 0f;
    private float overlapTimer = 0f;
    private float changeDirectionTimer = 0f;

    /**
     * Constructor for the Enemy class.
     *
     * @param x The initial x-coordinate of the enemy.
     * @param y The initial y-coordinate of the enemy.
     */
    public Enemy(float x, float y) {
        super(new Texture(Gdx.files.internal("enemy.png")), x, y);
    }

    /**
     * Updates the position of the enemy based on the chosen direction and speed.
     *
     * @param chosenDirection The direction in which the enemy is moving ("UP", "DOWN", "RIGHT", "LEFT").
     */
    public void updatePosition(String chosenDirection) {
        if (chosenDirection != null && !isPaused){
                switch (chosenDirection){
                    case "UP":
                        prevY = y;
                        y += Gdx.graphics.getDeltaTime()*Speed;
                        break;
                    case "DOWN":
                        prevY = y;
                        y -= Gdx.graphics.getDeltaTime()*Speed;
                        break;
                    case "RIGHT":
                        prevX = x;
                        x += Gdx.graphics.getDeltaTime()*Speed;
                        break;
                    case "LEFT":
                        prevX = x;
                        x -= Gdx.graphics.getDeltaTime()*Speed;
                }
        }
    }

    /**
     * Checks if the enemy is currently in a paused state.
     *
     * @return True if the enemy is paused, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Sets the paused state of the enemy.
     *
     * @param paused True to pause the enemy, false to resume.
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    /**
     * Gets the previous x-coordinate of the enemy.
     *
     * @return The previous x-coordinate.
     */
    public float getPrevX() {
        return prevX;
    }

    /**
     * Sets the previous x-coordinate of the enemy.
     *
     * @param prevX The new previous x-coordinate.
     */
    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    /**
     * Gets the previous y-coordinate of the enemy.
     *
     * @return The previous y-coordinate.
     */
    public float getPrevY() {
        return prevY;
    }

    /**
     * Sets the previous y-coordinate of the enemy.
     *
     * @param prevY The new previous y-coordinate.
     */
    public void setPrevY(float prevY) {
        this.prevY = prevY;
    }

    /**
     * Gets the overlap timer, used for handling collisions.
     *
     * @return The overlap timer value.
     */
    public float getOverlapTimer() {
        return overlapTimer;
    }

    /**
     * Sets the overlap timer, used for handling collisions.
     *
     * @param overlapTimer The new overlap timer value.
     */
    public void setOverlapTimer(float overlapTimer) {
        this.overlapTimer = overlapTimer;
    }

    /**
     * Gets the timer for changing direction.
     *
     * @return The change direction timer value.
     */
    public float getChangeDirectionTimer() {
        return changeDirectionTimer;
    }

    /**
     * Sets the timer for changing direction.
     *
     * @param changeDirectionTimer The new change direction timer value.
     */
    public void setChangeDirectionTimer(float changeDirectionTimer) {
        this.changeDirectionTimer = changeDirectionTimer;
    }

    /**
     * Gets the currently chosen direction of the enemy.
     *
     * @return The chosen direction.
     */
    public String getChosenDirection() {
        return chosenDirection;
    }

    /**
     * Sets the chosen direction of the enemy.
     *
     * @param chosenDirection The new chosen direction.
     */
    public void setChosenDirection(String chosenDirection) {
        this.chosenDirection = chosenDirection;
    }

    /**
     * Gets the array of possible directions for the enemy.
     *
     * @return The array of possible directions.
     */
    public String[] getPossibleDirections() {
        return possibleDirections;
    }

    /**
     * Sets the array of possible directions for the enemy.
     *
     * @param possibleDirections The new array of possible directions.
     */
    public void setPossibleDirections(String[] possibleDirections) {
        this.possibleDirections = possibleDirections;
    }
}