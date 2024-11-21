import java.awt.image.BufferedImage;
// import java.io.File;
// import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class App {
    public static void main(String[] args) throws Exception {
        // Plan:
        //      setup  | don't know if I need this
        //      when(ctrl+alt+C){
        //          selectRectangle
        // Done     takeScreenshot
        //          OCR
        // Done     toClipBoard
        //      }


        // Rectangle bounds = new Rectangle(200, 200, 800, 400);
        // BufferedImage image = takeScreenshot(bounds);
       
        // File outputfile = new File("./src/image.png");
        // ImageIO.write(image, "png", outputfile);
    }

    public static BufferedImage takeScreenshot (Rectangle bounds) throws Exception{
        Robot robot = new Robot(); // don't want to import because Robot is only used here
        return robot.createScreenCapture(bounds);
    }

    public static void toClipboard(String str){
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strse1 = new StringSelection(str);
        clip.setContents(strse1, strse1);
    }
}
