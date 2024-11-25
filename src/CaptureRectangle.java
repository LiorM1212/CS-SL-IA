import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Robot;

public class CaptureRectangle {
    public static Rectangle captureRectangle(){
        JFrame frame = new JFrame();

        frame.setSize(500, 600);
        frame.setLocation(0,0); // sets at top left corner        

        frame.add(new AdjustableRectangle());
        frame.setVisible(true);

        return null;
    }

    public static BufferedImage takeScreenshot (Rectangle bounds) throws Exception{
        Robot robot = new Robot();
        return robot.createScreenCapture(bounds);
    }
}
