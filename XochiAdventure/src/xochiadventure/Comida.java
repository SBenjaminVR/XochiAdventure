/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.Graphics;

/**
 *
 * @author betin
 */
public class Comida extends Item{

    /**
     * to create direction, width, height, speed in the x axis, and game
     * @param x to set the x of the food
     * @param y to set the y of the food
     * @param width to set the width of the food
     * @param height to set the height of the food
     * @param speedX to set the speed in the x axis of the food
     * @param game to set the game of the food
     */
    public Comida(int x, int y, int width, int height, int speedX, Game game) {
        super(x, y, width, height, speedX, game);
    }

    @Override
    public void loadFromString(String[] datos) {

    }

    @Override
    public String intoString() {
        return "";
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
      /**
       * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
       * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
       * En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
       */
      // hay que agregar una condicional para cuando este mero abajo del nivel, pero tenemos que acabar de diseñar el nivel para sacar bien las alturas
      // también hay que agregar una condicional para cuando esté hasta la mera derecha, pero al igual que la condicional de la "y", tenemos que terminar de diseñar bien los niveles para poder sacar bien las distancias
        if (game.getPlayer().getX() < game.getPlayerX()) {
          g.drawImage(Assets.comida, x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
        } else {
          g.drawImage(Assets.comida, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        }
    }

}
