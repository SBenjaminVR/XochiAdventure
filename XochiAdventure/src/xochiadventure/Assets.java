/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.image.BufferedImage;

/**
 *
 * @authors
 *      - Alberto García Viegas A00822649
 *      - Melba Geraldine Consuelos Fernández A01410921
 *      - Humberto
 *      - Benjamin
 */
public class Assets {
    // Images
    public static BufferedImage background;         // to store background image
    public static BufferedImage player;                 // to store the player image
    public static BufferedImage bomb;                   // to store the bomb image
    public static BufferedImage alien;                  // to store the alien image
    public static BufferedImage shot;                   //to store flask color
    public static BufferedImage asteroid;
    public static BufferedImage heart;

    public static BufferedImage titleScreen;        // to store the title screen
    public static BufferedImage menu;               // to store the menu image
    public static BufferedImage options;            // to store the options screen image
    public static BufferedImage controls;           // to store the instructions screen image
    public static BufferedImage select;                // to store the red rectangle image
    public static BufferedImage recipies;       // to store the recipies screen image
    public static BufferedImage chile;       // to store the chile image
    public static BufferedImage platform;
    public static BufferedImage powerup;
    public static BufferedImage comida;
    public static BufferedImage rectangle;
    public static BufferedImage fuente;
    public static BufferedImage xochi;              // to store the xochi image
    public static BufferedImage xochiAnim[];        // to store the frames for xochi animation

    // Power-Ups
    public static BufferedImage atole;              // to store the atole image
    public static BufferedImage atoleAnim[];        // to store the frames for atole animation
    public static BufferedImage dulce;              // to store the dulce image

    // Sounds
    public static SoundClip laser;
    public static SoundClip selectSnd;
    public static SoundClip atoleSnd;
    public static SoundClip dulceSnd;
//
    /**
     * initializing the images of the game
     */
    public static void init() {
//        background = ImageLoader.loadImage("/images/space.png");
//        player = ImageLoader.loadImage("/images/spaceship.png");
//        bomb = ImageLoader.loadImage("/images/bomb.png");
//        alien = ImageLoader.loadImage("/images/alien.png");
//        shot = ImageLoader.loadImage("/images/shot.png");
//        asteroid = ImageLoader.loadImage("/images/asteroid.png");


        //selectSnd = new SoundClip("/sounds/Retro_8-Bit_Game-Powerup_Achievement_05.wav");

        selectSnd = new SoundClip("/sounds/selectSound.wav");
        // selectSnd = new SoundClip("/sounds/select.wav"); OTRA OPCION NO SE CUAL ES MEJOR LOS TKM
        atoleSnd = new SoundClip("/sounds/powerUp.wav");
        dulceSnd = new SoundClip("/sounds/powerUp1.wav");

        titleScreen = ImageLoader.loadImage("/images/Title Screen.jpg");
        menu = ImageLoader.loadImage("/images/mainMenu.jpg");
        options = ImageLoader.loadImage("/images/MenuOpciones.jpg");
        controls = ImageLoader.loadImage("/images/controles.jpg");
        select = ImageLoader.loadImage("/images/select.png");
        recipies = ImageLoader.loadImage("/images/Recetario.png");
        chile = ImageLoader.loadImage("/images/chile.png");
        platform = ImageLoader.loadImage("/images/brick_barro.png");
        powerup = ImageLoader.loadImage("/images/flask2.png");
        player = ImageLoader.loadImage("/images/gyrados.png");
        comida = ImageLoader.loadImage("/images/malo.png");
        rectangle = ImageLoader.loadImage("/images/192.png");
        fuente = ImageLoader.loadImage("/images/fountain_beta.png");
        heart = ImageLoader.loadImage("/images/tacoHeart.png");
        shot = ImageLoader.loadImage("/images/bubble.png");
//        laser = new SoundClip("/sounds/laser.wav");

        atole = ImageLoader.loadImage("/images/atole.png");
        SpreadSheet atoleSpriteSheet = new SpreadSheet(atole);         // spritesheet of the atole
        atoleAnim = new BufferedImage[3];                              // the sprites of the atole animation

        dulce = ImageLoader.loadImage("/images/dulce.png");
        
        xochi = ImageLoader.loadImage("/images/Xochi.png");
        SpreadSheet xochiSpriteSheet = new SpreadSheet(xochi);         // spritesheet of the atole
        xochiAnim = new BufferedImage[2];   
        
        // cropping the pictures from the xochi sheet into the array
        for (int i = 0; i < 2; i++) {
            xochiAnim[i] = xochiSpriteSheet.crop(i * 250, 0, 250, 250);
        }

        // cropping the pictures from the atole sheet into the array
        for (int i = 0; i < 3; i++) {
            atoleAnim[i] = atoleSpriteSheet.crop(i * 200, 0, 200, 200);
        }
    }

}
