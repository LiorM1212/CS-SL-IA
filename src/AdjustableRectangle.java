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
    
    // a point representing where the user started dragging from
    // after drag: current point of mouse minus dragStart equals change in mouse position 
    private Point dragStart;

    // whether the user is currently resizing
    private boolean resizing;

    public AdjustableRectangle() {
        // Initial rectangle (x, y, width, height)
        rectangle = new Rectangle(100, 100, 200, 150); 

        // Mouse listener to handle mouse press/release
        addMouseListener(new MouseAdapter() {
            // when mouse is pressed find whether it is dragging or resizing the rectangle
            @Override
            public void mousePressed(MouseEvent e) {
                // if current mouse position is within the rectangle
                if (rectangle.contains(e.getPoint())) {
                    if (isNearEdge(e.getPoint())) {
                        resizing = true;
                    } else {
                        dragStart = e.getPoint();
                    } // if-else
                } // if current mouse position is within rectangle
            } // mousePressed
            
            // when mouse is released reset tracking variables
            @Override
            public void mouseReleased(MouseEvent e) {
                resizing = false;
                dragStart = null;
            } // mouseReleased
        }); // addMouseListener for press/release of mouse 

        // Mouse motion listener for moving and resizing the rectangles
        addMouseMotionListener(new MouseMotionAdapter() {
            /* when mouse is dragged,
             *    if user is resizing,
             *       resize the rectangle using current mouse position
             *    else if user is dragging rectangle
             *       move the rectangle using current mouse position
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing) {
                    resizeRectangle(e.getPoint());
                } else if (dragStart != null) {
                    moveRectangle(e.getPoint());
                } //if-else if
                // repaint rectangle after changing it
                repaint();
            } // mouseDragged
        }); // addMouseMotionListener for when moving and resizing rectangle

    } // adjustableRectangle constructor method

    // @param p current point of mouse
    // Check if the mouse is near the rectangle's edge for resizing
    private boolean isNearEdge(Point p) {
        int margin = 10; // Resize margin

        // distances from the mouse position to each edge of the rectangle
        int rightDist = Math.abs(p.x - (rectangle.x + rectangle.width)); // right side
        int leftDist =  Math.abs(p.x - rectangle.x); // left side 
        int topDist =  Math.abs(p.y - rectangle.y); // bottom side
        int bottomDist = Math.abs(p.y - (rectangle.y + rectangle.height)); // top side

        // if distance from any of the edges is less than the margin, the user is near the edge
        return rightDist <= margin
                || leftDist <= margin 
                || topDist <= margin
                || bottomDist <= margin;
    }

    // @param p current point of mouse
    // Resize the rectangle by dragging the edge
    private void resizeRectangle(Point p) {   
        final int minSize = 20;
        int newX = Math.max(minSize, p.x - rectangle.x);
        int newY = Math.max(minSize, p.y - rectangle.y);

        rectangle.setSize(newX, newY);
    }

    // @param p current point of mouse
    // Move the rectangle by dragging inside
    private void moveRectangle(Point p) {
        // current position - position at the start of dragging = change in position
        int dx = p.x - dragStart.x;
        int dy = p.y - dragStart.y;

        // move the location of the rectangle by the calculated changes
        rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
        
        // set the new starting position for dragging at the current position
        dragStart = p;
    } // moveRectangle

    // puts the rectangle on the screen
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        // without this the old rectangle will remain when painting the new one

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(rectangle);
    } // paintComponent
} // AdjustableRectangle
