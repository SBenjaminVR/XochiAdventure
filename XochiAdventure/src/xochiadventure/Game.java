/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
        aliens = new LinkedList<Enemy>();
        //powerUps = new LinkedList<PowerUps>();
        //pollos = new LinkedList<PowerUps>();
        nombreArchivo = "src/space/inavders/archivo.sf";
        texto = new Font("Font", 2, 32);
        bombs = new LinkedList<Bomb>();
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
        player = new Player(getWidth() / 2, getHeight() - 100, 1, 100, 80, this);
        shot = new Shot(player.getX() + player.getWidth() / 2, player.getY() - player.getHeight(), 5, 5, this) {
        };

        // set up the initial position for the aliens
        int iPosX = 0;
        int iPosY = 10;
        for (int i = 1; i <= 24; i++) {
            //aliens.add(new Enemy(iPosX, iPosY, 50, 50, this));
            iPosX += 60;
            aliens.add(new Enemy(iPosX, iPosY, 50, 50, 1, this));
            bombs.add(new Bomb(iPosX, iPosY, 10, 20, this));

            // create 6 aliens every row
            if (i % 6 == 0) {
                iPosY += 60;
                iPosX = 0;
            }

        }

        // setting up the game variables
        score = 0;
        cantAliens = aliens.size();
        endGame = false;
        display.getJframe().addKeyListener(keyManager);
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

    private void tick() {
        // ticks key manager
        keyManager.tick();

        // ckecks flags for pausing, saving, and loading
        if (getKeyManager().pause) {
            pauseGame = !pauseGame;
        }
        if (getKeyManager().save) {
            try {
                grabarArchivo();
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (getKeyManager().load) {
            try {
                leeArchivo();
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // checks flag for restarting and if that the game has ended
        if (getKeyManager().restart) {

            // resets the player position
            player.setX(getWidth() / 2);
            player.setY(getHeight() - player.getHeight());
            
            // reset shot
            shot.setX(player.getX() + player.getWidth() / 2);
            shot.setY(player.getY());
            shot.setFired(false);

            aliens.clear();
            bombs.clear();

            // set up the initial position for the aliens
            int iPosX = 0;
            int iPosY = 10;
            for (int i = 1; i <= 24; i++) {

                iPosX += 60;
                aliens.add(new Enemy(iPosX, iPosY, 50, 50, 1, this));
                bombs.add(new Bomb(iPosX, iPosY, 10, 20, this));

                // create 6 aliens every row
                if (i % 6 == 0) {
                    iPosY += 60;
                    iPosX = 0;
                }
            }

            // setting up the game variables
            score = 0;
            cantAliens = aliens.size();
            endGame = false;
            pauseGame = false;
        }

        if (!endGame && !pauseGame) {
            
            // ticks the player
            player.tick();

            //initializes shot and moves it
            if (keyManager.fireShot && !shot.isFired()) {
                shot.setFired(true);
                Assets.laser.play();
            }

            //ticks shot
            if (shot.isFired()) {
                shot.tick();
            } else {
                shot.setX(player.getX() + player.getWidth() / 2);
                shot.setY(player.getY());
            }
            if (shot.getY() <= 0) {
                shot.setX(player.getX() + player.getWidth() / 2);
                shot.setY(player.getY());
                shot.setFired(false);

            }


            //movement controler for aliens
            //ticking all aliens
            for (int i = 0; i < aliens.size(); i++) {
                Enemy alien = aliens.get(i);
                alien.tick();
                //alien movement
                if ((aliens.get(0).getX() + (50 * 6) + (10 * 5) >= getWidth())) {
                    aliens.get(i).setDirection(-1);
                    aliens.get(i).setY(alien.getY() + 40);
                }
                if ((aliens.get(23).getX() - (50 * 6) - (10) <= 0)) {
                    aliens.get(i).setDirection(1);
                    aliens.get(i).setY(alien.getY() + 40);
                }
                if (aliens.get(i).getY() >= getHeight() - 50) {
                    endGame = true;
                }
                // collision with bricks

                if (!alien.isDestroyed()) {
                    if (shot.intersectaAlien(alien)) {
                        if (!alien.isDestroyed()) {
                            if (shot.intersectaAlien(alien)) {
                                shot.setFired(false);
                                shot.setX(player.getX() + player.getWidth() / 2);
                                shot.setY(player.getY());
                                alien.setDestroyed(true);
                                cantAliens--;
                                score += 10;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < bombs.size(); i++) {
                Bomb bomb = bombs.get(i);
                int rand = (int) (Math.random() * 5000);
                if (rand < 2) {
                    bomb.setFired(true);
                    bomb.setX(aliens.get(i).getX() + aliens.get(i).getWidth() / 2);
                }
                if (bomb.isFired()) {
                    bomb.tick();
                    if (bomb.isFired() && bomb.intersectaJugador(player)) {
                        endGame = true;
                        bomb.setFired(false);
                        Enemy enem = aliens.get(i);
                        bomb.setX(enem.getX() + enem.getWidth() / 2);
                        bomb.setY(enem.getY() + enem.getHeight());
                    }

                    // if all aliens are destroyed the game is ended
                    if (cantAliens == 0) {
                        endGame = true;
                    }
                }
            }
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
            g.drawImage(Assets.background, 0, 0, width, height, null);
            if (pauseGame) {
                g.drawString("PAUSA", getWidth() / 2 - 60, getHeight() / 2);
            }
            if (endGame) {
                //g.drawImage(Assets.endGame, getWidth() / 2 - 131, getHeight() / 2 - 30, 262, 60, null);
                // we draw a string saying game over and another string giving instructions to the player on how to restart the game
                g.drawString("Game Over", getWidth() / 2 - 80, getHeight() / 2);
                g.drawString("Press 'r' to restart", getWidth() / 2 - 120, getHeight() / 2 + 30);
            } else {

                // rendering the ball and player
                if (shot.isFired()) {
                    shot.render(g);
                }
                player.render(g);
                //ball.render(g);

                //rendering all bricks
                for (int i = 0; i < aliens.size(); i++) {
                    Enemy alien = aliens.get(i);
                    if (!alien.isDestroyed()) {
                        alien.render(g);

                    }

                }
                // rendering all bombs
                for (int i = 0; i < bombs.size(); i++) {
                    Bomb bomb = bombs.get(i);
                    if (bomb.isFired()) {
                        bomb.render(g);
                    }
                }

            }
            // draw score
            g.drawString("Score: " + score, 5, getHeight() - 20);

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
        this.score = Integer.parseInt(datos[0]);
        this.cantAliens = Integer.parseInt(datos[1]);
        this.endGame = (Integer.parseInt(datos[2]) == 1 ? true : false);
        this.pauseGame = (Integer.parseInt(datos[3]) == 1 ? true : false);
    }

    /**
     * Writes in the given file all the given information
     *
     * @throws IOException when file not found
     */
    public void grabarArchivo() throws IOException {
        PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
        fileOut.println(this.toString());
        fileOut.println(player.toString());
        fileOut.println(shot.toString());
        for(Enemy alien : aliens){
            fileOut.println(alien.toString());
        }
        for (Bomb bomb : bombs) {
            fileOut.println(bomb.toString());
        }

        fileOut.close();
    }

    /**
     * Load all the information from the given file
     *
     * @throws IOException when file not found
     */
    public void leeArchivo() throws IOException {

        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            File archivo = new File(nombreArchivo);
            PrintWriter fileOut = new PrintWriter(archivo);
            fileOut.println("100,demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        }
        loadFromString(fileIn.readLine().split("\\s+"));
        this.player.loadFromString(fileIn.readLine().split("\\s+"));
        shot.loadFromString(fileIn.readLine().split("\\s+"));
        for(Enemy alien : aliens){
            alien.loadFromString(fileIn.readLine().split("\\s+"));
        }
        for (Bomb bomb : bombs) {
            bomb.loadFromString(fileIn.readLine().split("\\s+"));
        }

        fileIn.close();
    }

    String getLives() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setLives(int lives) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
