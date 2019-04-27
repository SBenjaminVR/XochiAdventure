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
public class Enemy extends Item {
    private int direction;
    private boolean destroyed;     // to store if the brick has been hit

    /**
     * to create direction, width, height, directionX, and directionY and set the enemy is not moving
     * @param x to set the x of the enemy
     * @param y to set the y of the enemy
     * @param width to set the width of the enemy
     * @param height  to set the height of the enemy
     * @param game to set the game of the enemy
     */
    public Enemy(int x, int y, int width, int height,int direction, Game game) {
        super(x, y, width, height, game);
        this.direction = direction;
        this.destroyed = false;
    }
    
    /**
     * To know if the brick has been hit
     * @return an <code>boolean</code> value of the state of the brick
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
      * To get the direction of the alien
      * @return an <code>int</code> value with the direction
      */
    public int getDirection() {
        return direction;
    }

    /**
    * To set the direction of the alien
    * @param direction to set the direction of the alien
    */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * To set if the brick has been hit
     * @param destroyed
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // Carga la información del objeto desde un string
    /**
     * To set the value of destroyed from the file that was loaded
     * @param datos to set all the variables
     */
    public void loadFromString(String[] datos){
        this.x = Integer.parseInt(datos[0]);
        this.y = Integer.parseInt(datos[1]);
        this.direction = Integer.parseInt(datos[2]);
        this.destroyed = (Integer.parseInt(datos[3]) == 1 ? true : false);
    }

    /**
     * To get all the variable that need to be stored in the file as a string
     * @return an <code>String</code> value with all the information of the variables
     */
    public String toString(){
        return (x + " " + y + " " + direction + " " + (destroyed ? "1":"0"));
    }

    /**
     * To get the rectangle of the brick
     * @return an <code>Rectangle</code> value with the rectangle of the brick
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.chile, getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void tick() {
        // this function exists so all abstracts methods from Item are overriden
        if(getDirection() >= 1){
        setX((getX()+1) * getDirection());
        }
        if(getDirection() < 1){
            setX((getX())-1);
        }
        /*
        if( getX() >= 500 || getX() == 0){
        setDirection(getDirection() * -1);
        }
     */
    }
}
