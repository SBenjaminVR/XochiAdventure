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
    public static BufferedImage rec;                // to store the red rectangle image
    public static BufferedImage recipies;       // to store the recipies screen image
    
    // Sounds
    public static SoundClip laser;
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
        titleScreen = ImageLoader.loadImage("/images/Title Screen.jpg");
        menu = ImageLoader.loadImage("/images/mainMenu.jpg");
        options = ImageLoader.loadImage("/images/MenuOpciones.jpg");
        controls = ImageLoader.loadImage("/images/controles.jpg");
        rec = ImageLoader.loadImage("/images/red_rectangle.png");
        recipies = ImageLoader.loadImage("/images/Recetario.png");
//        laser = new SoundClip("/sounds/laser.wav");
    }
    
}


