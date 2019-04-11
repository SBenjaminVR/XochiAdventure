/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.image.BufferedImage;

/**
 *
 * @author melba
 */
public class SpreadSheet {
    private BufferedImage sheet;
    
    /**
     * Create a new spritesheet
     */
    
    public SpreadSheet(BufferedImage sheet){
        this.sheet = sheet;
    }
    
    /**
     * crop a sprite from the spritesheet
     */
    public BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x, y, width, height);
    }
    
}
