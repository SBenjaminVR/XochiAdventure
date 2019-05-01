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
    private int direction;          // to store the direction in which the enemy is moving
    private boolean destroyed;      // to store if the brick has been hit

    /**
     * to create direction, width, height, directionX, and directionY and set the enemy is not moving
     * @param x to set the x of the enemy
     * @param y to set the y of the enemy
     * @param width to set the width of the enemy
     * @param height  to set the height of the enemy
     * @param direction to set the direcion of the enmy
     * @param speedX
     * @param game to set the game of the enemy
     */
    public Enemy(int x, int y, int width, int height, int direction, int speedX, Game game) {
        super(x, y, width, height, speedX, game);
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
        this.destroyed = (Integer.parseInt(datos[3]) == 1);
    }

    /**
     * To get all the variable that need to be stored in the file as a string
     * @return an <code>String</code> value with all the information of the variables
     */
    @Override
    public String intoString(){
        return (x + " " + y + " " + direction + " " + (destroyed ? "1":"0"));
    }

    @Override
    public void tick() {
        // this function exists so all abstracts methods from Item are overriden
        // if(getDirection() >= 1){
        // setX((getX() + speedX) * getDirection());
        // }
        // if(getDirection() < 1){
        //     setX((getX()) - speedX);
        // }
        setX(getX() + speedX * direction);

        if( getX() >= 500 || getX() == 0){
        setDirection(getDirection() * -1);
        }
    }

    @Override
    public void render(Graphics g) {
      /**
       * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
       * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
       * En este caso los chiles se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
       */
      // aqui hay que agregar una condicional para cuando este mero abajo del nivel, pero tenemos que acabar de diseñar el nivel para sacar bien las alturas
      if (game.getPlayer().getX() < game.getPlayerX()) { // aquí también hay que agregar una condicional para cuando esté hasta la mera derecha, pero al igual que la condicional de la "y", tenemos que terminar de diseñar bien los niveles para poder sacar bien las distancias
        g.drawImage(Assets.chile, x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
      } else {
        g.drawImage(Assets.chile, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
      }
        // g.drawImage(Assets.comida, (game.getPlayer().getX() - getX()), (game.getPlayer().getY() - getY()), getWidth(), getHeight(), null);
        // g.drawImage(Assets.comida, -1 * (game.getRec().x - getX()), -1 * (game.getRec().y - getY()), getWidth(), getHeight(), null);
        // g.drawImage(Assets.chile, getX(), getY(), getWidth(), getHeight(), null);
    }
}
