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
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández A01410921
 */
public abstract class Item {
    protected int x;        // to store x position
    protected int y;        // to store y position
    protected int width;
    protected int height;
    protected int speedY;
    protected int speedX;
    protected Game game;
    
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
     * 
     * @return 
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @return 
     */
    public int getHeight() {
        return height;
    }

    public int getSpeedX() {
        return speedX;
    }

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
     * 
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    
    // FUNCTIONS -----------------------------------------------------------
    
    /**
     * To get a rectangle with the position, width, and height of the power up
     * @return an <code>Rectangle</code> rectangle with the given position, width, and height
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    // Carga la información del objeto desde un string
   /**
     * To set the value of the position in the x axis and the width of the player from the file that was loaded
     * @param datos to set all the variables
     */
    public abstract void loadFromString(String[] datos);

    /**
     * To get all the variable that need to be stored in the file as a string
     * @return an <code>String</code> value with all the information of the variables
     */
    public abstract String intoString();
    
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
