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
public class Bomb extends Item {
  private int directionY;         // to know the direction in the y axis of tha shot
  private boolean fired;     // flag to know if the ball is moving

  /**
   * To create position, width, height, direction in the x and y axis, speed and game
   * @param x to set the x of the shot
   * @param y to set the y of the shot
   * @param width to set the width of the shot
   * @param height to set the height of the shot
   * @param game to ser the game of the shot
   */
  public Bomb (int x, int y, int width, int height, Game game) {
      super(x, y, width, height, game);
      this.directionY = 0;
      fired = false;
  }

  // GETS ----------------------------------------------------------------

 /**
   * To get the direction of the shot in the y axis
   * @return an <code>int</code> value with the direction in the axis
   */
  public int getDirectionY() {
      return directionY;
  }

  /**
   * To know if the bomb has been fired
   * @return an <code>boolean</code> value with the state of the bomb
   */
  public boolean isFired() {
      return fired;
  }

  //SETS ----------------------------------------------------------------

   /**
   * To set the direction of the shot
   * @param directionY to set the direction of the shot
   */
  public void setDirectionY(int directionY) {
      this.directionY = directionY;
  }
  
  /**
   * To set if the bomb has been fired
   * @param fired to set fired
   */
  public void setFired(boolean fired) {
      this.fired = fired;
  }

  // FUNCTIONS ----------------------------------------------------------------

  /**
   * To get a rectangle with the position, width, and height of the shot
   * @return an <code>Rectangle</code> rectangle with the given position, width, and height
   */
  public Rectangle getPerimetro() {
      return new Rectangle(getX(), getY(), getWidth(), getHeight());
  }

  /**
   * To know if the bomb is intersecting with the player
   * @param obj to know if the bomb is intersecting with it
   * @return an <code>boolean</code> value with the state of the collision
   */

  public boolean intersectaJugador(Object obj) {
      return ((obj instanceof Player) && (getPerimetro().intersects(((Player) obj).getPerimetro())));
   }

  // Carga la información del objeto desde un string
  /**
   * To set the value of position and the direction both in the 'x' and 'y' axis of the ball from the file that was loaded
   * @param datos to set all the variables
   */
  public void loadFromString(String[] datos){
      this.y = Integer.parseInt(datos[0]);
      this.directionY = Integer.parseInt(datos[1]);
  }

  /**
   * To get all the variable that need to be stored in the file as a string
   * @return an <code>String</code> value with all the information of the variables
   */
  public String toString(){
      return (y + " " + directionY);
  }

  public void reset() {

  }

  @Override
  public void tick() {
           // updates the position of the shot
           if (fired) {
             setY((getY()+3));
           }
  }

  @Override
  public void render(Graphics g) {
      g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
  }

}
