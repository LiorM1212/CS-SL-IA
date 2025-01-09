import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

// A rectangle that can be moved around and stretched
// purpose is for selecting the area to be captured
public class AdjustableRectangle extends JPanel {

    // the rectangle object that is seen by the user
    private Rectangle rectangle;

    // whether the user is currently resizing
    private boolean resizing;

    public AdjustableRectangle() {

        // Mouse listener to handle mouse press/release
        addMouseListener(new MouseAdapter() {
            // when mouse is pressed find whether it is dragging or resizing the rectangle
            @Override
            public void mousePressed(MouseEvent e) {
                // create rectangle at mouse position and start resizing
                rectangle = new Rectangle(e.getX(),e.getY(),0,0);
                resizing = true;
            } // mousePressed
            
            // when mouse is released reset tracking variables
            @Override
            public void mouseReleased(MouseEvent e) {
                resizing = false;
            } // mouseReleased
        }); // addMouseListener for press/release of mouse 

        // Mouse motion listener resizing the rectangle
        addMouseMotionListener(new MouseMotionAdapter() {
            /* when mouse is dragged,
             *    if user is resizing,
             *       resize the rectangle using current mouse position
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing) {
                    resizeRectangle(e.getPoint());
                } // if
                // repaint rectangle after changing it
                repaint();
            } // mouseDragged
        }); // addMouseMotionListener for when moving and resizing rectangle

    } // adjustableRectangle constructor method

    // @param p current point of mouse
    // Resize the rectangle by dragging the edge
    private void resizeRectangle(Point p) {   
        
        int newX = p.x - rectangle.x;
        int newY = p.y - rectangle.y;

        rectangle.setSize(newX, newY);
    }

    // puts the rectangle on the screen
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        // without this the old rectangle will remain when painting the new one

        // paint the rectangle only if it exists
        if(rectangle != null){
            // border around rectangle
            int borderWidth = 3;
            g.setColor(Color.BLACK);
            g.fillRect(rectangle.x - borderWidth, rectangle.y - borderWidth,
                    rectangle.width + 2*borderWidth, rectangle.height + 2*borderWidth);

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.WHITE);
            g2.fill(rectangle);
        } // if rectangle exists
        
        
    } // paintComponent
} // AdjustableRectangle
