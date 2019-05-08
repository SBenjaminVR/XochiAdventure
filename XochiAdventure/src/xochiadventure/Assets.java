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

    // ingredients
    public static String[] ingrEnchiladas = new String[7];
    public static String[] ingrQuecas = new String[6];
    public static String[] ingrMole = new String[4];
    public static BufferedImage recetaEnchiladas;
    public static BufferedImage recetaQuecas;
    public static BufferedImage recetaMole;

    // Images
    public static BufferedImage background;                 // to store background image
    public static BufferedImage player;                     // to store the player image
    public static BufferedImage pause;                     // to store the pause image
    public static BufferedImage shot;                       // to store flask color
    public static BufferedImage heart;                      // to store the taco heart image

    public static BufferedImage titleScreen;                // to store the title screen
    public static BufferedImage menu;                       // to store the menu image
    public static BufferedImage thinkingXochi;              // to store the image of xochi for the main menu
    public static BufferedImage miniLevel;                  // to store the level 1 image for the main menu
    public static BufferedImage miniLevel2;                 // to store the level 2 image for the main menu
    public static BufferedImage miniLevel3;                 // to store the level 3 image for the main menu
    
    public static BufferedImage cactus;                     // to store the decorative element for the main menu
    public static BufferedImage pyramid;                    // to store the decorative element for the main menu
    public static BufferedImage crab;                       // to store the decorative element for the main menu
    public static BufferedImage options;                    // to store the options screen image
    public static BufferedImage controls;                   // to store the instructions screen image
    public static BufferedImage select;                     // to store the red rectangle image
    public static BufferedImage recipies;                   // to store the recipies screen image
    public static BufferedImage chile;                      // to store the chile image
    public static BufferedImage platform;                   // to store the platform image
    public static BufferedImage powerup;                    // to store the power
    public static BufferedImage rectangle;
    public static BufferedImage fuente;
    public static BufferedImage xochiIdle;                  // to store the xochi image
    public static BufferedImage xochiIdleLeft;
    public static BufferedImage xochi;
    public static BufferedImage xochiLeft;
    public static BufferedImage xochiAnim[];                // to store the frames for xochi animation
    public static BufferedImage xochiAnimLeft[];
    public static BufferedImage enchiladas;
    public static BufferedImage ingredientesEnchiladas[];
    public static BufferedImage ingredientesMole[];
    public static BufferedImage mole;
    public static BufferedImage quecas;
    public static BufferedImage ingredientesQuecas[];
    public static BufferedImage checkmark;
    public static BufferedImage brightness1;               // to store a image for brightness
    public static BufferedImage brightness2;               // to store a image for brightness
    public static BufferedImage brightness4;               // to store a image for brightness
    public static BufferedImage brightness5;               // to store a image for brightness
    public static BufferedImage opbrightness1;             // to store the image for the menu of brightness
    public static BufferedImage opbrightness2;             // to store the image for the menu of brightness
    public static BufferedImage opbrightness3;             // to store the image for the menu of brightness
    public static BufferedImage opbrightness4;             // to store the image for the menu of brightness
    public static BufferedImage opbrightness5;             // to store the image for the menu of brightness
    public static BufferedImage pauseMenu;
    public static BufferedImage pasarPag;
    public static BufferedImage pasarPagReves;
    public static BufferedImage ganado;
    public static BufferedImage perdido;
    
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
    public static SoundClip mainMenu;
    public static SoundClip laser;
    public static SoundClip selectSnd;
    public static SoundClip atoleSnd;
    public static SoundClip dulceSnd;
    public static SoundClip loseSnd;
    public static SoundClip hurtSnd;
    public static SoundClip shootSnd;
    public static SoundClip winSnd;
//
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
        mainMenu = new SoundClip("/sounds/Mexico.wav");

        // Loading of images of menu screens
        titleScreen = ImageLoader.loadImage("/images/pantallas/titlescreen.png");
        menu = ImageLoader.loadImage("/images/pantallas/mainMenu.jpg");
        options = ImageLoader.loadImage("/images/pantallas/MenuOpciones.jpg");
        controls = ImageLoader.loadImage("/images/pantallas/controles.jpg");
        select = ImageLoader.loadImage("/images/select.png");
        recipies = ImageLoader.loadImage("/images/pantallas/Recetario.png");
        miniLevel = ImageLoader.loadImage("/images/pantallas/miniNivel1.png");
        miniLevel2 = ImageLoader.loadImage("/images/pantallas/miniNivel2.png");
        miniLevel3 = ImageLoader.loadImage("/images/pantallas/miniNivel3.png");
        thinkingXochi = ImageLoader.loadImage("/images/pantallas/xochiT.png");
        
        cactus = ImageLoader.loadImage("/images/pantallas/cactus.png");
        pyramid = ImageLoader.loadImage("/images/pantallas/pyramid.png");
        crab = ImageLoader.loadImage("/images/pantallas/crab.png");
        
        checkmark = ImageLoader.loadImage("/images/flechaVerde.png");

        // Loading of images of game objects
        chile = ImageLoader.loadImage("/images/chile.png");
        platform = ImageLoader.loadImage("/images/brick_barro.png");
        powerup = ImageLoader.loadImage("/images/flask2.png");
        rectangle = ImageLoader.loadImage("/images/192.png");
        fuente = ImageLoader.loadImage("/images/fountain_beta.png");
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
        
        //Loading images of brightness
        brightness1 = ImageLoader.loadImage("/images/pantallas/brillo1.png");
        brightness2 = ImageLoader.loadImage("/images/pantallas/brillo2.png");
        brightness4 = ImageLoader.loadImage("/images/pantallas/brillo4.png");
        brightness5 = ImageLoader.loadImage("/images/pantallas/brillo5.png");
        opbrightness1 = ImageLoader.loadImage("/images/pantallas/opb1.png");
        opbrightness2 = ImageLoader.loadImage("/images/pantallas/opb2.png");
        opbrightness3 = ImageLoader.loadImage("/images/pantallas/opb3.png");
        opbrightness4 = ImageLoader.loadImage("/images/pantallas/opb4.png");
        opbrightness5 = ImageLoader.loadImage("/images/pantallas/opb5.png");
        
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

        xochi = ImageLoader.loadImage("/images/Xochi.png");
        xochiLeft = ImageLoader.loadImage("/images/xochi_left.png");
        SpreadSheet xochiSpriteSheet = new SpreadSheet(xochi);
        SpreadSheet xochiLeftSpriteSheet = new SpreadSheet(xochiLeft);
        xochiAnim = new BufferedImage[5];
        xochiAnimLeft = new BufferedImage[5];
        // cropping the pictures from the xochi sheet into the array
        for (int i = 0; i < 5; i++) {
            xochiAnim[i] = xochiSpriteSheet.crop(i * 100, 0, 100, 90);
        }
        xochiIdle = xochiAnim[0];
        for (int i = 0; i < 5; i++) {
            xochiAnimLeft[i] = xochiLeftSpriteSheet.crop(i * 100, 0, 100, 90);
        }
        xochiIdleLeft = xochiAnimLeft[0];

        // cropping the pictures from the atole sheet into the array
        for (int i = 0; i < 3; i++) {
            atoleAnim[i] = atoleSpriteSheet.crop(i * 200, 0, 200, 200);
        }
    }

}
