import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class App {
    public static void main(String[] args) throws Exception {
        // Plan:
        //      setup  | don't know if I need this
        //      when(ctrl+alt+C){
        // working  selectRectangle
        // Done     takeScreenshot
        //          OCR
        // Done     toClipBoard
        //      }

        CaptureRectangle.captureRectangle();
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
