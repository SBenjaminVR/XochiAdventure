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
 * @author betin
 */
public class Platform extends Item {

    /**
     * Set the initial values to create the platform
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public Platform (int x, int y, int width, int height, Game game) {
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
    public void tick() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.platform, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
    }
}
