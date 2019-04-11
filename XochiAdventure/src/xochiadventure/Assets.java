/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.image.BufferedImage;

/**
 *
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández A01410921
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
    
    public static BufferedImage titleScreen;
    public static BufferedImage menu;
    public static BufferedImage options;
    public static BufferedImage instructions;
    
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
        instructions = ImageLoader.loadImage("/images/controles.jpg");
//        laser = new SoundClip("/sounds/laser.wav");
    }
    
}


