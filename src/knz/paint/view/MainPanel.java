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
import java.awt.Polygon;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainPanel extends JPanel {

    public enum Tool {
        BRUSH("Brush", "tool_7.png"),
        LINE("Line", "tool_10.png"),
        BEZIER("Bézier", "tool_11.png"),
        POLYGON("Polygon", "tool_13.png"),
        OVAL("Oval", "tool_14.png"),
        RECTANGLE("Rectangle", "tool_12.png"),
        AIRBRUSH("Airbrush", "tool_8.png"),
        PICK_COLOR("Pick color", "tool_4.png"),
        SIZE("S", ""), // FIXME add icon

        ;

        private String title;
        private String icon;

        Tool(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public String getIcon() {
            return icon;
        }
    }

    public enum FillStyle {
        NONE("No fill"),
        PRIMARY("Primary color"),
        SECONDARY("Secondary color"),

        ;

        private String title;

        FillStyle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private final int BEZIER_QUALITY = 100;
    private final int AIRBRUSH_SIZE = 10; // FIXME make variable

    private MainWindow parent;

    private BufferedImage image;
    private Graphics2D imageG2D;

    private Tool tool = Tool.BRUSH;
    private Color colorPrimary = Color.BLACK;
    private Color colorSecondary = Color.WHITE;
    private FillStyle fillStyle = FillStyle.NONE;

    private int lastX = -1, lastY = -1;
    private int x = -1, y = -1;
    private Polygon polygon = new Polygon();

    public MainPanel() {
        super();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                lastX = x;
                lastY = y;
                if (tool != Tool.BEZIER && tool != Tool.POLYGON) {
                    polygon.reset();
                }
                switch (tool) {
                case BRUSH:
                case LINE:
                    break;
                case BEZIER:
                case POLYGON:
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (polygon.npoints == 0) {
                            polygon.addPoint(x, y);
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        if (tool == Tool.BEZIER) {
                            drawBezier(imageG2D, false);
                        } else if (tool == Tool.POLYGON) {
                            drawPolygon(imageG2D, false);
                        }
                        polygon.reset();
                    }
                    break;
                case OVAL:
                case RECTANGLE:
                    break;
                case AIRBRUSH:
                    drawAirBrush();
                    break;
                case PICK_COLOR:
                    colorPrimary = new Color(image.getRGB(x, y));
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
                    drawLine(imageG2D);
                    break;
                case BEZIER:
                case POLYGON:
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        polygon.addPoint(x, y);
                    }
                    break;
                case OVAL:
                    drawOval(imageG2D);
                    break;
                case RECTANGLE:
                    drawRectangle(imageG2D);
                    break;
                case AIRBRUSH:
                    drawAirBrush();
                    break;
                case PICK_COLOR:
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
                        imageG2D.setColor(colorPrimary);
                        imageG2D.drawLine(lastX, lastY, x, y);
                    }
                    lastX = x;
                    lastY = y;
                    break;
                case LINE:
                case BEZIER:
                case POLYGON:
                case OVAL:
                case RECTANGLE:
                    break;
                case AIRBRUSH:
                    drawAirBrush();
                    break;
                case PICK_COLOR:
                    break;
                case SIZE:
                    changeSize();
                    break;
                }
                MainPanel.this.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                final int x = e.getX();
                final int y = e.getY();
                if (0 <= x && x < image.getWidth()
                 && 0 <= y && y < image.getHeight()) {
                    parent.updateStatusBarCurrentPixel(x, y, image.getRGB(x, y));
                } else {
                    parent.updateStatusBarCurrentPixel(0, 0, 0);
                }
            }
        });
    }

    public void setParent(MainWindow parent) {
        this.parent = parent;
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
            drawLine(g2d);
            break;
        case BEZIER:
            drawBezier(g2d, true);
            break;
        case POLYGON:
            drawPolygon(g2d, true);
            break;
        case OVAL:
            drawOval(g2d);
            break;
        case RECTANGLE:
            drawRectangle(g2d);
            break;
        case AIRBRUSH:
        case SIZE:
            break;
        }
    }

    private void drawLine(Graphics2D g2d) {
        if (x < 0 || y < 0 || lastX < 0 || lastY < 0) {
            return;
        }
        g2d.setColor(colorPrimary);
        g2d.drawLine(lastX, lastY, x, y);
    }

    private void drawBezier(Graphics2D g2d, boolean includeCurrentPixel) {
        if (x < 0 || y < 0 || polygon.npoints < 1) {
            return;
        }
        Polygon polygon2;
        if (includeCurrentPixel) {
            polygon2 = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
            polygon2.addPoint(x, y);
        } else {
            polygon2 = polygon;
        }
        g2d.setColor(colorPrimary);
        int lastX = -1, lastY = -1;
        for (int i = 0; i < BEZIER_QUALITY; i++) {
            final double ratio = i / (double) BEZIER_QUALITY;

            double[] xcoords = new double[polygon2.npoints];
            for (int j = 0; j < xcoords.length; j++) {
                xcoords[j] = (double) polygon2.xpoints[j];
            }
            while (xcoords.length > 1) {
                xcoords = calcBezier(xcoords, ratio);
            }

            double[] ycoords = new double[polygon2.npoints];
            for (int j = 0; j < ycoords.length; j++) {
                ycoords[j] = (double) polygon2.ypoints[j];
            }
            while (ycoords.length > 1) {
                ycoords = calcBezier(ycoords, ratio);
            }

            final int x = (int) xcoords[0];
            final int y = (int) ycoords[0];
            if (i > 0) {
                g2d.drawLine(lastX, lastY, x, y);
            }
            lastX = x;
            lastY = y;
        }
    }

    private double[] calcBezier(double[] coords, double ratio) {
        double[] result = new double[coords.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = inBetween(coords[i], coords[i + 1], ratio);
        }
        return result;
    }

    private void drawPolygon(Graphics2D g2d, boolean includeCurrentPixel) {
        if (x < 0 || y < 0 || polygon.npoints < 1) {
            return;
        }
        Polygon polygon2;
        if (includeCurrentPixel) {
            polygon2 = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
            polygon2.addPoint(x, y);
        } else {
            polygon2 = polygon;
        }
        switch (fillStyle) {
        case NONE:
            g2d.setColor(colorPrimary);
            g2d.drawPolygon(polygon2);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillPolygon(polygon2);
            /* workaround: otherwise there would be nothing drawn for npoints <= 3 */
            g2d.drawPolygon(polygon2);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillPolygon(polygon2);
            g2d.setColor(colorPrimary);
            g2d.drawPolygon(polygon2);
            break;
        }
    }

    private void drawOval(Graphics2D g2d) {
        if (x < 0 || y < 0 || lastX < 0 || lastY < 0) {
            return;
        }
        final int fromX = Math.min(lastX, x);
        final int fromY = Math.min(lastY, y);
        final int toX = Math.max(lastX, x);
        final int toY = Math.max(lastY, y);
        final int width = toX - fromX;
        final int height = toY - fromY;
        switch (fillStyle) {
        case NONE:
            g2d.setColor(colorPrimary);
            g2d.drawOval(fromX, fromY, width, height);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillOval(fromX, fromY, width, height);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillOval(fromX, fromY, width, height);
            g2d.setColor(colorPrimary);
            g2d.drawOval(fromX, fromY, width, height);
            break;
        }
    }

    private void drawRectangle(Graphics2D g2d) {
        if (x < 0 || y < 0 || lastX < 0 || lastY < 0) {
            return;
        }
        final int fromX = Math.min(lastX, x);
        final int fromY = Math.min(lastY, y);
        final int toX = Math.max(lastX, x);
        final int toY = Math.max(lastY, y);
        final int width = toX - fromX;
        final int height = toY - fromY;
        switch (fillStyle) {
        case NONE:
            g2d.setColor(colorPrimary);
            g2d.drawRect(fromX, fromY, width, height);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillRect(fromX, fromY, width, height);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillRect(fromX, fromY, width, height);
            g2d.setColor(colorPrimary);
            g2d.drawRect(fromX, fromY, width, height);
            break;
        }
    }

    private void drawAirBrush() {
        if (x < 0 || y < 0) {
            return;
        }
        final double a = Math.random() * 2.0 * Math.PI;
        final double d = AIRBRUSH_SIZE * Math.random();
        final int lx = x + (int) (d * Math.sin(a));
        final int ly = y + (int) (d * Math.cos(a));
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            image.setRGB(lx, ly, colorPrimary.getRGB());
        }
    }

    private void changeSize() {
        if (x < 0 || y < 0) {
            return;
        }
        if (imageG2D != null) {
            imageG2D.dispose();
        }
        BufferedImage imageNew = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        imageG2D = (Graphics2D) imageNew.createGraphics();
        imageG2D.setColor(colorSecondary);
        imageG2D.fillRect(0, 0, imageNew.getWidth(), imageNew.getHeight());
        imageG2D.drawImage(image, 0, 0, null);
        image = imageNew;
        updatePanelSize();
    }

    public void newImage(int width, int height, Color fillColor) {
        if (imageG2D != null) {
            imageG2D.dispose();
        }
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        imageG2D = (Graphics2D) image.createGraphics();
        if (fillColor != null) {
            imageG2D.setColor(fillColor);
            imageG2D.fillRect(0, 0, width, height);
        }
        updatePanelSize();
        repaint();
    }

    public void openImage(BufferedImage image) {
        if (imageG2D != null) {
            imageG2D.dispose();
        }
        this.image = image;
        imageG2D = (Graphics2D) image.createGraphics();
        updatePanelSize();
        repaint();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void swapColors() {
        final Color colorPrimaryOld = colorPrimary;
        final Color colorSecondaryOld = colorSecondary;
        colorPrimary = colorSecondaryOld;
        colorSecondary = colorPrimaryOld;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setColorPrimary(Color colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public void setColorSecondary(Color colorSecondary) {
        this.colorSecondary = colorSecondary;
    }

    public void setFillStyle(FillStyle fillStyle) {
        this.fillStyle = fillStyle;
    }

    private void updatePanelSize() {
        final int width = image.getWidth();
        final int height = image.getHeight();
        Dimension d = new Dimension(width, height);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
        parent.updateStatusBarSize(width, height);
    }

    private static double inBetween(double a, double b, double ratio) {
        if (a < 0. || b < 0. || !(0. <= ratio && ratio <= 1.)) {
            throw new IllegalArgumentException();
        }
        final double invRatio = 1. - ratio;
        return invRatio * a + ratio * b;
    }

}
