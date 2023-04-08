/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.image.BufferedImage;

/**
 *
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class Assets {	
    public static enum Comidas {
        ENCHILADAS,
        QUECAS,
        MOLE
    }


    // ingredients
    public static String[] ingrEnchiladas = new String[7];
    public static String[] ingrQuecas = new String[6];
    public static String[] ingrMole = new String[4];
    public static BufferedImage[] recetas = new BufferedImage[3];
    public static BufferedImage recetaEnchiladas;
    public static BufferedImage recetaQuecas;
    public static BufferedImage recetaMole;

    // Images
    public static BufferedImage background;                 // to store background image
    public static BufferedImage player;                     // to store the player image
    public static BufferedImage pause;                      // to store the pause image
    public static BufferedImage shot;                       // to store flask color
    public static BufferedImage heart;                      // to store the taco heart image
    
    // Menu screens
    public static enum Screen {
        TITLESCREEN,
        MENU,
        OPTIONS,
        CONTROLS,
        RECIPIES,
        STORY,
        BACKGROUND,
        CREDITS
    }
    public static BufferedImage[] menuScreens = new BufferedImage[10];

    // Main menu decorations
    public static enum MainMenuDecorations {
        XOCHI,
        LEVEL_1,
        LEVEL_2,
        LEVEL_3,
        CACTUS,
        PIRAMIDE,
        CRAB,
    }
    public static BufferedImage[] mainMenuDecorations = new BufferedImage[10];
    
    public static enum MenuIcons {
        SELECT,
    }
    public static BufferedImage[] menuIcons = new BufferedImage[10];

    // Enemy
    public static BufferedImage chile;                      // to store the chile image

    // Level stuff
    public static enum LevelAssets {
        PLATFORM,
        POWERUP,
        RECTANGLE,
        FUENTE
    }
    public static BufferedImage[] levelAssets = new BufferedImage[10];

    // Player
    public static enum PlayerAssets {
        IDLE_RIGHT,
        IDLE_LEFT,
        XOCHI_RIGHT,
        XOCHI_LEFT,
    }
    public static BufferedImage[] playerAssets = new BufferedImage[10];
    public static BufferedImage xochiAnim[];                // to store the frames for xochi animation
    public static BufferedImage xochiAnimLeft[];

    // Food
    public static BufferedImage enchiladas;
    public static BufferedImage ingredientesEnchiladas[];
    public static BufferedImage ingredientesMole[];
    public static BufferedImage mole;
    public static BufferedImage quecas;
    public static BufferedImage ingredientesQuecas[];

  // Options menu
    public static enum OptionsMenu {
        CHECKMARK,
        BRIGHTNESS_1,
        BRIGHTNESS_2,
        BRIGHTNESS_4,
        BRIGHTNESS_5,
        BRIGHTNESS_SLIDER_1,
        BRIGHTNESS_SLIDER_2,
        BRIGHTNESS_SLIDER_3,
        BRIGHTNESS_SLIDER_4,
        BRIGHTNESS_SLIDER_5,
    }
    public static BufferedImage[] optionsMenu = new BufferedImage[10];
    
    // Pause menu
    public static BufferedImage pauseMenu;
    public static BufferedImage pasarPag;
    public static BufferedImage pasarPagReves;

    // Victory/Defeat screens
    public static BufferedImage ganado;
    public static BufferedImage perdido;

    // letreros
    public static BufferedImage peligro;
    public static BufferedImage aviso;
    
    // spikes
    public static BufferedImage picoArriba;
    public static BufferedImage picoAbajo;
    public static BufferedImage picoDerecha;
    public static BufferedImage picoIzquierda;

    // Power-Ups
    public static BufferedImage atole;                      // to store the atole image
    public static BufferedImage atoleAnim[];                // to store the frames for atole animation
    public static BufferedImage dulce;                      // to store the dulce image

    // Sounds
    public static SoundClip mainMenuMusic;
    public static SoundClip laser;
    public static SoundClip selectSnd;
    public static SoundClip atoleSnd;
    public static SoundClip dulceSnd;
    public static SoundClip loseSnd;
    public static SoundClip hurtSnd;
    public static SoundClip shootSnd;
    public static SoundClip winSnd;
    public static SoundClip level1Music;
    public static SoundClip level2Music;
    public static SoundClip level3Music;
    
    static String soundsFolder =  "/sounds";
    static String imageFolder =  "/images";
    static String pantallasFolder =  imageFolder + "/pantallas";
    static String picosFolder =  imageFolder + "/picos";
    static String comidaFolder =  imageFolder + "/comida";
    static String[] comidasFolders = {"/enchiladas", "/quecas", "/mole"};

    /**
     * initializing the images of the game
     */
    public static void init() {
        //selectSnd = new SoundClip("/sounds/Retro_8-Bit_Game-Powerup_Achievement_05.wav");

        // Loading of sounds
        selectSnd = new SoundClip("/sounds/selectSound.wav");
        // selectSnd = new SoundClip("/sounds/select.wav"); OTRA OPCION NO SE CUAL ES MEJOR LOS TKM
        atoleSnd = new SoundClip("/sounds/powerUp.wav");
        dulceSnd = new SoundClip("/sounds/powerUp1.wav");
        loseSnd = new SoundClip("/sounds/lose.wav");
        hurtSnd = new SoundClip("/sounds/hurt.wav");
        shootSnd = new SoundClip("/sounds/shoot.wav");
        winSnd = new SoundClip("/sounds/win.wav");
        mainMenuMusic = new SoundClip("/sounds/Mexico.wav");
        level1Music = new SoundClip("/sounds/music/Continuum.wav");
        level2Music = new SoundClip("/sounds/music/cdmx.wav");
        level3Music = new SoundClip("/sounds/music/oaxaxa.wav");

        // Loading of images of menu screens
        menuScreens[Screen.TITLESCREEN.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/titlescreen.png");
        menuScreens[Screen.MENU.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/main_menu.jpg");
        menuScreens[Screen.OPTIONS.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/menu_opciones.jpg");
        menuScreens[Screen.CONTROLS.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/controles.jpg");
        menuScreens[Screen.RECIPIES.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/recetario.png");
        menuScreens[Screen.STORY.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/backstory.jpg");
        menuScreens[Screen.BACKGROUND.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/fondo.png");
        menuScreens[Screen.CREDITS.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/creditos.png");
        
        mainMenuDecorations[MainMenuDecorations.XOCHI.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/xochi_t.png");
        mainMenuDecorations[MainMenuDecorations.LEVEL_1.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/mini_nivel_1.png");
        mainMenuDecorations[MainMenuDecorations.LEVEL_2.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/mini_nivel_2.png");
        mainMenuDecorations[MainMenuDecorations.LEVEL_3.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/mini_nivel_3.png");
        mainMenuDecorations[MainMenuDecorations.CACTUS.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/cactus.png");
        mainMenuDecorations[MainMenuDecorations.PIRAMIDE.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/pyramid.png");
        mainMenuDecorations[MainMenuDecorations.CRAB.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/crab.png");

        optionsMenu[OptionsMenu.CHECKMARK.ordinal()] = ImageLoader.loadImage(imageFolder + "/FlechaVerde.png");
      //Loading images of brightness
        optionsMenu[OptionsMenu.BRIGHTNESS_1.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/brillo1.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_2.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/brillo2.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_4.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/brillo4.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_5.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/brillo5.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_SLIDER_1.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/opb1.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_SLIDER_2.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/opb2.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_SLIDER_3.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/opb3.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_SLIDER_4.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/opb4.png");
        optionsMenu[OptionsMenu.BRIGHTNESS_SLIDER_5.ordinal()] = ImageLoader.loadImage(pantallasFolder + "/opb5.png");

        Assets.menuIcons[Assets.MenuIcons.SELECT.ordinal()] = ImageLoader.loadImage(imageFolder + "/Select.png");

        // Loading of images of letreros
        peligro = ImageLoader.loadImage("/images/peligro.png");
        aviso = ImageLoader.loadImage("/images/Aviso.png");

        // Loading of images of game objects
        levelAssets[LevelAssets.PLATFORM.ordinal()] = ImageLoader.loadImage(imageFolder + "/brick_barro.png");
        levelAssets[LevelAssets.POWERUP.ordinal()] = ImageLoader.loadImage(imageFolder + "/flask2.png");
        levelAssets[LevelAssets.RECTANGLE.ordinal()] = ImageLoader.loadImage(imageFolder + "/192.png");
        levelAssets[LevelAssets.FUENTE.ordinal()] = ImageLoader.loadImage(imageFolder + "/fountain_beta.png");
        
        chile = ImageLoader.loadImage("/images/Chile.png");
        heart = ImageLoader.loadImage("/images/tacoHeart.png");
        shot = ImageLoader.loadImage("/images/bubble.png");

        // Loading of images of the spikes
        picoArriba = ImageLoader.loadImage("/images/picos/pico2.png");
        picoAbajo = ImageLoader.loadImage("/images/picos/pico1.png");
        picoDerecha = ImageLoader.loadImage("/images/picos/pico4.png");
        picoIzquierda = ImageLoader.loadImage("/images/picos/pico3.png");

        // loading of winning, loosing, and pause screens
        ganado = ImageLoader.loadImage("/images/pantallas/victoryRoyale.png");
        perdido = ImageLoader.loadImage("/images/pantallas/derrota.png");
        pause = ImageLoader.loadImage("/images/pantallas/pausa.png");
        
        //UI
        pasarPag = ImageLoader.loadImage("/images/PasarPag.png");
        pasarPagReves = ImageLoader.loadImage("/images/PasarPagReves.png");
        
        // Loading of images of ingredients for the enchiladas
        enchiladas = ImageLoader.loadImage("/images/comida/enchiladas/EnchiladaPotosina.png");
        ingredientesEnchiladas = new BufferedImage[7];
        ingredientesEnchiladas[0] = ImageLoader.loadImage("/images/comida/enchiladas/Tortillas.png");
        ingredientesEnchiladas[1] = ImageLoader.loadImage("/images/comida/enchiladas/biggieCheese.png");
        ingredientesEnchiladas[2] = ImageLoader.loadImage("/images/comida/enchiladas/chile_collectable.png");
        ingredientesEnchiladas[3] = ImageLoader.loadImage("/images/comida/enchiladas/tomatoB.png");
        ingredientesEnchiladas[4] = ImageLoader.loadImage("/images/comida/enchiladas/crema.png");
        ingredientesEnchiladas[5] = ImageLoader.loadImage("/images/comida/enchiladas/oil.png");
        ingredientesEnchiladas[6] = ImageLoader.loadImage("/images/comida/enchiladas/CebollaBlack.png");
        ingrEnchiladas[0] = "Tortillas Rojas";
        ingrEnchiladas[1] = "Queso";
        ingrEnchiladas[2] = "Chile";
        ingrEnchiladas[3] = "Tomate";
        ingrEnchiladas[4] = "Crema";
        ingrEnchiladas[5] = "Aceite";
        ingrEnchiladas[6] = "Cebolla";
        recetaEnchiladas = ImageLoader.loadImage("/images/comida/Receta Enchiladas.png");

        // Loading of images of ingredients for the mole
        ingredientesMole = new BufferedImage[4];
        mole = ImageLoader.loadImage("/images/comida/mole/mole.png");
        ingredientesMole[0] = ImageLoader.loadImage("/images/comida/mole/poyo.png");
        ingredientesMole[1] = ImageLoader.loadImage("/images/comida/enchiladas/chile_collectable.png");
        ingredientesMole[2] = ImageLoader.loadImage("/images/comida/mole/ajonjoli.png");
        ingredientesMole[3] = ImageLoader.loadImage("/images/comida/mole/manteca.png");
        ingrMole[0] = "Pollo";
        ingrMole[1] = "Chile x3";
        ingrMole[2] = "Ajonjoli";
        ingrMole[3] = "Manteca";        
        recetaMole = ImageLoader.loadImage("/images/comida/recetaOaxaca.png");

        // Loading of images of ingredients for the quesadillas
        quecas = ImageLoader.loadImage("/images/comida/quecas/Quesadilla.png");
        ingredientesQuecas = new BufferedImage[6];
        ingredientesQuecas[0] = ImageLoader.loadImage("/images/comida/quecas/Ajo.png");
        ingredientesQuecas[1] = ImageLoader.loadImage("/images/comida/quecas/CebollaBlack.png");
        ingredientesQuecas[2] = ImageLoader.loadImage("/images/comida/quecas/Champinon.png");
        ingredientesQuecas[3] = ImageLoader.loadImage("/images/comida/quecas/Cilantro.png");
        ingredientesQuecas[4] = ImageLoader.loadImage("/images/comida/quecas/tomatoB.png");
        ingredientesQuecas[5] = ImageLoader.loadImage("/images/comida/quecas/Tortillasb.png");
        ingrQuecas[0] = "Ajo";
        ingrQuecas[1] = "Cebolla";
        ingrQuecas[2] = "Champiñon";
        ingrQuecas[3] = "Cilantro";
        ingrQuecas[4] = "Tomate";
        ingrQuecas[5] = "Tortillas";
        recetaQuecas = ImageLoader.loadImage("/images/comida/RecetaQues.png");


        // Loading of animations
        atole = ImageLoader.loadImage("/images/atole.png");
        SpreadSheet atoleSpriteSheet = new SpreadSheet(atole);         // spritesheet of the atole
        atoleAnim = new BufferedImage[3];                              // the sprites of the atole animation

        dulce = ImageLoader.loadImage("/images/dulce.png");

        playerAssets[PlayerAssets.XOCHI_RIGHT.ordinal()] = ImageLoader.loadImage(imageFolder + "/Xochi.png");
        playerAssets[PlayerAssets.XOCHI_LEFT.ordinal()] = ImageLoader.loadImage(imageFolder + "/xochi_left.png");

        SpreadSheet xochiSpriteSheet = new SpreadSheet(playerAssets[PlayerAssets.XOCHI_RIGHT.ordinal()]);
        SpreadSheet xochiLeftSpriteSheet = new SpreadSheet(playerAssets[PlayerAssets.XOCHI_LEFT.ordinal()]);
        xochiAnim = new BufferedImage[5];
        xochiAnimLeft = new BufferedImage[5];
        // cropping the pictures from the xochi sheet into the array
        for (int i = 0; i < 5; i++) {
        xochiAnim[i] = xochiSpriteSheet.crop(i * 100, 0, 100, 90);
        }
        playerAssets[PlayerAssets.IDLE_RIGHT.ordinal()] = xochiAnim[0];
        for (int i = 0; i < 5; i++) {
        xochiAnimLeft[i] = xochiLeftSpriteSheet.crop(i * 100, 0, 100, 90);
        }
        playerAssets[PlayerAssets.IDLE_LEFT.ordinal()] = xochiAnimLeft[0];

        // cropping the pictures from the atole sheet into the array
        for (int i = 0; i < 3; i++) {
        atoleAnim[i] = atoleSpriteSheet.crop(i * 200, 0, 200, 200);
        }
    }
}
