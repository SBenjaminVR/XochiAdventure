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
import java.awt.image.BufferedImage;
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
import xochiadventure.Assets;

// enum to navigate all the screens that the game has
enum Screen {
  TITLESCREEN,
  STORY,
  MENU,
  OPTIONS,
  RECIPIES,
  LEVEL,
  CONTROLS,
  CREDITS
}

// enum to navigate through all the options in the Main menu
enum MenuOpt {
  ONE,
  TWO,
  THREE,
  OPTIONS,
  CONTROLS,
  RECIPIES,
  CREDITS
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
  
  private int[] selectValues = {0, 0, 100, 100};
  private int[] menuOptionValues = {0, 0, 0, 0};
  private BufferedImage selectImage;
  
  private GameLevel gameLevel;

  private String[] credits;

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
    
    hasPlayedWinSnd = false;
    brightness = 3;
    menuMusicPlaying = false;
    effectsOn = true;

    credits = new String[4];
    credits[0] = "Alejandra García - Programadora";
    credits[1] = "Benjamín Váldez - Programador";
    credits[2] = "Humberto González - Programador";
    credits[3] = "Melba Consuelos - Artista";
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
   * To check if the game has ended
   *
   * @return an <code>boolean</code> value of the state of the game
   */
  public boolean isEndGame() {
    return endGame;
  }

  public boolean isEffectsOn() {
    return effectsOn;
  }

  /**
   * To get the key manager
   * @return an <code>KeyManager</code> value with the key manager
   */
  public KeyManager getKeyManager() {
    return keyManager;
  }
  
  /**
   * To get the current level
   * @return an <code>int</code> value with the current level
   */
  public int getNivel() {
    return nivel;
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
  private void loadLevel() throws SQLException{

    gameLevel = new GameLevel(this);

    gameLevel.loadLevel(nivel);
    
    endGame = false;
    pauseGame = false;
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
          // Checks if the main menu song is already playing, if it is not it plays it
          if (!menuMusicPlaying && soundOn) {
            Assets.mainMenuMusic.play();
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
                menOpt = MenuOpt.CREDITS;
                break;
              case CREDITS:
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
                menOpt = MenuOpt.CREDITS;
                break;
              case RECIPIES:
                menOpt = MenuOpt.OPTIONS;
                break;
              case CONTROLS:
                menOpt = MenuOpt.RECIPIES;
                break;
              case CREDITS:
                menOpt = MenuOpt.CONTROLS;
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
              case CREDITS:
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
            // Checks if the main menu song is playing, it it is it stops it
            if (menuMusicPlaying) {
              Assets.mainMenuMusic.stop();
              menuMusicPlaying = false;
            }

            if (soundOn) {
              Assets.selectSnd.play();
            }

            // Checks to which screen you are changing to
            switch(menOpt) {
              case OPTIONS:
                screen = Screen.OPTIONS;
                optOpt = OptOpt.DALTONICO;
                break;
              case ONE:
                if (soundOn) {
                  Assets.level1Music.play();
                }
                nivel = 1;
                // DBFunctions.loadLevelFromDB(nivel, this);
                loadLevel();
                Assets.background = ImageLoader.loadImage("/images/niveles/nivel_1.png");
                screen = Screen.LEVEL;
                break;
              case TWO:
                if (soundOn) {
                  Assets.level2Music.play();
                }
                nivel = 2;
                // DBFunctions.loadLevelFromDB(nivel, this);
                loadLevel();
                Assets.background = ImageLoader.loadImage("/images/niveles/nivel_2.png");
                screen = Screen.LEVEL;
                break;
              case THREE:
                if (soundOn) {
                  Assets.level3Music.play();
                }
                nivel = 3;
                // DBFunctions.loadLevelFromDB(nivel, this);
                loadLevel();
                Assets.background = ImageLoader.loadImage("/images/niveles/nivel_3.png");
                screen = Screen.LEVEL;
                break;
              case RECIPIES:
                screen = Screen.RECIPIES;
                break;
              case CONTROLS:
                screen = Screen.CONTROLS;
                break;
              case CREDITS:
                screen = Screen.CREDITS;
                break;
            }
          }

          break;

        // Options screen ------------------------------------------------------------------
        case OPTIONS:
            
          // Checks if the user presses the backspace button to return to the main menu
          if (keyManager.back) {
            screen = Screen.MENU;
          }

          // Checks if the down arrow key was pressed
          if (keyManager.down) {
            // Navigates the Options menu
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

          // Checks if the up arrow key was pressed
          if (keyManager.up) {
            // Navigates the Options menu
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

          // Checks if the enter key was pressed
          if (keyManager.enter) {
            // Toggles on and off the options
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
            
          // Checks if the user presses the backspace button to return to the main menu
          if (keyManager.back) {
            screen = Screen.MENU;                    
          }

          // Checks if the right or left arrow keys were pressed to navigate the recipies book
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
          // Checks if the user presses the backspace button to return to the main menu
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
            // gameLevel.unloadLevel();
            screen = Screen.MENU;
          } else {
            // If the game is not paused or has not ended, the game continues
            if (!pauseGame && !endGame) {

              gameLevel.tick(keyManager);

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
                      // unloadLevel();
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
                  // unloadLevel();
                  loadLevel();
                  endGame = false;
                  pauseGame = false;
                }
              }
            }
          }

          break;
          case CREDITS:
          // Checks if the user presses enter to advance to the next screen
          if (keyManager.back) {
            screen = Screen.MENU;
            menOpt = MenuOpt.CREDITS;
          }
          break;
    }
  }


