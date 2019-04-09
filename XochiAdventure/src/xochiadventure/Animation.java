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
 * Esta clase nos permite animar los flasks que ocupamos como power ups 
 */
public class Animation {
    private int speed;
    private int index;
    private long lastTime;
    private long timer;
    
    private BufferedImage[] frames;
    
    public Animation(BufferedImage[] frames, int speed){
        this.frames = frames;
        this.speed = speed;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }
    public BufferedImage getCurrentFrame(){
        return frames[index];
    }
    
    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        if(timer > speed){
            index++;
            timer = 0;
            if(index >= frames.length){
                index = 0;
            }
        }
    }
}
