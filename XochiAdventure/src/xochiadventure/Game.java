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
//    private String nombreArchivo;                         // to store the name of the file
//    private Font texto;                                   // to change the font of string drawn in the screen
    private Shot shot;                                      //to have a missile to shoot
    private Rectangle rec;

    // Linked lists
    private LinkedList<Platform> platforms;
    private LinkedList<Enemy> chiles;                       // to move an enemy
    private LinkedList<PowerUps> powerups;
    private LinkedList<Comida> comidas;

    // Menu navigation variables
    private Screen screen;                  // to store in which screen you are
    private MenuOpt menOpt;             // to store in which option in the main menu screen you are
    private OptOpt optOpt;                  // to store in which option in the options screen you are

    private int playerX;
    private int playerY;

    private MouseManager mouseManager;          // to manage the mouse

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
        //powerUps = new LinkedList<PowerUps>();
       //pollos = new LinkedList<PowerUps>();
       // nombreArchivo = "src/space/inavders/archivo.sf";
       // texto = new Font("Font", 2, 32);
       // bombs = new LinkedList<Bomb>();
        screen = Screen.TITLESCREEN;
        mouseManager = new MouseManager();
        powerups = new LinkedList<PowerUps>();
        platforms = new LinkedList<Platform>();
        comidas = new LinkedList<Comida>();
        rec = new Rectangle(0, 0, getWidth(), getHeight());
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
      int iPosX = 10;
      int iPosY = 10;

      // se crean los chiles
      for (int i  = 0; i < 5; i++) {
          if (i % 2 == 0) {
              direction = 1;
          } else {
              direction = -1;
          }
          chiles.add(new Enemy(iPosX, iPosY, 50, 50, direction, 5, this));
          iPosX += 50;
          iPosY += 50;
      }

      iPosX = 50;
      iPosY = 10;

      // se crean los powerups
      for (int i  = 0; i < 2; i++) {
          powerups.add(new PowerUps(iPosX, iPosY, 50, 50, 5, 0, this));
          iPosX += 50;
          iPosY += 50;
      }

      iPosX = 100;
      iPosY = 10;
      // se crean las plataformas
      // for (int i  = 0; i < 2; i++) {
      //     platforms.add(new Platform(iPosX, iPosY, 50, 50, 5, this));
      //     iPosX += 50;
      //     iPosY += 50;
      // }
      platforms.add(new Platform(0, 250, 500, 200, 0, this));
      platforms.add(new Platform(1300, 250, 500, 200, 0, this));
      platforms.add(new Platform(2100, 250, 500, 200, 0, this));

      platforms.add(new Platform(550, 500, 500, 50, 0, this));
      platforms.add(new Platform(900, 500, 500, 50, 0, this));
      platforms.add(new Platform(250, 650, 2100, 200, 0, this));

      platforms.add(new Platform(0, 900, 150, 50, 0, this));
      Platform plat = platforms.get(5);
      platforms.add(new Platform(plat.getX() - plat.getWidth() / 2 - 75, 900, 150, 50, 0, this));
      platforms.add(new Platform(2450, 900, 150, 200, 0, this));

      platforms.add(new Platform(0, 1200, 500, 200, 0, this));
      platforms.add(new Platform(1000, 1200, 500, 200, 0, this));
      platforms.add(new Platform(1500, 1200, 500, 200, 0, this));

      platforms.add(new Platform(2350, 1200, 500, 200, 0, this));
      platforms.add(new Platform(600, 1500, 150, 50, 0, this));
      platforms.add(new Platform(1800, 1500, 500, 200, 0, this));

      platforms.add(new Platform(0, 1800, 500, 200, 0, this));
      platforms.add(new Platform(750, 1800, 1350, 200, 0, this));
      platforms.add(new Platform(2000, 1800, 500, 200, 0, this));

      // for (int i  = 0; i < 5; i++) {
      //     comida.add(new Platform(iPosX, iPosY, 50, 50, 5, this));
      //     iPosX += 50;
      //     iPosY += 50;
      // }
      player = new Player (100, 100, 100, 100, 5, 0 ,this);
      playerX = getWidth() / 2 - player.getWidth() / 2;
      playerY = getHeight() / 2 - player.getHeight() / 2;

