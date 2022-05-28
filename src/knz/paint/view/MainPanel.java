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

import knz.paint.tools.AbstractSelectionTool;
import knz.paint.tools.AbstractTool;
import knz.paint.tools.ChangeCanvasSizeTool;
import knz.paint.tools.DrawAirbrushTool;
import knz.paint.tools.DrawBezierTool;
import knz.paint.tools.DrawBrushTool;
import knz.paint.tools.DrawFreeFormTool;
import knz.paint.tools.DrawLineTool;
import knz.paint.tools.DrawOvalTool;
import knz.paint.tools.DrawPolygonTool;
import knz.paint.tools.DrawRectangleTool;
import knz.paint.tools.FloodFillTool;
import knz.paint.tools.PickColorTool;
import knz.paint.tools.SelectFreeFormTool;
import knz.paint.tools.SelectPolygonTool;
import knz.paint.tools.SelectRectangleTool;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

    public enum Tool {
        // FIXME move these two properties to the specific Tool classes
        BRUSH("Brush", "tool_7.png"),
        AIRBRUSH("Airbrush", "tool_8.png"),
        FLOOD_FILL("Flood fill", "tool_3.png"),
        LINE("Line", "tool_10.png"),
        BEZIER("Bézier", "tool_11.png"),
        FREE_FORM("Free-form", "tool_0b.png"),
        POLYGON("Polygon", "tool_13.png"),
        OVAL("Oval", "tool_14.png"),
        RECTANGLE("Rectangle", "tool_12.png"),
        SELECT_FREE_FORM("Select free-form", "tool_0.png"),
        SELECT_POLYGON("SP", ""), // FIXME "Select polygon", add icon
        SELECT_RECTANGLE("Select rectangle", "tool_1.png"),
        PICK_COLOR("Pick color", "tool_4.png"),
        SIZE("S", ""), // FIXME "Size", add icon

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

    private MainWindow parent;

    private AbstractTool[] toolClasses = new AbstractTool[Tool.values().length];

    private BufferedImage image;
    // FIXME rename
    private Graphics2D imageG2D;

    // View
    private int zoomLevel = 1;
    // Options
    // FIXME these two should be one
    private Tool tool = Tool.BRUSH;
    private AbstractTool toolClass;
    private Color colorPrimary = Color.BLACK;
    private Color colorSecondary = Color.WHITE;
    private FillStyle fillStyle = FillStyle.NONE;

    public MainPanel() {
        super();
        // FIXME ugly code
        toolClasses[0] = new DrawBrushTool(this);
        toolClasses[1] = new DrawAirbrushTool(this);
        toolClasses[2] = new FloodFillTool(this);
        toolClasses[3] = new DrawLineTool(this);
        toolClasses[4] = new DrawBezierTool(this);
        toolClasses[5] = new DrawFreeFormTool(this);
        toolClasses[6] = new DrawPolygonTool(this);
        toolClasses[7] = new DrawOvalTool(this);
        toolClasses[8] = new DrawRectangleTool(this);
        toolClasses[9] = new SelectFreeFormTool(this);
        toolClasses[10] = new SelectPolygonTool(this);
        toolClasses[11] = new SelectRectangleTool(this);
        toolClasses[12] = new PickColorTool(this);
        toolClasses[13] = new ChangeCanvasSizeTool(this);
        toolClass = toolClasses[0];
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toolClass.mousePressed(imageG2D, e);
                MainPanel.this.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                toolClass.mouseReleased(imageG2D, e);
                MainPanel.this.repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                toolClass.mouseDragged(imageG2D, e);
                MainPanel.this.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                final int x = e.getX() / zoomLevel;
                final int y = e.getY() / zoomLevel;
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
        BufferedImage canvas = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        // FIXME rename
        Graphics2D canvasG2D = canvas.createGraphics();
        toolClass.paint(canvasG2D);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(image, 0, 0, zoomLevel * image.getWidth(), zoomLevel * image.getHeight(), null);
        g2d.drawImage(canvas, 0, 0, zoomLevel * canvas.getWidth(), zoomLevel * canvas.getHeight(), null);
    }

    // File

    public BufferedImage getImage() {
        return image;
    }

    public void setImageAndGraphics(BufferedImage image, Graphics2D g2d) {
        if (imageG2D != null) {
            imageG2D.dispose();
        }
        this.image = image;
        this.imageG2D = g2d;
    }

    public void newImage(int width, int height, Color fillColor) {
        if (imageG2D != null) {
            imageG2D.dispose();
        }
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        imageG2D = image.createGraphics();
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
        imageG2D = image.createGraphics();
        updatePanelSize();
        repaint();
    }

    // Edit

    public void clearSelection() {
        if (toolClass instanceof AbstractSelectionTool) {
            AbstractSelectionTool ast = (AbstractSelectionTool) toolClass;
            ast.clearSelection(imageG2D);
            repaint();
        } else {
            // FIXME error: you need to select smt first!
        }
    }

    // View

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
        updatePanelSize();
        repaint();
    }

    // Options

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        // FIXME ugly code
        this.tool = tool;
        this.toolClass = toolClasses[tool.ordinal()];
    }

    public void swapColors() {
        final Color colorPrimaryOld = colorPrimary;
        final Color colorSecondaryOld = colorSecondary;
        colorPrimary = colorSecondaryOld;
        colorSecondary = colorPrimaryOld;
    }

    public Color getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(Color colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public Color getColorSecondary() {
        return colorSecondary;
    }

    public void setColorSecondary(Color colorSecondary) {
        this.colorSecondary = colorSecondary;
    }

    public FillStyle getFillStyle() {
        return fillStyle;
    }

    public void setFillStyle(FillStyle fillStyle) {
        this.fillStyle = fillStyle;
    }

    // Misc

    public void updatePanelSize() {
        final int width = image.getWidth();
        final int height = image.getHeight();
        Dimension d = new Dimension(zoomLevel * width, zoomLevel * height);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
        parent.updateStatusBarSize(width, height);
    }

}
