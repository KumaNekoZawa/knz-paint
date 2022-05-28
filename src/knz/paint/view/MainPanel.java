package knz.paint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MainPanel extends JPanel {

    public enum Tool {
        BRUSH,
        LINE,
        CIRCLE,
        RECTANGLE,
        AIRBRUSH,
        SIZE,
    }

    private final int AIRBRUSH_SIZE = 10; // FIXME make variable

    private BufferedImage image = new BufferedImage(400, 225, BufferedImage.TYPE_INT_ARGB);
    private Graphics2D imageG2D;

    private Tool tool = Tool.BRUSH;
    private Color color = Color.BLACK;
    private int lastX = -1, lastY = -1;
    private int x = -1, y = -1;

    public MainPanel() {
        super();
        updatePanelSize();
        imageG2D = (Graphics2D) image.createGraphics();
        clearImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                switch (tool) {
                case BRUSH:
                case LINE:
                case CIRCLE:
                case RECTANGLE:
                    lastX = x;
                    lastY = y;
                    break;
                case AIRBRUSH:
                    drawAirBrushPixel();
                    break;
                case SIZE:
                    changeSize();
                    break;
                }
                MainPanel.this.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                switch (tool) {
                case BRUSH:
                    break;
                case LINE:
                    if (lastX >= 0 && lastY >= 0) {
                        imageG2D.setColor(color);
                        imageG2D.drawLine(lastX, lastY, x, y);
                    }
                    break;
                case CIRCLE:
                    if (lastX >= 0 && lastY >= 0) {
                        imageG2D.setColor(color);
                        imageG2D.drawOval(lastX, lastY, x - lastX, y - lastY);
                    }
                    break;
                case RECTANGLE:
                    if (lastX >= 0 && lastY >= 0) {
                        imageG2D.setColor(color);
                        imageG2D.drawRect(lastX, lastY, x - lastX, y - lastY);
                    }
                    break;
                case AIRBRUSH:
                    drawAirBrushPixel();
                    break;
                case SIZE:
                    changeSize();
                    break;
                }
                MainPanel.this.repaint();
                lastX = -1;
                lastY = -1;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                switch (tool) {
                case BRUSH:
                    if (lastX >= 0 && lastY >= 0) {
                        imageG2D.setColor(color);
                        imageG2D.drawLine(lastX, lastY, x, y);
                    }
                    lastX = x;
                    lastY = y;
                    break;
                case LINE:
                case CIRCLE:
                case RECTANGLE:
                    break;
                case AIRBRUSH:
                    drawAirBrushPixel();
                    break;
                case SIZE:
                    changeSize();
                    break;
                }
                MainPanel.this.repaint();
            }
        });
    }

    private void updatePanelSize() {
        Dimension d = new Dimension(image.getWidth(), image.getHeight());
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
    }

    private void drawAirBrushPixel() {
        if (x < 0 || y < 0) {
            return;
        }
        final double a = Math.random() * 2.0 * Math.PI;
        final double d = AIRBRUSH_SIZE * Math.random();
        final int lx = x + (int) (d * Math.sin(a));
        final int ly = y + (int) (d * Math.cos(a));
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            image.setRGB(lx, ly, color.getRGB());
        }
    }

    private void changeSize() {
        if (x < 0 || y < 0) {
            return;
        }
        // FIXME do not create a new bufferedimage
        imageG2D.dispose();
        BufferedImage imageNew = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        imageG2D = (Graphics2D) imageNew.createGraphics();
        imageG2D.setColor(Color.WHITE);
        imageG2D.fillRect(0, 0, imageNew.getWidth(), imageNew.getHeight());
        imageG2D.drawImage(image, 0, 0, null);
        image = imageNew;
        updatePanelSize();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final int width = getWidth();
        final int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.drawImage(image, 0, 0, null);
        switch (tool) {
        case BRUSH:
            break;
        case LINE:
            if (lastX >= 0 && lastY >= 0) {
                g2d.setColor(color);
                g2d.drawLine(lastX, lastY, x, y);
            }
            break;
        case CIRCLE:
            if (lastX >= 0 && lastY >= 0) {
                g2d.setColor(color);
                g2d.drawOval(lastX, lastY, x - lastX, y - lastY);
            }
            break;
        case RECTANGLE:
            if (lastX >= 0 && lastY >= 0) {
                g2d.setColor(color);
                g2d.drawRect(lastX, lastY, x - lastX, y - lastY);
            }
            break;
        case AIRBRUSH:
        case SIZE:
            break;
        }
    }

    public void clearImage() {
        imageG2D.setColor(Color.WHITE);
        imageG2D.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void openImage(BufferedImage image) {
        imageG2D.dispose();
        this.image = image;
        imageG2D = (Graphics2D) image.createGraphics();
        updatePanelSize();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
