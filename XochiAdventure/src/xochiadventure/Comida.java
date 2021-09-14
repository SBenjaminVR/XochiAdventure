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
public class Comida extends Item{
  private int ingredient;

  /**
   * to create direction, width, height, speed in the x axis, and game of the food
   * @param x to set the x of the food
   * @param y to set the y of the food
   * @param width to set the width of the food
   * @param height to set the height of the food
   * @param game to set the game of the food
   */
  public Comida(int x, int y, int width, int height, int i, GameLevel game) {
    super(x, y, width, height, 0, game);
    this.ingredient = i;
  }

  // GETS ------------------------------------------------------------------

  // SETS ------------------------------------------------------------------

  // FUNCTIONS ------------------------------------------------------------------

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

  }

  @Override
  public void render(Graphics g) {
    /**
     * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
     * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
     * En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
     */
    switch (game.getNivel()) {
      case 1:
        g.drawImage(Assets.ingredientesEnchiladas[ingredient], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        break;
      case 3:
        if (ingredient > 1 && ingredient < 4) {
          g.drawImage(Assets.ingredientesMole[1], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        } else if (ingredient >= 4) {
          g.drawImage(Assets.ingredientesMole[ingredient - 2], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        } else {
          g.drawImage(Assets.ingredientesMole[0], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        }
        
        break;
      case 2:
        g.drawImage(Assets.ingredientesQuecas[ingredient], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        break;
    }
  }

  public void renderUI(Graphics g, int x, int y) {
    /**
     * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
     * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
     * En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
     */
    switch (game.getNivel()) {
      case 1:
        g.drawImage(Assets.ingredientesEnchiladas[ingredient], x, y, getWidth(), getHeight(), null);
        break;
      case 3:
        if (ingredient > 1 && ingredient < 4) {
          g.drawImage(Assets.ingredientesMole[1], x, y, getWidth(), getHeight(), null);
        } else if (ingredient >= 4) {
          g.drawImage(Assets.ingredientesMole[ingredient - 2], x, y, getWidth(), getHeight(), null);
        } else {
          g.drawImage(Assets.ingredientesMole[0], x, y, getWidth(), getHeight(), null);
        }
        
        break;
      case 2:
        g.drawImage(Assets.ingredientesQuecas[ingredient], x, y, getWidth(), getHeight(), null);
        break;
    }
  }
}
