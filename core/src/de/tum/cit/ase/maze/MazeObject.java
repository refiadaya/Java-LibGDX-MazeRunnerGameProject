package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * An abstract class representing objects in the maze, such as enemies, traps, keys, walls, exits, and entry points.
 * Each MazeObject has a texture, position (x, y), size (width, height), and bounding rectangle.
 */
public abstract class MazeObject {

    //The bounding rectangle that defines the position and size of the MazeObject.
    protected Rectangle bounds;

    //The x and y coordinates of the MazeObject's position.
    protected float x,y;

    //The width and height of the MazeObject.
    protected int width, height;

    //The texture representing the appearance of the MazeObject.
    protected Texture texture;


    /**
     * Constructor for MazeObject.
     *
     * @param texture The texture representing the appearance of the MazeObject.
     * @param x       The x-coordinate of the MazeObject's position.
     * @param y       The y-coordinate of the MazeObject's position.
     */
    public MazeObject(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;

        // Set width, height, and bounds based on the type of MazeObject.
        if (this instanceof Enemy || this instanceof Trap || this instanceof Key){
            width = 25;
            height = 40;
            bounds = new Rectangle(x, y, width, height);
        }
        if (this instanceof Wall || this instanceof Exit || this instanceof Entry){
            width = 64;
            height = 64;
            bounds = new Rectangle(x, y, width, height);
        }
    }

    /**
     * Gets the texture of the MazeObject.
     *
     * @return The texture of the MazeObject.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets the texture representing the maze object.
     *
     * @param texture The new texture for the maze object.
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Gets the rectangular bounds of the maze object.
     *
     * @return The bounds of the maze object.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Sets the rectangular bounds of the maze object.
     *
     * @param bounds The new bounds for the maze object.
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * Gets the x-coordinate of the maze object's position.
     *
     * @return The x-coordinate of the maze object.
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the maze object's position.
     *
     * @param x The new x-coordinate for the maze object.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the maze object's position.
     *
     * @return The y-coordinate of the maze object.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the maze object's position.
     *
     * @param y The new y-coordinate for the maze object.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the width of the maze object.
     *
     * @return The width of the maze object.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the maze object.
     *
     * @param width The new width for the maze object.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the maze object.
     *
     * @return The height of the maze object.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the maze object.
     *
     * @param height The new height for the maze object.
     */
    public void setHeight(int height) {
        this.height = height;
    }
}