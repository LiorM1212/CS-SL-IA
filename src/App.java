import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) throws Exception {
        // Plan:
        //      setup
        //      when(ctrl+alt+C){
        // Done  selectRectangle
        // Done     takeScreenshot
        //          OCR
        // Done     toClipBoard
        //      }

        Rectangle screenArea = CaptureRectangle.captureRectangle();

        if(screenArea != null){
            BufferedImage image = CaptureRectangle.takeScreenshot(screenArea);

            // put image through OCR
            String text = "";

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
}
