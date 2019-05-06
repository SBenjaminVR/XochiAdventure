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

    public boolean pause;   // flag to pause the game
    public boolean enter;
    public boolean back;
    public boolean down;
    public boolean up;
    public boolean right;
    public boolean left;
    public boolean space;
    public boolean o;
    public boolean z;

    public boolean lastPause;   // flag to know the state of the pause on the previous tick
    public boolean lastEnter;
    public boolean lastBack;
    public boolean lastUp;
    public boolean lastDown;
    public boolean lastLeft;
    public boolean lastRight;
    public boolean lastSpace;
    public boolean lastO;
    public boolean lastZ;

    public boolean a;
    public boolean d;



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
//        lastSave = keys[KeyEvent.VK_G];
//        lastLoad  = keys[KeyEvent.VK_C];
//        left = keys[KeyEvent.VK_LEFT];
//        right = keys[KeyEvent.VK_RIGHT];
//        lastRestart = keys[KeyEvent.VK_R];
//        fireShot = keys[KeyEvent.VK_SPACE];

        pause = lastPause && !keys[KeyEvent.VK_ESCAPE];
        enter = lastEnter  && !keys[KeyEvent.VK_ENTER];
        back = lastBack  && !keys[KeyEvent.VK_BACK_SPACE];
        up = lastUp  && !keys[KeyEvent.VK_UP];
        down = lastDown  && !keys[KeyEvent.VK_DOWN];
        left = lastLeft  && !keys[KeyEvent.VK_LEFT];
        right = lastRight  && !keys[KeyEvent.VK_RIGHT];
        space = lastSpace && !keys[KeyEvent.VK_SPACE];
        o = lastO && !keys[KeyEvent.VK_O];
        z = lastZ && !keys[KeyEvent.VK_Z];

        lastPause = keys[KeyEvent.VK_ESCAPE];
        lastEnter = keys[KeyEvent.VK_ENTER];
        lastBack = keys[KeyEvent.VK_BACK_SPACE];
        lastUp = keys[KeyEvent.VK_UP];
        lastDown = keys[KeyEvent.VK_DOWN];
        lastLeft = keys[KeyEvent.VK_LEFT];
        lastRight = keys[KeyEvent.VK_RIGHT];
        lastSpace = keys[KeyEvent.VK_SPACE];

        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        lastO = keys[KeyEvent.VK_O];
        lastZ = keys[KeyEvent.VK_Z];

    }
}
