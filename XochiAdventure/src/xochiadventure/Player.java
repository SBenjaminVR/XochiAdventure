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
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class Player extends Item{

    private int lives;					// to store the lives that the player has
    private int maxLives;				// to store the max lives the player can have
    private int water;					// to store how much water the player has
    private boolean inTheAir;			// to know if the player is in the air
    private int direction;				// to store the direction of the player
    private int contGotHit;				// to store how many more ticks the player is invunerable because they were hit
    private boolean drawPlayer;			// to know if the player should be drawn
    private Platform plat;              // to store the last platform the player was on to know when it is going to fall
    private Animation xochiAnim;		// to animate the player
    private Animation xochiAnimLeft;	// to animate the player
    private boolean moving;				// to know if the player is moving int the x axis

    /**
     * to create direction, width, height, speed in the x axis, and game
     * @param x to set the x of the player
     * @param y to set the y of the player
     * @param width to set the width of the player
     * @param height to set the height of the player
     * @param speedX to set the speed in the x axis of the player
     * @param lives to set the lives the player has
     * @param plat
     * @param game to set the game of the player
     */
    public Player(int x, int y, int width, int height, int speedX, int lives, Platform plat, Game game) {
        super(x, y, width, height, speedX, game);
        this.maxLives = lives;
        this.lives = maxLives;
        this.water = 100;
        this.inTheAir = true;
        this.direction = 1;
        this.contGotHit = 0;
        this.drawPlayer = true;
        this.plat = plat;
        moving = false;
        xochiAnim = new Animation(Assets.xochiAnim, 150);
        xochiAnimLeft = new Animation(Assets.xochiAnimLeft, 150);
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
     * To get how many more ticks will the player will be in a state of invunerability because they been hit
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
     * To know on which platform the player is currently standing
     * @return 
     */
    public Platform getPlat() {
        return plat;
    }

    // SETS ------------------------------------------------------------------

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
        this.drawPlayer = true;
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
     * To set the platform the player is currently standing on
     * @param plat 
     */
    public void setPlat(Platform plat) {
        this.plat = plat;
    }

    // tick y render ------------------------------------------------------------------

    @Override
    public void tick() {
        // moving player depending on flags
		if (getX() > 0 && (game.getKeyManager().lastLeft || game.getKeyManager().a)) {
			setX(getX() - getSpeedX());
            setDirection(-1);
		}
		if (getX() < game.getLevelWidth() - 100 && (game.getKeyManager().lastRight || game.getKeyManager().d)) {
			setX(getX() + getSpeedX());
            setDirection(1);
		}
		
		// checks if the player is moving
		if ((game.getKeyManager().lastRight || game.getKeyManager().d) || (game.getKeyManager().lastLeft || game.getKeyManager().a))
            moving = true;
		else
			moving = false;
		
		// making the player jump
		if (game.getKeyManager().lastSpace && !isInTheAir()) {
            setSpeedY(36);
            setInTheAir(true);
		}
		
		// checks if the player is on top of the last platform it collide with
		if (getX() + getWidth() <= getPlat().getX() || getX() >= getPlat().getX() + getPlat().getWidth()) {
            setInTheAir(true);
		}
	
	
		// checks if the player is in the air to update its position in the y axis
		if (isInTheAir()) {
            setY(getY() - getSpeedY());
			if (getSpeedY() > -20) {
                setSpeedY(getSpeedY() - 2);
            }
        }
	
		// checks if the player is moving to animate its walking
        if (moving)
            if (getDirection() == 1) {
                xochiAnim.tick();
            }
            else {
                xochiAnimLeft.tick();
            }
                
    }

    @Override
    public void render(Graphics g) {
		/**
		 * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
		 * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
		 * En este caso el jugador se dibujara en su respectiva 'x' y 'y' (dependiendo del caso)
		 */

        // Checks if the player is in an state of invunerability
        // It makes the player flicker when it gets hit or falls of the level
		if (contGotHit > 0) {
			contGotHit -= 1;
			if (contGotHit % 5 == 0) {
				drawPlayer = !drawPlayer;
			}
        }
        
        // Checks if the player needs to be drawn
		if (drawPlayer) {

            // Checks if the player if moving and in which direction it is facing to know which image to draw/animate
			if (moving) {
                if (getDirection() == 1)
				    g.drawImage(xochiAnim.getCurrentFrame(), getX() - game.getRec().x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
                else
                    g.drawImage(xochiAnimLeft.getCurrentFrame(), getX() - game.getRec().x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
            }
            else {
                if (getDirection() == 1)
				    g.drawImage(Assets.xochiIdle, getX() - game.getRec().x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
                else
                    g.drawImage(Assets.xochiIdleLeft, getX() - game.getRec().x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
            }
		}
    }
}
