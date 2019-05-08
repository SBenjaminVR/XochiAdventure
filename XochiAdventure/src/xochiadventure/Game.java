/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static xochiadventure.Assets.titleScreen;

// enum to navigate all the screens that the game has
enum Screen {
    TITLESCREEN,
    STORY,
    MENU,
    OPTIONS,
    RECIPIES,
    LEVEL,
    CONTROLS
}

// enum to navigate through all the options in the Main menu
enum MenuOpt {
    ONE,
    TWO,
    THREE,
    OPTIONS,
    CONTROLS,
    RECIPIES,
}

// enum to navigate through all the options in the Options menu
enum OptOpt {
    DALTONICO,
    SONIDO,
    BRILLO
}

enum PauseMenu {
    CONTINUE_LEVEL,
    EXIT
}

/**
 *
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class Game implements Runnable {

    // Screen's variables
    private BufferStrategy bs;                              // to have several buffers when displaying
    private Graphics g;                                     // to paint objects
    private Display display;                                // to display in the game
    String title;                                           // title of the window
    private int width;                                      // width of the window
    private int height;                                     // height of the window
    private Thread thread;                                  // thread to create the game
    private boolean running;                                // to set the game
    private int brightness;                                 // to set the brightness of the game

    // Game logic variables
    private Player player;                                  // to use a player
    private KeyManager keyManager;                          // to manage the keyboard
    private boolean endGame;                                // to know when to end the game
    private boolean pauseGame;                              // flag to know if the game is paused
    private Font texto;                                     // to change the font of string drawn in the screen
    private Shot shot;                                      // to have a missile to shoot
    private Rectangle rec;                                  // to store the rectangle that checks which sprites are going to be drawn
    private Rectangle fuente;                               // to store the position of the fuente
    private boolean soundOn;
    private boolean effectsOn;                              //to store the effects of sound
    private int nivel;                                      // to store in which level you are
    private int levelWidth;                                 // to store the width of the level
    private int levelHeight;                                // to store the height of the level
    private boolean menuMusicPlaying;

    // Linked lists
    private LinkedList<Platform> platforms;                 // to store all the platforms
    private LinkedList<Enemy> chiles;                       // to store all the enemies
    private LinkedList<PowerUps> powerups;                  // to store all the powerups
    private LinkedList<Comida> comidas;                     // to store all the food
    private LinkedList<Comida> recolectado;                 // to store the recolected food
    private LinkedList<Shot> disparos;                      // to store all the shot bubbles
    private LinkedList<Pico> picos;                         // to store all the spikes
    private LinkedList<Letrero> letreros;                   // to store all letreros

    // Menu navigation variables
    private Screen screen;                                  // to store in which screen you are
    private MenuOpt menOpt;                                 // to store in which option in the main menu screen you are
    private OptOpt optOpt;                                  // to store in which option in the options screen you are
    private PauseMenu pauseOpt;                             // to store in which option in the pause screen you are
    private int currentRecipePage = 1;

    // UI
    private int playerX;                                    // to store the position in which the player will be drawn
    private int playerY;                                    // to store the position in which the player will be drawn

    private SoundClip confirmSound;
    private boolean hasPlayedWinSnd;

    /**
     * to create title, width, height, keyManager, bricks,
     * nombreArchivo, and texto and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        running = false;
        keyManager = new KeyManager();
        
        texto = new Font("Font", 2, 32);
        screen = Screen.TITLESCREEN;

        // Initialization on linked lists
        chiles = new LinkedList<Enemy>();
        powerups = new LinkedList<PowerUps>();
        platforms = new LinkedList<Platform>();
        comidas = new LinkedList<Comida>();
        recolectado = new LinkedList<Comida>();
        disparos = new LinkedList<Shot>();
        picos = new LinkedList<Pico>();
        letreros = new LinkedList<Letrero>();

        rec = new Rectangle(0, 0, getWidth(), getHeight());
        fuente = new Rectangle(0, 0, 300, 300);
        hasPlayedWinSnd = false;
        brightness = 3;
        menuMusicPlaying = false;
        effectsOn = true;
    }

    // GETS ------------------------------------------------------------------------------------------------------------------------------------

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To get the list of all the chiles
     *
     * @return an <code>LinkedList<Enemey></code> list with all the chiles
     */
    public LinkedList<Enemy> getChiles() {
        return chiles;
    }

    /**
     * To get the player
     *
     * @return an <code>Player</code> player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * To check if the game has ended
     *
     * @return an <code>boolean</code> value of the state of the game
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**
     *  To get the position in the x axis of the player where it is going to be drawn
     * @return an <code>int</code> valuer of the x position
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     *  To get the position in the y axis of the player where it is going to be drawn
     * @return an <code>int</code> value of the y position
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * To get the rectangle that is used to know which sprites need to be drawned
     * @return an <code>Rectangle</code> value with the rectangle
     */
    public Rectangle getRec() {
        return rec;
    }

    /**
     * To get the key manager
     * @return an <code>KeyManager</code> value with the key manager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
<<<<<<< HEAD
     * To get the width of the current level
     * @return an <code>int</code> value with the width of the level
=======
     * To get the current level width
     * @return 
>>>>>>> 427efb1fd2378f5b7a78c369d5447e2e13eb1b3e
     */
    public int getLevelWidth() {
        return levelWidth;
    }

    /**
<<<<<<< HEAD
     * To get the height of the current level
     * @return an <code>int</code> value with the height of the level
     */
    public int getLevelHeight() {
        return levelHeight;
    }
    
    /**
     * To get the current level
     * @return an <code>int</code> value with the current level
=======
     * To get the current level
     * @return 
>>>>>>> 427efb1fd2378f5b7a78c369d5447e2e13eb1b3e
     */
    public int getNivel() {
        return nivel;
    }

    /**
<<<<<<< HEAD
     * To get the fountain
     * @return an <code>int</code> value with the fountain
=======
     * To get the current level fountain area of effect
     * @return 
>>>>>>> 427efb1fd2378f5b7a78c369d5447e2e13eb1b3e
     */
    public Rectangle getFuente() {
        return fuente;
    }

    /**
<<<<<<< HEAD
     * To get the list of all
=======
     * To get the food of the current level
>>>>>>> 427efb1fd2378f5b7a78c369d5447e2e13eb1b3e
     * @return 
     */
    public LinkedList<Comida> getComidas() {
        return comidas;
    }

    /**
     * To get the signs of the current level
     * @return 
     */
    public LinkedList<Letrero> getLetreros() {
        return letreros;
    }

    /**
     * To get the current level's spikes
     * @return 
     */
    public LinkedList<Pico> getPicos() {
        return picos;
    }

    /**
     * To get the current level's platforms
     * @return 
     */
    public LinkedList<Platform> getPlatforms() {
        return platforms;
    }

    /**
     * To get the current level's powerups
     * @return 
     */
    public LinkedList<PowerUps> getPowerups() {
        return powerups;
    }

    /**
     * To get the current level's shots
     * @return 
     */
    public LinkedList<Shot> getDisparos() {
        return disparos;
    }

    // SETS ------------------------------------------------------------------------------------------------------------------------------------

    /**
     * To set if the game is ended
     *
     * @param endGame to set the state of the game
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * To set the current level width
     * @param levelWidth 
     */
    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    /**
     * To set the current level height
     * @param levelHeight 
     */
    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }

    /**
     * To set the player object
     * @param player 
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * To set the player's x position
     * @param playerX 
     */
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * To set the player's y position
     * @param playerY 
     */
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    // FUNCTIONS ------------------------------------------------------------------------------------------------------------------------------------

    /**
     * To load the chosen level
     * @param txt to know which level to load
     */
    private void loadLevel() throws SQLException{

      switch(nivel){
        case 1:
          fuente.x = 1400;
          fuente.y = 500;
          chiles.add(new Enemy(1350, 200, 50, 50, 1, 5, 1300, 1550, this));
          chiles.add(new Enemy(1750, 200, 50, 50, -1, 5, 1550, 1800, this));
          chiles.add(new Enemy(955, 1300, 50, 50, 1, 5, 950, 1450, this));
          chiles.add(new Enemy(2100, 1300, 50, 50, -1, 5, 1650, 2150, this));
          chiles.add(new Enemy(955, 1850, 50, 50, 1, 5, 950, 1550, this));
          chiles.add(new Enemy(2100, 1850, 50, 50, -1, 5, 1550, 2150, this));

          // grandes 3
          platforms.add(new Platform(0,     250, 500, 100, this));
          platforms.add(new Platform(1300,  250, 500, 100, this));
          platforms.add(new Platform(2600,  250, 500, 100, this));

          // chicas 2
          platforms.add(new Platform(650,   500, 500, 100, this));
          platforms.add(new Platform(1950,  500, 500, 100, this));

          // 2500 5
          platforms.add(new Platform(300,   800, 500, 100, this));
          platforms.add(new Platform(800,   800, 500, 100, this));
          platforms.add(new Platform(1300,  800, 500, 100, this));
          platforms.add(new Platform(1800,  800, 500, 100, this));
          platforms.add(new Platform(2300,  800, 500, 100, this));

          // chicas 3
          platforms.add(new Platform(0,     1100, 150, 30, this));
          platforms.add(new Platform(1475,  1100, 150, 30, this));
          platforms.add(new Platform(2950,  1100, 150, 30, this));

          // grandes 4
          platforms.add(new Platform(0,     1350, 500, 100, this));
          platforms.add(new Platform(950,   1350, 500, 100, this));
          platforms.add(new Platform(1650,  1350, 500, 100, this));
          platforms.add(new Platform(2600,  1350, 500, 100, this));

          // chicas 2
          platforms.add(new Platform(650,   1650, 150, 30, this));
          platforms.add(new Platform(2300,  1650, 150, 30, this));

          // grandes 5
          platforms.add(new Platform(0,     1900, 500, 100, this));
          platforms.add(new Platform(950,   1900, 500, 100, this));
          platforms.add(new Platform(1450,  1900, 500, 100, this));
          platforms.add(new Platform(1650,  1900, 500, 100, this));
          platforms.add(new Platform(2600,  1900, 500, 100, this));

          // comidas
          comidas.add(new Comida(225, 200, 50, 50, 0, this));
          comidas.add(new Comida(1525, 200, 50, 50, 1, this));
          comidas.add(new Comida(2925, 200, 50, 50, 2, this));
          comidas.add(new Comida(1525, 1050, 50, 50, 3, this));
          comidas.add(new Comida(225, 1850, 50, 50, 4, this));
          comidas.add(new Comida(1525, 1850, 50, 50, 5, this));
          comidas.add(new Comida(2925, 1850, 50, 50, 6, this));

          // letreros
          letreros.add(new Letrero(300, 730, 70, 70, false, this));
          letreros.add(new Letrero(2730, 730, 70, 70, false, this));
          letreros.add(new Letrero(430, 1830, 70, 70, true, this));
          letreros.add(new Letrero(950, 1830, 70, 70, true, this));
          letreros.add(new Letrero(2080, 1830, 70, 70, true, this));
          letreros.add(new Letrero(2600, 1830, 70, 70, true, this));

          player = new Player (1475, 650, 100, 100, 6, 4, platforms.get(0), this);
          playerX = getWidth() / 2 - player.getWidth() / 2;
          playerY = getHeight() / 2 - player.getHeight() / 2;
          levelWidth = 3100;
          levelHeight = 2100;

          break;

        case 2:

          fuente.x = 1400;
          fuente.y = 500;
          // Chiles
          
          chiles.add(new Enemy(10, 200, 50, 50, 1, 5, 0, 500, this));
          chiles.add(new Enemy(3040, 200, 50, 50, -1, 5, 2600, 3100, this));
          chiles.add(new Enemy(310, 750, 50, 50, 1, 5, 300, 800, this));
          chiles.add(new Enemy(2740, 750, 50, 50, -1, 5, 2300, 2800, this));
          chiles.add(new Enemy(810, 1050, 50, 50, 1, 5, 800, 1300, this));
          chiles.add(new Enemy(2240, 1050, 50, 50, -1, 5, 1800, 2300, this));
          chiles.add(new Enemy(10, 1650, 50, 50, 1, 5, 0, 400, this));
          chiles.add(new Enemy(3040, 1650, 50, 50, -1, 5, 2700, 3100, this));

          // Plataformas
          platforms.add(new Platform(0, 250, 500, 100, this));
          platforms.add(new Platform(1300, 250, 500, 100, this));
          platforms.add(new Platform(2600, 250, 500, 100, this));

          platforms.add(new Platform(650, 500, 500, 100, this));
          platforms.add(new Platform(1950, 500, 500, 100, this));

          platforms.add(new Platform(-400, 800, 500, 100, this));
          platforms.add(new Platform(300, 800, 500, 100, this));
          // platforms.add(new Platform(1000, 800, 1100, 100, this));
          platforms.add(new Platform(1000, 800, 500, 100, this));
          platforms.add(new Platform(1500, 800, 500, 100, this));
          platforms.add(new Platform(1600, 800, 500, 100, this));
          platforms.add(new Platform(2300, 800, 500, 100, this));
          platforms.add(new Platform(3000, 800, 500, 100, this));

          platforms.add(new Platform(800, 1100, 500, 100, this));
          platforms.add(new Platform(1800, 1100, 500, 100, this));

          platforms.add(new Platform(550, 1400, 150, 30, this));
          platforms.add(new Platform(1475, 1400, 150, 30, this));
          platforms.add(new Platform(2400, 1400, 150, 30, this));

          platforms.add(new Platform(0, 1700, 400, 100, this));
          platforms.add(new Platform(575, 1700, 300, 100, this));
          platforms.add(new Platform(1050, 1700, 300, 100, this));
          platforms.add(new Platform(1500, 1700, 100, 20, this));
          platforms.add(new Platform(1750, 1700, 300, 100, this));
          platforms.add(new Platform(2225, 1700, 300, 100, this));
          platforms.add(new Platform(2700, 1700, 400, 100, this));

          // Comidas

          comidas.add(new Comida(1525, 200, 50, 50, 0, this));
          comidas.add(new Comida(2900, 200, 50, 50, 1, this));
          comidas.add(new Comida(200, 825, 50, 50, 2, this));
          comidas.add(new Comida(2900, 825, 50, 50, 3, this));
          comidas.add(new Comida(50, 1650, 50, 50,  4, this));
          comidas.add(new Comida(1525, 1650, 50, 50, 5, this));

          // letreros
          letreros.add(new Letrero(30, 730, 70, 70, false, this));
          letreros.add(new Letrero(3000, 730, 70, 70, false, this));
          letreros.add(new Letrero(330, 1630, 70, 70, true, this));
          letreros.add(new Letrero(1280, 1630, 70, 70, true, this));
          letreros.add(new Letrero(1750, 1630, 70, 70, true, this));
          letreros.add(new Letrero(2700, 1630, 70, 70, true, this));

          // picos
          picos.add(new Pico(575, 1660, 50, 50, "u", this));
          picos.add(new Pico(825, 1660, 50, 50, "u", this));
          picos.add(new Pico(1175, 1660, 50, 50, "u", this));
          picos.add(new Pico(1875, 1660, 50, 50, "u", this));
          picos.add(new Pico(2225, 1660, 50, 50, "u", this));
          picos.add(new Pico(2475, 1660, 50, 50, "u", this));

          player = new Player (1475, 650, 100, 100, 6, 4, platforms.get(0),  this);
          playerX = getWidth() / 2 - player.getWidth() / 2;
          playerY = getHeight() / 2 - player.getHeight() / 2;
          levelWidth = 3100;
          levelHeight = 1900;
          break;

        case 3:
          // 3300
          fuente.x = 1500;
          fuente.y = 600;

          // chiles
          chiles.add(new Enemy(240, 200, 50, 50, 1, 5, 225, 825, this));
          chiles.add(new Enemy(1350, 200, 50, 50, -1, 5, 825, 1425, this));
          chiles.add(new Enemy(1890, 200, 50, 50, 1, 5, 1875, 2475, this));
          chiles.add(new Enemy(3060, 200, 50, 50, -1, 5, 2475, 3075, this));
          chiles.add(new Enemy(460, 850, 50, 50, 1, 5, 450, 950, this));
          chiles.add(new Enemy(2790, 850, 50, 50, -1, 5, 2350, 2850, this));
          chiles.add(new Enemy(2000, 1400, 50, 50, -1, 5, 1975, 2775, this));
          chiles.add(new Enemy(0, 1950, 50, 50, 1, 5, 0, 1000, this));
          chiles.add(new Enemy(3250, 1950, 50, 50, -1, 5, 2300, 3300, this));

          // plataformas
          platforms.add(new Platform(225, 250, 500, 100, this));
          platforms.add(new Platform(725, 250, 500, 100, this));
          platforms.add(new Platform(925, 250, 500, 100, this));
          platforms.add(new Platform(1875, 250, 500, 100, this));
          platforms.add(new Platform(2375, 250, 500, 100, this));
          platforms.add(new Platform(2575, 250, 500, 100, this));

          platforms.add(new Platform(1575, 500, 150, 30, this));

          platforms.add(new Platform(1300, 700, 150, 30, this));
          platforms.add(new Platform(1850, 700, 150, 30, this));

          
          platforms.add(new Platform(300, 950, 50, 50, this));
          platforms.add(new Platform(450, 900, 500, 100, this));
          platforms.add(new Platform(1150, 900, 50, 50, this));
          platforms.add(new Platform(1400, 900, 500, 100, this));
          platforms.add(new Platform(2100, 900, 50, 50, this));
          platforms.add(new Platform(2350, 900, 500, 100, this));
          platforms.add(new Platform(2950, 950, 50, 50, this));

          platforms.add(new Platform(1100, 1150, 150, 30, this));
          platforms.add(new Platform(2050, 1150, 150, 30, this));

          platforms.add(new Platform(50, 1500, 50, 50, this));
          platforms.add(new Platform(400, 1500, 50, 50, this));
          platforms.add(new Platform(575, 1450, 500, 100, this));
          platforms.add(new Platform(875, 1450, 500, 100, this));
          platforms.add(new Platform(1975, 1450, 800, 100, this));
          platforms.add(new Platform(2850, 1700, 150, 30, this));

          platforms.add(new Platform(1575, 1650, 150, 30, this));

          platforms.add(new Platform(0, 2000, 500, 100, this));
          platforms.add(new Platform(500, 2000, 500, 100, this));
          platforms.add(new Platform(1250, 2000, 50, 50, this));
          platforms.add(new Platform(1600, 2000, 100, 100, this));
          platforms.add(new Platform(2000, 2000, 50, 50, this));
          platforms.add(new Platform(2300, 2000, 500, 100, this));
          platforms.add(new Platform(2800, 2000, 500, 100, this));

          // comidas
          comidas.add(new Comida(800, 200, 50, 50, 0, this));
          comidas.add(new Comida(300, 750, 50, 50, 1, this));
          comidas.add(new Comida(2950, 750, 50, 50, 2, this));
          comidas.add(new Comida(500, 1950, 50, 50, 3, this));
          comidas.add(new Comida(2800, 1950, 50, 50, 4, this));

          comidas.add(new Comida(1625, 1950, 50, 50, 5, this));

          // letreros
          letreros.add(new Letrero(225, 180, 70, 70, false, this));
          letreros.add(new Letrero(3005, 180, 70, 70, false, this));
          letreros.add(new Letrero(450, 830, 70, 70, false, this));
          letreros.add(new Letrero(2780, 830, 70, 70, false, this));
          letreros.add(new Letrero(930, 1930, 70, 70, true, this));
          letreros.add(new Letrero(2300, 1930, 70, 70, true, this));

          // picos
          picos.add(new Pico(300, 910, 50, 50, "u", this));
          picos.add(new Pico(300, 990, 50, 50, "d", this));
          picos.add(new Pico(260, 950, 50, 50, "l", this));
          picos.add(new Pico(340, 950, 50, 50, "r", this));

          picos.add(new Pico(1150, 860, 50, 50, "u", this));
          picos.add(new Pico(1150, 940, 50, 50, "d", this));
          picos.add(new Pico(1110, 900, 50, 50, "l", this));
          picos.add(new Pico(1190, 900, 50, 50, "r", this));

          picos.add(new Pico(2100, 860, 50, 50, "u", this));
          picos.add(new Pico(2100, 940, 50, 50, "d", this));
          picos.add(new Pico(2060, 900, 50, 50, "l", this));
          picos.add(new Pico(2140, 900, 50, 50, "r", this));

          picos.add(new Pico(2950, 910, 50, 50, "u", this));
          picos.add(new Pico(2950, 990, 50, 50, "d", this));
          picos.add(new Pico(2910, 950, 50, 50, "l", this));
          picos.add(new Pico(2990, 950, 50, 50, "r", this));

          picos.add(new Pico(50, 1460, 50, 50, "u", this));
          picos.add(new Pico(50, 1540, 50, 50, "d", this));
          picos.add(new Pico(10, 1500, 50, 50, "l", this));
          picos.add(new Pico(90, 1500, 50, 50, "r", this));

          picos.add(new Pico(400, 1460, 50, 50, "u", this));
          picos.add(new Pico(400, 1540, 50, 50, "d", this));
          picos.add(new Pico(360, 1500, 50, 50, "l", this));
          picos.add(new Pico(440, 1500, 50, 50, "r", this));

          player = new Player (1600, 700, 100, 100, 6, 4, platforms.get(0), this);
          playerX = getWidth() / 2 - player.getWidth() / 2;
          playerY = getHeight() / 2 - player.getHeight() / 2;
          levelWidth = 3300;
          levelHeight = 2200;
          break;
      }
      
      endGame = false;
      pauseGame = false;
    }

    /**
     *  To unload a level
     */
    private void unloadLevel() {
      player = null;
      chiles.clear();
      platforms.clear();
      comidas.clear();
      powerups.clear();
      recolectado.clear();
      disparos.clear();
      picos.clear();
      letreros.clear();
    }

    // tick and render ------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Tick
     * Function that is run every frame where game functionality is performed
     * @throws SQLException 
     */
    private void tick() throws SQLException {
        // ticks key manager
        keyManager.tick();

        // checks in which screen you are
        switch(screen) {

            // Tttle screen ------------------------------------------------------------------
            case TITLESCREEN:

                // Checks if the user presses enter to advance to the next screen
                if (keyManager.enter) {
                    screen = Screen.STORY;
                }
                break;

            case STORY:
                // Checks if the user presses enter to advance to the next screen
                if (keyManager.enter) {
                  screen = Screen.MENU;
                  menOpt = MenuOpt.OPTIONS;
                }
                break;

            // Main menu screen ------------------------------------------------------------------
            case MENU:
                if (!menuMusicPlaying && soundOn) {
                    Assets.mainMenu.play();
                    menuMusicPlaying = true;
                }
                // checks if the down arrow key is pressed
                if (keyManager.down) {
                    // checks to where you are navigating in the menu
                    switch(menOpt){
                        case OPTIONS:
                            menOpt = MenuOpt.RECIPIES;
                            break;
                        case RECIPIES:
                            menOpt = MenuOpt.CONTROLS;
                            break;
                        case CONTROLS:
                            menOpt = MenuOpt.OPTIONS;
                            break;
                        case ONE:
                            menOpt = MenuOpt.TWO;
                            break;
                        case TWO:
                            menOpt = MenuOpt.THREE;
                            break;
                        case THREE:
                            menOpt = MenuOpt.ONE;
                            break;
                    }
                }
                //Checks if the up arrow key is pressed
                if (keyManager.up) {
                    // checks to where you are navigating in the menu
                    switch(menOpt){
                        case OPTIONS:
                            menOpt = MenuOpt.CONTROLS;
                            break;
                        case RECIPIES:
                            menOpt = MenuOpt.OPTIONS;
                            break;
                        case CONTROLS:
                            menOpt = MenuOpt.RECIPIES;
                            break;
                        case ONE:
                            menOpt = MenuOpt.THREE;
                            break;
                        case TWO:
                            menOpt = MenuOpt.ONE;
                            break;
                        case THREE:
                            menOpt = MenuOpt.TWO;
                            break;
                    }
                }

                //Checks if the left or right arrow key is pressed
                if (keyManager.left || keyManager.right) {
                    // checks to where you are navigating in the menu
                    switch (menOpt) {
                        case OPTIONS:
                            menOpt = MenuOpt.ONE;
                            break;
                        case RECIPIES:
                            menOpt = MenuOpt.TWO;
                            break;
                        case CONTROLS:
                            menOpt = MenuOpt.THREE;
                            break;
                        case ONE:
                            menOpt = MenuOpt.OPTIONS;
                            break;
                        case TWO:
                            menOpt = MenuOpt.RECIPIES;
                            break;
                        case THREE:
                            menOpt = MenuOpt.CONTROLS;
                            break;
                    }
                }

                // Checks to which screen you are moving to
                if (keyManager.enter) {
                    
                    if (menuMusicPlaying) {
                        Assets.mainMenu.stop();
                        menuMusicPlaying = false;
                    }
                    switch(menOpt) {
                        case OPTIONS:
                            if (soundOn) {
                                Assets.selectSnd.play();
                            }
                            screen = Screen.OPTIONS;
                            optOpt = OptOpt.DALTONICO;
                            break;
                        case ONE:
                            if (soundOn) {
                                Assets.selectSnd.play();
                                Assets.level1Music.play();
                            }
                            //carga nivel 1
                            nivel = 1;
//                            DBFunctions.loadLevelFromDB(nivel, this);
                            loadLevel();
                            Assets.background = ImageLoader.loadImage("/images/niveles/nivel_1.png");
                            screen = Screen.LEVEL;
                                break;
                        case TWO:
                            if (soundOn) {
                                Assets.selectSnd.play();
                                Assets.level2Music.play();
                            }
                            //carga nivel2
                            nivel = 2;
//                            DBFunctions.loadLevelFromDB(nivel, this);
                            loadLevel();
                            Assets.background = ImageLoader.loadImage("/images/niveles/nivel_2.png");
                            screen = Screen.LEVEL;
                                break;
                        case THREE:
                            if (soundOn) {
                                Assets.selectSnd.play();
                                Assets.level3Music.play();
                            }
                            //carga nivel3
                            nivel = 3;
//                            DBFunctions.loadLevelFromDB(nivel, this);
                            loadLevel();
                            Assets.background = ImageLoader.loadImage("/images/niveles/nivel_3.png");
                            screen = Screen.LEVEL;
                                break;
                        case RECIPIES:
                            if (soundOn) {
                                Assets.selectSnd.play();
                            }
                            screen = Screen.RECIPIES;
                                break;
                       case CONTROLS:
                           if (soundOn) {
                                Assets.selectSnd.play();
                            }
                           screen = Screen.CONTROLS;
                                break;
                    }
                }

                break;

            // Options screen ------------------------------------------------------------------
            case OPTIONS:
                if (keyManager.back) {
                    screen = Screen.MENU;
                }

                if (keyManager.down) {
                    switch (optOpt) {
                        case DALTONICO:
                            optOpt = OptOpt.SONIDO;
                            break;
                        case SONIDO:
                            optOpt = OptOpt.BRILLO;
                            break;
                        case BRILLO:
                            optOpt = OptOpt.DALTONICO;
                            break;
                    }
                }

                if (keyManager.up) {
                    switch (optOpt) {
                        case DALTONICO:
                            optOpt = OptOpt.BRILLO;
                            break;
                        case SONIDO:
                            optOpt = OptOpt.DALTONICO;
                            break;
                        case BRILLO:
                            optOpt = OptOpt.SONIDO;
                            break;
                    }
                }

                if (keyManager.enter) {
                    switch (optOpt) {
                        case DALTONICO:
                            effectsOn = !effectsOn; 
                            break;
                        case SONIDO:
                            soundOn = !soundOn;
                            break;
                        default:
                            break;
                    }
                }

                // Checks that you are on the brightness setting
                if (optOpt == OptOpt.BRILLO) {
                    if (keyManager.left) {
                        // disminuye el brillo
                        if (brightness > 1) {
                            brightness--;
                        }
                    } else if (keyManager.right) {
                        // aumenta el brillo
                        if (brightness < 5) {
                            brightness++;
                        }
                    }
                }

            // Recipies screen ------------------------------------------------------------------
            case RECIPIES:
                
                if (keyManager.back) {
                    screen = Screen.MENU;                    
                }
                if (keyManager.right) {
                    if (currentRecipePage < 3) {
                        currentRecipePage++;
                    }
                }
                if (keyManager.left) {
                    if (currentRecipePage > 1) {
                        currentRecipePage--;
                    }
                }
               
                break;

            // Controls screen ------------------------------------------------------------------
            case CONTROLS:
                if (keyManager.back) {
                    screen = Screen.MENU;
                }
                break;

            // Level screen ------------------------------------------------------------------
            case LEVEL:
                hasPlayedWinSnd = false;

                // checks if the escape key was pressed to pause or unpause the game
                if (keyManager.pause && !endGame) {
                  pauseGame = !pauseGame;
                  pauseOpt = PauseMenu.CONTINUE_LEVEL;
                }

                // checks if the backspace key was pressed to return to the main menu
                if (keyManager.back) {
                    Assets.level1Music.stop();
                    Assets.level2Music.stop();
                    Assets.level3Music.stop();
                  unloadLevel();
                  screen = Screen.MENU;
                } else {
                  if (!pauseGame && !endGame) {

                    // se tickea al jugador
                    player.tick();

                    /**
                     * Como el rec se encarga de ver que sprite se van a dibujar en la pantalla, este sigue al jugador,
                     * por lo que hay que checar dos condiciones:
                     * - que el jugador esté en las orillas del nivel
                     * - que el jugador no esté en las orillas del nivel
                     * En el caso que la primera condicional se cumpla, solo actualizamos la 'y' del rec para que se pueda dibujar todo
                     * En el caso que no se actualizan la 'x' y la 'y' del rec para que así pueda seguir al jugador
                     */
                    if ((player.getX() < playerX || player.getX() + player.getWidth()> levelWidth - getPlayerX()) && player.getY() + player.getHeight() > levelHeight - 100 - getPlayerY()) {
                      rec.setRect(rec.x, rec.y, getWidth(), getHeight());
                    } else if (player.getX() < playerX || player.getX() + player.getWidth()> levelWidth - getPlayerX()) {
                      rec.setRect(rec.x, player.getY() - playerY, getWidth(), getHeight());
                    } else if (player.getY() + player.getHeight() > levelHeight - 100 - getPlayerY()) {
                      rec.setRect(player.getX() - playerX, rec.y, getWidth(), getHeight());
                    } else {
                      rec.setRect(player.getX() - playerX, player.getY() - playerY, getWidth(), getHeight());
                    }

                    // checks if the player is in the fountain so it can regain water
                    if (fuente.intersects(player.getPerimetro()) && player.getWater() < 100) {
                      player.setWater(player.getWater() + 1);
                    }

                    // checks if the player collides with a spike
                    for (int i = 0; i < picos.size(); i++) {
                      Pico pi = picos.get(i);
                      if (pi.intersectaJugador(getPlayer()) && player.getContGotHit() == 0) {
                        player.setLives(player.getLives() - 1);
                        if (effectsOn)
                            Assets.hurtSnd.play();
                        player.setContGotHit(60);
                      }
                    }

                    // checks if the player can shoot a bubble and if one the keyw for doing it was pressed
                    if (player.getWater() > 0 && (getKeyManager().z || getKeyManager().o)) {
          						if (effectsOn) {
          							Assets.shootSnd.play();
          						}
                      // checks in which direction the player is moving to know in which direction shoot the bubble
                      if (player.getDirection() == 1) {
                        // attack to the right
                        disparos.add(new Shot(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2, 50, 50, 12, 1, this));
                      } else {
                        // attack to the left
                        disparos.add(new Shot(player.getX(), player.getY() + player.getHeight() / 2, 50, 50, 12, -1, this));
                      }
                      player.setWater(getPlayer().getWater() - 10);
                    }

                    // bubbles are ticked
                    for (int j = 0; j < disparos.size(); j++) {
                      Shot disp = disparos.get(j);
                      disp.tick();
                      if (disp.getX() + disp.getWidth() <= 0 || disp.getX() >= 3100) {
                        disparos.remove(j);
                      }
                    }


                    // chiles are ticke
                    for (int i  = 0; i < chiles.size(); i++) {
                      Enemy chile = chiles.get(i);
                      chile.tick();

                      // se checa que los disparos colisionen con los chiles
                      for (int j = 0; j < disparos.size(); j++) {
                        Shot disp = disparos.get(j);

                        // si colisionan elimina el chile y el disparo, aparte de checar si el chile soltará algún power up o no
                        if (disp.intersectaChile(chile)) {
                          chiles.remove(i);
                          disparos.remove(j);
                          int max = 100;
                          int min = 0;
                          double numerito = (Math.random() * ((max - min) + 1)) + min;
                          if (numerito < 50) {
                            powerups.add(new PowerUps(chile.getX(), chile.getY(), 50, 50, this));
                          }
                        }
                      }

                      if (chile.intersectaJugador(player) && player.getContGotHit() == 0) {
                        // quitarle vida al jugador
                        player.setLives(player.getLives() - 1);
                        if (effectsOn)
                            Assets.hurtSnd.play();
                        player.setContGotHit(60);
                      }
                    }

                    // se tickea a los powerups
                    for (int i  = 0; i < powerups.size(); i++) {
                      PowerUps power = powerups.get(i);
                      power.tick();
                      if (power.intersectaJugador(player)) {
                          switch (power.getType()) {
                            case ATOLE:
                              // Recover all of the player hp/lives
                              getPlayer().setLives(getPlayer().getMaxLives());
                              if (effectsOn)
                                Assets.atoleSnd.play();
                              powerups.remove(i);
                              break;
                            case AGUA:
                              // Refill a little bit the players ammo
                              getPlayer().setWater(getPlayer().getWater() + 25);
                              if (getPlayer().getWater() > 100) {
                                getPlayer().setWater(100);
                              }
                              powerups.remove(i);
                              break;

                            case DULCE:
                              // Recover 1 life
                              if (getPlayer().getLives() < getPlayer().getMaxLives())
                                  getPlayer().setLives(getPlayer().getLives() + 1);
                              if (effectsOn)
                                Assets.dulceSnd.play();
                              powerups.remove(i);
                              break;
                          }
                      }
                    }

                    // se checa si el jugador está en el aire. si sí lo está se checa si ha colisionado con alguna plataforma
                    if (player.isInTheAir()) {
                      for (int i  = 0; i < platforms.size(); i++) {
                          Platform platf = platforms.get(i);
                          // platf.tick();
                          if (platf.intersectaJugador(player)) {
                            if (player.getSpeedY() <= 0) {
                              player.setInTheAir(false);
                              player.setSpeedY(0);
                              player.setPlat(platf);
                              getPlayer().setY(getPlayer().getPlat().getY() - getPlayer().getHeight());
                            } else {
                              player.setSpeedY(0);
                              player.setY(platf.getY() + platf.getHeight() + 1);
                            }
                          }
                      }
                    }

                    // Ingredients are ticked
                    for (int i = 0; i < comidas.size(); i++) {
                      Comida comi = comidas.get(i);
                      
                      // If the player collects an ingredient, it is removed form the 
                      // game and added to its collection of collected ingredients
                      if (comi.intersectaJugador(player)) {
                        comidas.remove(i);
                        recolectado.add(comi);

                      }
                    }

                    // Checks if the player fell of the level
                    if (player.getY() >= levelHeight) {

                      // Sets that the player no longer will be moving in the air
                      player.setSpeedY(0);
                      player.setInTheAir(false);

                      // Checks if the player is in a state of vulnerability
                      // If it is not, the player loses one life 
                      if (player.getContGotHit() == 0) {
                        player.setLives(player.getLives() - 1);
                      }
                      // Resets the player's position on top of the last platform it was on
                      int dif = (getPlayer().getX() < getPlayer().getPlat().getX()) ? 0 : getPlayer().getPlat().getWidth() - getPlayer().getWidth();
                      getPlayer().setX(getPlayer().getPlat().getX() + dif);
                      getPlayer().setY(getPlayer().getPlat().getY() - getPlayer().getHeight());
                      getPlayer().setContGotHit(60);
                      
                    }

                    // Checks if the player has won or lost the game
                    if (comidas.isEmpty() || player.getLives() == 0) {
                      endGame = true;
                    }

                  } else {

                    // Opens the pause menu
                    if (pauseGame) {

                      // Navigates the pause menu
                      if (getKeyManager().up || getKeyManager().down) {
                        switch(pauseOpt) {
                            case CONTINUE_LEVEL:
                                pauseOpt = PauseMenu.EXIT;
                                break;
                            case EXIT:
                                pauseOpt = PauseMenu.CONTINUE_LEVEL;
                                break;
                        }
                      }

                      // Selects an option from the pause menun
                      if (keyManager.enter) {
                        
                        switch(pauseOpt) {

                          // Continue playing
                          case CONTINUE_LEVEL:
                            pauseGame = false;
                            break;
                            
                          // Exit level and go back to menu
                          case EXIT:
                            unloadLevel();
                            endGame = false;
                            pauseGame = false;
                            screen = Screen.MENU;
                            break;
                        }
                      }
                    }

                    // Shows an end game screen. Changes either you won or lost
                    if (endGame) {
                      // Restart level
                      if (keyManager.enter) {
                        unloadLevel();
                        loadLevel();
                        endGame = false;
                        pauseGame = false;
                      }
                    }
                  }
                }

                break;
        }
    }
