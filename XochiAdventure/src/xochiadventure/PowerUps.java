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
public class PowerUps extends Item{
    private int direction;
    public enum Type {
        ATOLE,  // Drop que recupera toda la vida al jugador
        AGUA,   // Drop que funciona como municion para recargar el disparo del jugador
        DULCE,  // Drop que recupera lo equivalente a un golpe al jugador
        FRIJOL  // Drop que aumenta la resistencia del jugador
    }
    private Type tipo;
    private Animation atoleAnim;
     

    public PowerUps (int x, int y, int width, int height, int speedX, Game game) {
        super(x, y, width, height, speedX, game);
        this.direction = 1;
        int max = 100;
        int min = 0;
        double numerito = (Math.random() * ((max - min) + 1)) + min;
        if (numerito < 25) {
            this.tipo = Type.FRIJOL;
        }
        else if (numerito < 50) {
            this.tipo  = Type.DULCE;
        }
        else if (numerito < 75) {
            this.tipo = Type.ATOLE;
        }
        else {
            this.tipo = Type.AGUA;
        }        
        
        atoleAnim = new Animation(Assets.atoleAnim, 150);
    }

    // GETS

    /**
     * To get the direction of the power up
     * @return an <code>int</code> value with the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * To get the direction of the power up
     * @return an <code>int</code> value with the direction
     */
    public Type getType() {
        return this.tipo;
    }

    // SETS

    /**
     * To set the direction of the power up
     * @param direction to set the direction of the power up
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * To set the type of power up
     * @param type to set the type
     */
    public void setType(Type type) {
        this.tipo = type;
    }

    // FUNCTIONS

    /**
   * To know if the bomb is intersecting with the player
   * @param obj to know if the bomb is intersecting with it
   * @return an <code>boolean</code> value with the state of the collision
   */
    public boolean intersectaJugador(Object obj) {
        return ((obj instanceof Player) && (getPerimetro().intersects(((Player) obj).getPerimetro())));
    }

    @Override
    public void loadFromString(String[] datos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String intoString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick() {
        setX(getX() + getSpeedX() * getDirection());

        // checks that the object does not goes out of the bounds
        if (getX() + 60 >= game.getWidth()) {
            setX(game.getWidth() - this.getWidth());
        }
        else if (getX() <= -30) {
            setX(0);
        }
        if (getY() + 80 >= game.getHeight()) {
            setY(game.getHeight() - this.getHeight());
        }
        else if (getY() <= -20) {
            setY(0);
        }
        
        switch (tipo) {
            case ATOLE:
                atoleAnim.tick();
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        switch (tipo) {
            case ATOLE:
                g.drawImage(atoleAnim.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
                break;
            case DULCE:
                g.drawImage(Assets.dulce, getX(), getY(), getWidth(), getHeight(), null);
                break;
            default:    
                g.drawImage(Assets.powerup, getX(), getY(), getWidth(), getHeight(), null);
        }
       // g.drawImage(Assets.powerup, getX() - (getX() - game.getPlayer().getX()), getY() - (getY() - game.getPlayer().getY()), getWidth(), getHeight(), null);
       //g.drawImage(Assets.powerup, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
       // g.drawImage(Assets.powerup, getX(), getY(), getWidth(), getHeight(), null);

    }
}
