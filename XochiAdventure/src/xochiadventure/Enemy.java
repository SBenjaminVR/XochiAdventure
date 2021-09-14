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
public class Enemy extends Item {
  private int direction;          // to store the direction in which the enemy is moving
  private int leftLimit;			    // to store the left limit in the x axis until which the enemy can move
  private int rightLimit;			    // to store the rights limit in the x axis until which the enemy can move

  /**
   * to create direction, width, height, direction, speedX, leftLimit, rightLimit, and game of the enemy
   * @param x to set the x of the enemy
   * @param y to set the y of the enemy
   * @param width to set the width of the enemy
   * @param height  to set the height of the enemy
   * @param direction to set the direction of the enemy
   * @param speedX to set the speed in the x axis of the enemy
   * @param left to set the left limit in the x axis of the enemy
   * @param right to set the right limit in the x axis of the enemy
   * @param game to set the game of the enemy
   */
  public Enemy(int x, int y, int width, int height, int direction, int speedX, int left, int right, Game game) {
    super(x, y, width, height, speedX, game);
    this.direction = direction;
    this.leftLimit = left;
    this.rightLimit = right;
  }

  // GETS ------------------------------------------------------------------

  /**
    * To get the direction of the enemy
    * @return an <code>int</code> value with the direction of the enemy
    */
  public int getDirection() {
    return direction;
  }

  /**
   * To get the left limit in the x axis until which the enemy can move
   * @return an <code>int</code> value with the left limit
   */
  public int getLeftLimit() {
    return leftLimit;
  }

  /**
   * To get the right limit in the x axis until which the enemy can move
   * @return an <code>int</code> value with the right limit
   */
  public int getRightLimit() {
    return rightLimit;
  }

  // SETS ------------------------------------------------------------------

  /**
  * To set the direction of the enemy
  * @param direction to set the direction of the enemy
  */
  public void setDirection(int direction) {
    this.direction = direction;
  }

  /**
   * To set the left limit in the x axis until which the enemy can move
   * @param leftLimit to set the left limit of the enemy
   */
  public void setLeftLimit(int leftLimit) {
    this.leftLimit = leftLimit;
  }

  /**
   * To set the left limit in the x axis until which the enemy can move
   * @param rightLimit to set the right limit of the enemy
   */
  public void setRightLimit(int rightLimit) {
    this.rightLimit = rightLimit;
  }

  // FUNCTIONS ------------------------------------------------------------------

  /**
  * To know if the enemy is intersecting with the player
  * @param obj to know if the enemy is intersecting with it
  * @return an <code>boolean</code> value with the state of the collision
  */
  public boolean intersectaJugador(Object obj) {
    return ((obj instanceof Player) && (getPerimetro().intersects(((Player) obj).getPerimetro())));
  }

  // tick y render ------------------------------------------------------------------

  @Override
  public void tick() {
    // updates the position of the enemy
    setX(getX() + speedX * direction);

    // checks if the enemy has surpassed any of its limits, if it has it starts moving in the oposite direction
    if( getX() + getWidth() >= rightLimit || getX() <= leftLimit){
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
    g.drawImage(Assets.chile, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
  }
}
