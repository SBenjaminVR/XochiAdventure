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

enum Screen {
    TITLESCREEN,
    MENU,
    OPTIONS,
    RECIPIES,
    LEVEL,
    CONTROLS
}

enum MenuOpt {
    ONE,
    TWO,
    THREE,
    OPTIONS,
    CONTROLS,
    RECIPIES,
}

enum OptOpt {
    DALTONICO,
    SONIDO,
    BRILLO
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
   // private String nombreArchivo;                         // to store the name of the file
    private Font texto;                                     // to change the font of string drawn in the screen
    private Shot shot;                                      // to have a missile to shoot
    private Rectangle rec;                                  // to store the rectangle that checks which sprites are going to be drawn
    private Rectangle fuente;                               // to store the position of the fuente
    private boolean soundOn;
    private int nivel;
    private int levelWidth;
    private int levelHeight;
    private boolean menuMusicPlaying;

    // Linked lists
    private LinkedList<Platform> platforms;                 // to store all the platforms
    private LinkedList<Enemy> chiles;                       // to store all the enemies
    private LinkedList<PowerUps> powerups;                  // to store all the powerups
    private LinkedList<Comida> comidas;                     // to store all the food
    private LinkedList<Comida> recolectado;
    private LinkedList<Shot> disparos;

    // Menu navigation variables
    private Screen screen;                                  // to store in which screen you are
    private MenuOpt menOpt;                                 // to store in which option in the main menu screen you are
    private OptOpt optOpt;                                  // to store in which option in the options screen you are
    private int currentRecipePage = 1;

    // UI
    private int playerX;                                    // to store the position in which the player will be drawn
    private int playerY;                                    // to store the position in which the player will be drawn
    private int limitX[];

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
        chiles = new LinkedList<Enemy>();
        // nombreArchivo = "src/space/inavders/archivo.sf";
        texto = new Font("Font", 2, 32);
        screen = Screen.TITLESCREEN;
        powerups = new LinkedList<PowerUps>();
        platforms = new LinkedList<Platform>();
        comidas = new LinkedList<Comida>();
        recolectado = new LinkedList<Comida>();
        disparos = new LinkedList<Shot>();
        rec = new Rectangle(0, 0, getWidth(), getHeight());
        limitX = new int[2];
        fuente = new Rectangle(0, 0, 300, 300);
        hasPlayedWinSnd = false;
        brightness = 3; 


        menuMusicPlaying = false;
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

    // SETS ------------------------------------------------------------------------------------------------------------------------------------

