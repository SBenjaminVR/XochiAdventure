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

	// to know all the ingredients that exist
    public enum Type {
        Tortillas,
        Queso,   	
        Chile,  	
        Tomate,  	
        Crema,
        Aceite,
        Cebolla
    }
    private Type tipo;	// to store which ingredient is the item

    /**
     * to create direction, width, height, speed in the x axis, and game of the food
     * @param x to set the x of the food
     * @param y to set the y of the food
     * @param width to set the width of the food
     * @param height to set the height of the food
     * @param game to set the game of the food
     */
    public Comida(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, 0, game);
        int max = 100;
        int min = 0;
        double numerito = (Math.random() * ((max - min) + 1)) + min;
        if (numerito < 25) {
            this.tipo = Type.Tortillas;
        }
        else if (numerito < 50) {
            this.tipo  = Type.Queso;
        }
        else if (numerito < 75) {
            this.tipo = Type.Chile;
        }
        else {
            this.tipo = Type.Tomate;
        }
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
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        /**
         * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
         * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
         * En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
         */
        switch (tipo) {
            case Tortillas:
                g.drawImage(Assets.ingredientesEnchiladas[0], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case Queso:
                g.drawImage(Assets.ingredientesEnchiladas[1], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case Chile:
                g.drawImage(Assets.ingredientesEnchiladas[2], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case Tomate:
                g.drawImage(Assets.ingredientesEnchiladas[3], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
        }
    }

    public void renderUI(Graphics g, int x, int y) {
        /**
         * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
         * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
         * En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
         */
        switch (tipo) {
            case Tortillas:
                g.drawImage(Assets.ingredientesEnchiladas[0], x, y, getWidth(), getHeight(), null);
                break;
            case Queso:
                g.drawImage(Assets.ingredientesEnchiladas[1], x, y, getWidth(), getHeight(), null);
                break;
            case Chile:
                g.drawImage(Assets.ingredientesEnchiladas[2], x, y, getWidth(), getHeight(), null);
                break;
            case Tomate:
                g.drawImage(Assets.ingredientesEnchiladas[3], x, y, getWidth(), getHeight(), null);
                break;
        }

    }

}
