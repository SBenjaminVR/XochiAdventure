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
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
  private BufferStrategy bs;                 // to have several buffers when displaying
  private Graphics g;                        // to paint objects
  private Display display;                   // to display in the game
  String title;                              // title of the window
  private int width;                         // width of the window
  private int height;                        // height of the window
  private Thread thread;                     // thread to create the game
  private boolean running;                   // to set the game
  private int brightness;                    // to set the brightness of the game

  // Game logic variables
  private KeyManager keyManager;             // to manage the keyboard
  private boolean endGame;                   // to know when to end the game
  private boolean pauseGame;                 // flag to know if the game is paused
  private Font texto;                        // to change the font of string drawn in the screen

  private boolean soundOn;                   //
  private boolean effectsOn;                 // to store the effects of sound
  private int nivel;                         // to store in which level you are
  private boolean menuMusicPlaying;

  // Menu navigation variables
  private Screen screen;                     // to store in which screen you are
  private MenuOpt menOpt;                    // to store in which option in the main menu screen you are
  private OptOpt optOpt;                     // to store in which option in the options screen you are
  private PauseMenu pauseOpt;                // to store in which option in the pause screen you are
  private int currentRecipePage = 1;

  private SoundClip confirmSound;
  private boolean hasPlayedWinSnd;

  private GameLevel gameLevel;

  private String[] credits;

  private int[] selectValues = {0, 0, 100, 100};
  private int[] menuOptionValues = {0, 0, 0, 0};
  private BufferedImage selectImage;

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

      g.drawImage(Assets.menuScreens[Assets.Screen.BACKGROUND.ordinal()], 0, 0, getWidth(), getHeight(), null);
      // Checks which screen to render
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
            BufferedImage cactus = Assets.mainMenuDecorations[Assets.MainMenuDecorations.CACTUS.ordinal()];
            BufferedImage pyramid = Assets.mainMenuDecorations[Assets.MainMenuDecorations.PIRAMIDE.ordinal()];
            BufferedImage crab = Assets.mainMenuDecorations[Assets.MainMenuDecorations.CRAB.ordinal()];
            g.drawImage(cactus, 478, 220, cactus.getWidth(), cactus.getHeight(), null);
            g.drawImage(pyramid, 990, 420, pyramid.getWidth(), pyramid.getHeight(), null);
            g.drawImage(crab, 730, 460, crab.getWidth(), crab.getHeight(), null);

            // Checks where to draw the cursor that shows which option of the menu you are selecting
            // and whether to show a preview of the level you are selecting or a picture of the mian character
            BufferedImage extraImage = Assets.mainMenuDecorations[Assets.MainMenuDecorations.XOCHI.ordinal()];

          if (menOpt == MenuOpt.OPTIONS || menOpt == MenuOpt.RECIPIES || menOpt == MenuOpt.CONTROLS || menOpt == MenuOpt.CREDITS) {
            menuOptionValues[0] = 20;
            menuOptionValues[1] = 520;
            menuOptionValues[2] = 400;
            menuOptionValues[3] = 225;
            extraImage = Assets.mainMenuDecorations[Assets.MainMenuDecorations.XOCHI.ordinal()];
            switch(menOpt) {
              case OPTIONS:
                selectValues[0] = 810;
                selectValues[1] = 70;
                break;
              case RECIPIES:
                selectValues[0] = 785;
                selectValues[1] = 125;
                break;
              case CONTROLS:
                selectValues[0] = 770;
                selectValues[1] = 185;
                break;
              case CREDITS:
                selectValues[0] = 805;
                selectValues[1] = 240;
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
	            	extraImage = Assets.mainMenuDecorations[Assets.MainMenuDecorations.LEVEL_1.ordinal()];
	              break;
	            case TWO:
	          	  	selectValues[0] = 590;
	            	selectValues[1] = 450;
	            	extraImage = Assets.mainMenuDecorations[Assets.MainMenuDecorations.LEVEL_2.ordinal()];
	              break;
	            case THREE:
	          	  	selectValues[0] = 680;
	            	selectValues[1] = 535;
	            	extraImage = Assets.mainMenuDecorations[Assets.MainMenuDecorations.LEVEL_3.ordinal()];
	              break;
	          }
	        }

	        g.drawImage(selectImage, selectValues[0], selectValues[1], selectValues[2], selectValues[3], null);
	        g.drawImage(extraImage, menuOptionValues[0], menuOptionValues[1], menuOptionValues[2], menuOptionValues[3], null);
            break;
          case OPTIONS:
        	// Options menu background
  	        g.drawImage(Assets.menuScreens[Assets.Screen.OPTIONS.ordinal()], 0, 0, getWidth(), getHeight(), null);

  	        // Shows if the sound/effecstOn option is on/off
  	        if (soundOn) {
  	          g.drawImage(Assets.optionsMenu[Assets.OptionsMenu.CHECKMARK.ordinal()], 920, 278, 34, 34, null);
  	        }
  	        if (effectsOn) {
  	          g.drawImage(Assets.optionsMenu[Assets.OptionsMenu.CHECKMARK.ordinal()], 920, 172, 34, 34, null);
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

  	        BufferedImage brightnessOption = Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_SLIDER_3.ordinal()];

  	        // Shows how much brightness you are selecting
  	        switch (brightness) {
  	          case 1:
  	        	  brightnessOption = Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_SLIDER_1.ordinal()];
  	            break;
  	          case 2:
  	        	  brightnessOption = Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_SLIDER_2.ordinal()];
  	            break;
  	          case 3:
  	        	  brightnessOption = Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_SLIDER_3.ordinal()];
  	            break;
  	          case 4:
  	        	  brightnessOption = Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_SLIDER_4.ordinal()];
  	            break;
  	          case 5:
  	        	  brightnessOption = Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_SLIDER_5.ordinal()];
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

            gameLevel.render(g);

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

            break;
          case CREDITS:
            int i = 0;
            int x = getWidth() / 2, y = getHeight() / 2 - (credits.length / 2 * 40);
            g.drawImage(Assets.menuScreens[Assets.Screen.CREDITS.ordinal()], x - 190, 100, 380, 82, null);
            for (String credit : credits) {
              g.drawString(credit, x - (credit.length() / 2 * 16) , y + 35 * i);
              i++;
            }
            g.drawString("Presione backspace para regresar al menu principal", x - 385 , getHeight() - 100);
            break;
      }
      switch(brightness) {
        case 1:
          g.drawImage(Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_1.ordinal()], 0 , 0, getWidth() , getHeight(), null);
          break;
        case 2:
          g.drawImage(Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_2.ordinal()], 0 , 0, getWidth() , getHeight(), null);
          break;
        case 4:
          g.drawImage(Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_4.ordinal()], 0 , 0, getWidth() , getHeight(), null);
          break;
        case 5:
          g.drawImage(Assets.optionsMenu[Assets.OptionsMenu.BRIGHTNESS_5.ordinal()], 0 , 0, getWidth() , getHeight(), null);
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