//      System.out.println("x and y " + playerX + " " + playerY);
//      System.out.println("player " + player.getX() + " " + player.getY());
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
                    switch(menOpt) {
                        case OPTIONS:
                            screen = Screen.OPTIONS;
                            optOpt = OptOpt.DALTONICO;
                            break;
                        case ONE:
                            //carga nivel 1
                            loadLevel("nivel 1");
                            Assets.background = ImageLoader.loadImage("/images/nivel 1.jpg");
                            screen = Screen.LEVEL;
                                break;
                        case TWO:
                            //carga nivel2
                            loadLevel("nivel 2");
                            Assets.background = ImageLoader.loadImage("/images/nivel 2.jpg");
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
                if (keyManager.back) {
                    unloadLevel();
                    screen = Screen.MENU;
                } else {
                  player.tick();
                  if (player.getX() < playerX) {
                    rec.setRect(rec.x, player.getY() - playerY, getWidth(), getHeight());
                  } else {
                    rec.setRect(player.getX() - playerX, player.getY() - playerY, getWidth(), getHeight());
                  }


                  // se tickea a los chiles
                  for (int i  = 0; i < chiles.size(); i++) {
                      Enemy chile = chiles.get(i);
                      chile.tick();
                  }

                  // se tickea a los powerups
                  for (int i  = 0; i < powerups.size(); i++) {
                      PowerUps power = powerups.get(i);
                      power.tick();
                  }

                  // se tickean las plataformas
                  for (int i  = 0; i < platforms.size(); i++) {
                      Platform platform = platforms.get(i);
                      platform.tick();
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
            // Checks which screen to render
            switch(screen) {
                case TITLESCREEN:
                    g.drawImage(Assets.titleScreen, 0, 0, getWidth(), getHeight(), null);
                    break;
                case MENU:
                  g.drawImage(Assets.menu, 0, 0, getWidth(), getHeight(), null);
                  // Checks where to draw the rectangle that shows which option of the menu you are selecting
                  switch(menOpt) {
                    case OPTIONS:
                      //g.drawImage(Assets.rec, 1340, 125, 400, 100, null);
                      g.drawImage(Assets.rec, 800, 60, 200, 100, null);
                      break;
                    case RECIPIES:
                      //g.drawImage(Assets.rec, 1340, 200, 400, 100, null);
                      g.drawImage(Assets.rec, 800, 120, 200, 100, null);
                      break;
                   case CONTROLS:
                      //g.drawImage(Assets.rec, 1340, 280, 400, 100, null);
                      g.drawImage(Assets.rec, 800, 180, 200, 100, null);
                      break;
                    case ONE:
                      //g.drawImage(Assets.rec, 1200, 125, 400, 100, null);
                      g.drawImage(Assets.rec, 600, 125, 100, 100, null);
                      break;
                    case TWO:
                      g.drawImage(Assets.rec, 600, 150, 100, 100, null);
                      break;
                    case THREE:
                      g.drawImage(Assets.rec, 600, 175, 100, 100, null);
                      break;
                  }
                  break;
                case OPTIONS:
                  g.drawImage(Assets.options, 0, 0, getWidth(), getHeight(), null);
                  switch (optOpt) {
                      case DALTONICO:
                          g.drawImage(Assets.rec, 290, 140, 500, 100, null);
                          break;
                      case SONIDO:
                          g.drawImage(Assets.rec, 290, 260, 500, 100, null);
                          break;
                      case BRILLO:
                          g.drawImage(Assets.rec, 290, 380, 500, 100, null);
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
                  g.drawImage(Assets.background, 0, 0, getWidth(), getHeight(), null);

                  // g.drawImage(Assets.rectangle, (int)(rec.getX()), (int)(rec.getY()), (int)(rec.getWidth()), (int)(rec.getHeight()), null);
                  // g.drawImage(Assets.rectangle, player.getX() - playerX, player.getY() - playerY, (int)(rec.getWidth()), (int)(rec.getHeight()), null);

                  player.render(g);

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

                  // dibujar las plataformas
                  for (int i = 0; i < platforms.size(); i++) {
                    Platform platform = platforms.get(i);
                    // System.out.println("platform" + i + " " + (rec.intersects(platform.getPerimetro())));
                    if (rec.intersects(platform.getPerimetro())) {
                      platform.render(g);
                    }
                  }

                  // dibujar comidas
                  for (int i = 0; i < comidas.size(); i++) {
                    Comida comida = comidas.get(i);
                    if (rec.intersects(comida.getPerimetro())) {
                      comida.render(g);
                    }
                  }
                  break;
            }

           // g.setFont(texto);
           // draw score
           // g.drawString("Score: " + score, 5, getHeight() - 20);

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
//        BufferedReader fileIn;
//        try {
//            fileIn = new BufferedReader(new FileReader(nombreArchivo));
//        } catch (FileNotFoundException e) {
//            File archivo = new File(nombreArchivo);
//            PrintWriter fileOut = new PrintWriter(archivo);
//            fileOut.println("100,demo");
//            fileOut.close();
//            fileIn = new BufferedReader(new FileReader(nombreArchivo));
//        }
//        loadFromString(fileIn.readLine().split("\\s+"));
//        this.player.loadFromString(fileIn.readLine().split("\\s+"));
//        shot.loadFromString(fileIn.readLine().split("\\s+"));
//        for(Enemy alien : aliens){
//            alien.loadFromString(fileIn.readLine().split("\\s+"));
//        }
//        for (Bomb bomb : bombs) {
//            bomb.loadFromString(fileIn.readLine().split("\\s+"));
//        }
//
//        fileIn.close();
    }
}
