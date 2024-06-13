package de.tum.cit.ase.maze;

/**
 * Represents the player character in the maze game.
 */
public class Character{
    private int numOfLives;
    private boolean keyCollected;
    private String direction;
    private boolean isPaused = false;

    /**
     * Default constructor for the Character class.
     * Initializes the character with 5 lives, no collected key, and facing down.
     */
    public Character() {
        numOfLives = 5;
        keyCollected = false;
        direction = "DOWN";
    }

    /**
     * Decreases the number of lives of the character by 1.
     * Called when the character collides with an enemy or a trap.
     */
    public void loseLife(){
        this.setNumOfLives(getNumOfLives()-1);
    }

    /**
     * Gets the current number of lives of the character.
     *
     * @return The number of lives.
     */
    public int getNumOfLives() {
        return numOfLives;
    }

    /**
     * Sets the number of lives for the character.
     *
     * @param numOfLives The new number of lives to set.
     */
    public void setNumOfLives(int numOfLives) {
        this.numOfLives = numOfLives;
    }

    /**
     * Checks if the character has collected the key.
     *
     * @return True if the key is collected, false otherwise.
     */
    public boolean isKeyCollected() {
        return keyCollected;
    }

    /**
     * Sets the status of whether the character has collected the key.
     *
     * @param keyCollected True if the key is collected, false otherwise.
     */
    public void setKeyCollected(boolean keyCollected) {
        this.keyCollected = keyCollected;
    }

    /**
     * Gets the current direction the character is facing.
     *
     * @return The direction as a string ("UP", "DOWN", "LEFT", "RIGHT", etc.).
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction the character is facing.
     *
     * @param direction The new direction for the character.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Checks if the character is currently in a paused state.
     *
     * @return True if the character is paused, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Sets the paused state of the character.
     *
     * @param paused True to pause the character, false to resume.
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}