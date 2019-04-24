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

/**
 *
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández
 * A01410921
 */
public class Game implements Runnable {

    private BufferStrategy bs;                              // to have several buffers when displaying
    private Graphics g;                                     // to paint objects
    private Display display;                                // to display in the game
    String title;                                           // title of the window
    private int width;                                      // width of the window
    private int height;                                     // height of the window
    private Thread thread;                                  // thread to create the game
    private boolean running;                                // to set the game
    private Player player;                                  // to use a player
    private KeyManager keyManager;                          // to manage the keyboard
    private LinkedList<Enemy> aliens;                       // to move an enemy
    private boolean endGame;                                // to know when to end the game
    private int score;                                      // to store the score
    private boolean pauseGame;                              // flag to know if the game is paused
    private int cantAliens;                                 // to store the quantity of remaining aliens
    private String nombreArchivo;                           // to store the name of the file
    private Font texto;                                     // to change the font of string drawn in the screen
    private LinkedList<Bomb> bombs;
    private Shot shot;                              //to have a missile to shoot
    
    private Screen screen;                  // to store in which screen you are
    private MenuOpt menOpt;             // to store in which option in the menu you are
    private MouseManager mouseManager;          // to manage the mouse
    private boolean canChangeScreen;            // to know if you can change the screen
    private boolean sideOfMenu;                 // flag to know in which column of the menu are you

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
//        aliens = new LinkedList<Enemy>();
        //powerUps = new LinkedList<PowerUps>();
//        //pollos = new LinkedList<PowerUps>();
//        nombreArchivo = "src/space/inavders/archivo.sf";
//        texto = new Font("Font", 2, 32);
//        bombs = new LinkedList<Bomb>();
        screen = Screen.TITLESCREEN;
        mouseManager = new MouseManager();
    }

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
    public LinkedList<Enemy> getBricks() {
        return aliens;
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
     * To get the quantity of bricks that haven't been destroyed
     * @return an <code>int</code> value with the quantity of bricks that haven't been destroyed
     */
    public int getCantAliens() {
        return cantAliens;
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
     * To get the current score
     *
     * @return an <code>int</code> value with the score
     */
    public int getScore() {
        return score;
    }

    /**
     * To set if the game is ended
     *
     * @param endGame to set the state of the game
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * To set the score
     *
     * @param score to set the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * To set the shot
     * @param shot to set the shot
     */
    public void setShot(Shot shot) {
        this.shot = shot;
    }

    /**
     * To get the key manager
     * @return an <code>KeyManager</code> value with the key manager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

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
        endGame = false;
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        pauseGame = false;
        canChangeScreen = true;
        sideOfMenu = true;
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

    private void tick() {
        // ticks key manager
        keyManager.tick();
        
        // checks in which screen you are
        switch(screen) {
            case TITLESCREEN:
                if (keyManager.enter) {
                    screen = Screen.MENU;
                    menOpt = MenuOpt.OPTIONS;
                    canChangeScreen = false;
                }
                break;
            case MENU:
                // prevents you from going directly from the menu screen into the options screen coming from the title screen
                if (!canChangeScreen && !keyManager.enter) {
                    canChangeScreen = true;
                }
                // Checks if you are on the right or left column of the options
                if (sideOfMenu) {
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
                        }
                    }
                }
                if (!sideOfMenu) {
                    
                }
                
                // Checks to which screen you are moving to
                if (keyManager.enter && canChangeScreen) {
                    switch(menOpt) {
                        case OPTIONS:
                            screen = Screen.OPTIONS;
                            break;
                        case ONE:
                            //carga nivel 1
                            screen = Screen.LEVEL;
                                break;
                        case TWO:
                            //carga nivel2
                            screen = Screen.LEVEL;
                                break;
                        case THREE:
                            //carga nivel3
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
            case OPTIONS:
                if (keyManager.back) {
                    screen = Screen.MENU;
                }
                break;
            case RECIPIES:
                if (keyManager.back) {
                    screen = Screen.MENU;
                }
                break;
            case CONTROLS:
                if (keyManager.back) {
                    screen = Screen.MENU;
                }
                break;
            case LEVEL:
                
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
//                    g.drawImage(Assets.titleScreen, 0, 0, 1920, 1080, null);
                    g.drawImage(Assets.titleScreen, 0, 0, 1080, 720, null);
                    break;
                case MENU:
//                    g.drawImage(Assets.menu, 0, 0, 1920, 1080, null);
                    g.drawImage(Assets.menu, 0, 0, 1080, 720, null);
                    // Checks where to draw the rectangle that shows which option of the menu you are selecting
                    switch(menOpt) {
                        case OPTIONS:
//                            g.drawImage(Assets.rec, 1340, 125, 400, 100, null);
                            g.drawImage(Assets.rec, 800, 80, 200, 100, null);
                            break;
                        case RECIPIES:
//                            g.drawImage(Assets.rec, 1340, 200, 400, 100, null);
                            g.drawImage(Assets.rec, 800, 120, 200, 100, null);
                            break;
                       case CONTROLS:
//                           g.drawImage(Assets.rec, 1340, 280, 400, 100, null);
                           g.drawImage(Assets.rec, 800, 180, 200, 100, null);
                                break;
                        case ONE:
//                            g.drawImage(Assets.rec, 1200, 125, 400, 100, null);
                            g.drawImage(Assets.rec, 1340, 125, 400, 100, null);
                            //carga nivel 1
                                break;
                        case TWO:
                            //carga nivel2
                                break;
                        case THREE:
                            //carga nivel3
                                break;
                    }
                    break;
                case OPTIONS:
//                    g.drawImage(Assets.options, 0, 0, 1920, 1080, null);
                    g.drawImage(Assets.options, 0, 0, 1080, 720, null);

                    break;
                case RECIPIES:
                    g.drawImage(Assets.recipies, 0, 0, 1080, 720, null);
                    break;
                case CONTROLS:
                    g.drawImage(Assets.controls, 0, 0, 1080, 720, null);
                    break;
                case LEVEL:
                    g.drawImage(Assets.menu, 0, 0, 1080, 720, null);
                    break;
            }
            
//            g.setFont(texto);
//            // draw score
//            g.drawString("Score: " + score, 5, getHeight() - 20);

            bs.show();
            g.dispose();
        }

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

    /**
     * To get all the variable that need to be stored in the file as a string
     *
     * @return an <code>String</code> value with all the information of the
     * variables
     */
    public String toString() {
        return (score + " " + cantAliens + " " + (endGame ? 1 : 0) +  " " + (pauseGame ? 1 : 0));
    }

    // Carga la información del objeto desde un string
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

    String getLives() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setLives(int lives) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
