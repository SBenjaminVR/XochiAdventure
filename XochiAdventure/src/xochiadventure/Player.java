/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Set;

/**
 *
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández A01410921
 */
public class Player extends Item{

    private int lives;					// to store the lives that the player has
    private int maxLives;				// to store the max lives the player can have
    private int water;					// to store how much water the player has
    private boolean inTheAir;			// to know if the player is in the air
    private int direction;				// to store the direction of the player
    private int contGotHit;				// to store how many more ticks the player is invunerable because they were hit
    private boolean drawPlayer;			// to know if the player should be drawn
    private int leftLimit;				// to store the limit in the x axis where the player can move to the left before falling
    private int rightLimit;				// to store the limit in the x axis where the player can move to the right before falling
    private Animation xochiAnim;		// to animate the player
    private boolean moving;				// to know if the player is moving int the x axis
    private int lastX;					// to store the last position on the x axis while on the ground
    private int lastY;					// to store the last position on the y axis while on the ground

    /**
     * to create direction, width, height, speed in the x axis, and game
     * @param x to set the x of the player
     * @param y to set the y of the player
     * @param width to set the width of the player
     * @param height to set the height of the player
     * @param speedX to set the speed in the x axis of the player
     * @param game to set the game of the player
     */
    public Player(int x, int y, int width, int height, int speedX, int lives, int left, int right, Game game) {
        super(x, y, width, height, speedX, game);
        this.maxLives = lives;
        this.lives = maxLives;
        this.water = 100;
        this.inTheAir = false;
        this.direction = 1;
        this.contGotHit = 0;
        this.drawPlayer = true;
        this.leftLimit = left;
        this.rightLimit = right;
        moving = false;
        xochiAnim = new Animation(Assets.xochiAnim, 150);
        this.lastX = x;
        this.lastY = x;
    }

    // GETS ------------------------------------------------------------------
	
    /**
     * To get the lives the player has
     * @return an <code>int</code> value with the lives of the player
     */
    public int getLives() {
        return lives;
    }

    /**
     * To get the max lives the player can have
     * @return an <code>int</code> value with the max lives
     */
    public int getMaxLives() {
        return maxLives;
    }

    /**
     * To know if the player is in the air
     * @return an <code>boolean</code> value to know the state of the player
     */
    public boolean isInTheAir() {
        return inTheAir;
    }

     /**
     * To get how many more ticks will the player will be in a state of invunerability
     * @return an <code>int</code> value with the remaining ticks
     */
    public int getContGotHit() {
        return contGotHit;
    }

    /**
     * To get the direction in the x axis of the player
     * @return an <code>int</code> value with the direction 
     */
    public int getDirection() {
        return direction;
    }

    /**
     * To get the remaining quantity of water the player has
     * @return an <code>int</code> value with the remaining quantity of water
     */
    public int getWater() {
        return water;
    }

    /**
     * To get the last position in the x axis of the player while it was on the ground
     * @return an <code>int</code> value with the last position in the x axis
     */
    public int getLastX() {
        return lastX;
    }

    /**
     * To get the last position in the y axis of the player while it was on the ground
     * @return an <code>int</code> value with the last position in the y axis
     */
    public int getLastY() {
        return lastY;
    }

    /**
     * 
     * @return 
     */
    public int getLeftLimit() {
        return leftLimit;
    }

    /**
     * 
     * @return 
     */
    public int getRightLimit() {
        return rightLimit;
    }

    // SETS ------------------------------------------------------------------

	/**
     * To set the direction of the power up
     * @param direction to set the direction of the power up
     */

    /**
     * To set the lives the player has
     * @param lives to set the lives the player has
     */
    public void setLives(int lives){
        this.lives = lives;
    }

    /**
     * To set if the player is in the air
     * @param inTheAir to set if the player is in the air
     */
    public void setInTheAir(boolean inTheAir) {
        this.inTheAir = inTheAir;
    }

    /**
     * To set how many more ticks will the player will be in a state of invunerability
     * @param contGotHit to set how many more ticks will the player will be in a state of invunerability
     */
    public void setContGotHit(int contGotHit) {
        this.contGotHit = contGotHit;
    }

    /**
     * To set the direction of the player
     * @param direction to set the direction of the player
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * To set the remaining quantity of water the player has
     * @param water to set the remaining quantity of water the player has
     */
    public void setWater(int water) {
        this.water = water;
    }

    /**
     * 
     * @param left
     * @param right
     */
    public void setLimits (int left, int right) {
      this.leftLimit = left;
      this.rightLimit = right;
    }

    // Carga la información del objeto desde un string ------------------------------------------------------------------
    /**
     * To set the value of the position in the x axis and the width of the player from the file that was loaded
     * @param datos to set all the variables
     */
    @Override
    public void loadFromString(String[] datos) {
        this.x = Integer.parseInt(datos[0]);
        this.width = Integer.parseInt(datos[1]);
    }

    /**
     * To get all the variable that need to be stored in the file as a string
     * @return an <code>String</code> value with all the information of the variables
     */
    @Override
    public String intoString(){
        return (x +" " + width);
    }

    // tick y render ------------------------------------------------------------------

    @Override
    public void tick() {
        // moving player depending on flags
		if (getX() > 0 && (game.getKeyManager().lastLeft || game.getKeyManager().a)) {
			setX(getX() - 6);
			direction = -1;
		}
		if (getX() < 3000 && (game.getKeyManager().lastRight || game.getKeyManager().d)) {
			setX(getX() + 6);
			direction = 1;
		}
		
		// checks if the player is moving
		if ((game.getKeyManager().lastRight || game.getKeyManager().d) || (game.getKeyManager().lastLeft || game.getKeyManager().a))
			moving = true;
		else
			moving = false;
		
		// making the player jump
		if (game.getKeyManager().lastSpace && !inTheAir) {
			speedY = 36;
			inTheAir = true;
		}
		
		// checks if the player is on top of the last platform it collide with
		if (getX() + getWidth() <= getLeftLimit() || getX() >= getRightLimit()) {
			inTheAir = true;
		}
	
	
		// checks if the player is in the air to update its position in the y axis
		if (inTheAir) {
			y -= speedY;
			if (speedY > -20) {
				speedY -= 2;
			}
		} else {
			lastX = x;
			lastY = y;
		}
	
		// checks if the player is moving to animate its walking
        if (moving)
           xochiAnim.tick();
    }

    @Override
    public void render(Graphics g) {
		/**
		 * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
		 * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
		 * En este caso el jugador se dibujara en su respectiva 'x' y 'y' (dependiendo del caso)
		 */
		if (contGotHit > 0) {
			contGotHit -= 1;
			if (contGotHit % 5 == 0) {
				drawPlayer = !drawPlayer;
			}
		}
		if (drawPlayer) {
			if (moving)
				g.drawImage(xochiAnim.getCurrentFrame(), getX() - game.getRec().x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
			else
				g.drawImage(Assets.xochiIdle, getX() - game.getRec().x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
		}
    }
}
