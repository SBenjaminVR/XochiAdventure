/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xochiadventure;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public abstract class Item {
    protected int x;        // to store x position
    protected int y;        // to store y position
    protected int width;	// to store the width
    protected int height;	// to store the height
    protected int speedY;	// to store the speed in the y axis
    protected int speedX; 	// to store the speed in the x axis
    protected Game game;	// to store the game
    
    /**
     * Set the initial values to create the item
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     * @param width
     * @param height
     * @param speedX
     * @param game
     */
    public Item(int x, int y, int width, int height, int speedX, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedY = 0;
        this.speedX = speedX;
        this.game = game;
    }
    
    // GETS ----------------------------------------------------------------

    /**
     * Get x value
     * @return x 
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value
     * @return y 
     */
    public int getY() {
        return y;
    }
    
    /**
     * Get width value
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get height value
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get speedX value
     * @return speedX
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * Get speedY value
     * @return speedY
     */
    public int getSpeedY() {
        return speedY;
    }
    
    // SETS ----------------------------------------------------------------

    /**
     * Set x value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set width value
     * @param width to modify
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set height value
     * @param height to modify
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set speedX value
     * @param speedX to modify
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /**
     * Set speedY value
     * @param speedY to modify
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    
    // FUNCTIONS -----------------------------------------------------------
    
    /**
     * To get a rectangle with the position, width, and height of the item
     * @return an <code>Rectangle</code> rectangle with the given position, width, and height
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * To paint the item
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);
}
