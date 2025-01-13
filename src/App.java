import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

public class App {

    private static volatile boolean quit = false;

    private static WinUser.LowLevelKeyboardProc keyboardProc;
    private static WinUser.HHOOK keyboardHook;

    private static boolean pressedCtrl = false;
    private static boolean pressedAlt = false;

    // detects if Ctrl + Alt + C is pressed in that order
    // if detected it calls doImageTextToClipboard
    // adapted from https://github.com/java-native-access/jna/blob/master/contrib/w32windowhooks/com/sun/jna/contrib/demo/WindowHooks.java
    public static void main(String[] args) {

        final User32 lib = User32.INSTANCE;

        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        keyboardProc = new LowLevelKeyboardProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
                if (nCode >= 0) {
                    switch(wParam.intValue()) {
                        case WinUser.WM_KEYUP:

                            if(info.vkCode == 162){
                                pressedCtrl = false;
                                pressedAlt = false;
                            }
                            if(info.vkCode == 164){
                                pressedAlt = false;
                            }
                            break;

                        case WinUser.WM_KEYDOWN:
                            if (info.vkCode == 162) {
                                pressedCtrl = true;
                            }

                            if (pressedCtrl && info.vkCode == 164) {
                                pressedAlt = true;
                            }

                            if(pressedCtrl && pressedAlt && info.vkCode == 67){
                                pressedCtrl = false;
                                pressedAlt = false;
                                try{
                                    doImageTextToClipboard();
                                }catch(Exception e){}
                            }
                            break;
                            
                    }
                }

                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return lib.CallNextHookEx(keyboardHook, nCode, wParam, new LPARAM(peer));
            }
        };
        keyboardHook = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardProc, hMod, 0);

        new Thread() {
            @Override
            public void run() {

                while (!quit) {
                    try { Thread.sleep(10); } catch(Exception e) { }
                }

                System.err.println("unhook and exit");
                lib.UnhookWindowsHookEx(keyboardHook);
                System.exit(0);
            }
        }.start();

        // This bit never returns from GetMessage
        int result;
        MSG msg = new MSG();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            }
            else {
                System.err.println("got message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }
        lib.UnhookWindowsHookEx(keyboardHook);
    }

    // lets the user select a section of the screen to take text from and put into the clipboard
    public static void doImageTextToClipboard() throws Exception{
        Rectangle screenArea = CaptureRectangle.captureRectangle();

        if(screenArea != null){
            BufferedImage image = CaptureRectangle.takeScreenshot(screenArea);

            String text = imageToText(image);

            toClipboard(text);

        }
    }

    /*  @param str string to put onto clipboard
     *
     * puts the inputted string into the user's clipboard
     */
    public static void toClipboard(String str){
        // this code copy/pasted from somewhere
        // forgot where
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strse1 = new StringSelection(str);
        clip.setContents(strse1, strse1);
    }

    /*
     * @param BufferedImage image  the image that you are taking text from
     * 
     * A method to take text from an image using Tesseract OCR with the wrapper Tess4J
     * code adapted from https://tess4j.sourceforge.net/codesample.html
     */
    public static String imageToText(BufferedImage image){

        String result = "";
        
        Tesseract instance = new Tesseract();  // JNA Interface Mapping
        
        instance.setDatapath("tessdata"); // path to tessdata directory

        try {
            result = instance.doOCR(image);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        
        return result;
    }
}