    /**
     * To set if the game is ended
     *
     * @param endGame to set the state of the game
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    // FUNCTIONS ------------------------------------------------------------------------------------------------------------------------------------

    /**
     * To load the chosen level
     * @param txt to know which level to load
     */
    private void loadLevel() {
    
      // lee txt

      /*
      int posX;
      int posY;
      int iWidth;
      int iHeight
      int speedX;
      int playerPlat;
      int leftLimit;
      int rightLimit;
      */
      int direction;
      // int iPosX = 10;
      // int iPosY = 10;

      // poner donde va a estar la fuente
      fuente.x = 1400;
      fuente.y = 500;

      
      switch(nivel){
        case 1:
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
          // platforms.add(new Platform(950, 1900, 1200, 100, this));
          platforms.add(new Platform(950,   1900, 500, 100, this));
          platforms.add(new Platform(1450,  1900, 500, 100, this));
          platforms.add(new Platform(1650,  1900, 500, 100, this));
          platforms.add(new Platform(2600,  1900, 500, 100, this));

          // comidas
          comidas.add(new Comida(225, 200, 50, 50, this));
          comidas.add(new Comida(1525, 200, 50, 50, this));
          comidas.add(new Comida(2925, 200, 50, 50, this));
          comidas.add(new Comida(1525, 1050, 50, 50, this));
          comidas.add(new Comida(225, 1850, 50, 50, this));
          comidas.add(new Comida(1525, 1850, 50, 50, this));
          comidas.add(new Comida(2925, 1850, 50, 50, this));

          player = new Player (1475, 650, 100, 100, 6, 3, platforms.get(7), this);
          playerX = getWidth() / 2 - player.getWidth() / 2;
          playerY = getHeight() / 2 - player.getHeight() / 2;
          levelWidth = 3100;
          levelHeight = 2100;

          break;

        case 2:
          // Chiles
          chiles.add(new Enemy(10,    200,  50, 50, 1,  5, 0,     500, this));
          chiles.add(new Enemy(3040,  200,  50, 50, -1, 5, 2600,  3100, this));
          chiles.add(new Enemy(310,   750,  50, 50, 1,  5, 300,   800, this));
          chiles.add(new Enemy(2740,  750,  50, 50, -1, 5, 2300,  2800, this));
          chiles.add(new Enemy(810,   1050, 50, 50, 1,  5, 800,   1300, this));
          chiles.add(new Enemy(2240,  1050, 50, 50, -1, 5, 1800,  2300, this));
          chiles.add(new Enemy(10,    1650, 50, 50, 1,  5, 0,     400, this));
          chiles.add(new Enemy(3040,  1650, 50, 50, -1, 5, 2700,  3100, this));

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

          comidas.add(new Comida(225, 200, 50, 50, this));
          comidas.add(new Comida(1525, 200, 50, 50, this));
          comidas.add(new Comida(2925, 200, 50, 50, this));
          comidas.add(new Comida(1525, 1050, 50, 50, this));
          comidas.add(new Comida(225, 1850, 50, 50, this));
          comidas.add(new Comida(1525, 1850, 50, 50, this));
          comidas.add(new Comida(2925, 1850, 50, 50, this));

          player = new Player (1475, 650, 100, 100, 6, 3, platforms.get(7),  this);
          playerX = getWidth() / 2 - player.getWidth() / 2;
          playerY = getHeight() / 2 - player.getHeight() / 2;
          levelWidth = 3100;
          levelHeight = 1900;
          break;

        case 3:
          chiles.add(new Enemy(1350, 200, 50, 50, 1, 5, 1300, 1550, this));
          chiles.add(new Enemy(1750, 200, 50, 50, -1, 5, 1550, 1800, this));
          chiles.add(new Enemy(955, 1300, 50, 50, 1, 5, 950, 1450, this));
          chiles.add(new Enemy(2100, 1300, 50, 50, -1, 5, 1650, 2150, this));
          chiles.add(new Enemy(955, 1850, 50, 50, 1, 5, 950, 1550, this));
          chiles.add(new Enemy(2100, 1850, 50, 50, -1, 5, 1550, 2150, this));

          // grandes 3
          platforms.add(new Platform(0, 250, 500, 100, this));
          platforms.add(new Platform(1300, 250, 500, 100, this));
          platforms.add(new Platform(2600, 250, 500, 100, this));

          // chicas 2
          platforms.add(new Platform(650, 500, 500, 100, this));
          platforms.add(new Platform(1950, 500, 500, 100, this));

          // 2500 5
          platforms.add(new Platform(300, 800, 500, 100, this));
          platforms.add(new Platform(800, 800, 500, 100, this));
          platforms.add(new Platform(1300, 800, 500, 100, this));
          platforms.add(new Platform(1800, 800, 500, 100, this));
          platforms.add(new Platform(2300, 800, 500, 100, this));

          // chicas 3
          platforms.add(new Platform(0, 1100, 150, 30, this));
          platforms.add(new Platform(1550 - 75, 1100, 150, 30, this));
          platforms.add(new Platform(2950, 1100, 150, 30, this));

          // grandes 4
          platforms.add(new Platform(0, 1350, 500, 100, this));
          platforms.add(new Platform(950, 1350, 500, 100, this));
          platforms.add(new Platform(1650, 1350, 500, 100, this));
          platforms.add(new Platform(2600, 1350, 500, 100, this));

          // chicas 2
          platforms.add(new Platform(650, 1650, 150, 30, this));
          platforms.add(new Platform(2300, 1650, 150, 30, this));

          // grandes 5
          platforms.add(new Platform(0, 1900, 500, 100, this));
          // platforms.add(new Platform(950, 1900, 1200, 100, this));
          platforms.add(new Platform(950, 1900, 500, 100, this));
          platforms.add(new Platform(1450, 1900, 500, 100, this));
          platforms.add(new Platform(1550, 1900, 500, 100, this));
          platforms.add(new Platform(2600, 1900, 500, 100, this));

          comidas.add(new Comida(225, 200, 50, 50, this));
          comidas.add(new Comida(1525, 200, 50, 50, this));
          comidas.add(new Comida(2925, 200, 50, 50, this));
          comidas.add(new Comida(1525, 1050, 50, 50, this));
          comidas.add(new Comida(225, 1850, 50, 50, this));
          comidas.add(new Comida(1525, 1850, 50, 50, this));
          comidas.add(new Comida(2925, 1850, 50, 50, this));

          player = new Player (1475, 650, 100, 100, 6, 3, platforms.get(7), this);
          playerX = getWidth() / 2 - player.getWidth() / 2;
          playerY = getHeight() / 2 - player.getHeight() / 2;
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
    }

    // tick and render ------------------------------------------------------------------------------------------------------------------------------------

    private void tick() throws SQLException {
        // ticks key manager
        keyManager.tick();
        // System.out.println("" + keyManager.left + " " + keyManager.right);

        // checks in which screen you are
        switch(screen) {

            // Tttle screen ------------------------------------------------------------------
            case TITLESCREEN:
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
                            }
                            //carga nivel 1
                            nivel = 1;
                            loadLevel();
                            Assets.background = ImageLoader.loadImage("/images/niveles/nivel 1.png");
                            screen = Screen.LEVEL;
                                break;
                        case TWO:
                            if (soundOn) {
                                Assets.selectSnd.play();
                            }
                            //carga nivel2
                            nivel = 2;
                            loadLevel();
                            Assets.background = ImageLoader.loadImage("/images/niveles/nivel 2.png");
                            screen = Screen.LEVEL;
                                break;
                        case THREE:
                            if (soundOn) {
                                Assets.selectSnd.play();
                            }
                            //carga nivel3
                            nivel = 3;
                            loadLevel();
                            Assets.background = ImageLoader.loadImage("/images/niveles/nivel 3.png");
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
                            // turns on and off
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
                    else {
                        currentRecipePage = 1;
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
                }

                // checks if the backspace key was pressed to return to the main menu
                if (keyManager.back) {
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

                    // checks if the player can shoot a bubble and if one the keyw for doing it was pressed
                    if (player.getWater() > 0 && (getKeyManager().z || getKeyManager().o)) {
          						if (soundOn) {
          							Assets.shootSnd.play();
          						}
                      // checks in which direction the player is moving to know in which direction shoot the bubble
                      if (player.getDirection() == 1) {
                        // attack to the right
                        disparos.add(new Shot(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2, 50, 50, 8, 1, this));
                      } else {
                        // attack to the left
                        disparos.add(new Shot(player.getX(), player.getY() + player.getHeight() / 2, 50, 50, 8, -1, this));
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
                          if (numerito < 25) {
                            powerups.add(new PowerUps(chile.getX(), chile.getY(), 50, 50, this));
                          }
                        }
                      }

                      if (chile.intersectaJugador(player) && player.getContGotHit() == 0) {
                        // quitarle vida al jugador
                        player.setLives(player.getLives() - 1);
                        if (soundOn)
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
                                  if (soundOn)
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
                                  if (soundOn)
                                    Assets.dulceSnd.play();
                                  powerups.remove(i);
                                  break;

                              case FRIJOL:
                                  powerups.remove(i);

                                  break;
                              default:
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

                      // se tickea a los ingredientes
                      for (int i = 0; i < comidas.size(); i++) {
                        Comida comi = comidas.get(i);
                        if (comi.intersectaJugador(player)) {
                          comidas.remove(i);
                          // guardar de alguna manera que ya recolectamos una comida más o esa comida en especifico
                          recolectado.add(comi);

                        }
                      }

                      if (player.getY() >= levelHeight) {
                        player.setSpeedY(0);
                        player.setInTheAir(false);
                        if (player.getContGotHit() == 0) {
                          player.setLives(player.getLives() - 1);
                        }
                        if (player.getLives() == 0) {
                          endGame = false;

                        } else {
                          int dif = (getPlayer().getX() < getPlayer().getPlat().getX()) ? 0 : getPlayer().getPlat().getWidth() - getPlayer().getWidth();
                          getPlayer().setX(getPlayer().getPlat().getX() + dif);
                          getPlayer().setY(getPlayer().getPlat().getY() - getPlayer().getHeight());
                          getPlayer().setContGotHit(60);
                        }
                      }

                      if (comidas.isEmpty() || player.getLives() == 0) {
                        endGame = true;
                      }

                  } else {
                    if (endGame) {
                      if (keyManager.enter) {
                        unloadLevel();
                        loadLevel();
                      }
                    }
                  }
                }

                break;
        }
    }

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
                    g.drawImage(Assets.titleScreen, 0, 0, getWidth(), getHeight(), null);
                    break;
                case MENU:                    
                  g.drawImage(Assets.menu, 0, 0, getWidth(), getHeight(), null);
                  //Elementos decorativos en el mapa
                  g.drawImage(Assets.cactus, 478, 220, Assets.cactus.getWidth(), Assets.cactus.getHeight(), null);
                  g.drawImage(Assets.pyramid, 990, 420, Assets.pyramid.getWidth(), Assets.pyramid.getHeight(), null);
                  g.drawImage(Assets.crab, 730, 460, Assets.crab.getWidth(), Assets.crab.getHeight(), null);
                  g.drawString("" + nivel, 50, 480);
                  // Checks where to draw the rectangle that shows which option of the menu you are selecting
                  switch(menOpt) {
                    case OPTIONS:
                      //g.drawImage(Assets.rec, 1340, 125, 400, 100, null);
                      g.drawImage(Assets.select, 810, 70, 100, 100, null);
                      break;
                    case RECIPIES:
                      //g.drawImage(Assets.rec, 1340, 200, 400, 100, null);
                      g.drawImage(Assets.select, 785, 125, 100, 100, null);
                      break;
                   case CONTROLS:
                      //g.drawImage(Assets.rec, 1340, 280, 400, 100, null);
                      g.drawImage(Assets.select, 770, 185, 100, 100, null);
                      break;
                    case ONE:
                      //g.drawImage(Assets.rec, 1200, 125, 400, 100, null);
                      g.drawImage(Assets.miniLevel, 40, 420, 400, 230, null);
                      g.drawImage(Assets.select, 620, 380, 100, 100, null);
                      break;
                    case TWO:
                      g.drawImage(Assets.miniLevel2, 40, 420, 400, 230, null);
                      g.drawImage(Assets.select, 590, 450, 100, 100, null);
                      break;
                    case THREE:
                      g.drawImage(Assets.select, 680, 535, 100, 100, null);
                      break;
                  }
                  break;
                case OPTIONS:
                  g.drawImage(Assets.options, 0, 0, getWidth(), getHeight(), null);
                  if (soundOn) {
                    g.drawImage(Assets.checkmark, 920, 278, 34, 34, null);
                  }
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
                  g.drawImage(Assets.recipies, 0, 0, getWidth(), getHeight(), null);
                  if (currentRecipePage == 1) {
                    g.drawImage(Assets.enchiladas, 70, 10, 200, 200, null);
                    for (int i = 0; i < Assets.ingredientesEnchiladas.length; i++) {
                          g.drawImage(Assets.ingredientesEnchiladas[i], 100, 200 + i*50, 50, 50, null);
                    }
                    for (int i = 0; i < Assets.ingredientesEnchiladas.length; i++) {
                          g.drawString("nombre", 200, 240 + i * 50);
                    }
                  }
                  else if (currentRecipePage == 2) {
                    g.drawImage(Assets.quecas, 70, 10, 200, 200, null);
                    for (int i = 0; i < Assets.ingredientesQuecas.length; i++) {
                          g.drawImage(Assets.ingredientesQuecas[i], 100, 200 + i*50, 50, 50, null);
                    }
                    for (int i = 0; i < Assets.ingredientesQuecas.length; i++) {
                          g.drawString("nombre", 200, 240 + i * 50);
                    }
                  }
                  else if (currentRecipePage == 3) {
                      
                  }
                  
