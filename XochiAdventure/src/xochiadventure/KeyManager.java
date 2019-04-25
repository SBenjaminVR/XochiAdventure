/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández A01410921
 */
public class KeyManager implements KeyListener {

//    public boolean left;    // flag to move left the player
//    public boolean right;   // flag to move right the player
//    public boolean pause;   // flag to pause the game
//    public boolean save;    // flag to save the game
//    public boolean load;    // flag to load the game
//    public boolean restart;     // flag to restart the game
//    public boolean lastPause;   // flag to know the state of the pause on the previous tick
//    public boolean lastSave;    // flag to know the state of the save on the previous tick
//    public boolean lastLoad;    // flag to know the state of the load on the previous tick
//    public boolean lastRestart; // flag to know the state of the restart on the previous tick
//    public boolean fireShot;    // flag to shoot
    
    public boolean enter;
    public boolean back;
    public boolean down;
    public boolean up;
    public boolean right;
    public boolean left;
    
    public boolean lastEnter;
    public boolean lastBack;
    public boolean lastUp;
    public boolean lastDown;
    public boolean lastLeft;
    public boolean lastRight;
    
    private boolean keys[];  // to store all the flags for every key

    public KeyManager() {
        keys = new boolean[256];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
    }

    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        /**
         * checks if in the last tick the keys 'p', 'g', 'c', and 'r' were pressed and if in the current tick they were released
         * if this is true the corresponding flag of the key is true, else it is false
         */
//        lastPause = keys[KeyEvent.VK_P];
//        lastSave = keys[KeyEvent.VK_G];
//        lastLoad  = keys[KeyEvent.VK_C];
//        left = keys[KeyEvent.VK_LEFT];
//        right = keys[KeyEvent.VK_RIGHT];
//        lastRestart = keys[KeyEvent.VK_R];
//        fireShot = keys[KeyEvent.VK_SPACE];

        enter = lastEnter  && !keys[KeyEvent.VK_ENTER];
        
        back = lastBack  && !keys[KeyEvent.VK_BACK_SPACE];

        up = lastUp  && !keys[KeyEvent.VK_UP];

        down = lastDown  && !keys[KeyEvent.VK_DOWN];
         
        left = lastLeft  && !keys[KeyEvent.VK_LEFT];
        
        right = lastRight  && !keys[KeyEvent.VK_RIGHT];
        
        lastEnter = keys[KeyEvent.VK_ENTER];
        lastBack = keys[KeyEvent.VK_BACK_SPACE];
        lastUp = keys[KeyEvent.VK_UP];
        lastDown = keys[KeyEvent.VK_DOWN];
        lastLeft = keys[KeyEvent.VK_LEFT];
        lastRight = keys[KeyEvent.VK_RIGHT];
    }
}