  private void renderScreen() {
	  switch(screen) {
	      case TITLESCREEN:
	        // Title screen image
	        g.drawImage(Assets.menuScreens[Assets.Screen.TITLESCREEN.ordinal()], 0, 0, getWidth(), getHeight(), null);
	        break;
	
	      case STORY:
	        g.drawImage(Assets.menuScreens[Assets.Screen.STORY.ordinal()], 0, 0, getWidth(), getHeight(), null);
	        break;
	
	      case MENU:
	      
	        // Main menu background image
	        g.drawImage(Assets.menuScreens[Assets.Screen.MENU.ordinal()], 0, 0, getWidth(), getHeight(), null);
	
	        //Elementos decorativos en el mapa
	        g.drawImage(Assets.mainMenu[Assets.MainMenu.CACTUS.ordinal()], 478, 220, Assets.mainMenu[Assets.MainMenu.CACTUS.ordinal()].getWidth(), Assets.mainMenu[Assets.MainMenu.CACTUS.ordinal()].getHeight(), null);
	        g.drawImage(Assets.mainMenu[Assets.MainMenu.PIRAMIDE.ordinal()], 990, 420, Assets.mainMenu[Assets.MainMenu.PIRAMIDE.ordinal()].getWidth(), Assets.mainMenu[Assets.MainMenu.PIRAMIDE.ordinal()].getHeight(), null);
	        g.drawImage(Assets.mainMenu[Assets.MainMenu.CRAB.ordinal()], 730, 460, Assets.mainMenu[Assets.MainMenu.CRAB.ordinal()].getWidth(), Assets.mainMenu[Assets.MainMenu.CRAB.ordinal()].getHeight(), null);
	
	        // Checks where to draw the cursor that shows which option of the menu you are selecting
	        // and whether to show a preview of the level you are selecting or a picture of the mian character
	        BufferedImage idk = Assets.mainMenu[Assets.MainMenu.XOCHI.ordinal()];
	        
	        if (menOpt == MenuOpt.OPTIONS || menOpt == MenuOpt.RECIPIES || menOpt == MenuOpt.CONTROLS) {
	        	menuOptionValues[0] = 20;
	        	menuOptionValues[1] = 520;
	        	menuOptionValues[2] = 400;
	        	menuOptionValues[3] = 225;
	        	idk = Assets.mainMenu[Assets.MainMenu.XOCHI.ordinal()];
	        	switch(menOpt) {
	            case OPTIONS:
	              	selectValues[0] = 810;
	              	selectValues[1] = 70;
	              break;
	            case RECIPIES:
	          	  	selectValues[0] = 810;
	            	selectValues[1] = 125;
	              break;
	            case CONTROLS:
	          	  	selectValues[0] = 770;
	            	selectValues[1] = 185;
	              break;
	          }
	        } else {
	        	menuOptionValues[0] = 40;
	        	menuOptionValues[1] = 420;
	        	menuOptionValues[2] = 400;
	        	menuOptionValues[3] = 230;
	        	switch(menOpt) {
	            case ONE:
	            	selectValues[0] = 620;
	            	selectValues[1] = 380;
	            	idk = Assets.mainMenu[Assets.MainMenu.LEVEL_1.ordinal()];
	              break;
	            case TWO:
	          	  	selectValues[0] = 590;
	            	selectValues[1] = 450;
	            	idk = Assets.mainMenu[Assets.MainMenu.LEVEL_2.ordinal()];
	              break;
	            case THREE:
	          	  	selectValues[0] = 680;
	            	selectValues[1] = 535;
	            	idk = Assets.mainMenu[Assets.MainMenu.LEVEL_3.ordinal()];
	              break;
	          }
	        }
	
	        g.drawImage(selectImage, selectValues[0], selectValues[1], selectValues[2], selectValues[3], null);
	        g.drawImage(idk, menuOptionValues[0], menuOptionValues[1], menuOptionValues[2], menuOptionValues[3], null);
	        break;
	      case OPTIONS:
	        // Options menu background
	        g.drawImage(Assets.menuScreens[Assets.Screen.OPTIONS.ordinal()], 0, 0, getWidth(), getHeight(), null);
	
	        // Shows if the sound/effecstOn option is on/off
	        if (soundOn) {
	          g.drawImage(Assets.menuIcons[Assets.MenuIcons.FLECHITA.ordinal()], 920, 278, 34, 34, null);
	        }
	        if (effectsOn) {
	          g.drawImage(Assets.menuIcons[Assets.MenuIcons.FLECHITA.ordinal()], 920, 172, 34, 34, null);
	        }
	
	        // Shows which option you are selecting
	        switch (optOpt) {
	          case DALTONICO:
	            g.drawImage(selectImage, 190, 145, 100, 100, null);
	            break;
	          case SONIDO:
	            g.drawImage(selectImage, 190, 255, 100, 100, null);
	            break;
	          case BRILLO:
	            g.drawImage(selectImage, 190, 370, 100, 100, null);
	            break;
	        }
	        
	        BufferedImage brightnessOption = Assets.opbrightness3;;
	
	        // Shows how much brightness you are selecting
	        switch (brightness) {
	          case 1:
	        	  brightnessOption = Assets.opbrightness1;
	            break;
	          case 2:
	        	  brightnessOption = Assets.opbrightness2;
	            break;
	          case 3:
	        	  brightnessOption = Assets.opbrightness3;
	            break;
	          case 4:
	        	  brightnessOption = Assets.opbrightness4;
	            break;
	          case 5:
	        	  brightnessOption = Assets.opbrightness5;
	            break;
	        }
	        g.drawImage(brightnessOption, 600 , 380, 360 , 70, null);
	        break;
	      case RECIPIES:
	          // Sets the color for the name of the ingredients
	          g.setColor(Color.BLACK);
	
	          // Recipies menu background image
	          g.drawImage(Assets.menuScreens[Assets.Screen.RECIPIES.ordinal()], 0, 0, getWidth(), getHeight(), null);
	
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
	        g.drawImage(Assets.menuScreens[Assets.Screen.CONTROLS.ordinal()], 0, 0, getWidth(), getHeight(), null);
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
	              g.drawImage(selectImage, getWidth() / 2 - 200, getHeight() / 2 - 10, 100, 100, null);
	              break;
	            case EXIT:
	              g.drawImage(selectImage, getWidth() / 2 - 265, getHeight() / 2 + 60, 100, 100, null);
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
  }

  private void renderBrightness() {
	  BufferedImage brightnessImage;
      switch(brightness) {
        case 1:
        	brightnessImage = Assets.brightness1;
          break;
        case 2:
        	brightnessImage = Assets.brightness2;
          break;
        case 4:
        	brightnessImage = Assets.brightness4;
          break;
        case 5:
        	brightnessImage = Assets.brightness5;
          break;
        default:
        	brightnessImage = null;
        	break;
      }
      if (brightnessImage != null) {    	  
    	  g.drawImage(brightnessImage, 0 , 0, getWidth() , getHeight(), null);
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

      g.drawImage(Assets.blueBackground, 0, 0, getWidth(), getHeight(), null);
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
              case CREDITS:
                g.drawImage(Assets.select, 805, 240, 100, 100, null);
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

            gameLevel.render(g);

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

            break;
          case CREDITS:
            int i = 0;
            int x = getWidth() / 2, y = getHeight() / 2 - (credits.length / 2 * 40);
            g.drawImage(Assets.credits, x - 190, 100, 380, 82, null);
            for (String credit : credits) {
              g.drawString(credit, x - (credit.length() / 2 * 16) , y + 35 * i);
              i++;
            }
            g.drawString("Presione backspace para regresar al menu principal", x - 385 , getHeight() - 100);
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
    selectImage = Assets.menuIcons[Assets.MenuIcons.SELECT.ordinal()];

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
    int fps = 60;
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