                  break;
                case CONTROLS:
                  g.drawImage(Assets.controls, 0, 0, getWidth(), getHeight(), null);
                  break;
                case LEVEL:

                  // BACKGROUND
                  g.drawImage(Assets.background, 0, 0, getWidth(), getHeight(), null);

                  // g.drawImage(Assets.rectangle, (int)(rec.getX()), (int)(rec.getY()), (int)(rec.getWidth()), (int)(rec.getHeight()), null);
                  // g.drawImage(Assets.rectangle, player.getX() - playerX, player.getY() - playerY, (int)(rec.getWidth()), (int)(rec.getHeight()), null);

                  // GAME

                  // dibujar las plataformas
                  for (int i = 0; i < platforms.size(); i++) {
                    Platform platform = platforms.get(i);
                    // System.out.println("platform" + i + " " + (rec.intersects(platform.getPerimetro())));
                    if (rec.intersects(platform.getPerimetro())) {
                      platform.render(g);
                    }
                  }

                  // dibujar la fuente

                  if (player.getX() < playerX) {
                    g.drawImage(Assets.fuente, fuente.x, (fuente.y - rec.y), fuente.width, fuente.height, null);
                  } else {
                    g.drawImage(Assets.fuente, (fuente.x - rec.x), (fuente.y - rec.y), fuente.width, fuente.height, null);
                  }

