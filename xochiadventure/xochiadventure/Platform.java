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
public class Platform extends Item {
  /**
   * to create direction, width, height, and game of the platform
   * @param x to set the x of the platform
   * @param y to set the y of the platform
   * @param width to set the width of the platform
   * @param height  to set the height of the platform
   * @param game to set the game of the platform
   */
  public Platform (int x, int y, int width, int height, GameLevel game) {
    super(x, y, width, height, 0, game);
  }

  /**
 * To know if the bomb is intersecting with the player
 * @param obj to know if the bomb is intersecting with it
 * @return an <code>boolean</code> value with the state of the collision
 */
  public boolean intersectaJugador(Object obj) {
    return ((obj instanceof Player) && (getPerimetro().intersects(((Player) obj).getPerimetro())));
  }

  @Override
  public void tick(KeyManager keyManager) {
    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void render(Graphics g) {
    g.drawImage(Assets.levelAssets[Assets.LevelAssets.PLATFORM.ordinal()], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
  }
}
