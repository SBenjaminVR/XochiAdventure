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

    private int lives;
    private int water;
    private boolean inTheAir;

    /**
     * to create direction, width, height, and game and set the player is not moving
     * @param x to set the x of the player
     * @param y to set the y of the player
     * @param width to set the width of the player
     * @param speedX
     * @param height  to set the height of the player
     * @param game to set the game of the player
     */
    public Player(int x, int y, int width, int height, int speedX, int lives, Game game) {
        super(x, y, width, height, speedX, game);
        this.lives = lives;
        this.water = 100;
        this.inTheAir = false;
    }

    // GETS ------------------------------------------------------------------

    /**
     *
     * @return
     */
    public int getLives() {
        return lives;
    }

    /**
     * 
     * @return 
     */
    public boolean isInTheAir() {
        return inTheAir;
    }

    // SETS ------------------------------------------------------------------

    /**
     *
     * @param lives
     */
    public void setLives(int lives){
        this.lives = lives;
    }
    
    /**
     * 
     * @param inTheAir 
     */
    public void setInTheAir(boolean inTheAir) {
        this.inTheAir = inTheAir;
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
       if (game.getKeyManager().lastLeft || game.getKeyManager().a) {
          setX(getX() - 8);
       }
       if (game.getKeyManager().lastRight || game.getKeyManager().d) {
          setX(getX() + 8);
       }

       if (game.getKeyManager().lastUp) {
          y -= 8;
       }

       if (game.getKeyManager().lastDown) {
          y += 8;
       }

       if (game.getKeyManager().space && !inTheAir) {
          speedY = 40;
          inTheAir = true;
       }
       if (game.getKeyManager().z || game.getKeyManager().o) {
         //attack
       }

       if (inTheAir) {
         y -= speedY;
         if (speedY > -20) {
           speedY -= 2;
         }
       }

       // hacer que xochi ataque

        // checks that the object does not goes out of the bounds
        // if (getX() + getWidth() >= game.getWidth()) {
        //     setX(game.getWidth() - this.getWidth());
        // }
        // else if (getX() <= 0) {
        //     setX(0);
        // }
    }

    @Override
    public void render(Graphics g) {
      if (x < game.getPlayerX()) {
        g.drawImage(Assets.player, x, game.getPlayerY(), getWidth(), getHeight(), null);
      } else {
        g.drawImage(Assets.player, game.getPlayerX(), game.getPlayerY(), getWidth(), getHeight(), null);
      }

        // g.drawImage(Assets.comida, getX(), getY(), getWidth(), getHeight(), null);
        // g.drawImage(Assets.player, game.getWidth() / 2 - getWidth() / 2, game.getHeight() / 2 - getHeight() / 2, getWidth(), getHeight(), null);
        // g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
