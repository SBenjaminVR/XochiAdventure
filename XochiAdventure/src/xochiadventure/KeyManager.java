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
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class KeyManager implements KeyListener {

    public boolean pause;   	// flag to pause the game
    public boolean enter;		// flag to select an option in the menus or to restart the level
    public boolean back;		// flag to return to the previous screen
    public boolean down;		// flag to move down in the menus
    public boolean up;			// flag to move up in the menus
    public boolean right;		// flag to move right in the menus
    public boolean left;		// flag to move left in the menus
    public boolean space;		// flag to jump
    public boolean o;			// flag to shoot
    public boolean z;			// flag to shoot

    public boolean lastPause;   // flag to know the state of the pause on the previous tick
    public boolean lastEnter;	// flag to know the state of enter key on the previous tick
    public boolean lastBack;	// flag to know the state of backspace key on the previous tick
    public boolean lastUp;		// flag to know the state of up arrow key on the previous tick
    public boolean lastDown;	// flag to know the state of down arrow key on the previous tick
    public boolean lastLeft;	// flag to know the state of left arrow key on the previous tick
    public boolean lastRight;	// flag to know the state of right arrow key on the previous tick
    public boolean lastSpace;	// flag to know the state of space bar on the previous tick
    public boolean lastO;		// flag to know the state of O key pause on the previous tick
    public boolean lastZ;		// flag to know the state of Z key pause on the previous tick

    public boolean d;			// flag to move to the left
    public boolean a;			// flag to move to the right



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
         * For every key it check if in the last tick the key was pressed and if in the current tick the key is released
         * If this is true, the flags are true, else the flag is false
         */

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

		// update the flags to wether the keys are being pressed or not

        lastPause = keys[KeyEvent.VK_ESCAPE];
        lastEnter = keys[KeyEvent.VK_ENTER];
        lastBack = keys[KeyEvent.VK_BACK_SPACE];
        lastUp = keys[KeyEvent.VK_UP];
        lastDown = keys[KeyEvent.VK_DOWN];
        lastLeft = keys[KeyEvent.VK_LEFT];
        lastRight = keys[KeyEvent.VK_RIGHT];
        lastSpace = keys[KeyEvent.VK_SPACE];
        lastO = keys[KeyEvent.VK_O];
        lastZ = keys[KeyEvent.VK_Z];

        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        

    }
}
