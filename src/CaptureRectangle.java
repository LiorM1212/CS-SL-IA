import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Robot;

// a class with methods involved in selecting and capturing the area to screenshot and returning said screenshot
// unfinished
public class CaptureRectangle {
    /*
     * @return the rectangle of the user selected area 
     * 
     * a method to have the user choose the are to screenshot
     */
    public static Rectangle captureRectangle(){
        JFrame frame = new JFrame();

        // set the frame to be the whole screen
        frame.setSize(500, 600); //todo: set size max
        frame.setLocation(0,0); // sets at top left corner        

        // set the frame to be opaque
        frame.setUndecorated(true);
        frame.setOpacity(0.5f);

        // add an adjustable rectangle on the screen for the user to adjust
        frame.add(new AdjustableRectangle());
        frame.setVisible(true);


        // return selected rectangle 
        return null;
    }
    /* @param bounds rectangle of screen to take screenshot of
     * @return a BufferedImage of the screenshot taken
     * 
     * a method that takes a screenshot of the area of the inputed rectangle
     */
    public static BufferedImage takeScreenshot (Rectangle bounds) throws Exception{
        Robot robot = new Robot();
        return robot.createScreenCapture(bounds);
    }
}
