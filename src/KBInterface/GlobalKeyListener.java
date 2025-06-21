/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KBInterface;

/**
 *
 * @author zhenkog78028
 */
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
//import java.io.Serializable;
//import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import java.io.Serializable;

public class GlobalKeyListener implements NativeKeyListener, Serializable { //main keylistener using JNativeHook which provides the arraylist for the main method
    // uses KBKeyAdapter to convert JNativeHook NativeKeyEvents to AWT KeyEvents
    
    //private  List<KeyEventEntry> keyEventList = new CopyOnWriteArrayList<>();
    private List<KeyEvent> keyEventList = new EventList<>();
    private transient volatile boolean listening;
    private transient final KBKeyAdapter KBKA;
    private transient MainWindow mainWindow;
    public  void setListening(boolean state) {
        listening = state;
    }

    public  void clearEvents() {
        keyEventList.clear();
    }
   
    public void addKey(KeyEvent key)
    {
        keyEventList.add(key);
    }
    
    public  List<KeyEvent> getKeyEvents() {
        return (keyEventList);
    }
    
    public  void setKeyEvents(List<KeyEvent> otherList)
    {
        keyEventList = new CopyOnWriteArrayList<>(otherList);

    }
        @Override
	public void nativeKeyPressed(NativeKeyEvent e) {
                
            if (e.getKeyCode() == NativeKeyEvent.VC_F7) {
                    SwingUtilities.invokeLater(() -> mainWindow.record());
                } else if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
                    SwingUtilities.invokeLater(() -> mainWindow.play());
                }
            
                //System.out.println("NativeKeyPressed called");
                
		if (listening) {
                    
                if(e.getKeyCode()!=NativeKeyEvent.VC_F7&&e.getKeyCode()!=NativeKeyEvent.VC_F8)
                {
                    //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                 //if (e.getID() == NativeKeyEvent.NATIVE_KEY_PRESSED || e.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
                 
                    KBKA.nativeKeyPressed(e);
                    //keyEventList.add(KBKA.nativeKeyPressed(e));
                }
                }
            }

        @Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if (listening) {
                if(e.getKeyCode()!=NativeKeyEvent.VC_F7&&e.getKeyCode()!=NativeKeyEvent.VC_F8)
                {
                 //if (e.getID() == NativeKeyEvent.NATIVE_KEY_PRESSED || e.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
                    KBKA.nativeKeyReleased(e);
                    //keyEventList.add(e);
                }
                }
	}
        //@Override
	/*public void nativeKeyTyped(NativeKeyEvent e) {
            /*if (e.getKeyCode() == NativeKeyEvent.VC_F7) {
                    SwingUtilities.invokeLater(() -> mainWindow.record());
                } else if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
                    SwingUtilities.invokeLater(() -> mainWindow.play());
                }*/
		//System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
	//} moved to keypressed
        
        public void unregisterScreen()
        {
            try
            {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex)
            {
            }
        }
        
	//public static void main(String[] args) {
        public GlobalKeyListener(MainWindow mw){
            listening = false;
            mainWindow = mw;
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
                KBKA = new KBKeyAdapter(this);
		GlobalScreen.addNativeKeyListener(this); // Netbeans warns that this code isn't good, TODO: figure out how to clean up
	}
        
        public class EventList<T> extends CopyOnWriteArrayList implements Serializable
        {
            
        }
        /*public static class KeyEventEntry implements Serializable {
        private static final long serialVersionUID = 1L;
        public final int keyCode;
        public final int eventType; // KeyEvent.KEY_PRESSED or KEY_RELEASED
        public final Instant timestamp;timestamp

        public KeyEventEntry(int keyCode, int eventType, Instant timestamp) {
            this.keyCode = keyCode;
            this.eventType = eventType;
            this.timestamp = timestamp;
        }*/
    }
