/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KBInterface;
//import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author zhenkog78028
 */

public class KBKeyAdapter extends SwingKeyAdapter { //extends the swingadapter which converts JNativeHook keyevents to swing/awt keyevents
    //so that they can be used by AWT Robot to be typed back out
    private final GlobalKeyListener gKL;
     //@Override
    public void keyTyped(KeyEvent keyEvent) {
        gKL.addKey(keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        gKL.addKey(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gKL.addKey(keyEvent);
    }
    
    public KBKeyAdapter(GlobalKeyListener listener)
    {
        gKL = listener;
    }
}
