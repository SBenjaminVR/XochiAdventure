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

    // private boolean recolectado;
    public enum Type {
        Tortillas,  // Drop que recupera toda la vida al jugador
        Queso,   // Drop que funciona como municion para recargar el disparo del jugador
        Chile,  // Drop que recupera lo equivalente a un golpe al jugador
        Tomate  // Drop que aumenta la resistencia del jugador
    }
    private Type tipo;

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
        // recolectado = false;
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

    // /**
    //  *
    //  * @return
    //  */
    // public boolean isRecolectado() {
    //     return recolectado;
    // }

    // SETS ------------------------------------------------------------------

    // /**
    //  *
    //  * @param recolectado
    //  */
    // public void setRecolectado(boolean recolectado) {
    //     this.recolectado = recolectado;
    // }


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
        // if (game.getPlayer().getX() < game.getPlayerX()) {
        //   g.drawImage(Assets.comida, x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
        // } else {
        //   g.drawImage(Assets.comida, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        // }
        switch (tipo) {
            case Tortillas:
                g.drawImage(Assets.ingredientes[0], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case Queso:
                g.drawImage(Assets.ingredientes[1], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case Chile:
                g.drawImage(Assets.ingredientes[2], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
            case Tomate:
                g.drawImage(Assets.ingredientes[3], (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
                break;
        }
    }

    public void renderUI(Graphics g, int x, int y) {
      /**
       * Como estamos simulando una camara que siga al jugador, tenemos que dibujar al jugador siempre en medio
       * pero vamos a tener un caso en el que no va a pasar esto: cuando el jugador esté cerca de las orillas del nivel
       * En este caso los powerups se dibujaran en su respectiva 'x' y 'y' (dependiendo del caso)
       */
      // hay que agregar una condicional para cuando este mero abajo del nivel, pero tenemos que acabar de diseñar el nivel para sacar bien las alturas
      // también hay que agregar una condicional para cuando esté hasta la mera derecha, pero al igual que la condicional de la "y", tenemos que terminar de diseñar bien los niveles para poder sacar bien las distancias
        // if (game.getPlayer().getX() < game.getPlayerX()) {
        //   g.drawImage(Assets.comida, x, (getY() - game.getRec().y), getWidth(), getHeight(), null);
        // } else {
        //   g.drawImage(Assets.comida, (getX() - game.getRec().x), (getY() - game.getRec().y), getWidth(), getHeight(), null);
        // }
        switch (tipo) {
            case Tortillas:
                g.drawImage(Assets.ingredientes[0], x, y, getWidth(), getHeight(), null);
                break;
            case Queso:
                g.drawImage(Assets.ingredientes[1], x, y, getWidth(), getHeight(), null);
                break;
            case Chile:
                g.drawImage(Assets.ingredientes[2], x, y, getWidth(), getHeight(), null);
                break;
            case Tomate:
                g.drawImage(Assets.ingredientes[3], x, y, getWidth(), getHeight(), null);
                break;
        }

    }

}