                  // dibujar los chiles
                  for (int i = 0; i < chiles.size(); i++) {
                    Enemy chile = chiles.get(i);
                    // System.out.println("Chile" + i + " " + (rec.intersects(chile.getPerimetro())));
                    if (rec.intersects(chile.getPerimetro())) {
                      chile.render(g);
                    }
                  }

                  // dibujar los powerups
                  for (int i = 0; i < powerups.size(); i++) {
                    PowerUps powerup = powerups.get(i);
                    // System.out.println("powerup" + i + " " + (rec.intersects(powerup.getPerimetro())));
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

                  for (int i = 0; i < disparos.size(); i++) {
                    Shot disp = disparos.get(i);
                    if (rec.intersects(disp.getPerimetro())) {
                      disp.render(g);
                    }
                  }

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
                    g.drawImage(Assets.pause, 0, 0, getWidth(), getHeight(), null);
                  }

                  if (endGame) {
                    // g.setFont(texto);
                    if (comidas.isEmpty()) {
                      // you won
                      g.drawString("GANASTE", getWidth()/2 - 100, getHeight()/2 - 10);
                    } else if (player.getLives() == 0) {
                      // you lost
                      g.drawString("PERDISTE", getWidth()/2 - 100, getHeight()/2 - 10);
                    }
                    g.drawString("Presiona Enter para reiniciar el nivel", getWidth()/2 - 300, getHeight()/2 + 15);
                    g.drawString("Presiona Backspace para regresar al menu principal", getWidth()/2 - 400, getHeight()/2 + 40);
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