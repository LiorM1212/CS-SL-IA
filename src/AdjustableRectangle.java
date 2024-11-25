import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class AdjustableRectangle extends JPanel {
    private Rectangle rectangle;
    private Point dragStart;
    private boolean resizing;

    public AdjustableRectangle() {
        rectangle = new Rectangle(100, 100, 200, 150); // Initial rectangle (x, y, width, height)

        // Mouse listener to handle dragging and resizing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rectangle.contains(e.getPoint())) {
                    if (isNearEdge(e.getPoint())) {
                        resizing = true;
                    } else {
                        dragStart = e.getPoint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resizing = false;
                dragStart = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing) {
                    resizeRectangle(e.getPoint());
                } else if (dragStart != null) {
                    moveRectangle(e.getPoint());
                }
                repaint();
            }
        });
    }

    // Check if the mouse is near the rectangle's edge for resizing
    private boolean isNearEdge(Point p) {
        int margin = 10; // Resize margin
        return Math.abs(p.x - (rectangle.x + rectangle.width)) <= margin
                && Math.abs(p.y - (rectangle.y + rectangle.height)) <= margin;
    }

    // Resize the rectangle by dragging the edge
    private void resizeRectangle(Point p) {
        rectangle.setSize(Math.max(10, p.x - rectangle.x), Math.max(10, p.y - rectangle.y));
    }

    // Move the rectangle by dragging inside
    private void moveRectangle(Point p) {
        int dx = p.x - dragStart.x;
        int dy = p.y - dragStart.y;
        rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
        dragStart = p;
    }

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        // without this the old rectangle will remain when painting the new one

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(rectangle);
    }
}
