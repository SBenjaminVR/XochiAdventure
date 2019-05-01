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
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speedX
     * @param game
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
        if (game.getPlayer().getX() < game.getPlayerX()) {
          g.drawImage(Assets.comida, x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
        } else {
          g.drawImage(Assets.comida, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        }
    }

}
