/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.Graphics;

/**
 *
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class Shot extends Item {
  private int directionX;         // to know the direction in the x axis of tha shot

  /**
   * To create position, width, height, direction in the x and y axis, speed and game
   * @param x to set the x of the shot
   * @param y to set the y of the shot
   * @param width to set the width of the shot
   * @param height to set the height of the shot
   * @param speedX to set the speed in the x axis of the shot
   * @param game to ser the game of the shot
   */
  public Shot (int x, int y, int width, int height, int speedX, int directionX, Game game) {
    super(x, y, width, height, speedX, game);
    this.directionX = directionX;
  }

  // GETS ----------------------------------------------------------------

  /**
   * To get the direction of the shot in the y axis
   * @return an <code>int</code> value with the direction in the axis
   */
  public int getDirectionX() {
    return directionX;
  }

  //SETS ----------------------------------------------------------------

    /**
   * To set the direction of the shot
   * @param directionX
   */
  public void setDirectionX(int directionX) {
    this.directionX = directionX;
  }

  // FUNCTIONS ----------------------------------------------------------------

  /**
   * To know if the shot is intersecting with the alien
   * @param obj to know if the shot is intersecting with it
   * @return an <code>boolean</code> value with the state of the collision
   */
  public boolean intersectaChile(Object obj) {
    return ((obj instanceof Enemy) && (getPerimetro().intersects(((Enemy) obj).getPerimetro())));
  }
  
  @Override
  public void tick() {
    // updates the position of the shot
    setX(getX() + getSpeedX() * getDirectionX());
  }

  @Override
  public void render(Graphics g) {
    g.drawImage(Assets.shot, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
  }
}
