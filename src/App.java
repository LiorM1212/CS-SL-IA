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

    // puts the inputted string into the user's clipboard
    public static void toClipboard(String str){
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strse1 = new StringSelection(str);
        clip.setContents(strse1, strse1);
    }
}
