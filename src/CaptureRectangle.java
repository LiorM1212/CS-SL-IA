import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
// a class with methods involved in selecting and capturing the area to screenshot and returning said screenshot
public class CaptureRectangle {

    // for the button in the captureRectangle method
    // needs to be a global for captureRectangle to work properly
    static boolean clicked = false;
    static boolean done = false;

    /*
     * @return the rectangle of the user selected area 
     * 
     * a method to have the user choose the are to screenshot
     */
    public static Rectangle captureRectangle(){
        
        JFrame frame = new JFrame();

        // size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // set the frame to be the whole screen
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLocation(0,0); // sets at top left corner        

        frame.setUndecorated(true);

        // set the frame to be opaque
        float opacity = .3f;
        frame.setOpacity(opacity);

        // create confirmation button
        Button confirmButton = new Button("OK");
        confirmButton.setBounds(screenSize.width/2,0,50,50);
        confirmButton.setBackground(Color.GREEN);

        // create selection rectangle
        AdjustableRectangle adjustableRectangle = new AdjustableRectangle();


        // when button pressed return rectangle
        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clicked = true;
                done = true;
                frame.removeAll();
                frame.setVisible(false);
            }
        });
        
        frame.setFocusable(true);

        // listener for escape key
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // not needed
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    done = true;
                    frame.removeAll();
                    frame.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not needed
            }
        });

        frame.add(confirmButton);
        frame.add(adjustableRectangle);
        frame.setVisible(true);

        // wait for the confirmation to be clicked
        // a method call in the while is neccessary for it to work
        // some methods seem to work others don't
        while(!done){System.out.print("");} // ugly code
        done = false;

        if (!clicked)
            return null;

        clicked = false;
        return adjustableRectangle.getRectangle();

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
