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
public class Letrero extends Item{

    private boolean type;

    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param type
     * @param game 
     */
    public Letrero (int x, int y, int width, int height, boolean type, Game game) {
        super (x, y, width, height, 0, game);
        this.type = type;
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        if (type) {
            g.drawImage(Assets.peligro, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        } else {
            g.drawImage(Assets.aviso, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        }
    }
    
}
