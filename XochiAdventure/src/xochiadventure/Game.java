/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

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
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández
 * A01410921
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

    // UI
    private int playerX;                                    // to store the position in which the player will be drawn
    private int playerY;                                    // to store the position in which the player will be drawn
    private String nivel;
    private int limitX[];



    private MouseManager mouseManager;                      // to manage the mouse

    private SoundClip confirmSound;

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
        mouseManager = new MouseManager();
        powerups = new LinkedList<PowerUps>();
        platforms = new LinkedList<Platform>();
        comidas = new LinkedList<Comida>();
        recolectado = new LinkedList<Comida>();
        disparos = new LinkedList<Shot>();
        rec = new Rectangle(0, 0, getWidth(), getHeight());
        limitX = new int[2];
        fuente = new Rectangle(0, 0, 300, 300);
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
     * To get the list of all the bricks
     *
     * @return an <code>LinkedList<Enemey></code> list with all the bricks
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
     * to get the shots
     *
     * @return
     */
    public Shot getShot() {
        return shot;
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

    /**
     * To set the shot
     * @param shot to set the shot
     */
    public void setShot(Shot shot) {
        this.shot = shot;
    }

    // FUNCTIONS ------------------------------------------------------------------------------------------------------------------------------------

    /**
     * To load the chosen level
     * @param txt to know which file to read
     */
    private void loadLevel(String txt) {

      // lee txt

      /*
      int cantChiles;
      int cantPlatforms;
      int cantPowerUps
      int posX;
      int posY;
      int iWidth;
      int iHeight
      int speed;
      */
      int direction;
      // int iPosX = 10;
      // int iPosY = 10;

      // poner donde va a estar la fuente
      fuente.x = 1400;
      fuente.y = 500;

      // se crean los chiles
      // for (int i  = 0; i < 5; i++) {
      //     if (i % 2 == 0) {
      //         direction = 1;
      //     } else {
      //         direction = -1;
      //     }
      //     chiles.add(new Enemy(iPosX, iPosY, 50, 50, direction, 5, 0, 500, this));
      //     iPosX += 50;
      //     iPosY += 50;
      // }

      chiles.add(new Enemy(1350, 200, 50, 50, 1, 5, 1300, 1550, this));
      chiles.add(new Enemy(1750, 200, 50, 50, -1, 5, 1550, 1800, this));
      chiles.add(new Enemy(955, 1300, 50, 50, 1, 5, 950, 1450, this));
      chiles.add(new Enemy(2100, 1300, 50, 50, -1, 5, 1650, 2150, this));
      chiles.add(new Enemy(955, 1850, 50, 50, 1, 5, 950, 1550, this));
      chiles.add(new Enemy(2100, 1850, 50, 50, -1, 5, 1550, 2150, this));

      // iPosX = 50;
      // iPosY = 10;

      // se crean los powerups
      // for (int i  = 0; i < 2; i++) {
      //     powerups.add(new PowerUps(iPosX, iPosY, 50, 50, 5, this));
      //     iPosX += 50;
      //     iPosY += 50;
      // }

      // iPosX = 100;
      // iPosY = 10;
      // se crean las plataformas

      // for (int i  = 0; i < 2; i++) {
      //     platforms.add(new Platform(iPosX, iPosY, 50, 50, 5, this));
      //     iPosX += 50;
      //     iPosY += 50;
      // }

      // grandes
      platforms.add(new Platform(0, 250, 500, 100,  0, this));
      platforms.add(new Platform(1300, 250, 500, 100, 0, this));
      platforms.add(new Platform(2600, 250, 500, 100, 0, this));

      // chicas
      platforms.add(new Platform(650, 500, 500, 100, 0, this));
      platforms.add(new Platform(1950, 500, 500, 100, 0, this));

      // 2500
      platforms.add(new Platform(300, 800, 500, 100, 0, this));
      platforms.add(new Platform(800, 800, 500, 100, 0, this));
      platforms.add(new Platform(1300, 800, 500, 100, 0, this));
      platforms.add(new Platform(1800, 800, 500, 100, 0, this));
      platforms.add(new Platform(2300, 800, 500, 100, 0, this));

      // chicas
      platforms.add(new Platform(0, 1100, 150, 30, 0, this));
      platforms.add(new Platform(1550 - 75, 1100, 150, 30, 0, this));
      platforms.add(new Platform(2950, 1100, 150, 30, 0, this));

      // grandes
      platforms.add(new Platform(0, 1350, 500, 100, 0, this));
      platforms.add(new Platform(950, 1350, 500, 100, 0, this));
      platforms.add(new Platform(1650, 1350, 500, 100, 0, this));

      platforms.add(new Platform(2600, 1350, 500, 100, 0, this));

      // chicas
      platforms.add(new Platform(650, 1650, 150, 30, 0, this));
      platforms.add(new Platform(2300, 1650, 150, 30, 0, this));

      // grandes
      platforms.add(new Platform(0, 1900, 500, 100, 0, this));
      platforms.add(new Platform(950, 1900, 1200, 100, 0, this));
      platforms.add(new Platform(2600, 1900, 500, 100, 0, this));

      // se crean los ingredientes
      // for (int i  = 0; i < 5; i++) {
      //     comidas.add(new Comida(iPosX, iPosY, 50, 50, 5, this));
      //     iPosX += 50;
      //     iPosY += 50;
      // }

      comidas.add(new Comida(225, 200, 50, 50, 0, this));
      comidas.add(new Comida(1525, 200, 50, 50, 0, this));
      comidas.add(new Comida(2925, 200, 50, 50, 0, this));
      comidas.add(new Comida(1525, 1050, 50, 50, 0, this));
      comidas.add(new Comida(225, 1850, 50, 50, 0, this));
      comidas.add(new Comida(1525, 1850, 50, 50, 0, this));
      comidas.add(new Comida(2925, 1850, 50, 50, 0, this));

      player = new Player (1475, 650, 100, 100, 5, 3, 0, 150, this);
      playerX = getWidth() / 2 - player.getWidth() / 2;
      playerY = getHeight() / 2 - player.getHeight() / 2;

      endGame = false;
      pauseGame = false;

     // System.out.println("x and y " + playerX + " " + playerY);
     // System.out.println("player " + player.getX() + " " + player.getY());
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

    private void tick() {
        // ticks key manager
        keyManager.tick();
//         System.out.println("" + keyManager.left + " " + keyManager.right);

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
                    Assets.selectSnd.play();
                    // confirmSound.play();
                    switch(menOpt) {
                        case OPTIONS:
                            screen = Screen.OPTIONS;
                            optOpt = OptOpt.DALTONICO;
                            break;
                        case ONE:
                            //carga nivel 1
                            Assets.selectSnd.play();
                            loadLevel("nivel 1");
                            Assets.background = ImageLoader.loadImage("/images/nivel 1.png");
                            screen = Screen.LEVEL;
                                break;
                        case TWO:
                            //carga nivel2
                            loadLevel("nivel 2");
                            Assets.background = ImageLoader.loadImage("/images/nivel 2.png");
                            screen = Screen.LEVEL;
                                break;
                        case THREE:
                            //carga nivel3
                            loadLevel("nivel 3");
                            Assets.background = ImageLoader.loadImage("/images/nivel 3.png");
                            screen = Screen.LEVEL;
                                break;
                        case RECIPIES:
                            screen = Screen.RECIPIES;
                                break;
                       case CONTROLS:
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
                            // turns on and off
                            break;
                        default:
                            break;
                    }
                }

                // Checks that you are on the brightness setting
                if (optOpt == OptOpt.BRILLO) {
                    if (keyManager.left) {
                        // disminuye el brillo
                    } else if (keyManager.right) {
                        // aumenta el brillo
                    }
                }

            // Recipies screen ------------------------------------------------------------------
            case RECIPIES:
                if (keyManager.back) {
                    screen = Screen.MENU;
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


                // checks if the escape key was pressed to pause or unpause the game
                if (keyManager.pause) {
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
                     if (player.getX() < playerX || player.getX() + player.getWidth()> 3100 - getPlayerX()) {
                      rec.setRect(rec.x, player.getY() - playerY, getWidth(), getHeight());
                    } else {
                      rec.setRect(player.getX() - playerX, player.getY() - playerY, getWidth(), getHeight());
                    }

                    // checar si el jugador está en la fuente
                    if (fuente.intersects(player.getPerimetro()) && player.getWater() < 100) {
                      player.setWater(player.getWater() + 1);
                    }

                    if (player.getWater() > 0 && (getKeyManager().z || getKeyManager().o)) {
                      //attack
                      if (player.getDirection() == 1) {
                        // attack to the right
                        disparos.add(new Shot(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2, 50, 50, 4, 1, this));
                      } else {
                        // attack to the left
                        disparos.add(new Shot(player.getX(), player.getY() + player.getHeight() / 2, 50, 50, 4, -1, this));
                      }
                      player.setWater(getPlayer().getWater() - 10);
                    }

                    // se tickea a los disparos
                    for (int j = 0; j < disparos.size(); j++) {
                      Shot disp = disparos.get(j);
                      disp.tick();
                      if (disp.getX() + disp.getWidth() <= 0 || disp.getX() >= 3100) {
                        disparos.remove(j);
                      }
                    }


                    // se tickea a los chiles
                    for (int i  = 0; i < chiles.size(); i++) {
                        Enemy chile = chiles.get(i);
                        chile.tick();

                        // se checa que los disparos colisionen con los chiles
                        for (int j = 0; j < disparos.size(); j++) {
                          Shot disp = disparos.get(j);
                          if (disp.intersectaChile(chile)) {
                            chiles.remove(i);
                            disparos.remove(j);
                          }
                        }

                        if (chile.intersectaJugador(player) && player.getContGotHit() == 0) {
                          // chiles.remove(i);
                          // quitarle vida al jugador
                          player.setLives(player.getLives() - 1);
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
                                    Assets.atoleSnd.play();
                                    powerups.remove(i);
                                    break;
                                case AGUA:
                                    // Refill a little bit the players ammo
                                    break;

                                case DULCE:
                                    // Recover 1 life
                                    if (getPlayer().getLives() < getPlayer().getMaxLives())
                                        getPlayer().setLives(getPlayer().getLives() + 1);
                                    Assets.dulceSnd.play();
                                    powerups.remove(i);
                                    break;

                                case FRIJOL:

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
                              player.setLimits(platf.getX(), platf.getX() + platf.getWidth());
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

                    if (player.getY() >= 2100) {
                      player.setSpeedY(0);
                      player.setInTheAir(false);
                    }

                    if (comidas.isEmpty() || player.getLives() == 0) {
                      endGame = true;
                    }

                  } else {
                    if (endGame) {
                      if (keyManager.enter) {
                          unloadLevel();
                          screen = Screen.MENU;
                      }
                    }
                  }
                }

                // ckecks if the game is paused

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
                  g.drawString(nivel, 50, 480);
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
                      nivel = "nivel 1";
                      //g.drawImage(Assets.rec, 1200, 125, 400, 100, null);
                      g.drawImage(Assets.select, 620, 380, 100, 100, null);
                      break;
                    case TWO:
                      nivel = "nivel 2";
                      g.drawImage(Assets.select, 590, 450, 100, 100, null);
                      break;
                    case THREE:
                      nivel = "nivel 3";
                      g.drawImage(Assets.select, 680, 535, 100, 100, null);
                      break;
                  }
                  break;
                case OPTIONS:
                  g.drawImage(Assets.options, 0, 0, getWidth(), getHeight(), null);
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
                    break;
                case RECIPIES:
                  g.drawImage(Assets.recipies, 0, 0, getWidth(), getHeight(), null);
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
                    g.drawImage(Assets.heart, 0 + i * 80, 0, 75 , 75, null); // PLACEHOLDER
                  }

                  // water

                  int iPosX = getWidth() - 55;
                  int iPosY = 20;

                  // dibujar ingredientes recolectados
                  for (int i = 0; i < recolectado.size(); i++) {
                    Comida recol = recolectado.get(i);
                    recol.renderUI(g, iPosX, iPosY);
                    iPosX -= 55;
                  }

                  if (endGame) {
                    g.setFont(texto);
                    if (comidas.isEmpty()) {
                      // you won
                      g.drawString("YOU WON", getWidth()/2 - 15, getHeight()/2 - 10);
                    } else if (player.getLives() == 0) {
                      // you lost
                      g.drawString("YOU LOST", getWidth()/2 - 15, getHeight()/2 - 10);
                    }
                  }

                  break;
            }


           // draw score


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

        //creating the player and the shot
//        player = new Player(getWidth() / 2, getHeight() - 100, 1, 100, 80, this);
//        shot = new Shot(player.getX() + player.getWidth() / 2, player.getY() - player.getHeight(), 5, 5, this) {
//        };
//
//        // set up the initial position for the aliens
//        int iPosX = 0;
//        int iPosY = 10;
//        for (int i = 1; i <= 24; i++) {
//            //aliens.add(new Enemy(iPosX, iPosY, 50, 50, this));
//            iPosX += 60;
//            aliens.add(new Enemy(iPosX, iPosY, 50, 50, 1, this));
//            bombs.add(new Bomb(iPosX, iPosY, 10, 20, this));
//
//            // create 6 aliens every row
//            if (i % 6 == 0) {
//                iPosY += 60;
//                iPosX = 0;
//            }
//
//        }
//
//        // setting up the game variables
//        score = 0;
//        cantAliens = aliens.size();


        // se inicializan las variables
        endGame = false;
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        pauseGame = false;
        confirmSound = Assets.selectSnd;
        nivel = "";
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
                tick();
                render();
                delta--;
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

    // LECTURA Y GUARDADO DE DATOS

    /**
     * To get all the variable that need to be stored in the file as a string
     *
     * @return an <code>String</code> value with all the information of the
     * variables
     */
    public String intoString() {
        //return (score + " " + cantAliens + " " + (endGame ? 1 : 0) +  " " + (pauseGame ? 1 : 0));
        return "";
    }

    // Carga la información del objeto desde un string ------------------------------------------------------------------
    /**
     * To set the value of the score, lives and the state of the game from the
     * file that was loaded
     *
     * @param datos to set all the variables
     */
    public void loadFromString(String[] datos) {
//        this.score = Integer.parseInt(datos[0]);
//        this.cantAliens = Integer.parseInt(datos[1]);
//        this.endGame = (Integer.parseInt(datos[2]) == 1 ? true : false);
//        this.pauseGame = (Integer.parseInt(datos[3]) == 1 ? true : false);
    }

    /**
     * Writes in the given file all the given information
     *
     * @throws IOException when file not found
     */
    public void grabarArchivo() throws IOException {
//        PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
//        fileOut.println(this.toString());
//        fileOut.println(player.toString());
//        fileOut.println(shot.toString());
//        for(Enemy alien : aliens){
//            fileOut.println(alien.toString());
//        }
//        for (Bomb bomb : bombs) {
//            fileOut.println(bomb.toString());
//        }
//
//        fileOut.close();
    }

    /**
     * Load all the information from the given file
     *
     * @throws IOException when file not found
     */
    public void leeArchivo() throws IOException {
       //
       // BufferedReader fileIn;
       // try {
       //     fileIn = new BufferedReader(new FileReader(nombreArchivo));
       // } catch (FileNotFoundException e) {
       //     File archivo = new File(nombreArchivo);
       //     PrintWriter fileOut = new PrintWriter(archivo);
       //     fileOut.println("100,demo");
       //     fileOut.close();
       //     fileIn = new BufferedReader(new FileReader(nombreArchivo));
       // }
       // loadFromString(fileIn.readLine().split("\\s+"));
       // this.player.loadFromString(fileIn.readLine().split("\\s+"));
       // shot.loadFromString(fileIn.readLine().split("\\s+"));
       // for(Enemy alien : aliens){
       //     alien.loadFromString(fileIn.readLine().split("\\s+"));
       // }
       // for (Bomb bomb : bombs) {
       //     bomb.loadFromString(fileIn.readLine().split("\\s+"));
       // }
       //
       // fileIn.close();
        // if (getKeyManager().save) {
        //     try {
        //         grabarArchivo();
        //     } catch (IOException ex) {
        //         Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        //     }
        // }
        // if (getKeyManager().load) {
        //     try {
        //         leeArchivo();
        //     } catch (IOException ex) {
        //         Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        //     }
        // }
    }
}