/**
 * Render function that displays the assets on screen
 */
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the
        buffer strategy element.
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.setFont(texto);
            // Checks which screen to render
            switch(screen) {
                case TITLESCREEN:
                    // Title screen image
                    g.drawImage(Assets.titleScreen, 0, 0, getWidth(), getHeight(), null);
                    break;

                case STORY:
                     g.drawImage(Assets.story, 0, 0, getWidth(), getHeight(), null);
                    break;

                case MENU:  
                
                  // Main menu background image
                  g.drawImage(Assets.menu, 0, 0, getWidth(), getHeight(), null);

                  //Elementos decorativos en el mapa
                  g.drawImage(Assets.cactus, 478, 220, Assets.cactus.getWidth(), Assets.cactus.getHeight(), null);
                  g.drawImage(Assets.pyramid, 990, 420, Assets.pyramid.getWidth(), Assets.pyramid.getHeight(), null);
                  g.drawImage(Assets.crab, 730, 460, Assets.crab.getWidth(), Assets.crab.getHeight(), null);

                  // Checks where to draw the cursor that shows which option of the menu you are selecting
                  // and whether to show a preview of the level you are selecting or a picture of the mian character
                  switch(menOpt) {
                    case OPTIONS:
                      g.drawImage(Assets.select, 810, 70, 100, 100, null);
                      g.drawImage(Assets.thinkingXochi, 20, 520, 400, 225, null);
                      break;
                    case RECIPIES:
                      g.drawImage(Assets.select, 785, 125, 100, 100, null);
                      g.drawImage(Assets.thinkingXochi, 20, 520, 400, 225, null);
                      break;
                   case CONTROLS:
                      g.drawImage(Assets.select, 770, 185, 100, 100, null);
                      g.drawImage(Assets.thinkingXochi, 20, 520, 400, 225, null);
                      break;
                    case ONE:
                      g.drawImage(Assets.miniLevel, 40, 420, 400, 230, null);
                      g.drawImage(Assets.select, 620, 380, 100, 100, null);
                      break;
                    case TWO:
                      g.drawImage(Assets.miniLevel2, 40, 420, 400, 230, null);
                      g.drawImage(Assets.select, 590, 450, 100, 100, null);
                      break;
                    case THREE:
                      g.drawImage(Assets.miniLevel3, 40, 420, 400, 230, null);
                      g.drawImage(Assets.select, 680, 535, 100, 100, null);
                      break;
                  }
                  break;
                case OPTIONS:
                  // Options menu background
                  g.drawImage(Assets.options, 0, 0, getWidth(), getHeight(), null);

                  // Shows if the sound/effecstOn option is on/off
                  if (soundOn) {
                    g.drawImage(Assets.checkmark, 920, 278, 34, 34, null);
                  }
                  if (effectsOn) {
                    g.drawImage(Assets.checkmark, 920, 172, 34, 34, null);
                  }

                  // Shows which option you are selecting
                  switch (optOpt) {
                      case DALTONICO:
                          g.drawImage(Assets.select, 190, 145, 100, 100, null);
                          break;
                      case SONIDO:
                          g.drawImage(Assets.select, 190, 255, 100, 100, null);
                          break;
                      case BRILLO:
                          g.drawImage(Assets.select, 190, 370, 100, 100, null);
                          break;
                  }

                  // Shows how much brightness you are selecting
                  switch (brightness) {
                      case 1:
                           g.drawImage(Assets.opbrightness1, 600 , 380, 360 , 70, null);
                           break;
                      case 2:
                           g.drawImage(Assets.opbrightness2, 600 , 380, 360 , 70, null);
                           break;
                      case 3:
                          g.drawImage(Assets.opbrightness3, 600 , 380, 360 , 70, null);
                          break;
                      case 4:
                           g.drawImage(Assets.opbrightness4, 600 , 380, 360 , 70, null);
                           break;
                      case 5:
                           g.drawImage(Assets.opbrightness5, 600 , 380, 360 , 70, null);
                           break;
                  }
                    break;
                case RECIPIES:
                    // Sets the color for the name of the ingredients
                    g.setColor(Color.BLACK);

                    // Recipies menu background image
                    g.drawImage(Assets.recipies, 0, 0, getWidth(), getHeight(), null);

                    // Shows a recipie depending in which page you are
                    switch (currentRecipePage) {
                        case 1:
                            g.drawImage(Assets.enchiladas, 70, 10, 200, 200, null);
                            g.drawString("Enchiladas Potosinas", 270, 125);
                            g.drawImage(Assets.recetaEnchiladas, 725, 70, 420, 543, null);
                            for (int i = 0; i < Assets.ingredientesEnchiladas.length; i++) {
                                g.drawImage(Assets.ingredientesEnchiladas[i], 100, 200 + i*50, 50, 50, null);
                                g.drawString(Assets.ingrEnchiladas[i], 200, 240 + i * 50);
                            }
                            g.drawImage(Assets.pasarPag, getWidth()-200, getHeight()-100, 100, 100, null);
                            break;
                        case 2:
                            g.drawImage(Assets.quecas, 140, 65, 100, 100, null);
                            g.drawString("Quesadillas", 270, 125);
                            g.drawImage(Assets.recetaQuecas, 725, 70, 420, 543, null);
                            for (int i = 0; i < Assets.ingredientesQuecas.length; i++) {
                                g.drawImage(Assets.ingredientesQuecas[i], 100, 200 + i*50, 50, 50, null);
                                g.drawString(Assets.ingrQuecas[i], 200, 240 + i * 50);
                            }                           
                            g.drawImage(Assets.pasarPag, getWidth()-200, getHeight()-100, 100, 100, null);
                            g.drawImage(Assets.pasarPagReves, getWidth()-300, getHeight()-100, 100, 100, null);
                            break;
                        case 3:
                            g.drawImage(Assets.mole, 130, 65, 100, 100, null);
                            g.drawString("Mole Oaxaqueño", 270, 125);
                            g.drawImage(Assets.recetaMole, 725, 70, 420, 543, null);
                            for (int i = 0; i < Assets.ingredientesMole.length; i++) {
                                g.drawImage(Assets.ingredientesMole[i], 100, 200 + i*50, 50, 50, null);
                                g.drawString(Assets.ingrMole[i], 200, 240 + i * 50);
                            }                           
                            g.drawImage(Assets.pasarPagReves, getWidth()-200, getHeight()-100, 100, 100, null);
                            break;
                        default:
                            break;
            }
                  
                  break;
                case CONTROLS:

                  // Controls screen image
                  g.drawImage(Assets.controls, 0, 0, getWidth(), getHeight(), null);
                  break;
                case LEVEL:

                  // BACKGROUND
                  g.drawImage(Assets.background, 0, 0, getWidth(), getHeight(), null);

                  // GAME

                  // Draw the platforms
                  for (int i = 0; i < platforms.size(); i++) {
                    Platform platform = platforms.get(i);
                    // System.out.println("platform" + i + " " + (rec.intersects(platform.getPerimetro())));
                    if (rec.intersects(platform.getPerimetro())) {
                      platform.render(g);
                    }
                  }

                  // Draw the fountain

                  
                  g.drawImage(Assets.fuente, (fuente.x - rec.x), (fuente.y - rec.y), fuente.width, fuente.height, null);
                  

                  // dibujar los chiles
                  for (int i = 0; i < chiles.size(); i++) {
                    Enemy chile = chiles.get(i);
                    if (rec.intersects(chile.getPerimetro())) {
                      chile.render(g);
                    }
                  }

                  for (int i = 0; i < picos.size(); i++) {
                    Pico pi = picos.get(i);
                    if (rec.intersects(pi.getPerimetro())) {
                      pi.render(g);
                    }
                  }

                  // dibujar los powerups
                  for (int i = 0; i < powerups.size(); i++) {
                    PowerUps powerup = powerups.get(i);
                    if (rec.intersects(powerup.getPerimetro())) {
                      powerup.render(g);
                    }
                  }

                  // dibujar comidas
                  for (int i = 0; i < comidas.size(); i++) {
                    Comida comida = comidas.get(i);
                    if (rec.intersects(comida.getPerimetro())) {
                      comida.render(g);
                    }
                  }

                  // Draw bubbles
                  for (int i = 0; i < disparos.size(); i++) {
                    Shot disp = disparos.get(i);
                    if (rec.intersects(disp.getPerimetro())) {
                      disp.render(g);
                    }
                  }

                  // Draw signs
                  for (int i = 0; i < letreros.size(); i++) {
                    Letrero sign = letreros.get(i);
                    if (rec.intersects(sign.getPerimetro())) {
                      sign.render(g);
                    }
                  }

                  // draw player
                  player.render(g);

                  // UI

                  // lives
                  for (int i = 0; i < player.getLives(); i++) {
                    g.drawImage(Assets.heart, 0 + i * 60, 0, 50 , 50, null); // PLACEHOLDER
                  }

                  // bubbles
                  for (int i = 0; i < 100 - (100 - player.getWater()); i+=10) {
                    g.drawImage(Assets.shot, 0 + i * 6, 60, 50 , 50, null); // PLACEHOLDER
                  }

                  int iPosX = getWidth() - 55;
                  int iPosY = 20;

                  // dibujar ingredientes recolectados
                  for (int i = 0; i < recolectado.size(); i++) {
                    Comida recol = recolectado.get(i);
                    recol.renderUI(g, iPosX, iPosY);
                    iPosX -= 55;
                  }

                  // pause menu
                  if (pauseGame) {
                    g.setColor(Color.WHITE);
                    g.drawImage(Assets.pause, 0, 0, getWidth(), getHeight(), null);
                    g.drawString("Continuar jugando", getWidth() / 2 - 100, getHeight() / 2 + 50);
                    g.drawString("Regresar al menu principal", getWidth() / 2 - 165, getHeight() / 2 + 120);
                    switch(pauseOpt) {
                      case CONTINUE_LEVEL:
                        g.drawImage(Assets.select, getWidth() / 2 - 200, getHeight() / 2 - 10, 100, 100, null);
                        break;
                      case EXIT:
                        g.drawImage(Assets.select, getWidth() / 2 - 265, getHeight() / 2 + 60, 100, 100, null);
                        break;
                    }
                  }

                  if (endGame) {
                    // g.setFont(texto);
                    if (comidas.isEmpty()) {
                      // you won
                      g.drawImage(Assets.ganado, 0, 0, getWidth(), getHeight(), null);
                    } else if (player.getLives() == 0) {
                      // you lost
                      g.drawImage(Assets.perdido, 0, 0, getWidth(), getHeight(), null);
                    }
                  }

                  break;
            }
            switch(brightness) {
                case 1:
                    g.drawImage(Assets.brightness1, 0 , 0, getWidth() , getHeight(), null);
                    break;
                case 2:
                    g.drawImage(Assets.brightness2, 0 , 0, getWidth() , getHeight(), null);
                    break;
                case 4:
                    g.drawImage(Assets.brightness4, 0 , 0, getWidth() , getHeight(), null);
                    break;
                case 5:
                    g.drawImage(Assets.brightness5, 0 , 0, getWidth() , getHeight(), null);
                    break;
            }

            bs.show();
            g.dispose();
        }
    }

    // start(), init(), run(), and stop() ------------------------------------------------------------------

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();

        // se inicializan las variables
        endGame = false;
        pauseGame = false;
        confirmSound = Assets.selectSnd;
        soundOn = true;

        display.getJframe().addKeyListener(keyManager);
        // display.getJframe().addMouseListener(mouseManager);
        // display.getJframe().addMouseMotionListener(mouseManager);
        // display.getCanvas().addMouseListener(mouseManager);
        // display.getCanvas().addMouseMotionListener(mouseManager);
        
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                try {
                    tick();
                    render();
                    delta--;
                } catch (SQLException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        stop();
    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}