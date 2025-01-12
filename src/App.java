import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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
