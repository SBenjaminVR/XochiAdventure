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
	
    private int direction; // to store the direction of the power up
	// to know which type of power up exist
	public enum Type {
        ATOLE,  // Drop que recupera toda la vida al jugador
        AGUA,   // Drop que funciona como municion para recargar el disparo del jugador
        DULCE,  // Drop que recupera lo equivalente a un golpe al jugador
        FRIJOL  // Drop que aumenta la resistencia del jugador
    }
    private Type tipo;				// to store which type of power up it is
    private Animation atoleAnim;	// to animate the power up
	
    /**
     * To create position, width, height, direction in the x and y axis, speed and game of the power up
     * @param x to set the x of the power up
     * @param y to set the y of the power up
     * @param width to set the width of the power up
     * @param height to set the height of the power up
     * @param game to ser the game of the power up
     */
    public PowerUps (int x, int y, int width, int height, Game game) {
        super(x, y, width, height, 0, game);
        this.direction = 1;
		
		// to determine which type of power up it will be
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
	
		// to make the animation work
        switch (tipo) {
            case ATOLE:
                atoleAnim.tick();
                break;
        }
    }

    @Override
    public void render(Graphics g) {
		/**
		* Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
		* pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
		* En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
		*/
	   
        switch (tipo) {
            case ATOLE:
                g.drawImage(atoleAnim.getCurrentFrame(), (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case DULCE:
                g.drawImage(Assets.dulce, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            default:
                g.drawImage(Assets.powerup, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
        }
    }
}
