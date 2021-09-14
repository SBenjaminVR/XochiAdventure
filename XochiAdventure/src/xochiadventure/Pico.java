/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.Graphics;

enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT
}

/**
 *
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class Pico extends Item{
  private Direction dir;

  /**
   * 
   * @param x
   * @param y
   * @param width
   * @param height
   * @param dir
   * @param game 
   */
  public Pico(int x, int y, int width, int height, String dir, GameLevel game) {
      super (x, y, width, height, 0, game);
      switch(dir) {
        case "u":
          this.dir = Direction.UP;
          break;
        case "d":
          this.dir = Direction.DOWN;
          break;
        case  "l":
          this.dir = Direction.LEFT;
          break;
        case "r":
          this.dir = Direction.RIGHT;
          break;
      }
  }

  /**
  * To know if the spike is intersecting with the player
  * @param obj to know if the spike is intersecting with it
  * @return an <code>boolean</code> value with the state of the collision
  */
  public boolean intersectaJugador(Object obj) {
    return ((obj instanceof Player) && (getPerimetro().intersects(((Player) obj).getPerimetro())));
  }

  @Override
  public void tick(KeyManager keyManager) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void render(Graphics g) {
    switch(dir) {
      case UP:
        g.drawImage(Assets.picoArriba, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        break;
      case DOWN:
        g.drawImage(Assets.picoAbajo, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        break;
      case LEFT:
        g.drawImage(Assets.picoIzquierda, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        break;
      case RIGHT:
        g.drawImage(Assets.picoDerecha, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        break;
    }
  }
}
